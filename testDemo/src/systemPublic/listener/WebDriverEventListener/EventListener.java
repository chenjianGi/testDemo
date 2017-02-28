package systemPublic.listener.WebDriverEventListener;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EventListener extends AbstractWebDriverEventListener{

	//alert窗口统一设置
	@Override
	public void afterClickOn(WebElement element, WebDriver driver) {
		boolean flag = false;
		 Alert alert = null;
	        try {

	            new WebDriverWait(driver, 4).until(ExpectedConditions
	                    .alertIsPresent());
	            alert = driver.switchTo().alert();
	            flag = true;
	        } catch (NoAlertPresentException e) {
	        		e.printStackTrace();
	        }

	        if (flag) {
	            alert.accept();
	        }

	}
	
}
