#!/bin/sh
mkdir "logs"
# 此处需遇到一个坑 若以后台方式运行jar 则所在的容器会被关闭(我猜测是因为若以后台方式运行 docker会以为容器的命令已经执行完毕 便退出了 未证实仅为猜测) 故不能在运行命令后面加 &
nohup java -jar eureka-0.0.1-SNAPSHOT.jar > console.log