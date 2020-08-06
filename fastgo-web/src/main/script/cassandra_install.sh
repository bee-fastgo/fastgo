#!/bin/bash

software=$1
version=$2
targetPath=/data/fastgo/software
downloadUrl=$3
ip=$4

if [[ -z $(rpm -qa | grep wget) ]];then
        yum -y install wget
fi

if [[ -z $(ls /data/fastgo | grep jdk) ]];then
        echo "uninstall jdk..."
        exit
fi

wget -P /root/data $downloadUrl

tar -zxf /root/data/$software-$version.tar.gz -C $targetPath


sed -i "s@rpc_address: 127.0.0.1@rpc_address: $ip@g" $targetPath/$software-$version/conf/cassandra.yaml

$targetPath/$software-$version/bin/cassandra -R

if [[ 0 -eq $? ]];then
        echo "cassandra run success..."
        echo "clear package..."
        rm -rf /root/data/$software-$version.tar.gz
fi