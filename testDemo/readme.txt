场景:
   1)打开网页后台
   2)登录
   3)点击新建，弹出新建窗口
   4)输入标题，发布时间，上传附件，确认输入无误后点击确认按钮
   5)弹出alert窗口，内容为“发布成功”，点击确认按钮
   6)回到后台页面，表格中多出一条数据，选中，该数据前的radio按钮，点击查看
   7)弹出详细页面窗口，验证标题和文本内容
   8)焦点回到后台页面，输入url转向前台页面
   9)查看页面右上角，是否在最上方新插入一条记录，点击标题，进入详细页面
   11)验证标题和文本内容，判断图片资源是否存在，是否准确 
   
实现:
    1)静态页面元素封装在页面对象属性中，定位元素参数写在yaml文件中，数据类型为Map<String,Map<String,String>>
      定位类型包含id,name,xpath,className,linkText,cssSelector,partialLinkText和selenium定位方式一一对应
	2)比较复杂动态页面元素的获取可以通过js,jquery来实现，定位元素类型为js，参数为执行的JavaScript的
	3)所有页面对象继承父类pageObject,构造器参数设置为true时，新建页面对象时，给所有页面属性类型为WebElement的属性赋值
	4)测试参数写在xml中，与test类名关联，在test的父类中解析，利用testng的TestDataProvider机制设置，并通过对应的方法名过滤
	5)启用selenium grid hub，注册远程node节点，设置浏览器driver
	6)对于断言类错误，捕获其异常，使其不影响程序运行，在test执行结束后，在监听类中把捕获的异常追加到对应的test结果集中
	7)实现IReporter类，获取测试结果，打印每个test的执行信息到txt文本中，错误的输出错误信息