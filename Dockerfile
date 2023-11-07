FROM openjdk:11
WORKDIR /tmp
# WORKDIR /englishcenter
COPY target/com.englishcenter.main.jar /tmp/com.englishcenter.main.jar
COPY src/main/resources/application.properties /tmp/application.properties
CMD ["java","-jar","com.englishcenter.main.jar"]
# RUN mvn clean install

# CMD mvn spring-boot:run