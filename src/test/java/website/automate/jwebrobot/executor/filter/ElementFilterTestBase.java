package website.automate.jwebrobot.executor.filter;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public abstract class ElementFilterTestBase {

    private static final String DEFAULT_BASE_URL = "https://www.wikipedia.org/";
    
    private WebDriver webDriver;
    
    private WebElement body;
    
    @Before
    public void init(){
        webDriver  = new FirefoxDriver();
        webDriver.get(getBaseUrl());
        body = webDriver.findElement(By.tagName("html"));
    }
    
    @After
    public void destroy(){
        webDriver.close();
    }
    
    protected String getBaseUrl(){
        return DEFAULT_BASE_URL;
    }
    
    protected WebElement getBody() {
        return body;
    }
}
