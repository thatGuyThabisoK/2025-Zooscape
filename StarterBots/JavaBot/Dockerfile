FROM maven:3.9.2-eclipse-temurin-11 AS build

COPY ./ ./

RUN mvn clean install

FROM public.ecr.aws/m5z5a5b2/languages/java:2021

WORKDIR /app

# The directory of the built code to copy into this image, to be able to run the bot.
COPY --from=build /target/JavaBot-jar-with-dependencies.jar ./JavaBot.jar

# The entrypoint to run the bot
ENTRYPOINT ["java", "-jar", "JavaBot.jar"]