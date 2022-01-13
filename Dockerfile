FROM openjdk:8-jre-alpine
MAINTAINER info@brainupgrade.in
COPY target/*.jar app.jar
ENTRYPOINT ["/usr/bin/java", "-Djava.security.egd=file:/dev/./urandom", "-jar","app.jar"]

# mvn clean package && docker build -f Dockerfile -t brainupgrade/global-bank-authentication:1.0.0 -t localhost:32000/brainupgrade/global-bank-authentication:1.0.0 . && docker push localhost:32000/brainupgrade/global-bank-authentication:1.0.0 && docker push brainupgrade/global-bank-authentication:1.0.0