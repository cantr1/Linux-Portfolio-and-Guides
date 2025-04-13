# Rundeck Automation Guide
## Overview
This guide covers the setup of a basic Rundeck instance to automate tasks across multiple Linux virtual machines. It walks through installation, configuration, creating a project, and integrating Ansible for node management.

## Features
- Install and configure Rundeck on Ubuntu

- Create administrative users

- Set Rundeck to listen on a custom IP address

- Build a project for VM automation

- Integrate Ansible inventories into Rundeck

- Configure SSH access for automation

## Prerequisites
- Ubuntu-based system (22.04 or later recommended)

- Java 11 installed

- SSH access to target machines

- Ansible installed and configured

## Implementation
### 1. Install Java

Rundeck requires Java. Verify installation:

`java -version`

Result:

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Rundeck/Images/1.png)


### 2. Install Rundeck Repository

Set up the Rundeck package repository:

`curl https://raw.githubusercontent.com/rundeck/packaging/main/scripts/deb-setup.sh 2> /dev/null | sudo bash -s rundeck`

Result:

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Rundeck/Images/2.png)


### 3. Start Rundeck

Reload systemd and start the Rundeck service:
```
sudo systemctl daemon-reload
sudo service rundeckd start
```
Monitor the service log:

`tail -f /var/log/rundeck/service.log`

Result:

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Rundeck/Images/3.png)

Rundeck will now be available via the host IP and port 4440:

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Rundeck/Images/4.png)

Here is a basic overview of the directory structure for Rundeck after our initial setup:

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Rundeck/Images/5.png)


### 4. Configure Default User

Edit /etc/rundeck/realm.properties to define the default admin credentials:

`admin:admin,user,admin,architect,deploy,build`

Result:

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Rundeck/Images/6.png)


### 5. Configure Rundeck Server URL

Edit /etc/rundeck/rundeck-config.properties:

`grails.serverURL=http://localhost:4440`

Later, update it to your server's IP for external access.

Result:

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Rundeck/Images/7.png)


Edit /etc/rundeck/framework.properties to set IP address and port:

```
framework.server.name = 192.168.1.95
framework.server.hostname = 192.168.1.95
framework.server.port = 4440
framework.server.url = http://192.168.1.95:4440
```

Result:

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Rundeck/Images/8.png)


### 6. Create a Rundeck Project

Once logged into the GUI, create a new project.

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Rundeck/Images/9.png)

Example:

- Project Name: Ops-Automation

- Display Name: Home Lab Automation

- Description: Automates tasks across VMs

Result:

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Rundeck/Images/10.png)


### 7. Add Ansible Node Source

In your project settings:

- Add Ansible Resource Model Source

- Set paths for Ansible inventory and configuration

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Rundeck/Images/11.png)

Example:

- Ansible inventory File path: /etc/ansible/hosts
- Ansible config file path: /etc/ansible/ansible.log
  
Result:

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Rundeck/Images/13.png)

**Be sure to have the inventory file in the standard ansible format, like so:**

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Rundeck/Images/12.png)

Yuo will then be able to see the node:

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Rundeck/Images/14.png)


### 8. Configure Authentication

Set your Ansible SSH authentication options:

User: skynet

Provide a password or an SSH key. Then make sure that the key is pushed to the remote host to allow for passwordless authentication.

Result:

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Rundeck/Images/15.png)


9. Set Executable

Define the executable for remote shell:

- /bin/bash
- Check the "Generate inventory" option if needed.

Result:

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Rundeck/Images/17.png)


## Best Practices
- Secure Rundeck with SSL for production environments

- Use SSH key authentication instead of passwords

- Integrate dynamic Ansible inventories for scalability

- Regularly back up Rundeck projects and job definitions

## Note
I actually had better success with using SSH as the default node executor and storing the password as a key in Rundeck. 

## Future Improvements
Migrate from H2 database to PostgreSQL for stability

Enable LDAP authentication

Integrate webhook triggers for automation

Set up project export/import for disaster recovery

---

Check out the rest of this repo for more Linux! 
