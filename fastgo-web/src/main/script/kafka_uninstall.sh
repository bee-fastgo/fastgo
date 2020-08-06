#!/bin/bash

software=$1
version=$2
targetPath=/data/fastgo/software

if [[ -z $(ls /data/fastgo | grep jdk) ]];then
        echo "uninstall jdk..."
        exit
fi

for pid in $(jps | grep Kafka | awk '{print $1}')
do
        kill -9 $pid
done

if [[ -d "$targetPath/$software-$version" ]];then
        rm -rf $targetPath/$software-$version
fi
echo "kafka uninstall success..."