package systemPublic.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.ho.yaml.Yaml;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class YamlParser {
	   public WebDriver driver;
       private String yamlFile;
       private HashMap<String,HashMap<String,String>> ml;
       private HashMap<String,HashMap<String,String>> extendLocator;
       private HashMap<String,HashMap<String,String>> dynamicNodes;
       private int waitTime;
     
       public YamlParser(String yamlFile,WebDriver driver){
          // driver = DriverInstance.getInstance();
    	   this.driver = driver;
    	   this.waitTime = Integer.parseInt(Utilitys.getPropertiesFile("init.properties").getProperty("waitTime"));
           this.yamlFile = yamlFile;
           
           this.getYamlFile();
           this.getDynamicNodes();
       }
      
       
       @SuppressWarnings("unchecked")
	protected void getYamlFile(){
    	  String yamlSrc =Utilitys.applicationPath()+ Utilitys.getPropertiesFile("init.properties").getProperty("yamlSrc");
          File f = new File(yamlSrc+yamlFile+".yaml");
           try{
        	   		
                   ml = Yaml.loadType(new FileInputStream(f.getAbsolutePath()),HashMap.class);
               }catch(FileNotFoundException e){
                    e.printStackTrace();
               }
       }
   
    private  void getDynamicNodes(){
    		dynamicNodes = new HashMap<String, HashMap<String,String>>();
    		Set<String> keys = ml.keySet();
    		for(String key:keys){
    			 if(ml.get(key).get("type").equals("js")){
    				  dynamicNodes.put(key, ml.get(key));
    			 }
    		}
    }
    
    @SuppressWarnings("unchecked")
	public List<WebElement> getDynamicElement(String key,List<Object> param){
    	List<WebElement> elements=null;  
    	
    	JavascriptExecutor jse = (JavascriptExecutor) driver;
    	   this.injectjQueryIfNeeded(jse);
    	   try{
    	   if(dynamicNodes.containsKey(key)){
    		   	String javaScript = dynamicNodes.get(key).get("value");
    		   		if(javaScript.contains("return")){
    		   			 elements = (List<WebElement>) jse.executeScript(javaScript,param);
    		   			
    		   		}else{
    		   			 jse.executeScript(javaScript, param);
    		   		}
    	   }else System.out.println("元素"+key+"在"+yamlFile+".yaml中未设置!");
    	   }catch(Exception e){
    		   System.out.println("执行js脚本出错!");
    		   e.printStackTrace();
    	   }
    	   if(elements==null)  throw new NullPointerException("元素获取异常,值为空!");  
    	   return elements;
    	  
    }
    
 	@SuppressWarnings("unchecked")
	public List<WebElement> getDynamicElement(String key,String[] replace){
       	List<WebElement> elements=null;  
       	try{
       	JavascriptExecutor jse = (JavascriptExecutor) driver;
       	   this.injectjQueryIfNeeded(jse);
       	   if(dynamicNodes.containsKey(key)){
       		   	String value = dynamicNodes.get(key).get("value");
       		   	String javaScript = this.getLocatorString(value, replace);
       		   	System.out.println(javaScript);
       		   		if(javaScript.contains("return")){
       		   			 elements = (List<WebElement>) jse.executeScript(javaScript);
       		   			
       		   		}else{
       		   			 jse.executeScript(javaScript);
       		   		}
       	   }else System.out.println("元素"+key+"在"+yamlFile+".yaml中未设置!");
       	}catch(Exception e){
       		 System.out.println("执行js脚本出错!");
       		 e.printStackTrace();
       	}
       	   if(elements==null)  throw new NullPointerException("元素获取异常,值为空!");  
       	   return elements;
       	  
       }
 	
    private void injectjQueryIfNeeded(JavascriptExecutor jse){
		if(!jQueryLoaded(jse)){
			injectjQuery(jse);
		}
	}
	
	private void injectjQuery(JavascriptExecutor jse) {
		
		jse.executeScript(
				"var headID = document.getElementsByTagName(\"head\")[0];" +
				"var newScript = document.createElement('script');" +
				"newScript.type='text/javascript';" +
				"newScript.src='file:///F:/Java/Dreamweaver/Dreamweaver_workspace/js/js/jquery/jquery.js';" +
				"headID.appendChild(newScript)");
	}

	private boolean jQueryLoaded(JavascriptExecutor jse) {
		Boolean loaded = true;
		try{
			loaded = (Boolean) jse.executeScript("return jQuery()!=null");
			
		}catch(WebDriverException e){
			loaded = false;
		}
		return loaded;
	}
    
       @SuppressWarnings("unchecked")
	public void loadExtendLocator(String fileName){
    	   String yamlSrc = Utilitys.getPropertiesFile("init.properties").getProperty("yamlSrc"); 
           File f =new File(yamlSrc+fileName+".yaml");
           try{
                   extendLocator = Yaml.loadType(new FileInputStream(f.getAbsolutePath()),HashMap.class);
                    ml.putAll(extendLocator);
               }catch(FileNotFoundException e){
                   e.printStackTrace();
               }
       }
      
       public void setLocatorVariableValue(String variable,String value){
              Set<String> keys = ml.keySet();
               for(String key:keys){
                   String v = ml.get(key).get("value").replaceAll("%"+variable+"%",value);
                   ml.get(key).put("value",v);
               }
       }
       
       private String getLocatorString(String locatorString,String[] ss){
           for(String s:ss){
                   locatorString = locatorString.replace("%s",s);
               }
           return     locatorString;
       }
   
       private By getBy(String type,String value){
           By by = null;
             if(type.equals("id")){
            	 
                   by = By.id(value);
               }
              if(type.equals("name")){
                   by = By.name(value);
               }
               if(type.equals("xpath")){
                   by = By.xpath(value);
               }
               if(type.equals("className")){
                   by = By.className(value);
               }
               if(type.equals("linkText")){
                   by = By.linkText(value);
               }
               if(type.equals("cssSelector")){
            	   by =	By.cssSelector(value);
               }
               /* if(type.equals("tagName")){
            	   by = By.tagName(value);
               }*/
               if(type.equals("partialLinkText")){
            	   by = By.partialLinkText(value);
               }
           return by;
       }
       
       
	private WebElement waitForElement(final By by){
               WebElement element = null;
              
               try{
                       element = new WebDriverWait(driver,waitTime)
                                       .until(new ExpectedCondition<WebElement>(){
                                               public WebElement apply(WebDriver d){
                                                       return d.findElement(by);
                                                   }
                                         });
                   }catch(Exception e){
                	   System.out.println("元素"+by.toString()+"不存在或者定位多个!");
                	   					e.printStackTrace();
                                      
                           }   
           return element;
       }
    
       private boolean waitElementToBeDisplayed(final WebElement element){
               boolean wait = false;
               if(element==null) return wait;
               try{
                       wait = new WebDriverWait(driver,waitTime)
                                   .until(new ExpectedCondition<Boolean>(){
                                                     public Boolean apply(WebDriver d){
                                                               return element.isDisplayed();
                                                       }
                                   }); 
                   }catch(Exception e){
                           System.out.println("元素"+element.toString()+"在最大响应时间后还是未显示出来!");
                           e.printStackTrace();
                   }
           return wait;
       }
   
       public boolean waitElementToBeNonDisplayed(final WebElement element){
               boolean wait = false;
               if(element == null){
                   return wait;
               }
               try{
                       wait = new WebDriverWait(driver,waitTime)
                                       .until(new ExpectedCondition<Boolean>(){
                                                       public Boolean apply(WebDriver d){
                                                               return !element.isDisplayed();
                                                           }
                                       });
                   }catch(Exception e){System.out.println("元素"+element.toString()+"在最大响应时间后还是没有消失!");}
               return wait;
       }
       
     
       //定位元素 参数 分别为 键值key ，是否需要动态替代%s
       private WebElement getLocator(String key,String[] replace,boolean wait){
                   WebElement element =null;    
                   if(ml.containsKey(key)){
                	   
                       HashMap<String,String> m = ml.get(key);
                       String type = m.get("type");
                       String value = m.get("value");
                      
                       if(replace !=null)
                           value = this.getLocatorString(value,replace);
                          
                         By  by = this.getBy(type,value);
                         if(wait){
                                   element = this.waitForElement(by);
                                   
                                   boolean flag = this.waitElementToBeDisplayed(element);
                                  
                                    if(!flag){ 
                                    	
                                    	element= null;
                                    	}
                           }else{
                                       try{
                                               element = driver.findElement(by);
                                           }catch(Exception e){
                                        	   e.printStackTrace();
                                        	   element = null;}
                                   }    
                       }else  System.out.println("元素"+key+"在"+yamlFile+".yaml中未设置!");
                  
                 if(element==null)  throw new NullPointerException("元素值为空!");
                   return element;
       }
   
      
       public WebElement getElement(String key){
               return this.getLocator(key,null,true);
           }
       public WebElement getElementNoWait(String key){
               return this.getLocator(key,null,false);
       }
       public WebElement getElement(String key,String[] replace){
               return this.getLocator(key,replace,true);
       }
       public WebElement getElementNoWait(String key,String[] replace){
               return this.getLocator(key,replace,false);
       }
      
       public boolean IsElementExist(String key){
    	  
    	   WebElement element;
    	   try{
    	    	 element =  this.getElement(key);
    	    	  if(element!=null) return true;
    	    	  return false;
    	      }catch(Exception e){
    	    	   return false;
    	      }
       }
       
       public boolean IsElementExist(String key,String[] replace){
     	  
    	   WebElement element;
    	   try{
    	    	 element =  this.getElement(key,replace);
    	    	  if(element!=null) return true;
    	    	  return false;
    	      }catch(Exception e){
    	    	   return false;
    	      }
       }
       
       public static void main(String[] args){
    	   System.setProperty("webdriver.firefox.bin","D:/程序/Firefox 39/firefox.exe");
    	   WebDriver driver = new FirefoxDriver();
    	   driver.get("http://192.168.248.132:8080/jskxWeb/admin/login.jsp");
    	   YamlParser yamlParse = new YamlParser("AdminLoginPage", driver);
    	   
    	   WebElement login = yamlParse.getElement("userName");
    	   login.sendKeys("chenjian");
    	   
    	   
       }
}
