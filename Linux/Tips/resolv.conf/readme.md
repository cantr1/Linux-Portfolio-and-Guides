# Managing DNS Configuration with systemd-resolved
## Overview
This project demonstrates how to correctly configure DNS settings on a Linux system that uses systemd-resolved. By default, systemd-resolved dynamically manages /etc/resolv.conf, often pointing to a local stub resolver (127.0.0.53). This can cause unexpected behavior if you want to manually specify DNS servers. The guide explains how to ensure your actual DNS settings persist correctly using Netplan.

## Features
- Ensure system-wide DNS resolution reflects intended nameserver settings
- Configure Netplan to specify external DNS servers
- Understand the interaction between systemd-resolved and /etc/resolv.conf

## Prerequisites
- A Linux system using systemd-resolved (e.g., Ubuntu 20.04+)
- Basic knowledge of networking and the command line
- Permissions to modify system configuration files

## Implementation

**1. Verify Current DNS Configuration**
Check the contents of /etc/resolv.conf to determine the current setup:

`cat /etc/resolv.conf`

By default, it may look like this:

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Tips/resolv.conf/Images/1.png)

This shows that DNS resolution is being handled by systemd-resolved with a stub resolver (127.0.0.53), which may not reflect your actual nameservers.

**2. Configure DNS in Netplan**
To specify custom DNS servers, modify your Netplan configuration file. Open the relevant YAML file under /etc/netplan/:

`sudo nano /etc/netplan/01-network-manager-all.yaml`

Add or modify the nameservers section:

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Tips/resolv.conf/Images/2.png)

Save the file and apply changes:

`sudo netplan apply`

**3. Confirm Changes**

After applying Netplan, you will need to create a symlink to the appropriate file that manages the systemd-resolved service and reload the service:

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Tips/resolv.conf/Images/3.png)

It should now display your specified DNS servers instead of 127.0.0.53:

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Tips/resolv.conf/Images/4.png)

You also may need to edit /etc/gai.conf to disable resolution with IPv6:

`sudo vi /etc/gai.conf`

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Tips/resolv.conf/Images/5.png)

Additionally, verify DNS settings with:

`resolvectl status`

And always make sure that DNS is working properly:

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Tips/resolv.conf/Images/6.png)

## Best Practices
- Avoid directly modifying /etc/resolv.conf, as systemd-resolved regenerates it
- Use Netplan or resolvectl for persistent DNS configuration
- Verify DNS resolution with nslookup or dig after making changes

## Future Improvements
- Automate DNS configuration using Ansible or Bash scripts
- Explore alternative resolvers like dnsmasq for advanced setups
- Implement split-DNS for managing internal vs. external queries
