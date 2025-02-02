# Linux RAID 5 Setup

## Overview

This project focuses on setting up a RAID 5 array using mdadm on a Linux system. The goal is to improve redundancy and performance while gaining hands-on experience with RAID configurations.

RAID 5 provides a balance between performance, redundancy, and storage efficiency by using striping with parity. This means data remains intact even if a single drive fails, making it a practical choice for many environments.

For this project, I will be using a system running RHEL 9.5. RedHat is one of the most popular distros for enterprise environments so it never hurts to get more experience with it.

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/RAID/Images/1.png)

## Prerequisites

- A Linux-based system with mdadm installed

- At least four partitions or separate drives

- Basic understanding of partitioning tools like fdisk and parted

## Steps to Set Up RAID 5

### 1. Partitioning the Drives

First, create four partitions using fdisk or parted. This ensures that we have the required storage space for RAID.

To check what block devices the system currently sees run this command:

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/RAID/Images/2.png)

Using fdisk:

`sudo fdisk /dev/sdb`

Create four partitions (/dev/sdb1, /dev/sdb2, /dev/sdb3, /dev/sdb4).

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/RAID/Images/3.png)

Using the defaults is generally recommended. Unless you really know what you are doing, it is a good idea to just use the defaults aside from choosing the sizes of your partitions.

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/RAID/Images/4.png)

We can then use the following commands to make the system aware of the new partitions and create a filesystem on said partition (ext4 in this example).
~~~
partprobe
mkfs.ext4
~~~

Partprobe can help to avoid errors when creating the file system, although not necessarily required per se.

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/RAID/Images/5.png)

Repeat for the process for each partition. RAID 5 requires at least four drives/partititons in order to function properly.

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/RAID/Images/8.png)


### 2. Create the RAID 5 Array

Using mdadm, create the RAID 5 array:

`sudo mdadm --create /dev/md0 --level=5 --raid-devices=4 /dev/sdb1 /dev/sdb2 /dev/sdb3 /dev/sdb4`

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/RAID/Images/10.png)

We can check the status of the array by running the following:

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/RAID/Images/11.png)

### 3. Format the RAID Array

Once the RAID array is created, format it with the ext4 filesystem:

`sudo mkfs.ext4 /dev/md0`

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/RAID/Images/12.png)

### 4. Mount the RAID Array

Create a mount point and mount the RAID array:
~~~
sudo mkdir -p /mnt/raid
sudo mount /dev/md0 /mnt/raid
~~~

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/RAID/Images/13.png)

### 5. Verify the RAID Status

To confirm that the RAID is functioning properly, check its status again with:

`cat /proc/mdstat`

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/RAID/Images/14.png)

This should display all drives as active and synchronized.

### 6. Make the RAID Persistent

To save the RAID configuration, we can tee the details into the RAID configuration file. This will make the RAID persistent across boots.

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/RAID/Images/15.png)

Finally, to update initramfs we run the following:

![Images](https://github.com/cantr1/Linux-Portfolio-and-Guides/blob/main/Linux/RAID/Images/16.png)

Then as a final task, we can update fstab to make the RAID available upon start up.

`UUID=<your-raid-uuid> /mnt/raid ext4 defaults 0 0`

## Conclusion

This project provided hands-on experience in setting up RAID 5 using mdadm, reinforcing practical Linux administration skills. RAID 5 is a great choice for scenarios requiring fault tolerance and efficient storage management.

For further details and additional projects, check out the rest of this repository!
