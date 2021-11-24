#1网站地址
##线上地址
http://hanf666666.top:9999/
##本地启动
http://localhost:9999/

#2项目部署
##启动
 nohup java -jar /export/projects/my-blog-4.0.0-SNAPSHOT.jar >blog.log &
##项目停止
ps -ef|grep "my-blog*"|awk '{print $2}'|xargs kill -9
#3.git地址
git@github.com:LongShengTeam/blogSpace.git

