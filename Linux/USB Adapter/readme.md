# Automating USB/RJ45 Adapter Setup for DMZ

## Overview

In environments where a DMZ is needed for external customers, setting up a dedicated network interface over a USB/RJ45 adapter was necessary to allow connection. This guide provides a step-by-step approach and an automation script to configure the interface with Netplan.

## Steps to Configure the USB/RJ45 Adapter

### 1. Identify the USB Network Interface

To find the interface name, run:

`ip addr | grep "enx00"`

### 2. Retrieve Interface Details

~~~
# Get the interface name
target_interface=$(ip addr | grep "enx00" | awk -F: '{sub(/^ +/, "", $2); print $2}')

# Get the MAC address of the interface
interface_mac=$(ip addr show dev $target_interface | grep "ether" | awk '{print $2}')
~~~

### 3. Append the Configuration to Netplan

Modify /etc/netplan/01-network-manager-all.yaml to include the new interface:

~~~
ens3f0:
  match:
    macaddress: $interface_mac
  set-name: ens3f0
  dhcp4: no
  dhcp6: no
  addresses:
  routes:
    - to: 0.0.0.0/0
      via: 192.168.1.1
  nameservers:
    addresses: [8.8.8.8, 8.8.4.4]
~~~

### 4. Apply Netplan Configuration

`sudo netplan apply`

## Automating the Process

To automate the setup, I created the following Bash script:

~~~
#!/bin/bash
# This script configures a USB/RJ45 adapter for DMZ using Netplan
# Written by: Kelly Cantrell

# VARIABLES
usb_interface=$(ip addr | grep "enx00" | awk -F: '{sub(/^ +/, "", $2); print $2}')
usb_mac=$(ip addr show dev $usb_interface | grep "ether" | awk '{print $2}')

# Append the Netplan Configuration
echo "
    ens3f0:
      match:
        macaddress: $usb_mac
      set-name: ens3f0
      dhcp4: no
      dhcp6: no
      addresses:
      routes:
        - to: 0.0.0.0/0
          via: 192.168.1.1
      nameservers:
        addresses: [8.8.8.8, 8.8.4.4]" | sudo tee -a /etc/netplan/01-network-manager-all.yaml 1>/dev/null

# Apply Netplan
sudo netplan apply
~~~

### Usage:

Save the script as setup-usb-dmz.sh, make it executable, and run:

~~~
chmod +x setup-usb-dmz.sh
sudo ./setup-usb-dmz.sh
~~~

## Conclusion

Automating the setup of a USB/RJ45 adapter for a DMZ reduces configuration time and minimizes errors. If you encounter issues, verify the network settings with:

~~~
ip addr show
ip route
cat /etc/netplan/01-network-manager-all.yaml
~~~

---
Thanks for checking out this project! Be sure to check the rest of this repo for other cool stuff!

