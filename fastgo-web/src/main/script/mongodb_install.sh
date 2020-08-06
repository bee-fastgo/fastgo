#!/bin/bash

software=$1
version=$2
targetPath=/data/fastgo/software
downloadUrl=$3

if [[ -z $(rpm -qa | grep wget) ]];then
	yum -y install wget
fi

for pid in $(ps -A | grep mongod  | awk '{print $1}')
do
	kill -9 $pid
done

wget -P /root/data $downloadUrl

if [[ ! -d "$targetPath" ]];then
	mkdir -p $targetPath
fi

tar -zxf /root/data/$software-$version.tar.gz -C $targetPath

$targetPath/$software-$version/bin/mongod --config $targetPath/$software-$version/mongodb.conf --auth

if [[ 0 -eq $? ]];then
	echo "mongodb install and run success..."
	echo "clear package..."
	rm -rf /root/data/$software-$version.tar.gz
	exit
fi
echo "mongodb install fial.."