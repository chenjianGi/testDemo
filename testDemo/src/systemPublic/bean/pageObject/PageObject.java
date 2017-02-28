package systemPublic.bean.pageObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;

import systemPublic.utilities.Utilitys;
import systemPublic.utilities.YamlParser;



public abstract class PageObject {
		
		public WebDriver driver;
		public  String windowHandle;
		public YamlParser yamlParse;
		 
		public PageObject(WebDriver driver,boolean s){
			  
					this.driver = driver;
					windowHandle = driver.getWindowHandle();
				    yamlParse = new YamlParser(Utilitys.subClassName(this.getClass().getName()),driver);
				    //为TRUE，定位所以WebElement元素
				    if(s)SetAll();
				 
		 }
		
		
		protected void SetAll(){
			
			Field[] fields = this.getClass().getDeclaredFields();
			  for(Field f:fields){
				  
				if(f.getType().getName().equals("org.openqa.selenium.WebElement"))
					{ 
						f.setAccessible(true);
									//切割类名
									String name = f.getName(); 
								    String Uname = name.substring(0, 1).toUpperCase()+name.substring(1);
								 
								    Method m;
									try {
										m = this.getClass().getMethod("set"+Uname, f.getType());
										m.invoke(this, yamlParse.getElement(name));
									} catch (NoSuchMethodException e) {
										e.printStackTrace();
									} catch (SecurityException e) {
										e.printStackTrace();
									} catch (IllegalAccessException e) {
										e.printStackTrace();
									} catch (IllegalArgumentException e) {
										e.printStackTrace();
									} catch (InvocationTargetException e) {
										e.printStackTrace();
									}
							   
								    
								
							
				  	    f.setAccessible(false);
					}
			  }
		};
	   
}
