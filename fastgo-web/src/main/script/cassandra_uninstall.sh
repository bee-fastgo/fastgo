#!/bin/bash

software=$1
version=$2
targetPath=/data/fastgo/software

kill -9 $(ps -ef | grep $software | awk '{print $2}' | head -n 1)


if [[ -d "$targetPath/$software-$version" ]];then
        rm -rf $targetPath/$software-$version
fi
echo "uninstall success..."
exit