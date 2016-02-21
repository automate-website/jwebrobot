#!/usr/bin/env bash
if ( [ "$TRAVIS_BRANCH" = 'master' ] || [ ! -z "$TRAVIS_TAG" ] ) && [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
	openssl aes-256-cbc -K $encrypted_69a424f7a752_key -iv $encrypted_69a424f7a752_iv -in .travis/codesigning.asc.enc -out .travis/codesigning.asc -d
	gpg --fast-import .travis/codesigning.asc
fi
