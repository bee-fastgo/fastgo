#!/bin/bash

software=$1
version=$2
downloadUrl=$3
targetPath=/data/fastgo/software
zookeeperCluster=$4


# 安装weget
if [[ -z $(rpm -qa | grep wget) ]];then
	yum -y install wget
fi

# 判断是否安装了jdk
if [[ -z $(ls /data/fastgo |grep jdk) ]];then
	echo "uninstall jdk"
	exit
fi

# 结束之前安装的kafka
for pid in $(jps | grep $software)
do
	kill -9 $pid
done

# 下载kafka
wget -P /root/data $downloadUrl

# 目标文件夹如果不存在则创建
if [[ ! -d "$targetpath" ]];then
	mkdir -p $targetPath
fi

# 解压文件
tar -zxf /root/data/$software-$version.tar.gz -C $targetPath

# 创建数据文件夹
mkdir $targetPath/$software-$version/data

# 日志存放路径
mkdir $targetPath/$software-$version/logs

# 修改数据存放目录
dataDir=$(grep 'log.dirs=' $targetPath/$software-$version/config/server.properties)
if [[ ! -z $dataDir ]];then
	sed -i "s@$dataDir@log.dirs=$targetPath/$software-$version/data@g" $targetPath/$software-$version/config/server.properties
fi

# 设置zk集群
zookeeperConnect=$(grep 'zookeeper.connect=' $targetPath/$software-$version/config/server.properties)
if [[ ! -z $zookeeperConnect ]];then
	sed -i "s@$zookeeperConnect@zookeeper.connect=$zookeeperCluster@g" $targetPath/$software-$version/config/server.properties
fi

# 启动kafka
$targetPath/$software-$version/bin/kafka-server-start.sh $targetPath/$software-$version/config/server.properties >> $targetPath/$software-$version/logs/logs 2>&1 &

echo "kafka install and run success..."
echo "clear package..."
rm -rf /root/data/$software-$version.tar.gz