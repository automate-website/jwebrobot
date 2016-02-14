package website.automate.jwebrobot.model;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ActionTest {

    @Mock private Map<String, CriteriaValue> criteriaValueMap;
    
    @InjectMocks private Action action;
    
    private CriteriaValue 
        absentCriterionValue = new CriteriaValue("true"),
        textCriterionValue = new CriteriaValue("text"),
        selectorCriterionValue = new CriteriaValue("selector"),
        valueCriterionValue = new CriteriaValue("value"),
        inputCriterionValue = new CriteriaValue("input"),
        clearCriterionValue = new CriteriaValue("true"),
        ifCriterionValue = new CriteriaValue("if"),
        unlessCriterionValue = new CriteriaValue("unless"),
        scenarioCriterionValue = new CriteriaValue("scenario"),
        urlCriterionValue = new CriteriaValue("url"),
        timeoutCriterionValue = new CriteriaValue("5");
    
    @Test
    public void setAbsentCriterionIsReturned(){
        when(criteriaValueMap.get(CriteriaType.ABSENT.getName())).thenReturn(absentCriterionValue);
        
        assertThat(action.getAbsent(), is(Boolean.TRUE));
    }
    
    @Test
    public void unsetAbsentCriterionResolvesToFalse(){
        assertThat(action.getAbsent(), is(Boolean.FALSE));
    }
    
    @Test
    public void setClearCriterionIsReturned(){
        when(criteriaValueMap.get(CriteriaType.CLEAR.getName())).thenReturn(clearCriterionValue);
        
        assertThat(action.getClear(), is(Boolean.TRUE));
    }
    
    @Test
    public void unsetClearCriterionResolvesToFalse(){
        assertThat(action.getClear(), is(Boolean.FALSE));
    }
    
    @Test
    public void setTextCriterionIsReturned(){
        when(criteriaValueMap.get(CriteriaType.TEXT.getName())).thenReturn(textCriterionValue);
        
        assertThat(action.getText(), is(textCriterionValue.asString()));
    }
    
    @Test
    public void unsetTextCriterionResolvesToNull(){
        assertNull(action.getText());
    }
    
    @Test
    public void setSelectorCriterionIsReturned(){
        when(criteriaValueMap.get(CriteriaType.SELECTOR.getName())).thenReturn(selectorCriterionValue);
        
        assertThat(action.getSelector(), is(selectorCriterionValue.asString()));
    }
    
    @Test
    public void unsetSelectorCriterionResolvesToNull(){
        assertNull(action.getSelector());
    }
    
    @Test
    public void setScenarioCriterionIsReturned(){
        when(criteriaValueMap.get(CriteriaType.SCENARIO.getName())).thenReturn(scenarioCriterionValue);
        
        assertThat(action.getScenario(), is(scenarioCriterionValue.asString()));
    }
    
    @Test
    public void unsetScenarioCriterionResolvesToNull(){
        assertNull(action.getScenario());
    }
    
    @Test
    public void setIfCriterionIsReturned(){
        when(criteriaValueMap.get(CriteriaType.IF.getName())).thenReturn(ifCriterionValue);
        
        assertThat(action.getIf(), is(ifCriterionValue.asString()));
    }
    
    @Test
    public void unsetIfCriterionResolvesToNull(){
        assertNull(action.getIf());
    }
    
    @Test
    public void setUnlessCriterionIsReturned(){
        when(criteriaValueMap.get(CriteriaType.UNLESS.getName())).thenReturn(unlessCriterionValue);
        
        assertThat(action.getUnless(), is(unlessCriterionValue.asString()));
    }
    
    @Test
    public void unsetUnlessCriterionResolvesToNull(){
        assertNull(action.getUnless());
    }
    
    @Test
    public void setUrlCriterionIsReturned(){
        when(criteriaValueMap.get(CriteriaType.URL.getName())).thenReturn(urlCriterionValue);
        
        assertThat(action.getUrl(), is(urlCriterionValue.asString()));
    }
    
    @Test
    public void unsetUrlCriterionResolvesToNull(){
        assertNull(action.getUrl());
    }
    
    @Test
    public void setInputCriterionIsReturned(){
        when(criteriaValueMap.get(CriteriaType.INPUT.getName())).thenReturn(inputCriterionValue);
        
        assertThat(action.getInput(), is(inputCriterionValue.asString()));
    }
    
    @Test
    public void unsetInputCriterionResolvesToNull(){
        assertNull(action.getInput());
    }
    
    @Test
    public void setValueCriterionIsReturned(){
        when(criteriaValueMap.get(CriteriaType.VALUE.getName())).thenReturn(valueCriterionValue);
        
        assertThat(action.getValue(), is(valueCriterionValue.asString()));
    }
    
    @Test
    public void unsetValueCriterionResolvesToNull(){
        assertNull(action.getValue());
    }
    
    @Test
    public void setTimeoutCriterionIsReturned(){
        when(criteriaValueMap.get(CriteriaType.TIMEOUT.getName())).thenReturn(timeoutCriterionValue);
        
        assertThat(action.getTimeout(), is(timeoutCriterionValue.asString()));
    }
    
    @Test
    public void unsetTimeoutCriterionResolvesToNull(){
        assertNull(action.getTimeout());
    }
}
