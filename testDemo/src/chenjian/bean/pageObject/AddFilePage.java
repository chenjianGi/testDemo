package chenjian.bean.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import systemPublic.bean.pageObject.PageObject;
import systemPublic.utilities.Utilitys;

public class AddFilePage  extends PageObject{

	public static String getPageTitle() {
		return pageTitle;
	}
	public static void setPageTitle(String pageTitle) {
		AddFilePage.pageTitle = pageTitle;
	}
	public WebElement getScan() {
		return scan;
	}
	public void setScan(WebElement scan) {
		this.scan = scan;
	}
	public WebElement getConfirm() {
		return confirm;
	}
	public void setConfirm(WebElement confirm) {
		this.confirm = confirm;
	}
	public WebElement getCancel() {
		return cancel;
	}
	public void setCancel(WebElement cancel) {
		this.cancel = cancel;
	}
	public AddFilePage(WebDriver driver, boolean s) {
		super(driver, s);
	}
	
	
	public static String pageTitle = "上传附件";
	
	private WebElement scan;
	private WebElement confirm;
	private WebElement cancel;
	
	
	public static void main(String[] args){
		new AddFilePage(Utilitys.getFirefoxDriver("http://192.168.248.132:8080/jskxWeb/admin/addFile.jsp"), true);
	}
	
}
