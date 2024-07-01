# 測試須知

1. 應用程式聽埠號: 18081，測試時請使用此埠號。
   
2. 使用schema.sql與data.sql兩檔進行資料庫初始化，不需要再額外跑SQL語句。
  
3. 資料庫中既有使用者1帳號與密碼為user1, password1；以及使用者2帳號與密碼為user2, password2。 

# 部署指南

以下是如何部署此專案的兩種方法：

## 方法一：手動部署

1. **Clone 專案**

   首先，將此專案 clone 到本地端：

   ```sh
   git clone https://github.com/your-repo/your-project.git
   cd your-project
   ```

2. **確認資料庫連線資訊**

   確保 `application.yml` 中的資料庫連線資訊正確：

   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/baas_demo
       username: postgres
       password: 1234
     jpa:
       hibernate:
         ddl-auto: none
       properties:
         hibernate:
           format_sql: true
     sql:
       init:
         mode: always

   springdoc:
     default-consumes-media-type: application/json
     default-produces-media-type: application/json

   server:
     port: 18081

   logging:
     level:
       root: warn
   ```

3. **打包專案**

   執行以下命令進行打包，並跳過測試：

   ```sh
   mvn clean package -DskipTests
   ```

4. **運行應用程式**

   使用以下命令運行生成的 jar 檔案：

   ```sh
   java -jar target/demo-0.0.1-SNAPSHOT.jar
   ```

## 方法二：使用 Docker Compose 部署

1. **Clone 專案**

   首先，將此專案 clone 到本地端：

   ```sh
   git clone https://github.com/your-repo/your-project.git
   cd your-project
   ```

2. **運行 Docker Compose**

   執行以下命令來啟動 Docker 容器：

   ```sh
   docker compose up -d
   ```

   此命令將會自動建立並啟動包含 PostgreSQL 資料庫和 Spring Boot 應用程式的 Docker 容器。

3. **檢查服務狀態**

   執行以下命令檢查服務是否正常運行：

   ```sh
   docker compose ps
   ```

以上兩種方法都能成功部署此專案，請根據您的需求選擇適合的方法。若有任何問題，請隨時聯絡我們。
