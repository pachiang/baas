# 使用 JDK 17 基础镜像
FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 复制 Maven 构建的 jar 文件到工作目录
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# 暴露应用程序运行的端口
EXPOSE 18081

# 运行应用程序
ENTRYPOINT ["java", "-jar", "app.jar"]
