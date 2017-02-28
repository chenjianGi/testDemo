package chenjian.bean.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import systemPublic.bean.pageObject.PageObject;
import systemPublic.utilities.Utilitys;

/**
 * 		信息详细页面
 * 		后台“新建”，“修改”，“删除”，“查看”共用了这个页面
 * */


public class NewInfoPage extends PageObject{

	public static String getPageTitle() {
		return pageTitle;
	}
	public static void setPageTitle(String pageTitle) {
		NewInfoPage.pageTitle = pageTitle;
	}
	public WebElement getTitle() {
		return title;
	}
	public void setTitle(WebElement title) {
		this.title = title;
	}
	public WebElement getContent() {
		return content;
	}
	public void setContent(WebElement content) {
		this.content = content;
	}
	public WebElement getAddFile() {
		return addFile;
	}
	public void setAddFile(WebElement addFile) {
		this.addFile = addFile;
	}
	public WebElement getConfirm() {
		return confirm;
	}
	public void setConfirm(WebElement confirm) {
		this.confirm = confirm;
	}
	public WebElement getClose() {
		return close;
	}
	public void setClose(WebElement close) {
		this.close = close;
	}
	public NewInfoPage(WebDriver driver, boolean s) {
		super(driver, s);
	}
	
	public static String pageTitle ="最新动态维护";
	
		//标题输入框
		private WebElement title;
		//内容输入框
		private WebElement content;
		//上传按钮
		private WebElement addFile;
		//确定按钮
		private WebElement confirm;
		//关闭按钮
		private WebElement close;


		public static void main(String[] args){
			new NewInfoPage(Utilitys.getFirefoxDriver("http://192.168.248.132:8080/jskxWeb/admin/newsInfo.jsp"), true) ;
		}

}
