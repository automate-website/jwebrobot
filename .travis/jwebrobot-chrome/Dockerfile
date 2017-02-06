FROM selenium/node-chrome:latest

MAINTAINER automate.website <hello@automate.website>

ARG JWEBROBOT_SCENARIOS

ENV JWEBROBOT_SCENARIOS /var/jwebrobot
ENV JWEBROBOT_BROWSER chrome
ENV JWEBROBOT_OPTS ""

ENV SCREEN_WIDTH 1366
ENV SCREEN_HEIGHT 768
ENV SCREEN_DEPTH 16
ENV JAVA_OPTS ""

VOLUME ["$JWEBROBOT_SCENARIOS"]

USER root

COPY entry_point.sh /opt/jwebrobot/entry_point.sh
RUN chmod +x /opt/jwebrobot/entry_point.sh

COPY jwebrobot.jar /opt/jwebrobot/jwebrobot.jar

RUN chown -R seluser:seluser /opt/jwebrobot
USER seluser

CMD ["/opt/jwebrobot/entry_point.sh"]
