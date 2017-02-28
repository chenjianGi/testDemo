package chenjian.bean.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import systemPublic.bean.pageObject.PageObject;

public class New_conPage extends PageObject{

	
	public static String getPageTitle() {
		return pageTitle;
	}
	public static void setPageTitle(String pageTitle) {
		New_conPage.pageTitle = pageTitle;
	}
	public WebElement getP_title() {
		return p_title;
	}
	public void setP_title(WebElement p_title) {
		this.p_title = p_title;
	}
	public WebElement getP_content() {
		return p_content;
	}
	public void setP_content(WebElement p_content) {
		this.p_content = p_content;
	}
	public WebElement getP_img() {
		return p_img;
	}
	public void setP_img(WebElement p_img) {
		this.p_img = p_img;
	}
	public New_conPage(WebDriver driver, boolean s) {
		super(driver, s);
	}
	
	public static String pageTitle = "第五届江苏省青年科学家年会-最新动态";
	private WebElement p_title;
	private WebElement p_content;
	private WebElement p_img;
	
}
