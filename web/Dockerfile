FROM tomcat:9.0.71-jdk17-corretto-al2
ENV CATALINA_OPTS -Dsecurerandom.source=file:/dev/urandom
COPY ../vehicle-fleet.war /usr/local/tomcat/webapps/
CMD ["catalina.sh", "run"]