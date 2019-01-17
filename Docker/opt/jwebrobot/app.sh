#!/usr/bin/env bash

JWEBROBOT_ARGS=${JWEBROBOT_ARGS:-}
JWEBROBOT_PATH=/opt/jwebrobot/app.jar

java -jar ${JWEBROBOT_PATH} ${JWEBROBOT_ARGS} "$@" 2>/dev/null
