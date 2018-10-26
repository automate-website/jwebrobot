#!/usr/bin/env bash

JWEBROBOT_JAR=$(ls -1 target/*.jar | grep -E 'target/jwebrobot-[0-9]+\.[0-9]+\.[0-9]+?(-SNAPSHOT)\.jar$')
cp $JWEBROBOT_JAR .travis/jwebrobot-chrome/jwebrobot.jar
docker build -t automatewebsite/jwebrobot .travis/jwebrobot-chrome
JWEBROBOT_TEST_DIR=$TRAVIS_BUILD_DIR/.travis/jwebrobot-docker-test
ls -l $JWEBROBOT_TEST_DIR
chmod -R 777 $JWEBROBOT_TEST_DIR
docker run -v $JWEBROBOT_TEST_DIR:/var/jwebrobot automatewebsite/jwebrobot-chrome
JWEBROBOT_TEST_REPORT_DIR=$JWEBROBOT_TEST_DIR/report
JWEBROBOT_TEST_REPORT_FILES_COUNT=$(ls -1 $JWEBROBOT_TEST_REPORT_DIR | wc -l)
if [ "$JWEBROBOT_TEST_REPORT_FILES_COUNT" < "1" ]; then
    ls -l $JWEBROBOT_TEST_REPORT_DIR;
    exit 1;
    fi
