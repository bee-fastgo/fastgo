#!/bin/bash

software=$1
version=$2
downloadUrl=$3
targetPath=/data/fastgo/software

# 安装wget
if [[ -z $(rpm -qa | grep wget) ]];then
  yum -y install wget
fi

# 停止redis
for pid in $(ps -A | grep $software | awk '{print $1}')
do
  kill -9 $pid
done


# 下载redis
wget -P /root/data $downloadUrl

# 如果基本文件夹不存在,则创建
if [[ ! -d "$targetPath" ]];then
  mkdir -p $targetPath
fi

# 解压文件
tar -zxf /root/data/$software-$version.tar.gz -C $targetPath

# 启动
$targetPath/$software-$version/redis-server $targetPath/$software-$version/redis.conf

# 清理资源
if [[ 0 -eq $? ]];then
  echo "redis install and run success..."
  echo "clear package..."
  rm -rf /root/data/$software-$version.tar.gz
fi