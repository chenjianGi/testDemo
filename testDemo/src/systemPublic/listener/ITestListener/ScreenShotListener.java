package systemPublic.listener.ITestListener;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import systemPublic.utilities.Utilitys;

import chenjian.tests.AddNews;


//错误时调用driver截图，如果是driver错误则无效
public class ScreenShotListener extends TestListenerAdapter{

	@Override
	public void onTestFailure(ITestResult tr) {
			System.out.println("screenShot");
			AddNews ad = (AddNews) tr.getInstance();
		   Utilitys.ScreenShot(Utilitys.getPropertiesFile("init.properties").getProperty("screenShotSrc"), ad.getDriver());
		
	}
		
}
