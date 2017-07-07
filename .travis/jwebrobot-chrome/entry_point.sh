#!/bin/bash

export GEOMETRY="$SCREEN_WIDTH""x""$SCREEN_HEIGHT""x""$SCREEN_DEPTH"

function shutdown {
  kill -s SIGTERM $NODE_PID
  wait $NODE_PID
}

if [ ! -z "JWEBROBOT_OPTS" ]; then
  echo "Appending JWebRobot options: ${JWEBROBOT_OPTS}"
fi

SERVERNUM=99

rm -f /tmp/.X*lock

JWEBROBOT_SCENARIOS_DIR=/var/jwebrobot
chown -R $UID:$UID ${JWEBROBOT_SCENARIOS_DIR}
JWEBROBOT_REPORT_DIR=$JWEBROBOT_SCENARIOS_DIR/report

mkdir -p $JWEBROBOT_REPORT_DIR
chown -R $UID:$UID ${JWEBROBOT_REPORT_DIR}

JWEBROBOT_REPORT_FILE=$JWEBROBOT_REPORT_DIR/report.yaml

cd $JWEBROBOT_SCENARIOS

xvfb-run -n $SERVERNUM --server-args="-screen 0 $GEOMETRY -ac +extension RANDR" \
  java ${JAVA_OPTS} -jar /opt/jwebrobot/jwebrobot.jar \
    -browser $JWEBROBOT_BROWSER \
    -scenarioPath $JWEBROBOT_SCENARIOS_DIR \
    -maximizeWindow true \
    -reportPath $JWEBROBOT_REPORT_FILE \
    -screenshotPath $JWEBROBOT_REPORT_DIR \
    ${JWEBROBOT_OPTS} &

NODE_PID=$!

trap shutdown SIGTERM SIGINT
wait $NODE_PID
