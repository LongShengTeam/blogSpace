#1网站地址
##线上地址
http://hanf666666.top:9999/
http://hanf666666.top:9999/admin
##本地启动
http://localhost:9999/
http://localhost:9999/admin
#2项目部署
##启动
ps -ef|grep myblog_1.0.jar|awk '{print $2}'|xargs kill -9
rm -rf /export/projects/my-blog/*
 nohup java -jar /export/projects/my-blog/myblog_1.0.jar &
##项目停止
ps -ef|grep "my-blog*"|awk '{print $2}'|xargs kill -9
#3.git地址
git@github.com:LongShengTeam/blogSpace.git

