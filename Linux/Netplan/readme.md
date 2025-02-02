# Converting a Linux Server from NetworkManager to Netplan

## Overview

In our production environment, we encountered issues where interface names changed unexpectedly, breaking network configurations. To resolve this, we migrated all servers from NetworkManager to Netplan, ensuring a stable and predictable network setup.

This guide provides a walkthrough of the conversion process, including the necessary commands and an automated script to streamline the migration.

## Steps to Convert a Server to Netplan

### 1. Identify the Active Network Interface

To determine which interface is currently in use, run:

`ip addr | grep "mq state UP"`

### 2. Gather Interface Information

Retrieve essential network details:

~~~
# Get the active interface name
ACTIVE_INTERFACE=$(ip addr | grep "mq state UP" | awk -F: '{sub(/^ +/, "", $2); print $2}')

# Get the MAC address of the interface
MAC_ADDRESS=$(ip addr show dev $ACTIVE_INTERFACE | grep "ether" | awk '{print $2}')

# Get the IPv4 and IPv6 addresses
IPV4_ADDRESS=$(ip addr show dev $ACTIVE_INTERFACE | grep -w "inet" | awk '{print $2}')
IPV6_ADDRESS=$(ip addr show dev $ACTIVE_INTERFACE | grep -w "inet6" | awk 'NR==1 {print $2; exit}')

# Get the default gateway
IPV4_GATEWAY=$(ip route | grep "default" | awk '{print $3}')
~~~

### 3. Disable NetworkManager and Enable Systemd-Networkd

~~~
sudo systemctl disable NetworkManager
sudo systemctl enable --now systemd-networkd
~~~

### 4. Create a Netplan Configuration File

Replace ens4f0 with your actual interface name if needed:

~~~
network:
  version: 2
  renderer: networkd
  ethernets:
    ens4f0:
      match:
        macaddress: $MAC_ADDRESS
      set-name: ens4f0
      dhcp4: no
      dhcp6: no
      addresses: [$IPV4_ADDRESS, "$IPV6_ADDRESS"]
      routes:
        - to: 0.0.0.0/0
          via: ${IPV4_GATEWAY}
      nameservers:
        addresses: [8.8.8.8, 8.8.4.4]
~~~

Save this file as `/etc/netplan/01-network-manager-all.yaml.`

### 5. Apply the New Netplan Configuration

`sudo netplan apply`

### 6. Stop and disabel NetworkManager

`sudo systemctl disable --now NetworkManager`

### Automating the Process

To simplify the conversion, I combined all of the above into the following Bash script:

Script:
~~~
#!/bin/bash
# This script grabs the needed info to 
# convert a linux server to netplan from network manager
# Written by: Kelly Cantrell

# VARIABLES
# Get the name of the interface currently in use
ACTIVE_INTERFACE=$(ip addr | grep "mq state UP" | awk -F: '{sub(/^ +/, "", $2); print $2}')

# Get the macaddress of the interface
MAC_ADDRESS=$(ip addr show dev $ACTIVE_INTERFACE | grep "ether" | awk '{print $2}')

# Get the associated IP addresses
IPV4_ADDRESS=$(ip addr show dev $ACTIVE_INTERFACE | grep -w "inet" | awk '{print $2}')
IPV6_ADDRESS=$(ip addr show dev $ACTIVE_INTERFACE | grep -w "inet6" | awk 'NR==1 {print $2; exit}')

# Get the default gateway
IPV4_GATEWAY=$(ip route | grep "default" | awk '{print $3}')


# NETPLAN IMPLEMENTATION
# Echo the config file for netplan into sudo tee to write it to the file
echo "network:
  version: 2
  renderer: networkd
  ethernets:
    ens4f0:
      match:
        macaddress: $MAC_ADDRESS
      set-name: ens4f0
      dhcp4: no
      dhcp6: no
      addresses: [$IPV4_ADDRESS, \"$IPV6_ADDRESS\"]
      routes:
        - to: 0.0.0.0/0
          via: ${IPV4_GATEWAY}
      nameservers:
        addresses: [8.8.8.8, 8.8.4.4]" | sudo tee /etc/netplan/01-network-manager-all.yaml 1>/dev/null
    
sudo systemctl disable NetworkManager
sudo systemctl enable --now systemd-netword
sudo netplan apply
sudo systemctl stop NetworkManager
~~~

## Conclusion

Migrating from NetworkManager to Netplan provides better stability in environments where interface names may change unpredictably. This guide and script help automate the process, reducing downtime and misconfiguration risks.

For any issues, verify network settings with:

~~~
ip addr show
ip route
cat /etc/netplan/01-network-manager-all.yaml
~~~

---

Thanks for checking out this project!  
