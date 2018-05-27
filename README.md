```
Java版本：1.8.0_162
mvn版本：3.5.3
mvn镜像：http://maven.aliyun.com/nexus
操作系统(64位)：Ubuntu 16.04.3 LTS下使用docker运行maven:3.5.3-jdk-8镜像
```
### 注意事项：
#### 1、不要修改pom.xml中的build节内的任何内容；
#### 2、入口程序在./src/main/java/Main.java，不要变更main函数所在的文件；
#### 3、我们最后的打包命令是：mvn clean install package
#### 4、评测命令：java -jar output/UAVGoodsAI.jar 39.106.111.130 4010 ABC123DEFG
```
参数1：39.106.111.130，是裁判服务器的IP，您在本地调试时，可在“我的对战”中获取；正式评测时，由评测调度程序生成；
参数2：4010，是裁判服务器允许您连接的端口，您在本地调试时，可在“我的对战”中获取；正式评测时，由评测调度程序生成；
参数3：字符串，是本次运行的令牌，以便校验您的资格，您在本地调试时，可在“我的对战”中获取；正式评测时，由评测调度程序生成；。
```
```
java -version
openjdk version "1.8.0_162"
OpenJDK Runtime Environment (build 1.8.0_162-8u162-b12-1~deb9u1-b12)
OpenJDK 64-Bit Server VM (build 25.162-b12, mixed mode)
```
```
mvn -version
Apache Maven 3.5.3 (3383c37e1f9e9b3bc3df5050c29c8aff9f295297; 2018-02-24T19:49:05Z)
Maven home: /usr/share/maven
Java version: 1.8.0_162, vendor: Oracle Corporation
Java home: /usr/lib/jvm/java-8-openjdk-amd64/jre
Default locale: en, platform encoding: UTF-8
OS name: "linux", version: "4.4.0-105-generic", arch: "amd64", family: "unix"
```