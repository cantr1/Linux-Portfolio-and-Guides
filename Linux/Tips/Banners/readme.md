# Linux Login Banner Guide

## Overview

This guide covers how to customize the login experience on a Linux system by modifying SSH banners and disabling unwanted login messages. A custom banner can personalize your system and provide useful system information upon SSH login.

## Features

- Set a custom SSH login banner

- Disable MOTD and last login messages

- Remove PAM authentication success messages

- Transfer banner files between systems using SCP

## Prerequisites

- A Linux-based system

- Root or sudo access

- Basic knowledge of editing system configuration files

## Implementation

**1. Disable Default MOTD and Last Login Messages**

Edit the SSH daemon configuration file:

`sudo nano /etc/ssh/sshd_config`

Find and modify these lines:

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Tips/Banners/Images/3.png)

Save and exit.

**2. Remove PAM Success Messages**

Edit the PAM SSH authentication file:

`sudo nano /etc/pam.d/sshd`

Comment out or remove the following line to prevent additional messages:

`session optional pam_motd.so motd=/run/motd.dynamic`

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Tips/Banners/Images/2.png)

Save and exit.

**3. Set a Custom SSH Banner**

Specify a custom banner file in the SSH configuration:

`sudo nano /etc/ssh/sshd_config`

Add or modify this line:

`Banner /etc/motd.d/your-banner.txt`

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Tips/Banners/Images/4.png)

Now create the banner file:

`sudo nano /etc/motd.d/your-banner.txt`

Add your custom ASCII text or message. Example:
```
########################################
#                                      #
#  Welcome to Your Secure Linux Box!   #
#                                      #
########################################
```
Save and exit.

**4. Restart SSH Service**

Apply changes by restarting SSH:

`sudo systemctl restart sshd`

**5. Transfer Banner Files via SCP**

For my project, I found it easier to create the text file on my Windows machine and copy it to my Linux machine. If you are not familiar with how to do this, you use the SCP utitility, a crucial skill to have!

To copy the banner file from a Windows machine to the Linux VM:

`scp banner.txt user@linux-vm:/etc/motd.d/com-banner.txt`

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Tips/Banners/Images/1.png)

Adjust the port and username as needed.

## New Banner

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Tips/Banners/Images/5.png)

## Best Practices

- Use concise banners for readability

- Avoid exposing sensitive information in SSH banners

- Keep SSH configurations backed up before making changes

## Future Improvements

- Automate banner setup using a script

- Dynamically display system info (uptime, disk usage) in the banner

---
Check out the rest of this repo for more cool tricks!
