#### 创建OpenJDK镜像
创建方法
- 直接在此目录下运行 `docker image build -t ssh-openjdk:8`
- 将此镜像推送至docker仓库
  - 创建仓库 `docker image tag ssh-openjdk:8 shishuheng/ssh-openjdk:8`
  - 推送 `docker push shishuheng/ssh-openjdk:8`

详细解释
- 参看 Dockerfile 文件
- 参看 init.sh 文件