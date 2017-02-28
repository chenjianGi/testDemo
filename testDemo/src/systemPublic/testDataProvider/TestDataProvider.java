package systemPublic.testDataProvider;


import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;



import systemPublic.utilities.Utilitys;
import systemPublic.utilities.XmlParser;



	public class TestDataProvider {
		@SuppressWarnings("rawtypes")
		private List l;
		 
		protected WebDriver driver;
		
		
		public WebDriver getDriver(){
				return driver;
		}
		
		public TestDataProvider(){
			
			String xmlSrc = Utilitys.applicationPath()+Utilitys.getPropertiesFile("init.properties").getProperty("dataXmlSrc");
			
			this.getXmlData(xmlSrc+Utilitys.subClassName(this.getClass().getName())+".xml");
		}
		
		public void getXmlData(String path){
			
			XmlParser p = new XmlParser();
			l = p.parser3Xml(new File(path).getAbsolutePath());
		}
		
		//注意：这儿与之前的数据提供者有不同，这个方法含 参数method(就是数据接收者@Test的方法名)，我以前
		//写的是 通过@Test的方法名 来过滤 xml的数据 让标签 同方法名对应 ，我觉得这样有点繁琐，适用场景不是很多
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@DataProvider
		public Object[][] chenjian(Method method){
			
			List<Map<String,String>> result = new ArrayList<Map<String,String>>();
			
			for(int i=0;i<l.size();i++){
				Map m = (Map)l.get(i);
				if(m.containsKey(method.getName())){
					Map<String,String> dm =(Map<String,String>) m.get(method.getName());
					result.add(dm);
				}
			}
			
			Object[][] files= new Object[result.size()][];
			
			for(int i=0;i<result.size();i++){
				files[i] = new Object[]{result.get(i)};
			}
			
			return files;
		}
		
		@BeforeTest
		public void setUp(){
			
		}
		
		@AfterTest
		public void teardown(){
			driver.quit();
		}
	}

