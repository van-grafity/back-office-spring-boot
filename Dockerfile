FROM adoptopenjdk/openjdk11

ADD target/bo-api-0.0.1-SNAPSHOT.jar application.jar
ENV JAVA_OPTS="-Xms512m -Xmx1g"
ENV CONFIG_FILE="application.properties"
ENTRYPOINT ["sh", "-c", "java -jar $JAVA_OPTS application.jar configFile=$CONFIG_FILE"]
