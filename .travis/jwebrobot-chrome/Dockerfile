FROM selenium/node-chrome:latest

MAINTAINER automate.website <hello@automate.website>

ARG JWEBROBOT_SCENARIOS

ENV JWEBROBOT_SCENARIOS /var/jwebrobot
ENV JWEBROBOT_OPTS ""

ENV SCREEN_WIDTH 1366
ENV SCREEN_HEIGHT 768
ENV SCREEN_DEPTH 16
ENV JAVA_OPTS ""

VOLUME ["$JWEBROBOT_SCENARIOS"]

USER root

COPY entry-point.sh /opt/jwebrobot/entry-point.sh

RUN apt-get update -qqy && \
    apt-get -qqy install zip curl && \
    rm -rf /var/lib/apt/lists/* && \
    chmod +x /opt/jwebrobot/entry-point.sh

COPY jwebrobot.jar /opt/jwebrobot/jwebrobot.jar

RUN chown -R seluser:seluser /opt/jwebrobot && \
    chown -R seluser:seluser $JWEBROBOT_SCENARIOS

USER seluser

CMD ["/opt/jwebrobot/entry-point.sh"]
