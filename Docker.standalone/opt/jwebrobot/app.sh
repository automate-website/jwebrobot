#!/bin/bash

set -e

JWEBROBOT_ARGS="$JWEBROBOT_ARGS $@"

function preHandleTestPayload(){
	local sourceUrl=$1
	local workingDir=$2
	local testPayloadPath="$workingDir/test-payload.zip"

	mkdir -p "$workingDir"
	echo "Downloading the execution payload"
	wget -O "$testPayloadPath" "$sourceUrl"

	echo "Execution payload downloaded:"
	ls -l "$testPayloadPath"

	echo "Unzipping artifact..."
	unzip -o "$testPayloadPath" -d "$workingDir"
}

function postHandleResultPayload(){
	local targetUrl=$1
	local workingDir=$2
	local resultPayloadPath="$workingDir/test-results.zip"

	echo "Zipping the result file..."
	zip -j "$resultPayloadPath" "$workingDir/"*
	ls -l "$resultPayloadPath"

	echo "Pushing results..."
	curl --retry 5 --retry-max-time 300 --connect-timeout 30 -F "file=@$resultPayloadPath" "$targetUrl"
}

function shutdown {
    kill -s SIGTERM $NODE_PID
    wait $NODE_PID
}

if [ -n "$PULL_URL" ]; then
    preHandleTestPayload "$PULL_URL" "$SCENARIO_DIR"
fi

if [[ $BROWSER == *-headless ]]; then
    java $JWR_OPTS -jar /opt/jwebrobot/app.jar ${JWEBROBOT_ARGS}
else
    xvfb-run --server-args="-screen 0 $GEOMETRY -ac +extension RANDR" \
        java $JWR_OPTS -jar /opt/jwebrobot/app.jar ${JWEBROBOT_ARGS}
fi

if [ -n "$PUSH_URL" ]; then
    postHandleResultPayload "$PUSH_URL" "$REPORT_DIR"
fi
