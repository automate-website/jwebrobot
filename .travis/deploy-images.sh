#!/usr/bin/env bash

set -e

isDeploy(){
    if [ ! -z "$TRAVIS_TAG" ]; then
        return 0
    fi
    if [ "$TRAVIS_BRANCH" == "master" ]; then
        return 0
    fi
    return 1
}

getVersion(){
    if [[ ! -z "$TRAVIS_TAG" ]]; then
        echo "${TRAVIS_TAG}"
    else
        echo 'latest'
    fi
}

deployImage(){
    local imageName="${1}"
    local versionedImageName="${2}"
    docker tag "${imageName}" "${versionedImageName}"
    docker push "${versionedImageName}"
}

if isDeploy; then
    docker login -u="$DOCKER_USERNAME" -p="$DOCKER_PASSWORD"
    version="$(getVersion)"
    deployImage "automatewebsite/jwebrobot" "automatewebsite/jwebrobot:$version"
    deployImage "automatewebsite/jwebrobot:standalone-chrome" "automatewebsite/jwebrobot:$version-standalone-chrome"
    deployImage "automatewebsite/jwebrobot:standalone-firefox" "automatewebsite/jwebrobot:$version-standalone-firefox"
    deployImage "automatewebsite/jwebrobot:standalone-chrome-headless" "automatewebsite/jwebrobot:$version-standalone-chrome-headless"
fi
