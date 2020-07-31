package com.bee.team.fastgo.controller.server;

import com.bee.team.fastgo.hander.JobHandler;
import com.bee.team.fastgo.hander.JobPushImpl;
import com.bee.team.fastgo.hander.SimpleExecutorCmd;
import com.bee.team.fastgo.job.core.glue.GlueTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author luke
 * @date 17/5/10
 */
@Controller
@RequestMapping("/api")
public class JobTestController {



    /**
     * api
     *
     * @param data
     * @return
     */
    @RequestMapping("/test")
    @ResponseBody
    public String test(HttpServletRequest request, @RequestBody(required = false) String data) {
        String cmd = request.getParameter("cmd");
        String ip = request.getParameter("ip");
        String join = StringUtils.join(Arrays.asList("mysql", "5.7", "http://172.22.5.248:9999/fastgo/mysql-5.7.tar.gz", "afadad"), ",");
        String logId = SimpleExecutorCmd.executorCmd(GlueTypeEnum.GLUE_SHELL, "#!/bin/bash\n" +
                "\n" +
                "software=$1\n" +
                "version=$2\n" +
                "downloadUrl=$3\n" +
                "targetPath=/data/fastgo/software\n" +
                "dataSourceName=$4\n" +
                "\n" +
                "# 安装wget\n" +
                "if [[ -z $(rpm -qa | grep wget) ]];then\n" +
                "\tyum -y install wget\n" +
                "fi\n" +
                "\n" +
                "# 结束正在运行的mysql\n" +
                "for mysqlPid in $(ps -A | grep $software | awk '{print $1}')\n" +
                "do\n" +
                "\tkill -9 $mysqlPid\n" +
                "done\n" +
                "\n" +
                "# 结束正在运行的mariadb\n" +
                "for mariadbPid in $(ps -A |grep mariadb |awk '{print $1}')\n" +
                "do\n" +
                "\tkill -9 $mariadbPid\n" +
                "done\n" +
                "\n" +
                "# 卸载mysql\n" +
                "for mysql in $(rpm -qa |grep mysql)\n" +
                "do\n" +
                "\trpm -e --nodeps $mysql\n" +
                "done\n" +
                "\n" +
                "# 卸载mariadb\n" +
                "for mariadb in $(rpm -qa |grep mariadb)\n" +
                "do\n" +
                "\trpm -e --nodeps $mariadb\n" +
                "done\n" +
                "\n" +
                "if [[ ! -d \"$targetPath/$software-$version\" ]];then\n" +
                "\t# 下载mysql\n" +
                "\twget -P $(pwd) $downloadUrl\n" +
                "\n" +
                "\t# 如果基本文件夹不存在,则创建\n" +
                "\tif [[ ! -d \"$targetPath\" ]];then\n" +
                "\t\tmkdir -p $targetPath\n" +
                "\tfi\n" +
                "\n" +
                "\t# 解压文件\n" +
                "\ttar -zxf $(pwd)/$software-$version.tar.gz -C $targetPath\n" +
                "\n" +
                "\t# 重写my.cnf文件\n" +
                "\tcat > /etc/my.cnf <<EOF\n" +
                "\t[mysqld]\n" +
                "\tbasedir=/data/fastgo/software/mysql-5.7/\n" +
                "\tdatadir=/data/fastgo/software/mysql-5.7/data/\n" +
                "\tsocket=/tmp/mysql.sock\n" +
                "\tuser=root\n" +
                "\n" +
                "\t[mysql_safe]\n" +
                "\tlog-error=/data/fastgo/software/mysql-5.7/log/mysqld.log\n" +
                "\tpid-file=/data/fastgo/software/mysql-5.7/pid/mysqld.pid\n" +
                "\n" +
                "\t[client]\n" +
                "\tport=3306\n" +
                "\tsocket=/tmp/mysql.sock\n" +
                "EOF\n" +
                "fi\n" +
                "\n" +
                "\n" +
                "# 启动mysql\n" +
                "$targetPath/$software-$version/support-files/mysql.server start\n" +
                "\n" +
                "wait $!\n" +
                "\n" +
                "cat > ./createdb.sh << EOF\n" +
                "#!/bin/bash\n" +
                "\n" +
                "$targetPath/$software-$version/bin/mysql -uroot -p123456 -e \"create database $dataSourceName;\"\n" +
                "\n" +
                "if [[ 5 -eq $# ]];then\n" +
                "\twget $5 -O /root/db.sql\n" +
                " \t$targetPath/$software-$version/bin/mysql -uroot -p123456 -D$dataSourceName < /root/db.sql\n" +
                "fi\n" +
                "\n" +
                "pkill -9  mysql\n" +
                "\n" +
                "$targetPath/$software-$version/support-files/mysql.server start\n" +
                "\n" +
                "if [[ 0 -eq $? ]];then\n" +
                "\techo \"mysql install and run success...\"\n" +
                "\techo \"clear package...\"\n" +
                "\trm -rf $(pwd)/$software-$version.tar.gz\n" +
                "\tif [[ 5 -eq $# ]];then\n" +
                "\t\trm -rf /root/db.sql\n" +
                "\tfi\n" +
                "\texit 0\n" +
                "fi\n" +
                "EOF\n" +
                "\n" +
                "bash ./createdb.sh" +
                "kill -9 $$", join, -1, ip);
        JobHandler.jobMap.put(logId,new JobPushImpl());
        return logId;
    }

}
