# 基础的环境是用已经封装过的openjdk8
FROM shishuheng/ssh-openjdk:8
# 工作目录设置为 /usr/src/app
WORKDIR /usr/src/app
# 把当前目录下的所有文件复制到生成镜像的工作目录 此处即 /usr/src/app
COPY . .
# 添加 runapp.sh 执行权限
RUN chmod u+x runapp.sh
# 暴露容器(container)的 9030 端口
EXPOSE 9030
# 当创建容器后 执行 runapp.sh 脚本
CMD ["./runapp.sh"]
