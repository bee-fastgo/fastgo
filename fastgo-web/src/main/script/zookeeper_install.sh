#!/bin/bash

software=$1
version=$2
downloadUrl=$3
targetPath=/data/fastgo/software

# 安装weget
if [[ -z $(rpm -qa | grep wget) ]];then
	yum -y install wget
fi

# 判断是否安装了jdk
if [[ -z $(ls /data/fastgo |grep jdk) ]];then
	echo "uninstall jdk"
	exit
fi

# 停止zookeeper
for pid in $(jps | grep QuorumPeerMain | awk '{print $1}' )
do
	kill -9 $pid
done

# 下载zookeeper 
wget -P /root/data $downloadUrl

if [[ ! -d "$targetpath" ]];then
	mkdir -p $targetPath
fi

# 解压文件
tar -zxf /root/data/$software-$version.tar.gz -C $targetPath

# 创建数据文件夹
mkdir $targetPath/$software-$version/data

# 拷贝配置文件
cp $targetPath/$software-$version/conf/zoo_sample.cfg $targetPath/$software-$version/conf/zoo.cfg

# 替换配置文件中的数据文件夹路径
dataDir=$(grep 'dataDir=' $targetPath/$software-$version/conf/zoo.cfg)
if [[ ! -z $dataDir ]];then
	sed -i "s@$dataDir@dataDir=$targetPath/$software-$version/data@g" $targetPath/$software-$version/conf/zoo.cfg
fi

# 启动zookeeper
nohup $targetPath/$software-$version/bin/zkServer.sh start $targetPath/$software-$version/conf/zoo.cfg >> /dev/null 2>&1 &

if [[ 0 -eq $? ]];then
	echo "zookeeper install and run success..."
	echo "clear package..."
	rm -rf /root/data/$software-$version.tar.gz
	exit
fi
echo "zookeeper install and run fail..."
