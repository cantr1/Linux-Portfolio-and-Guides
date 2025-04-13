# Docker + Rundeck Container Automation Lab
## Overview
This project simulates setting up a Docker container to act as a managed node for Rundeck.
The goal was to prepare for an upcoming work project that will involve automating tasks inside containerized environments.

This project helped strengthen my knowledge of Docker container fundamentals, custom image creation, and dynamic SSH automation using Rundeck.

## Features
- Custom Ubuntu-based Docker container with SSH and Python installed

- Custom SSH port mapping to allow independent access to container

- Rundeck configured to manage both the host machine and the container as separate nodes

- Automated deployment and execution of a fake hardware test script written in Python

## Prerequisites
- A Linux-based system with Docker installed

- Basic knowledge of containerization concepts

- Rundeck installed and configured

- Basic understanding of SSH and automation workflows

## Setup Steps
### 1. Create the Dockerfile
```
FROM ubuntu:22.04

RUN apt-get update && apt-get install -y openssh-server python3 python3-pip sudo

RUN mkdir /var/run/sshd

RUN useradd -m -s /bin/bash skynet && echo 'skynet:password' | chpasswd && adduser skynet sudo

RUN sed -i 's/#PasswordAuthentication yes/PasswordAuthentication yes/' /etc/ssh/sshd_config

EXPOSE 22

CMD ["/usr/sbin/sshd", "-D"]
This sets up an Ubuntu container that allows SSH access with the skynet user.
```

### 2. Build and Run the Container

```
sudo docker build -t ubuntu-ssh-skynet .
sudo docker run -d -p 2222:22 --name TestPC ubuntu-ssh-skynet
```

This builds the container and maps host port 2222 to container port 22 for SSH access.

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Containers/Images/14.png)

### 3. Verify SSH Connectivity

From the host, verify you can SSH into the container:

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Containers/Images/15.png)

### 4. Configure Rundeck Node Source

Define nodes in resources.yaml:
```
- nodename: TestPC
  hostname: 192.168.1.97:2222
  username: skynet
  description: Ubuntu container for hardware tests

- nodename: RHEL-VM2
  hostname: 192.168.1.97:22
  username: kelz
  description: Host machine
```

This allows Rundeck to recognize the container and the host separately.

### 5. Configure SSH Password Storage

Store the SSH password for skynet in Rundeck Key Storage, and reference it from jobs if needed.

### 6. Create and Push Test Script

Example script pushed to the container via Rundeck:

```
#!/usr/bin/env python3

def run_tests():
    print("Running hardware tests...")
    print("CPU Check: OK")
    print("Memory Check: OK")
    print("Disk Check: OK")
    print("Network Interface Check: OK")
    print("Battery Health: OK (even if not present)")
    print("Temperature Sensors: OK")

if __name__ == "__main__":
    run_tests()
```

The script was pushed using a Rundeck job that wrote the script file and made it executable.

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Containers/Images/21.png)

### 7. Execute Test Script from Rundeck

A Rundeck job was created to remotely execute /home/skynet/testscript.py inside the container.

![Image](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Containers/Images/22.png)

## Verification
- To test the full setup:

- Verify the container can be managed independently from the host through Rundeck.

- Push the hardware test script through Rundeck.

- Execute the script through Rundeck and verify successful output.

## Best Practices
- Use SSH key authentication instead of passwords for production environments.

- Use proper port isolation and firewall rules when exposing container ports.

- Regularly audit node access configurations to minimize security risks.

- Build minimal Docker images for more efficient resource usage.

---

Thanks for checking this out! Let me know if you have any suggestions or feedback!
