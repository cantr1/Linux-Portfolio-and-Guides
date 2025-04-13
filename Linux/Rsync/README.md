# Rsync Project Guide
## Overview
This guide covers how to synchronize files between a source and destination directory, both locally and remotely, using rsync. It also demonstrates how to automate the synchronization process with SSH key authentication and cron scheduling.

## Features
- Create source and destination directories

- Use rsync to sync files locally

- Use rsync over SSH to sync files remotely

- Set up passwordless SSH login

- Automate file synchronization using a cron job

## Prerequisites
- Two Linux-based systems (or a local and remote Linux machine)

- SSH access between the systems

- Basic knowledge of shell scripting and SSH

## Implementation
### 1. Create Source and Destination Directories

Make directories for syncing:

```
mkdir -p ~/rsync_project/source
mkdir -p ~/rsync_project/destination
```

Add a test file:

`echo "Hello, world!" >> ~/rsync_project/source/hello.txt`

### 2. Perform Local Rsync Sync

Use rsync to copy from source to destination:

`rsync -av ~/rsync_project/source/ ~/rsync_project/destination/`

Result:

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Rsync/Images/1.png)


### 3. Perform Remote Rsync Sync Over SSH

Use rsync with SSH to sync to a remote host:

`rsync -av -e ssh ~/rsync_project/source/ kelz@192.168.1.95:/home/kelz/rsync_project/destination/`

Accept the fingerprint and enter your password on first connection.

Result:

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Rsync/Images/2.png)



Check on the remote machine:

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Rsync/Images/3.png)



4. Set Up Passwordless SSH Login

Generate an SSH keypair (if you don’t have one):

`ssh-keygen`

Copy your public key to the remote machine:

`ssh-copy-id kelz@192.168.1.95`

Result:

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Rsync/Images/4.png)


5. Automate Rsync with a Cron Job

Create a sync script:

`nano ~/sync.sh`

Content:
```
#!/bin/bash
rsync -av --delete -e ssh ~/rsync_project/source/ kelz@192.168.1.95:/home/kelz/rsync_project/destination/
```

Make the script executable:

`chmod +x ~/sync.sh`

Add a cron job to run the script as needed:

`crontab -e`

Add:

`0 * * * * /home/kelz/sync.sh`

Result:

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/Rsync/Images/5.png)



## Best Practices
- Use --delete carefully to prevent accidental data loss.

- Regularly back up important files before syncing.

- Secure your SSH access with key authentication and limit users if possible.

---

Check out the rest of this repo for more Linux tricks and automation tips!
