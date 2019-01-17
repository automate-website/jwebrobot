# JWebRobot – The Reference Implementation of WAML Executor

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/website.automate/jwebrobot/badge.svg)](https://maven-badges.herokuapp.com/maven-central/website.automate/jwebrobot) [![Build Status](https://travis-ci.org/automate-website/jwebrobot.svg?branch=master)](https://travis-ci.org/automate-website/jwebrobot) [![codecov.io](https://codecov.io/github/automate-website/jwebrobot/coverage.svg?branch=master)](https://codecov.io/github/automate-website/jwebrobot?branch=master) [![Gitter](https://badges.gitter.im/automate-website/jwebrobot.svg)](https://gitter.im/automate-website/jwebrobot?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge) [![Docker Hub](https://img.shields.io/docker/pulls/automatewebsite/jwebrobot.svg)](https://hub.docker.com/r/automatewebsite/jwebrobot) 


**JWebRobot** is the reference implementation of executor which processes the [Web Automation Markup Language][waml-git]. Currently the it is based on the [2.0](http://waml-schema.org/2.0/schema#) schema.


## How to Run

There are two options how to execute JWebRobot:

1. In Docker (easiest)
2. Using the JAR

It is assumed that your already have a test scenario in which is stored in 

    /var/scenarios/checkout-test.yaml
     
and having a valid content in [WAML format][waml-git], e.g.

```yaml
name: Checkout Button Presence Test
steps:
  - open: 'https://example.com/checkout'
  - ensure: 'button#buy-now'
```

### Run JWebRobot using Docker

#### Prerequisites

- Get latest `Docker` version available [here](https://docs.docker.com/install/).
- Get latest `Docker Compose` version available [here](https://docs.docker.com/compose/install/)*.
- Get latest `VNC Client` version available [here](https://www.realvnc.com/en/connect/download/viewer/)*. 

_* required for container toolkit only_
#### Run Container Toolkit

Download the [docker-compose](docker-compose.yml).

Run JWebRobot from the console:

```
$ docker-compose run jwebrobot
```

By default the directory of `docker-compose.yml` will be scanned for executable tests (`*.yml`, `*.yaml`).
 

#### Run Standalone Container

For easier bootstrapping, the JWebRobot is [available as Docker image][docker-jwebrobot] 
on Docker Hub. The _automatewebsite/jwebrobot-chrome_ image is based on the official [selenium/node-chrome](https://github.com/SeleniumHQ/docker-selenium/tree/master/NodeChrome)
image.

You can easily execute it by starting the _jwebrobot-chrome_ Docker container 
while the volume`/var/scenarios` is bound to `/var/jwebrobot`:

```bash
$ docker run -v /var/scenarios:/var/jwebrobot automatewebsite/jwebrobot-chrome
```

In complex setup, you may want to pass additional parameters to the container, e.g.:

```bash
$ docker run -v /var/scenarios:/var/jwebrobot \
    -e JWEBROBOT_OPTS="-timeout 10 -takeScreenshots ON_EVERY_STEP" \
    -e SCREEN_WIDTH=800 \
    -e SCREEN_HEIGHT=600 \
    -e JAVA_OPTS="-Dhttp.proxyHost=proxy.example.com -Dhttp.proxyPort=1234" \
    automatewebsite/jwebrobot-chrome 
```
        
The execution report will be stored in `/var/scenarios/report.yaml`.

### Run JWebRobot from JAR

1. Download the latest release from [the releases page][download] or from [Maven repository].
2. Install of the supported browsers (_please consider that browsers usually require a running X server, consider to use 
[Xvfb] if you do not have any_).
3. Download a WebDriver for your browser (e.g. [geckodriver] for Firefox or [ChromeDriver] for Chrome) and save it to 
`/bin` folder.
4. Start execution by passing the browser, the driver path the scenario path and :
 
```bash
$ java \
    -Djwebrobot.browser=firefox \
    -Dwebdriver.gecko.driver=/bin/geckodriver \
    # -Dwebdriver.chrome.driver=/bin/chromedriver \
    # -Dwebdriver.opera.driver=/bin/operadriver \
    -jar jwebrobot-<version>.jar \
    -scenarioPath /var/scenarios
```
This will perform execution using Firefox (communicating via the [gekodriver]) and publish results to 
`report.yaml` in the current folder.

If the path to your browser is not on the default location, you can provide it by passing the following JVM parameter:
    
    -Dwebdriver.firefox.bin="/bin/firefox-unstable"

#### Options
Options may be passed to the **JWebRobot** using single hyphen notation:

```
java -jar <path to jar> -<argument name> [<argument value> ...]
```

| Name  | Type | Description | Default | Example |
| ------------- | ------------- | ------------- | ------------- | ------------- |
| *scenarioPath*  | optional  | Scenario path may be a directory or a single scenario file. | `./` |`../path/to/my/scenario` |
| *scenarioPaths*  | optional | Scenario paths may be a directory, a single scenario file or a set of both. |  |`../path/to/my/scenario` `../path/to/my/another/scenario` |
| *scenarioPattern*  | optional | If set, only non fragment scenarios with a name matching the pattern are executed. | `-` | `'^desired-scenario$'` |
| *context*  | optional | Context is a multi value argument that populates the context utilized during expression evaluation. | `-` |`baseUrl=http://www.wikipedia.com language=en` |
| *timeout* | optional | Timeout waiting for conditions to be fulfilled in seconds. Globally overrides timeout settings defined in the scenarios. | `-` | `5` |
| *screenshotPath*  | optional | Path to the directory where created screenshots must be saved. | `./` | `./` |
| *screenshotType*  | optional | Defines the way screenshots must be taken - fullscreen vs. viewport. | `VIEW_PORT` | `FULLSCREEN` |
| *screenshotFormat*  | optional | Defines the screenshot format. | `png` | `gif` |
| *takeScreenshots*  | optional | Defines when to take screenshots: NEVER, ON_FAILURE, ON_EVERY_STEP | `ON_FAILURE` | `./` |
| *browser*  | optional | A browser can be selected by passing this option when running JWebRobot. Please consider that some browsers require additional configuration parameters. WAML scenarios are executed with Mozilla Firefox per default. Firefox must be installed on the same machine. E.g.: Chrome does not provide embedded webdriver so that it has to be [downloaded manually](webdriver-chrome). The path to the downloaded executable has to be forwarded via the system property `webdriver.chrome.driver`. Of course, Chrome must be present on the same machine. | `firefox` | `chrome`, `chrome-headless`, `opera`  |
| *reportPath*  | optional  | Path to which the report is written to. | `./report.yaml` | `./myreport.yaml` |
| *maximizeWindow* | optional | Toggles window maximization before scenario execution. | `false` | `true` |

## Expressions
Expression are evaluated by the awesome templating engine [SpEL].

| Expression | Context | Result | Description |
| ------------- | ------------- | ------------- | ------------- |
| ${foo} | foo="bar" | bar | Renders the context parameter value |
| ${foo == "bar"} | foo="bar" | true | Tests context parameter foo for having the value "bar" and renders the boolean value. |

### Reserved Namespace

The lodash (`_`) name space is reserved for utility functions.

#### Mock

Mock utility provides an easy way to create test data required during test execution.

Find some examples below:

| Expression | Description |
| ------------- | ------------- |
| ${ _.mock.person().fullName() } | Creates a sane person full name |
| ${ _.mock.person().email() } | Creates a valid email address |
| ${ _.mock.person().telephoneNumber() } | Creates a valid phone number |
| ${ _.mock.company().name() } | Creates a sane company name |

Behind the scenes a powerful test data framework [jfairy] is doing the job. More usage examples may be found there.

### Store Criterion & Element Reference
While using steps containing filter criteria (e.g. `ensure`, `click`, `enter`, `select`, `move`) the reference to the filtered element may be stored using the `store` criterion, e.g.:

    ensure:
        selector: input[type=text]
        store: userEmailInput
    store:
        userEmailInputEnabled: ${userEmailInput.isEnabled()}

Note that the element reference currently exports the underlying [selenium webelement api]. The direct access is an experimental feature and may be subject to change in the future releases.

## IFrame Scoped Elements
A single set of filter criteria can not be applied across multiple documents, thus if the desired element is located within a different document on the same page (e.g. iframe), it might be accessed by pointing to the target document within the parent filter criteria):

    ensure:
        selector: input[type=username]
        parent:
            selector: iframe[src=login]

## Source Build

An executable JAR can be created by executing the _package_ Maven goal:

```
mvn package
```

## References
Refer to the [waml-schema] for schema sources.


[webdriver-chrome]: http://chromedriver.storage.googleapis.com/index.html
[waml-schema]: http://waml-schema.org
[waml-git]: https://github.com/automate-website/waml
[download]: https://github.com/automate-website/jwebrobot/releases
[SpEL]: https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#expressions
[jfairy]: https://github.com/Codearte/jfairy
[selenium webelement api]: https://seleniumhq.github.io/selenium/docs/api/java/org/openqa/selenium/WebElement.html
[docker-jwebrobot]: https://hub.docker.com/r/automatewebsite/jwebrobot-chrome/
[Maven repository]: https://mvnrepository.com/artifact/website.automate/jwebrobot
[geckodriver]: https://github.com/mozilla/geckodriver/releases
[ChromeDriver]: https://sites.google.com/a/chromium.org/chromedriver/downloads
[Xvfb]: https://www.x.org/archive/X11R7.7/doc/man/man1/Xvfb.1.xhtml
