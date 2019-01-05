#!/usr/bin/env bash

if [ "$TRAVIS_BRANCH" == "master" ]; then
    docker login -u="$DOCKER_USERNAME" -p="$DOCKER_PASSWORD"
    docker push automatewebsite/jwebrobot
    docker push automatewebsite/jwebrobot-standalone-chrome
fi

if [ ! -z "$TRAVIS_TAG" ]; then
    docker login -u="$DOCKER_USERNAME" -p="$DOCKER_PASSWORD"
    docker tag automatewebsite/jwebrobot automatewebsite/jwebrobot:$TRAVIS_TAG
    docker tag automatewebsite/jwebrobot-standalone-chrome automatewebsite/jwebrobot:$TRAVIS_TAG-standalone-chrome
    docker push automatewebsite/jwebrobot:$TRAVIS_TAG
    docker push automatewebsite/jwebrobot:$TRAVIS_TAG-standalone-chrome;
fi
