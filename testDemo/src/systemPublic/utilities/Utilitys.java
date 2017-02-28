package systemPublic.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

	public class Utilitys {
	
		//窗口焦点转向
		public static void SwitchToWindow(WebDriver driver,String pageTitle){
		 Set<String> allWindowsIds = driver.getWindowHandles();
		   for(String windowId:allWindowsIds){
		    	if(driver.switchTo().window(windowId).getTitle().contains(pageTitle)){
		    		driver.switchTo().window(windowId);
		    		break;
		    	}
		    }
	}
	
		//读取property文件
		public static Properties getPropertiesFile(String propName){
		
			Properties pp = new Properties();
	
		try {
		
			String applicationPath  = new File("").getCanonicalPath();
			String 	websiteURL = applicationPath.replace('\\', '/')+"/WebRoot/WEB-INF/"+propName;
			pp.load(new FileInputStream(websiteURL));
		
		} catch (IOException e) {
			e.printStackTrace();
		}
				return pp;
		}
	
		public static String subClassName(String className){
			String str = 	className.substring(className.lastIndexOf(".")+1,className.length());
			return str;
		}
	
		//获取当前应用名
		public static String applicationPath(){
			try {
				return  new File("").getCanonicalPath();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		
		}
	
		//获取火狐39.0版本
		public static WebDriver  getFirefoxDriver(String uri){
			System.setProperty("webdriver.firefox.bin","D:/程序/Firefox 39/firefox.exe");
			WebDriver driver = new FirefoxDriver();
			driver.get(uri);
			return driver;
		}
	
		//获取远程浏览器
		public static WebDriver getRemoteNodeBrowserDriver(String openUri,String BrowserType){
			try {
					WebDriver driver;
					if(BrowserType.equals("firefox")){
			 		  DesiredCapabilities firefoxDC = DesiredCapabilities.firefox();
					/*capability.setBrowserName("firefox");
			          capability.setPlatform(org.openqa.selenium.Platform.WINDOWS);
					  capability.setVersion("39.0");*/
					
						driver = new RemoteWebDriver(new URL(Utilitys.getPropertiesFile("init.properties").getProperty("firefoxURL")), firefoxDC);
						driver.get(openUri);
						return driver;
						
					}
					if(BrowserType.equals("chrome")){
					//	System.setProperty("webdriver.chrome.driver", "F:/Java/class_lib/SeleniumGridHub/chromedriver_win.exe");
						System.out.println("chrome");
						DesiredCapabilities chromeDC = DesiredCapabilities.chrome();
						
						driver = new RemoteWebDriver(new URL(Utilitys.getPropertiesFile("init.properties").getProperty("chromeURL")),chromeDC);
						driver.get(openUri);
						return driver;
					}
					
					if(BrowserType.equals("ie")){
						DesiredCapabilities ieDC = DesiredCapabilities.internetExplorer();
						System.setProperty("webdriver.ie.driver", "F:\\Java\\class_lib\\SeleniumGridHub\\IEDriverServer_win64.exe");
						driver = new RemoteWebDriver(new URL(Utilitys.getPropertiesFile("init.properties").getProperty("ieURL")),ieDC);
						driver.get(openUri);
						return driver;
					}
					
					return null;
			
			}catch (Exception e) {
						e.printStackTrace();
						return null;
					}
					
			 	
			 	
		}
	//截屏
	public static void ScreenShot(String dirpath,WebDriver driver){
		 try {
			 SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
			 String str = format.format(new Date()).toString();
			 String screenName = str+".jpg";
			 File dir = new File(dirpath);
			 if(!dir.exists()){
				 dir.mkdir();
			 }
			 String screenPath = dir.getAbsolutePath()+"/"+screenName;
			 File srcFile =  ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE); 
			 FileUtils.copyFile(srcFile, new File(screenPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//执行js的替换参数
	public static List<Object> getArguments(Map<?,?> param,String str){
		 List<Object> arguments = new ArrayList<Object>();
		if(str.contains(";")){
		String[] strs = str.split(";");
		 for(String s:strs){
		 arguments.add(param.get(s));
		 }
		
		}else {
		 arguments.add(param.get(str));
		}
		 return arguments;
	}
	
	//获取字符串数组，用于替换查找元素中的动态%s
	public static String[] getStringArray(Map<?,?> param,String str){
		 List<String> arguments = new ArrayList<String>();
			if(str.contains(";")){
			String[] strs = str.split(";");
			 for(String s:strs){
			 arguments.add((String) param.get(s));
			 }
			
			}else {
			 arguments.add((String) param.get(str));
			}
			String[] stt = new String[arguments.size()];
			stt = arguments.toArray(stt);	
			return stt;
	}
}
