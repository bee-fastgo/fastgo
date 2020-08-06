#!/bin/bash

software=$1
version=$2
targetPath=/data/fastgo/software

# 停止zookeeper
for pid in $(jps | grep QuorumPeerMain | awk '{print $1}')
do
	kill -9 $pid
done

if [[ -d "$targetPath/$software-$version" ]];then
	rm -rf $targetPath/$software-$version
	echo "zookeeper uninstall success..."
	exit
fi
