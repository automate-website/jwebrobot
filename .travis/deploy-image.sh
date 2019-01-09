#!/usr/bin/env bash

getVersionedImageName(){
    local prefix=${1}
    if [ "$TRAVIS_BRANCH" == "master" ]; then
        echo "${prefix}"
        return
    fi
    if [ ! -z "$TRAVIS_TAG" ]; then
        echo "${prefix}-${TRAVIS_TAG}"
        return
    fi
}

isDeploy(){
    if [ ! -z "$TRAVIS_TAG" ]; then
        return 0
    fi
    if [ "$TRAVIS_BRANCH" == "master" ]; then
        return 0
    fi
    return 1
}

deployImage(){
    local imageName="${1}"
    local versionedImageName="$(getVersionedImageName $imageName)"
    docker tag "${imageName}" "${versionedImageName}"
    docker push "${versionedImageName}"
}

if isDeploy; then
    docker login -u="$DOCKER_USERNAME" -p="$DOCKER_PASSWORD"
    deployImage 'automatewebsite/jwebrobot'
    deployImage 'automatewebsite/jwebrobot:standalone-chrome'
    deployImage 'automatewebsite/jwebrobot:standalone-firefox'
    deployImage 'automatewebsite/jwebrobot:standalone-chrome-headless'
fi
