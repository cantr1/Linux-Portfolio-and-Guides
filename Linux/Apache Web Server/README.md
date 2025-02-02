# Apache Web Server Setup

## Overview

This project provides a structured guide for setting up and securing an Apache web server. This is an essential task for Linux administrators, as web servers form the backbone of many enterprise environments. By following this guide, you will configure a web server, apply security measures, and deploy a simple website.

## Features

- Installation and configuration of Apache

- Securing the server with firewall rules and permissions

- Setting up a basic HTML website

## Prerequisites

- A Linux-based system (Ubuntu preferred)

- Basic understanding of Linux system administration

- Internet access to download required packages

## Install Apache

For this project, I will be using Ubuntu as the server operating system. The installation process is straightforward using the package manager.

Ensure the system is up to date

`sudo apt update && sudo apt upgrade -y`

Now install Apache

`sudo apt install -y apache2`

![](https://github.com/cantr1/ProfessionalPortfolio/blob/main/Apache%20Web%20Server/Images/install-apache.PNG)


Verify Apache is running

`sudo systemctl status apache2`

![](https://github.com/cantr1/ProfessionalPortfolio/blob/main/Apache%20Web%20Server/Images/apache-running.PNG)

You can also verify by navigating to http://<server-ip> in a browser.

![](https://github.com/cantr1/ProfessionalPortfolio/blob/main/Apache%20Web%20Server/Images/default-page.PNG)

## Secure the Server

Set the hostname and update the hosts file

`sudo hostnamectl set-hostname mywebserver`

`sudo nano /etc/hosts`
   
![Complete](https://github.com/cantr1/ProfessionalPortfolio/blob/main/Apache%20Web%20Server/Images/hosts.PNG)


Disable root login over SSH

`sudo nano /etc/ssh/sshd_config`

![Disable root SSH login](https://github.com/cantr1/ProfessionalPortfolio/blob/main/Apache%20Web%20Server/Images/permit-root.PNG)

Set PermitRootLogin no and restart SSH:

`sudo systemctl restart sshd`

This disables root login but still allows for remote administration by users. Individuals on Windows systems can use PowerShell to remote into the server via SSH.

![Disable root SSH login](https://github.com/cantr1/ProfessionalPortfolio/blob/main/Apache%20Web%20Server/Images/powershell-ssh.PNG)



## Configure firewall rules
~~~
sudo ufw allow 'Apache Full'
sudo ufw allow OpenSSH
sudo ufw enable
sudo ufw status
~~~

![](https://github.com/cantr1/ProfessionalPortfolio/blob/main/Apache%20Web%20Server/Images/firewall.PNG)

## Configure the Website

Create the directory structure
~~~
sudo mkdir -p /var/www/mywebserver.net
sudo mkdir -p /var/www/mywebserver.net/public_html
sudo mkdir -p /var/www/mywebserver.net/log
sudo mkdir -p /var/www/mywebserver.net/backups

~~~
![Disable root SSH login](https://github.com/cantr1/ProfessionalPortfolio/blob/main/Apache%20Web%20Server/Images/set-up-directories.PNG)

Set permissions

`sudo chown -R $USER:$USER /var/www/mywebsite/public_html`

`sudo chmod -R 755 /var/www`

Create an Apache virtual host file

`sudo nano /etc/apache2/sites-available/mywebserver.net.conf`

![Disable root SSH login](https://github.com/cantr1/ProfessionalPortfolio/blob/main/Apache%20Web%20Server/Images/set-up-config.PNG)

Here is a sample configuration and implementation:

~~~
<VirtualHost *:80>
    ServerAdmin webmaster@mywebsite.com
    ServerName mywebsite.com
    ServerAlias www.mywebsite.com
    DocumentRoot /var/www/mywebsite/public_html
    ErrorLog ${APACHE_LOG_DIR}/error.log
    CustomLog ${APACHE_LOG_DIR}/access.log combined
</VirtualHost>
~~~

![HTML content creation](https://github.com/cantr1/ProfessionalPortfolio/blob/main/Apache%20Web%20Server/Images/web-server-conf.PNG)


## Enable the new site and reload Apache

`sudo a2ensite mywebsite.conf`

`sudo systemctl reload apache2`

Add sample HTML content

![HTML content creation](https://github.com/cantr1/ProfessionalPortfolio/blob/main/Apache%20Web%20Server/Images/set-up-html.PNG)

![HTML content creation](https://github.com/cantr1/ProfessionalPortfolio/blob/main/Apache%20Web%20Server/Images/add-some-html.PNG)
   

## Verify and Finalize

We can now navigate to the site from the web browser!

![Complete](https://github.com/cantr1/ProfessionalPortfolio/blob/main/Apache%20Web%20Server/Images/web-sever-complete.PNG)

## Additional Security

Disable the default Apache page for security

`sudo a2dissite 000-default.conf`

`sudo systemctl reload apache2`

![](https://github.com/cantr1/ProfessionalPortfolio/blob/main/Apache%20Web%20Server/Images/disable-default.PNG)



Future Improvements

- Implement SSL/TLS for HTTPS security

- Set up automatic backups and monitoring

- Optimize Apache performance with tuning configurations

Thanks for checking this out!
