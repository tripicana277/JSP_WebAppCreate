# ベースイメージとしてJava 17を使用
FROM openjdk:17-jdk-slim

# 作業ディレクトリを作成
WORKDIR /app

# アプリケーションのWARまたはJARファイルをコピー
COPY target/your-app.jar /app/app.jar

# アプリケーションを実行
CMD ["java", "-jar", "/app/app.jar"]