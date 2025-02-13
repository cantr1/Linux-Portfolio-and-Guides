# Linux Auditd Setup Guide

## Overview

This project provides a structured guide for setting up and using auditd (Linux Audit Daemon) for monitoring and logging system activities. This is essential for Linux administrators who need to track security events and system changes.

## Features

- Installation and configuration of auditd

- Setting up monitoring rules for security-critical files and actions

- Viewing and analyzing audit logs

- Automating reports and monitoring user activity

## Prerequisites

- A Linux-based system (Ubuntu preferred)

- Basic understanding of Linux system administration

- Root or sudo privileges

## Install and Enable Auditd

**1. Install auditd**

`sudo apt install auditd -y`

**2. Enable and Start the Service**
```
sudo systemctl enable --now auditd
sudo systemctl status auditd
```
**3. Verify auditd is Running**

`sudo auditctl -s`

Expected output should include enabled 1 indicating that auditd is active.

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Auditd/Images/1.png)

## Setting Up Audit Rules

Audit rules can be temporary (via auditctl) or permanent (via /etc/audit/rules.d/).

**1. Create an Audit Rule (Temporary)**

Monitor changes to /etc/passwd:

`sudo auditctl -w /etc/passwd -p wa -k passwd_changes`

**2. Create a Permanent Rule**

You can also make this rule permanent by entering rules in the same format into a custom file created in the rules directory.

Open a new file in /etc/audit/rules.d/ (I am using emacs here as an opportunity to learn a new editor):

`sudo emacs /etc/audit/rules.d/custom.rules`

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Auditd/Images/2.png)

Explanation of flags:

- w → Watch file/directory

- p → Permissions (read, write, execute, attribute alteration)

- k → Custom key (used for filtering logs)

**3. Reload All Rules**

`sudo augenrules --load`

## Viewing Audit Logs

**1. Search Logs by Custom Key**

`sudo ausearch -k failed_logins`

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Auditd/Images/3.png)

**2. View Recent Logs in Real-Time**

tail -f /var/log/audit/audit.log

**3. Generate Reports**

Audit logs can be converted into structured reports:

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Auditd/Images/4.png)

For more detailed reports, here are some added options:
```
sudo aureport -au       # Authentication attempts
sudo aureport -x        # Executed commands
sudo aureport -f        # File access attempts
sudo aureport -l        # Logins and logouts
sudo aureport -u        # User activity
sudo aureport --start today --summary  # Daily summary
```
To view autentication attempts:

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Auditd/Images/5.png)

To view filesystem access:

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Auditd/Images/6.png)

## Future Improvements

- Implement log forwarding and centralized logging with rsyslog

- Automate periodic audit reports with cron jobs

- Integrate with SIEM tools for advanced analysis

---

Thanks for checking out this project!
