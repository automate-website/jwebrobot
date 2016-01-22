package website.automate.jwebrobot;

import com.beust.jcommander.JCommander;
import com.google.inject.Guice;
import com.google.inject.Injector;
import website.automate.jwebrobot.executor.ScenarioExecutor;
import website.automate.jwebrobot.models.factories.ScenarioFactory;
import website.automate.jwebrobot.models.mapper.actions.ActionMapperModule;
import website.automate.jwebrobot.models.mapper.criteria.CriterionMapperModule;
import website.automate.jwebrobot.models.scenario.Scenario;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * Hello world!
 *
 */
public class JWebRobot {

    public static void main(String[] args) throws FileNotFoundException {
        ConfigurationProperties configurationProperties = new ConfigurationProperties();
        new JCommander(configurationProperties, args);

        System.out.println("Executing: " + configurationProperties.getScenarioFilename());

        Injector injector = configureModules();
        ScenarioFactory scenarioFactory = injector.getInstance(ScenarioFactory.class);
        ScenarioExecutor scenarioExecutor = injector.getInstance(ScenarioExecutor.class);

        InputStream inputStream = new FileInputStream(configurationProperties.getScenarioFilename());
        List<Scenario> scenarioList = scenarioFactory.createFromInputStream(inputStream);

        scenarioExecutor.execute(scenarioList, null, null);


        // Create a new instance of the Firefox driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
//        WebDriver driver = new FirefoxDriver();
//
//        // And now use this to visit Google
//        driver.get("http://www.google.com");
//        // Alternatively the same thing can be done like this
//        // driver.navigate().to("http://www.google.com");
//
//        // Find the text input element by its name
//        WebElement element = driver.findElement(By.name("q"));
//
//        // Enter something to search for
//        element.sendKeys("Cheese!");
//
//        // Now submit the form. WebDriver will find the form for us from the element
//        element.submit();
//
//        // Check the title of the page
//        System.out.println("Page title is: " + driver.getTitle());
//
//        // Google's search is rendered dynamically with JavaScript.
//        // Wait for the page to load, timeout after 10 seconds
//        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
//            public Boolean apply(WebDriver d) {
//                return d.getTitle().toLowerCase().startsWith("cheese!");
//            }
//        });
//
//        // Should see: "cheese! - Google Search"
//        System.out.println("Page title is: " + driver.getTitle());
//
//        //Close the browser
//        driver.quit();
    }

    public static Injector configureModules() {
        Injector injector = Guice.createInjector(
            new ActionMapperModule(),
            new CriterionMapperModule()
        );

        return injector;
    }
}
