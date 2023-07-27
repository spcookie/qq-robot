FROM java-runtime

COPY . /root

WORKDIR /root

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "service-text-3.0.0.jar"]