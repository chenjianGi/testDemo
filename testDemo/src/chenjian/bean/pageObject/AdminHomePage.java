package chenjian.bean.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import systemPublic.bean.pageObject.PageObject;
import systemPublic.utilities.Utilitys;

/**
 * 		后台主界面
 */

public class AdminHomePage  extends PageObject {

	
	
	public static String getPageTitle() {
		return pageTitle;
	}

	public static void setPageTitle(String pageTitle) {
		AdminHomePage.pageTitle = pageTitle;
	}

	public WebElement getCheck() {
		return check;
	}

	public void setCheck(WebElement check) {
		this.check = check;
	}

	public WebElement getQuery() {
		return query;
	}

	public void setQuery(WebElement query) {
		this.query = query;
	}

	public AdminHomePage(WebDriver driver, boolean s) {
		super(driver, s);
	}

	public WebElement getNewOne() {
		return newOne;
	}

	public void setNewOne(WebElement newOne) {
		this.newOne = newOne;
	}

	

	public static String pageTitle = "江苏省青年科学家年会后台管理" ;
	//新建按钮
	private WebElement newOne;
	//查看按钮
	private WebElement check;
	//查询按钮
	private WebElement query;
	//第一条信息前的序号按钮
	private WebElement number;
	
	public static void main(String[] args){
		//等待15秒后无报错，说明yaml文件与本页面对象元素一一对应，为什么是15秒，因为我在init.properties中设置的查找元素的最大响应时间就是15秒
		//后期我想要添加包含用户登录信息的cookies，这样不用先手动登录了
		new AdminHomePage(Utilitys.getFirefoxDriver("http://192.168.248.132:8080/jskxWeb/admin/home.jsp?userName=admin"), true);
	}

	public WebElement getNumber() {
		return number;
	}

	public void setNumber(WebElement number) {
		this.number = number;
	}
}
