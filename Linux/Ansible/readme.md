# Ansible Setup

## Overview

This project provides a structured guide for setting up and using Ansible for automation. This is a key task for Linux administrators as we often have to provide updates and or packages to dozens if not hundreds of systems. Ansible provides a framework for us to do exactly this in a way this easy to implement once you understand how playbooks work.

## Features

- Installation and configuration of Ansible

- Creating and managing inventory files

- Writing and running Ansible playbooks

## Prerequisites

- A Linux-based system (Ubuntu, Red Hat, or CentOS preferred)

- Python 3 installed

- SSH access to managed nodes

- Basic understanding of Linux administration

--- 

## Install Ansible

For this project, I will be using and RedHat system as my ansible host and a Ubuntu system as the client. Both machines are being used via VirtualBox since I'm not made of money and can afford two seperate machines. This seemed wise to do as most enterprise environments are using RedHat and Ubuntu. Plus, virtualization becomes a more important feature of Linux administration every day!

RedHat System:

![RedHat](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Ansible/Images/1.png)

Ubuntu System:

![RedHat](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Ansible/Images/4.png)

Ansible is an agentless service, which means that it only needs to be installed on the server machine and not the client.
To do so on our RedHat machine, we run the following:

`sudo dnf install -y ansible`

---

## Usage

To apply changes to the client, we need to establish an SSH key on our server. This allows the RedHat machine to remote into the client and run the processes we outline in our playbook.

First, we generate the SSH key:

`ssh-keygen -t rsa -b 2048`

![RedHat](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Ansible/Images/5.png)

We then copy this SSH key to the client. In this case, I used my account that I set up on the Ubuntu machine but generally one would set up an account exclusively for Ansible in an enterprise environment. It is important to secure this account as it needs the privelages of an administrator to work as we want it to.

`ssh-copy-id kelz@192.168.1.89`

![RedHat](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Ansible/Images/6.png)

We can verify that this process worked as intended by trying to SSH to the client from the host. If done properly, we should be able to login without entering a password.

![RedHat](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Ansible/Images/7.png)

We are now able to push updates, packages, and or scripts to our client. The next step to do this is to define an inventory file (/etc/ansible/inventory.ini). This essentially tells Ansible where its targets are.

In our file, we will include a name for the host and the IP address of the client. We can also create an overarching hierarchy to segment servers with different purposes, like webservers in this example.

![RedHat](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Ansible/Images/8.png)

Ansible has a ping function (it works a little different than the standard ping) and this allows us to check our connection to the client.

![RedHat](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Ansible/Images/3.png)

We are now approaching the finish line! The real bread and butter of Ansible is playbooks, YAML files that take arguments and apply a process to the client. For this example, we will install the webserver package Apache.

Example Playbook
~~~
- hosts: webservers
  become: yes
  tasks:
    - name: Ensure Apache is installed
      apt:
        name: apache2
        state: present
~~~

![RedHat](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Ansible/Images/10.png)

Finally, we run the playbook:

`ansible-playbook install_apache2.yml --ask-become-pass`

![RedHat](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Ansible/Images/11.png)

Lastly, we verify that the playbook ran as expected by checking the status of Apache on the client.

![RedHat](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Ansible/Images/12.png)

---

And with that, we now have a firm grasp of the basics with Ansible. There is still so much that can be done and the playbooks can become quite complicated if you want to apply major changes, but this is the foundation upon which those more complex tasks are built upon.

Some best practices:

- Use roles to modularize playbooks

- Leverage variables and templates for flexibility

- Secure sensitive data using Ansible Vault

- Test playbooks in a staging environment before production

Check out my other project which goes into how to setup an Apache server! These two projects show a pretty standard workflow for a Linux admin.

[Apache WebServer](https://github.com/cantr1/Linux-Portfolio-and-Guides/tree/main/Linux/Apache%20Web%20Server)

Thanks for checking this out!
