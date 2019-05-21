#!/usr/bin/env bash  
 
LVDISPLAY="/sbin/lvdisplay"  
PVCREATE="/sbin/pvcreate"  
VGCREATE="/sbin/vgcreate"  
LVCREATE="/sbin/lvcreate"  
 
 
DEVICE_PREFIX="/dev/xvd" 
MKFS="/sbin/mkfs -t ext4" 
MOUNTPOINT="/mnt/data" 

function mount_volume { 
echo "mounting: $1 => ${MOUNTPOINT}" 
mount $1 ${MOUNTPOINT} 
} 

# Detects all local block devices present on the machine,
# skipping the first (which is assumed to be root). 
function detect_devices { 
  local PREFIX=$1 
  for x in {b..z} 
  do 
    DEVICE="${PREFIX}${x}" 
    if [[ -b ${DEVICE} ]] 
    then 
      echo "${DEVICE}" 
    fi 
  done 
} 

# Creates a new LVM volume. Accepts an array of block devices to 
# use as physical storage. 
function create_volume { 
  for device in $@ 
  do 
    ${PVCREATE} ${device} 
  done 

  # Creates a new volume group called 'data' which pools all 
  # available block devices. 
  ${VGCREATE} data $@ 

  # Create a logical volume with all the available storage space 
  # assigned to it. 
  ${LVCREATE} -l 100%FREE data 

  # Create a filesystem so we can use the partition. 
  ${MKFS} $(get_volume) 
} 

function detect_volume { 
  echo $(${LVDISPLAY} | grep 'LV Path' | awk '{print $3}') 
} 

# Similar to detect_volume, but fails if no volume is found. 
function get_volume { 
  local VOLUME=$(detect_volume) 
  if [[ -z ${VOLUME} ]] 
  then 
    echo "Fatal error: LVM volume not found!" 1>&2 
    exit 1 
  fi 
  echo $VOLUME 
} 

# Detect existing LVM volume 
VOLUME=$(detect_volume) 

# And create a brand new LVM volume if none were found 
if [[ -z ${VOLUME} ]] 
then 
  create_volume $(detect_devices ${DEVICE_PREFIX}) 
fi 

mkdir $MOUNTPOINT
mount_volume $(get_volume)