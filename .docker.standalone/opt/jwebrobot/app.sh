#!/bin/bash

set -e

# constants
WORKING_DIR="${JWEBROBOT_SCENARIOS}"
SCENARIO_PATH="$WORKING_DIR"
REPORT_PATH="$WORKING_DIR/report"

# defaults
BROWSER="${BROWSER:-chrome-headless}"
SCREENSHOT_FORMAT="${SCREENSHOT_FORMAT:-png}"
GEOMETRY="$SCREEN_WIDTH""x""$SCREEN_HEIGHT""x""$SCREEN_DEPTH"
TAKE_SCREENSHOTS="${TAKE_SCREENSHOTS:-ON_EVERY_STEP}"
TIMEOUT="${TIMEOUT:-5}"

if [ -z "$CONTEXT" ]; then
    CONTEXT_PARAM=''
else
	CONTEXT_PARAM="-context $CONTEXT"
fi

echo "Using browser $BROWSER."

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
    preHandleTestPayload "$PULL_URL" "$SCENARIO_PATH"
fi

echo "Starting xvfb..."

mkdir -p $REPORT_PATH

cd $JWEBROBOT_SCENARIOS

xvfb-run -n 99 --server-args="-screen 0 $GEOMETRY -ac +extension RANDR" \
    java $JWR_OPTS -jar /opt/jwebrobot/jwebrobot.jar \
    	-browser "$BROWSER" \
    	-scenarioPath "$SCENARIO_PATH" \
    	-screenshotPath "$REPORT_PATH/" \
		-screenshotFormat "$SCREENSHOT_FORMAT" \
    	-takeScreenshots "$TAKE_SCREENSHOTS" \
    	-reportPath "$REPORT_PATH/report.yaml" \
    	-timeout "$TIMEOUT" \
#    	-maximizeWindow true \
		$CONTEXT_PARAM &

NODE_PID=$!

echo "Process $NODE_PID started. Waiting until it has finished..."
trap shutdown SIGTERM SIGINT

if [ -n "$PUSH_URL" ]; then
    postHandleResultPayload "$PUSH_URL" "$REPORT_PATH"
fi
