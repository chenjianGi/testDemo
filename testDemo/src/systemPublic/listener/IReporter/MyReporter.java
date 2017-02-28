package systemPublic.listener.IReporter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

public class MyReporter implements IReporter{

	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
			String outputDirectory) {
		List<ITestResult> list = new ArrayList<ITestResult>();
		 for(ISuite suite:suites){
             Map<String,ISuiteResult> suiteResults = suite.getResults();
             for(ISuiteResult suiteResult:suiteResults.values()){
            	 		 ITestContext testContext = suiteResult.getTestContext();
            	 		 IResultMap passedTests = testContext.getPassedTests();
                         IResultMap failedTests = testContext.getFailedTests();
                         IResultMap skippedTests = testContext.getSkippedTests();
                         IResultMap failedConfig = testContext.getFailedConfigurations();
                         list.addAll(this.listTestResult(passedTests));
                         list.addAll(this.listTestResult(failedTests));
                         list.addAll(this.listTestResult(skippedTests));
                         list.addAll(this.listTestResult(failedConfig));
             }        
         }        
         this.sort(list);
         this.outputResult(list,outputDirectory+"/report_chen.txt");
		
	}

    private ArrayList<ITestResult> listTestResult(IResultMap resultMap){
        Set<ITestResult> results = resultMap.getAllResults();
        return new ArrayList<ITestResult>(results);
}        

    //排序
    private void sort(List<ITestResult> list){
       
    		Collections.sort(list, new Comparator<ITestResult>(){

				public int compare(ITestResult o1, ITestResult o2) {
					if(o1.getStartMillis()>o2.getStartMillis()){
                        return 1;
                   }else{
                        return -1;
                }
				}
    			
    		});
}
    //设置输出格式
    private void outputResult(List<ITestResult> list,String path){
        try{
                BufferedWriter output = new BufferedWriter(new FileWriter(new File(path)));
                StringBuffer sb = new StringBuffer();
                for(ITestResult result:list){
                        if(sb.length()!=0){
                                    sb.append("\r\n");
                                    sb.append("\r\n");
                                }
                          sb.append("类:")
                            .append(result.getTestClass().getRealClass().getName())
                            .append(" 方法:")
                            .append(result.getMethod().getMethodName())
                            .append(" 执行时间:")
                            .append(this.formatDate(result.getStartMillis()))
                            .append(" 耗时:")
                            .append(result.getEndMillis()-result.getStartMillis())
                            .append("毫秒  结果:")
                            .append(this.getStatus(result.getStatus()));
                            
                            if(this.getStatus(result.getStatus()).equals("FAILUER")){
                            		sb.append(" 报错信息:");
                            		/*	.append(result.getThrowable().getStackTrace().toString())
                            			.append(" LocalizedMessage:")
                            			.append(result.getThrowable().getLocalizedMessage())
                            			.append(" message:")
                            			.append(result.getThrowable().getMessage());*/
                            			//StackTraceElemnt类元素代表一个堆栈帧
                            		for(StackTraceElement st:result.getThrowable().getStackTrace()){
                            			// sb.append(" line:"+st.getLineNumber()+" classname:"+st.getClassName()+" filename:"+st.getFileName()+" methodname:"+st.getMethodName());
                            			sb.append(st.toString()+"\r\n") ;
                            		}
                            }
                    }    
                        output.write(sb.toString());
                        output.flush();
                        output.close();
            }catch(IOException e){
                    e.printStackTrace();
                }
}
        
    private String getStatus(int status){
        String statusString = null;
        switch(status){
                case 1:
                    statusString = "SUCCESS";
                    break;
                case 2:
                    statusString = "FAILUER";
                    break;
                case 3:
                    statusString = "SKIP";
                    break;
                default:
                    break;
            }
        return statusString;
    }
    
    private String formatDate(long date){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return formatter.format(date);
    }
		
}
		
	
	

