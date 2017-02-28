package chenjian.bean.pageObject;

import org.openqa.selenium.WebDriver;


import systemPublic.bean.pageObject.PageObject;

public class IndexPage  extends PageObject{


	public IndexPage(WebDriver driver, boolean s) {
		super(driver, s);
	}
	
	
	public static String pageTitle = "第五届江苏省青年科学家年会";
	
	
}
