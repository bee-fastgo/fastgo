#!/bin/bash

software=$1
version=$2
targetPath=/data/fastgo/software

# 停止mysql服务
for pid in $(ps -A | grep $software | awk '{print $1}')
do
	kill -9 $pid
done

# 卸载redis
if [[ -d "$targetPath/$software-$version" ]];then
	rm -rf $targetPath/$software-$version
	rm -rf /etc/my.cnf
fi
echo "uninstall success..."
exit
