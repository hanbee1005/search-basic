FROM amazoncorretto:17
ARG SPRING_PROFILE=dev
ENV SPRING_PROFILE_ACTIVE=${SPRING_PROFILE}

RUN mkdir deploy
COPY build/libs/search-basic.jar /app.jar
RUN chmod +x /app.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILE_ACTIVE}", "-jar", "/app.jar"]