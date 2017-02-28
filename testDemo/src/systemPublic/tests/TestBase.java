package systemPublic.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.BeforeTest;

import systemPublic.listener.WebDriverEventListener.EventListener;
import systemPublic.testDataProvider.TestDataProvider;
import systemPublic.utilities.Utilitys;

public class TestBase {
	WebDriver driver;
	String uri = Utilitys.getPropertiesFile("init.properties").getProperty("url");
	public WebDriver getDriver(){
		return driver;
	}
	
	
	
	@BeforeTest
	public void  SetUp(){
		System.setProperty("webdriver.firefox.bin", "D:/程序/Firefox 39/firefox.exe");
		
	//	DesiredCapabilities dc =  new DesiredCapabilities();
	//	dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
		driver = new FirefoxDriver();
		
		EventFiringWebDriver eventFiringDriver = new EventFiringWebDriver(driver);
		EventListener afterClickOn = new EventListener();
		eventFiringDriver.register(afterClickOn);
		driver = eventFiringDriver;
		driver.get(uri);
		
	}
	/*@BeforeTest
	public void  SetUp1(){
		System.setProperty("webdriver.chrome.driver", "F:\\Java\\class_lib\\SeleniumGridHub\\chromedriver_win.exe");
		driver = new ChromeDriver();
		driver.get(uri);
		
	}*/
	/*@BeforeTest
	public void  SetUp2(){
		System.setProperty("webdriver.ie.driver", "F:/Java/class_lib/SeleniumGridHub/IEDriverServer_win64.exe");
		driver = new InternetExplorerDriver();
		driver.get(uri);
		
	}*/
	
}
