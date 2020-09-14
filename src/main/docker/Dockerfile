FROM java:8
VOLUME /tmp
ADD back_end-0.0.1-SNAPSHOT.jar /back_end-0.0.1-SNAPSHOT.jar
RUN bash -c "touch /back_end-0.0.1-SNAPSHOT.jar"
EXPOSE 18081
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/back_end-0.0.1-SNAPSHOT.jar"]