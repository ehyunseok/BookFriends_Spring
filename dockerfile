FROM eclipse-temurin:21-jre

# Build argument for the WAR file location
ARG WAR_FILE=build/libs/*.war

# Copy the WAR file to the container
COPY ${WAR_FILE} /app.war

# Copy JSP files to the proper location
COPY src/main/webapp/WEB-INF/views /app/WEB-INF/views

# Expose the port the app runs on
EXPOSE 8001

# Entry point to run the application
ENTRYPOINT ["java", "-jar", "/app.war"]

# dockerfile:  애플리케이션의 Docker 이미지를 생성하기 위한 설정 파일
# 일련의 명령어를 통해 애플리케이션 환경을 설정하고, 필요한 파일을 추가하며, 실행할 명령을 정의한다.
#`FROM eclipse-temurin:21-jre`
    #eclipse-temurin:21-jre 이미지를 기반 이미지로 사용한다. 이 이미지는 Java 21 JRE 환경을 제공한다.
#`ARG JAR_FILE=build/libs/*.jar`
    #JAR_FILE이라는 빌드 인수를 정의한다. 기본값은 build/libs/*.jar이다.
#`COPY ${JAR_FILE} app.jar`
    #JAR_FILE에서 지정한 경로에 있는 파일을 컨테이너의 app.jar로 복사한다.
    #JAR_FILE은 build/libs/*.jar로 지정되어 있으므로, 빌드된 JAR 파일을 컨테이너로 복사한다.
# `COPY src/main/webapp/WEB-INF/views /META-INF/resources/WEB-INF/views`
    # JSP 파일을 컨테이너 내의 /META-INF/resources/WEB-INF/views 경로로 복사한다.
# `EXPOSE 8001`
    #컨테이너가 실행될 때 외부에 노출할 포트를 지정한다. 여기서는 8001 포트를 노출한다.
# `ENTRYPOINT ["java", "-jar", "/app.jar"]`
    #컨테이너가 시작될 때 실행할 기본 명령어를 지정한다. 여기서는 java 애플리케이션을 jar 파일로 실행한다.
