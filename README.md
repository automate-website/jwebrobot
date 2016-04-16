# JWebRobot

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/website.automate/jwebrobot/badge.svg)](https://maven-badges.herokuapp.com/maven-central/website.automate/jwebrobot) [![Build Status](https://travis-ci.org/automate-website/jwebrobot.svg?branch=master)](https://travis-ci.org/automate-website/jwebrobot) [![codecov.io](https://codecov.io/github/automate-website/jwebrobot/coverage.svg?branch=master)](https://codecov.io/github/automate-website/jwebrobot?branch=master) [![Gitter](https://badges.gitter.im/automate-website/jwebrobot.svg)](https://gitter.im/automate-website/jwebrobot?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)

## Short Description

**JWebRobot** is the reference implementation of [web automation markup language]. Currently the implementation is based on the [draft-02] schema.

## References
Refer to the [changelog] for recent notable changes and modifications.

Refer to the [waml-schema] for schema sources.

## Options
Options may be passed to the **JWebRobot** using single hyphen notation:

```
java -jar <path to jar> -<argument name> [<argument value> ...]
```

| Name  | Type | Description | Example |
| ------------- | ------------- | ------------- | ------------- |
| Scenario Path  | mandatory  | Scenario path may be a directory or a single scenario file. | `../path/to/my/scenario` |
| Scenario Pattern  | optional | If set, only non fragment scenarios with a name matching the pattern are executed. | `'^desired-scenario$'` |
| Context  | optional | Context is a multi value argument that populates the context utilized during expression evaluation. | `baseUrl=http://www.wikipedia.com language=en` |
| Timeout  | optional | Default timeout waiting for conditions to be fulfilled in seconds. | `5` |
| Screenshot Path  | optional | Path to the directory where created screenshots must be saved. | `./` |
| Browser  | optional | A browser can be selected by passing this option when running JWebRobot. Please consider that some browsers require additional configuration parameters. WAML scenarios are executed with Mozilla Firefox per default. Firefox must be installed on the same machine. E.g.: Chrome does not provide embedded webdriver so that it has to be [downloaded manually](webdriver-chrome). The 
path to the downloaded executable has to be forwarded via the system property `webdriver.chrome.driver`. Of course, Chrome must be present on the same machine. | `./` |

## Source Build

An executable JAR can be created by executing the _package_ Maven goal:

```
mvn package
```

[webdriver-chrome]: http://chromedriver.storage.googleapis.com/index.html
[changelog]: CHANGELOG.md
[waml-schema]: http://waml-schema.org
[web automation markup language]: https://github.com/automate-website/waml
[draft-02]: http://waml-schema.org/draft-02/schema#