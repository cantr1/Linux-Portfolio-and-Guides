# Chroot Jail Setup

## Overview

This project provides a guide for setting up a chroot jail for SSH users. In this case, I was working on building a jumper server for outside clients to access an internal server.

Chroot jails are useful for restricting users to a specific directory, improving security by isolating processes and limiting access to system resources. This is a common practice in Linux administration to enhance system security.

## Features

- Creates a chroot jail for an SSH user

- Restricts the user to a specific environment

- Ensures required binaries and dependencies are available inside the jail

- Configures SSH to enforce chroot jail

## Prerequisites

- A Linux-based system (Ubuntu, Red Hat, or CentOS preferred)

- Root or sudo access

- SSH installed and configured

- Basic understanding of Linux file systems and permissions

## Setup Steps

**1. Create the SSH User**

`sudo useradd -m -s /bin/bash -G Jumper ssh-user && echo "ssh-user:password" | sudo chpasswd`

This creates an SSH user and sets their password. Ensure that the proper group is also on the system before executing the command.

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Chroot%20Jail/Images/8.png)

**2. Create Jail Directory Structure**
```
sudo mkdir -p /home/Jails/dev
sudo mkdir -p /home/Jails/home
sudo mkdir -p /home/Jails/etc
sudo mkdir -p /home/Jails/bin
sudo mkdir -p /home/Jails/lib64
sudo mkdir -p /home/Jails/lib
sudo mkdir -p /home/Jails/usr
sudo mkdir -p /home/Jails/usr/bin
```
This sets up the necessary directories inside the jail.

**3. Create Special Device Files**
```
sudo mknod -m 666 /home/Jails/dev/null c 1 3
sudo mknod -m 666 /home/Jails/dev/tty c 5 0
sudo mknod -m 666 /home/Jails/dev/zero c 1 5
sudo mknod -m 666 /home/Jails/dev/random c 1 8
```
These device files are required for the jailed environment to function correctly.

**4. Set Permissions**
```
sudo chown root:root /home/Jails
sudo chmod 0755 /home/Jails
```
This ensures that the jail is owned by root and has the correct permissions.

**5. Copy Necessary Binaries**
```
sudo cp /bin/bash /home/Jails/bin/
sudo cp /usr/bin/bash /home/Jails/usr/bin/
```
These binaries are needed for the jailed user to interact with the shell.

**6. Bind Mount System Libraries**
```
sudo mount --bind /lib /home/Jails/lib
sudo mount --bind /lib64 /home/Jails/lib64
```
To make these mounts persistent, add the following lines to /etc/fstab:
```
/lib /home/Jails/lib none bind 0 0
/lib64 /home/Jails/lib64 none bind 0 0
```
**7. Link System Files**
```
sudo ln -s /etc/passwd /home/Jails/etc/passwd
sudo ln -s /etc/group /home/Jails/etc/group
```
This ensures that user and group information is accessible within the jail.

**8. Configure SSH to Enforce the Jail**
```
echo "
# Define username to apply chroot jail to
Match User ssh-user
# Specify chroot jail
ChrootDirectory /home/Jails" | sudo tee -a /etc/ssh/sshd_config
```

The output should look like the following if executed properly:

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Chroot%20Jail/Images/6.png)

Restart SSH to apply changes:

`sudo systemctl restart ssh`

**9. Copy Essential Utilities**
```
sudo cp -v /usr/bin/ssh /home/Jails/usr/bin/
sudo cp -v /usr/bin/telnet /home/Jails/usr/bin/
sudo cp -v /usr/bin/cat /home/Jails/usr/bin/
sudo cp -v /usr/bin/less /home/Jails/usr/bin/
sudo cp -v /usr/bin/ls /home/Jails/usr/bin/
```
This ensures that the user has access to common commands within the jail.

**10. Adjust User File Ownership**
```
sudo chown ssh-user:ssh-user /home/ssh-user
sudo chown ssh-user:ssh-user /home/ssh-user/.bash_logout
sudo chown ssh-user:ssh-user /home/ssh-user/.bashrc
sudo chown ssh-user:ssh-user /home/ssh-user/.profile
```
## Verification

To test the chroot jail setup:

Try logging in as ssh-user via SSH.

Run ls / inside the jail. The user should only see the jailed directory structure.

Attempt to access files outside /home/Jails. Access should be restricted.

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Chroot%20Jail/Images/10.png)

## Best Practices

- Ensure only necessary binaries and libraries are included in the jail.

- Restrict user access to minimize privilege escalation risks.

- Regularly audit the jail environment for security vulnerabilities.

---

Thanks for checking this out! Let me know if you have any questions or suggestions!
