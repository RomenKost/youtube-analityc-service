FROM openjdk:11
COPY build/libs/youtube-analytic-service-0.0.1-SNAPSHOT.jar youtube-analytic-service-0.0.1-SNAPSHOT.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "youtube-analytic-service-0.0.1-SNAPSHOT.jar", "--youtube.v3.api.key=AIzaSyDoJ5rO0qSWZd4ehdFXU-_2SGIDdO3fvA0"]
