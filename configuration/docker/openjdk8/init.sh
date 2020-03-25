#!/bin/sh
# 修改alpine的软件源镜像为阿里云镜像
sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories
# 更新软件源
apk update
# 安装openjdk8
apk add openjdk8