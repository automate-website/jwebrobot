#!/usr/bin/env bash

set -e

MAVEN_CLI_OPTS=${MAVEN_CLI_OPTS:-}

allowWriteToTestReportDir(){
    mkdir -p .travis/test/report
    chmod -R 777 .travis/test/report
}

cleanupTestReportDir(){
    rm -rf .travis/test/report
}

runPackageIntegrationTests(){
    docker-compose -f .travis/docker-compose.package-it.yml up -d
    mvn clean verify ${MAVEN_CLI_OPTS}
    docker-compose -f .travis/docker-compose.package-it.yml down
}

runImageIntegrationTests(){
    echo "Run image integration tests."
    docker-compose -f .travis/docker-compose.image-it.yml run jwebrobot
    docker-compose -f .travis/docker-compose.image-it.yml down
}

runStandaloneImageIntegrationTests(){
    local browser="$1"

    echo "Run standalone image ${browser} integration tests."

    exportVariables "$browser"

    docker-compose -f .travis/docker-compose.standalone-image-it.yml build "jwebrobot-standalone"
    docker-compose -f .travis/docker-compose.standalone-image-it.yml run "jwebrobot-standalone"
    docker-compose -f .travis/docker-compose.standalone-image-it.yml down
}

exportVariables(){
    local browser="$1"

    if [[ "$browser" == "chrome-headless" ]]; then
        export BROWSER_TYPE="chrome"
    else
        export BROWSER_TYPE="$browser"
    fi

    export BROWSER="$browser"
}

runPackageIntegrationTests

allowWriteToTestReportDir

runImageIntegrationTests
cleanupTestReportDir

runStandaloneImageIntegrationTests 'firefox'
cleanupTestReportDir

runStandaloneImageIntegrationTests 'chrome'
cleanupTestReportDir

runStandaloneImageIntegrationTests 'chrome-headless'
cleanupTestReportDir
