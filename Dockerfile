FROM java:8-jre

LABEL MAINTAINER="automate.website <hello@automate.website>"

COPY target/jwebrobot-*.jar /opt/jwebrobot/
COPY Docker /

RUN export JWEBROBOT_REAL_PATH=$(ls -1 /opt/jwebrobot/*.jar | grep -E '/opt/jwebrobot/jwebrobot-[0-9]+\.[0-9]+\.[0-9]+?(-SNAPSHOT)\.jar$') \
    && ln -s $JWEBROBOT_REAL_PATH /opt/jwebrobot/app.jar \
    && ln -s /opt/jwebrobot/app.sh /usr/bin/jwebrobot

ENTRYPOINT /usr/bin/jwebrobot
