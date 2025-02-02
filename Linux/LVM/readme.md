# Setting Up LVM on Newly Installed SSDs

## Overview

In our production environment, we needed to integrate new SSDs into servers and configure them with Logical Volume Manager (LVM). This guide outlines the manual setup process and provides a script to automate the procedure.

## Steps to Configure LVM

### 1. Verify System Recognizes the New Disk

Run the following command to list available block devices:

`lsblk`

### 2. Partition the New Disk

If partitioning is required, use fdisk:

`sudo fdisk /dev/nvmeX`

Partitioning Steps:

- Press n to create a new partition.

- Select p for primary.

- Press Enter to accept default values.

- Set the end sector (e.g., +1700G).

- Press t, then enter 8e to set the partition type to Linux LVM.

- Press w to write changes.

### 3. Initialize the Disk for LVM

`sudo pvcreate /dev/nvme1n1`

### 4. Extend the Volume Group

Identify the existing volume group:

`vgdisplay`

The default volume group is likely vgubuntu.

Ensure block sizes match:

~~~
sudo blockdev --getbsz /dev/nvme1n1
sudo blockdev --getbsz /dev/nvme0n1
~~~

If needed, enable mixed block sizes in /etc/lvm/lvm.conf:

`allow_mixed_block_sizes = 1`

Add the new physical volume to the volume group:

`sudo vgextend vgubuntu /dev/nvme1n1`

5. Extend the Logical Volume

Identify the logical volume name:

`lvdisplay`

Extend the logical volume:

`sudo lvextend -l +100%FREE /dev/vgubuntu/root`

6. Resize the Filesystem

Check filesystem type:

`lsblk -f`

For ext4:

`sudo resize2fs /dev/vgubuntu/root`

## Automating the Process

To simplify LVM setup, I created the following Bash script:

~~~
#!/bin/bash
# This script partitions and adds multiple drives to LVM.
# Written by: Kelly Cantrell

# Enable mixed block sizes
sudo sed -i 's/allow_mixed_block_sizes = 0/allow_mixed_block_sizes = 1/' /etc/lvm/lvm.conf

# Loop through all provided drives
for drive in "$@"; do
    selected_drive="/dev/$drive"
    
    # Check if the drive has no partitions
    line_count=$(lsblk -n $selected_drive -o NAME | wc -l)
    if [ "$line_count" -eq 1 ]; then
        echo "Processing $selected_drive..."
        
        # Partition the drive
        (
        echo n # Add a new partition
        echo p # Primary partition
        echo 1 # Partition number
        echo   # First sector (Accept default)
        echo   # Last sector (Accept default)
        echo t # Change partition type
        echo 8e # Linux LVM type
        echo w # Write changes
        ) | sudo fdisk $selected_drive
        echo "$selected_drive has been partitioned..."

        # Create the physical volume
        echo y | sudo pvcreate $selected_drive
        echo "Physical volume created..."

        # Extend the volume group
        sudo vgextend vgubuntu $selected_drive
        echo "Volume group extended..."

        # Extend the logical volume
        sudo lvextend -l +100%FREE /dev/vgubuntu/root
        echo "Logical volume extended..."

        # Resize the file system
        sudo resize2fs /dev/vgubuntu/root
        echo "File system resized..."

        echo "$selected_drive added to LVM."
    elif [ "$line_count" -eq 0 ]; then
        echo "$selected_drive not detected"
    else
        echo "$selected_drive is already partitioned. Skipping."
    fi

done

echo "All drives processed."
~~~

Conclusion

LVM provides flexibility for storage management, and this script helps automate the process, minimizing human error and setup time.

For troubleshooting, use:

~~~
lsblk
vgdisplay
lvdisplay
cat /etc/lvm/lvm.conf
~~~

---

Thanks for checking out this project! Be sure to check the rest of the repo for informative guides!
