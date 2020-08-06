#!/bin/bash

software=$1
version=$2
downloadUrl=$3
targetPath=/data/fastgo/software
dataSourceName=$4

# 安装wget
if [[ -z $(rpm -qa | grep wget) ]];then
	yum -y install wget
fi

# 结束正在运行的mysql
for mysqlPid in $(ps -A | grep $software | awk '{print $1}')
do
	kill -9 $mysqlPid
done

# 结束正在运行的mariadb
for mariadbPid in $(ps -A |grep mariadb |awk '{print $1}')
do
	kill -9 $mariadbPid
done

# 卸载mysql
for mysql in $(rpm -qa |grep mysql)
do
	rpm -e --nodeps $mysql
done

# 卸载mariadb
for mariadb in $(rpm -qa |grep mariadb)
do
	rpm -e --nodeps $mariadb
done

if [[ ! -d "$targetPath/$software-$version" ]];then
	# 下载mysql
	wget -P /root/data $downloadUrl

	# 如果基本文件夹不存在,则创建
	if [[ ! -d "$targetPath" ]];then
		mkdir -p $targetPath
	fi

	# 解压文件
	tar -zxf /root/data/$software-$version.tar.gz -C $targetPath

	# 重写my.cnf文件
	cat > /etc/my.cnf <<EOF
	[mysqld]
	basedir=/data/fastgo/software/mysql-5.7/
	datadir=/data/fastgo/software/mysql-5.7/data/
	socket=/tmp/mysql.sock
	user=root

	[mysql_safe]
	log-error=/data/fastgo/software/mysql-5.7/log/mysqld.log
	pid-file=/data/fastgo/software/mysql-5.7/pid/mysqld.pid

	[client]
	port=3306
	socket=/tmp/mysql.sock
EOF
fi


# 启动mysql
$targetPath/$software-$version/support-files/mysql.server start


cat > ./createdb.sh << EOF
#!/bin/bash

$targetPath/$software-$version/bin/mysql -uroot -p123456 -e "create database $dataSourceName;"

if [[ 5 -eq $# ]];then
	wget $5 -O /root/db.sql
 	$targetPath/$software-$version/bin/mysql -uroot -p123456 -D$dataSourceName < /root/db.sql
fi


if [[ 0 -eq $? ]];then
	echo "mysql install and run success..."
	echo "clear package..."
	rm -rf /root/data/$software-$version.tar.gz
	if [[ 5 -eq $# ]];then
		rm -rf /root/db.sql
	fi
	exit 0
fi
EOF

bash ./createdb.sh