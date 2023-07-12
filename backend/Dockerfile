FROM openjdk:20
EXPOSE 8080
# Wichtig! Wenn eure jar nicht "backend" heißt, müsst ihr das hier anpassen!
ADD "target/backend.jar" app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]