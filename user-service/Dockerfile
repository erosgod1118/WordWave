FROM java:8

ADD target/user-service-1.0.0.RELEASE.jar /usr/local/projects/user-service-1.0.0.RELEASE.jar

ENTRYPOINT exec java $JAVA_OPTIONS -jar /usr/local/projects/user-service-1.0.0.RELEASE.jar