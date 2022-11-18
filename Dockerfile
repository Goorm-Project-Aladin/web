## Multi-build
# 자바 소스를 빌드해 JAR 프로젝트 파일 생성
FROM openjdk:11 AS init-build
LABEL description="Java Application builder"
WORKDIR /
RUN apt install git
RUN git clone https://github.com/Goorm-Project-Aladin/web.git
WORKDIR /web
RUN chmod 700 gradlew
RUN ./gradlew clean build

# 빌드된 JAR 프로젝트를 경량화 이미지에 복사해 이미지 빌드
FROM gcr.io/distroless/java:11
LABEL description="Java Application run image"
ARG JAR_FILE=./build/libs/web-0.0.1-SNAPSHOT.jar
EXPOSE 60434
COPY ${JAR_FILE} /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]