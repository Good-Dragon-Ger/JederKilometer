FROM gradle:8.7-jdk21-alpine AS build

WORKDIR /JederKilometer

COPY . /JederKilometer

RUN gradle bootJar

FROM eclipse-temurin:21-alpine

WORKDIR /JederKilometer

COPY --from=build /JederKilometer/build/libs/JederKilometer-0.0.1-SNAPSHOT.jar /JederKilometer/JederKilometer.jar

ENV DATABASE_PW=default_value
ENV DATABASE_USER=default_value
ENV GITHUB_CLIENT_ID=default_value
ENV GITHUB_CLIENT_SECRET=default_value
ENV STRAVA_CLIENT_ID=default_value
ENV STRAVA_CLIENT_SECRET=default_value
ENV STRAVA_CLUB_ID=default_value
ENV STRAVA_CLUB_PAGE=default_value
ENV STRAVA_PAGE_ENTRIES=default_value
ENV STRAVA_REFRESH_TOKEN=default_value

CMD java -jar JederKilometer.jar
