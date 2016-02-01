# WebRobot for Java

[![Build Status](https://travis-ci.org/automate-website/jwebrobot.svg?branch=master)](https://travis-ci.org/automate-website/jwebrobot)

## Building an Executable JAR

An executable JAR can be created by executing the _package_ Maven goal:

```
mvn package
```

## Execution

A scenario can be executed from console by us passing _scenarioPath_ parameter, e.g.:

```
java -jar <path to jar> --scenarioPath=<path to WAML file or WAML project directory>
```

## Browser Support 

A browser can be selected by passing the ```--browser=<browser name>``` property when running JWebRobot. Please consider 
that some browsers require additional configuration parameters.

## Firefox (Default)

WAML scenarios are executed with Mozilla Firefox per default. Firefox must be installed on the same machine.  

### Chrome

Since Chrome does not provide embedded webdriver it has to be [downloaded manually](webdriver-chrome). When execute, the 
path to the downloaded executable has to be passed via the system property ```webdriver.chrome.driver```. Of course, 
Chrome must be present on the same machine.

```
java -jar <path to jar> --scenarioPath=<path to WAML> --browser=chrome -Dwebdriver.chrome.driver=<path to chrome driver>
```

[webdriver-chrome]: http://chromedriver.storage.googleapis.com/index.html
