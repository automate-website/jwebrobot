package website.automate.jwebrobot.executor.filter;

import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import website.automate.jwebrobot.context.ScenarioExecutionContext;
import website.automate.waml.io.model.main.action.FilterAction;

import java.util.List;

@Service
public class ElementsFilter {

    private ElementFilterChain elementFilterChain;

    @Autowired
    public ElementsFilter(ElementFilterChain elementFilterChain){
        this.elementFilterChain = elementFilterChain;
    }

    public <T extends FilterAction> WebElement filter(ScenarioExecutionContext context, T action){
        return getFirstOrNull(elementFilterChain.filter(context, action));
    }

    private WebElement getFirstOrNull(List<WebElement> webElements){
        if(webElements.isEmpty()){
            return null;
        }
        return webElements.get(0);
    }
}
