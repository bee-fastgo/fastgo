#!/bin/bash

software=$1
version=$2
targetPath=/data/fastgo/software

for pid in $(ps -A | grep mongod | awk '{print $1}')
do
	kill -9 $pid
done

if [[ -d "$targetPath/$software-$version" ]];then
	rm -rf $targetPath/$software-$version
fi
echo "mongo uninstall success..."