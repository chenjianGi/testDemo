package chenjian.tests;




import java.util.Map;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import chenjian.bean.pageObject.AddFilePage;
import chenjian.bean.pageObject.AdminHomePage;
import chenjian.bean.pageObject.AdminLoginPage;
import chenjian.bean.pageObject.IndexPage;
import chenjian.bean.pageObject.NewInfoPage;
import chenjian.bean.pageObject.New_conPage;

import systemPublic.bean.Assertion.Assertion;
import systemPublic.listener.IReporter.MyReporter;
import systemPublic.listener.ITestListener.AssertionListener;
import systemPublic.listener.ITestListener.ScreenShotListener;
import systemPublic.testDataProvider.TestDataProvider;
import systemPublic.utilities.Utilitys;

/**
 	后台添加一条记录，前台能显示出来
 */


@Listeners({MyReporter.class,AssertionListener.class})
public class AddNews extends TestDataProvider {
	
	


	
	
	@Test(dataProvider="chenjian")
	
	public void addOne(Map<?,?> param) throws InterruptedException{
					
				
					
				//获取浏览器driver
				driver = Utilitys.getRemoteNodeBrowserDriver(Utilitys.getPropertiesFile("init.properties").getProperty("admin_uri"), 
						(String)param.get("browser"));
				
				//登录
				new AdminLoginPage(driver,true).Login();
				
				//来到AdminHomePage页面，点击新建按钮
			     AdminHomePage ahp = 	new AdminHomePage(driver,true);
			     ahp.getNewOne().click();
			     
				//焦点转向NewInfoPage页面
				 Utilitys.SwitchToWindow(driver, NewInfoPage.pageTitle);
				 
				 //新建NewInfoPage页面对象
				 NewInfoPage nip = new NewInfoPage(driver, true);
				
				 //设置标题
				 nip.getTitle().sendKeys((String) param.get("title"));
				
				 //设置内容
				 nip.getContent().sendKeys((String) param.get("content"));
				
				 //点击上传,弹出AddFile页面
				 nip.getAddFile().click();
				
				 //焦点转向AddFile页面
				 Utilitys.SwitchToWindow(driver, AddFilePage.pageTitle);
				
				 //新建AddFilePage页面对象
				 AddFilePage afp = new AddFilePage(driver, true);
				
				 //设置上传图片路径
				 afp.getScan().sendKeys((String) param.get("imgSrc"));
				
				 //点击确定
				 afp.getConfirm().click();
				 
				 //焦点转回NewInfoPage页面
				 driver.switchTo().window(nip.windowHandle);
				 
				 //点击确认，弹出"发布成功"alert窗口
				 nip.getConfirm().click();
				
				 
				 //焦点转向alert窗口并点击确认关闭alert窗口
				 driver.switchTo().alert().accept();
				
				 //焦点转回AdminHomePage页面
				 driver.switchTo().window(ahp.windowHandle);
				
				//之前得到的元素因为driver的转向而not found in the cache,所以重构页面对象
				 ahp = new AdminHomePage(driver, true);
				 
				 //根据标题关联选择序号
				 ahp.yamlParse.getDynamicElement("sequence", Utilitys.getStringArray(param, "title"));
				 
				 //点击查看按钮,弹出最新动态维护页面
				 ahp.getCheck().click();
				 
				 //转向该页面
				 Utilitys.SwitchToWindow(driver, NewInfoPage.pageTitle);
				 
				 //新建页面对象,因为页面的“确定”按钮在查看页面时是被隐藏的，所以不全局设置
				 nip = new NewInfoPage(driver,false);
				
				 
				 //判断输入的信息,查看页面元素，发现内容是设置在属性value中的
				 Assertion.verifyEquals(nip.yamlParse.getElement("title").getAttribute("value"),(String)param.get("title"));
				 Assertion.verifyEquals(nip.yamlParse.getElement("content").getAttribute("value"),(String) param.get("content"));
				 
				 
				//转向前台
				 driver.get(Utilitys.getPropertiesFile("init.properties").getProperty("front_uri"));
				 driver.manage().window().maximize();
				 
				//点击滚动图片下的链接,转向详细页面
				 new IndexPage(driver,false).yamlParse.getElement("newsBoxLink", Utilitys.getStringArray(param, "title")).click();
				 Utilitys.SwitchToWindow(driver, New_conPage.pageTitle);
				 
				 //新建页面对象
				 New_conPage ncp = new New_conPage(driver, true);
				 
				 //判断输入信息
				 Assertion.verifyEquals(ncp.getP_title().getText(), (String)param.get("title"));
				 Assertion.verifyEquals(ncp.getP_content().getText(),(String)param.get("content"));
				//判断图片是否存在
				 Assertion.verifyTrue(ncp.yamlParse.IsElementExist("s_img"));
				//截图
				 Utilitys.ScreenShot(Utilitys.getPropertiesFile("init.properties").getProperty("screenShotSrcforCheck"), driver);
		}
	

	
}