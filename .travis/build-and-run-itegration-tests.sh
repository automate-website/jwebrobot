#!/usr/bin/env bash

MAVEN_CLI_OPTS=${MAVEN_CLI_OPTS:-}

runPackageIntegrationTests(){
    docker-compose -f .travis/docker-compose.package-it.yml up -d
    mvn clean verify ${MAVEN_CLI_OPTS}
    docker-compose -f .travis/docker-compose.package-it.yml down
}

runImageIntegrationTests(){
    docker-compose -f .travis/docker-compose.image-it.yml run jwebrobot
    docker-compose -f .travis/docker-compose.image-it.yml down
}

runStandaloneImageIntegrationTests(){
    local imageName=${1}
    docker-compose -f .travis/docker-compose.standalone-image-it.yml build ${imageName}
    docker-compose -f .travis/docker-compose.standalone-image-it.yml run ${imageName}
    docker-compose -f .travis/docker-compose.standalone-image-it.yml down
}

runPackageIntegrationTests
runImageIntegrationTests
runStandaloneImageIntegrationTests 'firefox'
runStandaloneImageIntegrationTests 'chrome'
runStandaloneImageIntegrationTests 'chrome-headless'
