package chenjian.bean.pageObject;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


import systemPublic.bean.pageObject.PageObject;
import systemPublic.utilities.Utilitys;








/**
 * 后台登录页面
 * */
public class AdminLoginPage extends PageObject {	
		



	public AdminLoginPage(WebDriver driver, boolean s) {
		super(driver, s);
	}



	public static String pageTitle = "江苏省青年科学家年会后台管理" ;
	
	
	


/**
 * 	注意:	页面元素名设置 要和yaml中相同 ,方便setAll定位所有元素
 * */
	//用户名输入框
		private WebElement userName;
		//密码输入框
		private WebElement userPass;
		//登录按钮
		private WebElement login;
		//重置按钮
		private WebElement reset;
	
	
	  
		
		
		
		//登录行为
		  public void Login(){
			  this.userName.sendKeys(Utilitys.getPropertiesFile("init.properties").getProperty("user"));
			  this.userPass.sendKeys(Utilitys.getPropertiesFile("init.properties").getProperty("password"));
			  this.login.click();
		  }
		  
		  
	  
	  
		
		
	
	  
	  
	  
	  
	
	  
	  
	  
	  
	
	  
	  
	  
	  
	  
	public WebElement getUserName() {
		return userName;
	}
	public void setUserName(WebElement userName) {
		this.userName = userName;
	}
	public WebElement getUserPass() {
		return userPass;
	}
	public void setUserPass(WebElement userPass) {
		this.userPass = userPass;
	}
	public WebElement getLogin() {
		return login;
	}
	public void setLogin(WebElement login) {
		this.login = login;
	}
	public WebElement getReset() {
		return reset;
	}
	public void setReset(WebElement reset) {
		this.reset = reset;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args){
	 System.setProperty("webdriver.firefox.bin","D:/程序/Firefox 39/firefox.exe");
   	   WebDriver driver = new FirefoxDriver();
   	   driver.get("http://192.168.248.132:8080/jskxWeb/admin/login.jsp");
   	   
   	  AdminLoginPage alg = new AdminLoginPage( driver,true);
   	  alg.Login();
		
	}



	
}
