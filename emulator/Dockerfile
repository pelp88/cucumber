FROM adoptopenjdk/openjdk14:x86_64-alpine-jdk-14.0.2_12
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY /run_app.sh /run_app.sh
RUN apk add --no-cache bash
RUN chmod +x /run_app.sh
EXPOSE 8090
ENTRYPOINT ["/bin/bash", "-c", "/run_app.sh"]
