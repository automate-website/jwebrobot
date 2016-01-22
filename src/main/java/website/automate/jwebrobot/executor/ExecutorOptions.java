package website.automate.jwebrobot.executor;

public class ExecutorOptions {
    private WebDriverProvider.Type webDriverType = WebDriverProvider.Type.FIREFOX;

    public WebDriverProvider.Type getWebDriverType() {
        return webDriverType;
    }

    public void setWebDriverType(WebDriverProvider.Type webDriverType) {
        this.webDriverType = webDriverType;
    }
}
