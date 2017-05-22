FROM openjdk:8-jre-alpine

ENV VERTICLE_FILE com.nextbreakpoint.nextfractal.verticle-2.0.1-fat.jar

ENV VERTICLE_HOME /usr/verticles

EXPOSE 8080

COPY target/$VERTICLE_FILE $VERTICLE_HOME/
COPY default.json $VERTICLE_HOME/

WORKDIR $VERTICLE_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec java -Xmx1024M -cp $VERTICLE_FILE io.vertx.core.Launcher run com.nextbreakpoint.nextfractal.verticle.FractalsVerticle -conf default.json"]
