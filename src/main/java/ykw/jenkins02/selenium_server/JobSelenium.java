package ykw.jenkins02.selenium_server;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ykw.jenkins02.dao.EnvTypeDao;
import ykw.jenkins02.dao.JobDao;
import ykw.jenkins02.entity.EnvType;
import ykw.jenkins02.entity.Job;
import ykw.jenkins02.entity.Ssh;
import ykw.jenkins02.selenium_server.script.ScriptSelenium;


@Service("jobSelenium")
@Transactional
public class JobSelenium extends ScriptSelenium{
	
	@Autowired
	private JobDao jobDao;
	@Autowired
	private EnvTypeDao envTypeDao;
	@Autowired
	private SeleUtil seleUtil;
	
	public JobSelenium() {

	}
	/* -----------------------读取信息------------------------------*/	
	/**
	 * 从整个页面或单个view中获取工程名集合
	 * @param envTypeId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getJobNameListByEnvTypeId(int envTypeId,String... view){
		WebDriver  webDriver = null;
		List<String> jobNameList = new ArrayList<String>();
		try {
			EnvType envType = envTypeDao.findEnvTypeById(envTypeId);
			webDriver = seleUtil.login(envType.getLoginUrl(), envType.getLoginName(), envType.getPassword());
			if(view!=null && view.length > 0){
				if(!view[0].trim().equals("")){ //view字符不为空
					webDriver = seleUtil.getView(view[0], envType.getLoginUrl(), webDriver);
				}
			}
			jobNameList  = (List<String>)((JavascriptExecutor)webDriver).executeScript(RETURN_JOBNAMELIST_SCRIPT);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(webDriver!=null){
				webDriver.close();
			}
		}
		return jobNameList;
	}

	/**
	 * 获取单个job的svnUrl
	 * @param jobName
	 * @return
	 */
	public String getSvnUrl(int envTypeId,String jobName,WebDriver... drivers){
		WebDriver webDriver = null;
		String svnUrl = "";
		try {
			EnvType envType = envTypeDao.findEnvTypeById(envTypeId);
			String loginUrl = envType.getLoginUrl();
			String username  = envType.getLoginName();
			String password = envType.getPassword();
			
			if(drivers!=null && drivers.length > 0){  // 1. 存在,获取webDriver
				webDriver = drivers[0];
			}else{
				webDriver = seleUtil.login(loginUrl, username, password); //2.不存在,则初始化,即登录
			}
			webDriver = seleUtil.getJobConfigByJobName(jobName, loginUrl, webDriver); //获取某个job配置页面
			WebElement svnRadio = (WebElement)((JavascriptExecutor)webDriver).executeScript(RETURN_SUBVERSION_RADIO_SCRIPT);
			//svnRadio是否被选中
			if(svnRadio.getAttribute("checked")!=null && svnRadio.getAttribute("checked").equalsIgnoreCase("true")){
				//选中
				WebElement input = webDriver.findElement(By.xpath("//input[@id='svn.remote.loc']"));
				svnUrl = input.getAttribute("value") ; //获取svn路径值
			}else{
				svnUrl = "none";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(webDriver != null){
				webDriver.close();
			}
		}
		return svnUrl;
	}
	
	/**
	 * 从页面获取Job信息
	 * @param jobName
	 * @return
	 */
	public List<Job> getAllJobInfo(int envTypeId,List<String> jobNameList,String... view){
		WebDriver webDriver = null;
		List<Job> jobList = new ArrayList<Job>();
		String viewStr = "All"; //jenkins中view视图
		
		try {
			EnvType envType = envTypeDao.findEnvTypeById(envTypeId);
			String loginUrl = envType.getLoginUrl();
			
			webDriver = seleUtil.login(envType.getLoginUrl(), envType.getLoginName(), envType.getPassword());
			if(view!=null && view.length > 0){
				viewStr = view[0];
				webDriver = seleUtil.getView(view[0], envType.getLoginUrl(), webDriver);
			}
			//循环每个配置页面
			Job job ;
			for(String jobName:jobNameList){
				job = new Job();
				
				webDriver = seleUtil.getJobConfigByJobName(jobName, loginUrl, webDriver); //获取某个job配置页面
				WebDriverWait wait = new WebDriverWait(webDriver, 10);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.name("goals")));
				
				/*---------------------------------svnUrl的获取----------------------------------*/
				String svnUrl="";
				WebElement svnRadio = (WebElement)((JavascriptExecutor)webDriver).executeScript(RETURN_SUBVERSION_RADIO_SCRIPT);
				//svnRadio是否被选中
				if(svnRadio.getAttribute("checked")!=null && svnRadio.getAttribute("checked").equalsIgnoreCase("true")){
					//选中
					WebElement input = webDriver.findElement(By.xpath("//input[@id='svn.remote.loc']"));
					svnUrl = input.getAttribute("value") ; //获取svn路径值
				}else{
					svnUrl = "none";
				}
				
				/*----------------------------------------ssh获取----------------------------------*/
				Ssh ssh = new Ssh();
				//获取ssh地址
				WebElement sshSelect = (WebElement)webDriver.findElement(By.name("_.siteName"));
				String sshSite = sshSelect.getAttribute("value");
				if(sshSite!=null){
					ssh.setSshSite(sshSite);
				}else{
					ssh.setSshSite("none");
				}
				
				//获取command命令
				WebElement command = (WebElement)((JavascriptExecutor)webDriver).executeScript(RETURN_SSHSITE_COMMAND_SCRIPT);
				if(command!=null){
					ssh.setSshSiteCommand(command.getText().trim());
				}else{
					ssh.setSshSiteCommand("none");
				}
				
				//获取ssh server name
				Long select = ((Long)((JavascriptExecutor)webDriver).executeScript(RETURN_SSH_SERVER_NAME));
				//TODO 选中option有问题 
//				System.out.println(select);
//				System.out.println("---------"+jobName+"------------");
				
				//获取source files
				List<WebElement> sourceFiles = webDriver.findElements(By.name("_.sourceFiles"));
				for(WebElement each :sourceFiles){
					if(each.getAttribute("value").contains("target")){			// 可能定位不准确
						ssh.setSourceFiles(each.getAttribute("value").trim());
						break;
					}
				}
				//获取removePrefix
				List<WebElement> removePrefix = webDriver.findElements(By.name("_.removePrefix"));
				for(WebElement each :  removePrefix){   							
					if(each.getAttribute("value").contains("target")){ 					// 可能定位不准确
						ssh.setRemovePrefix(each.getAttribute("value").trim());
						break;
					}
				}
				
				//获取remoteDirectory
				List<WebElement> remoteDirectory = webDriver.findElements(By.name("_.remoteDirectory"));
				for(WebElement each :  remoteDirectory){   							
					if(each.getAttribute("value").contains("/opt/lsmp")){ 					// 可能定位不准确
						ssh.setRemoteDirectory(each.getAttribute("value").trim());
						break;
					}
				}
				
				/*---------------------maven命令--------------------------------------*/
				String mavenCommand = ((String)((JavascriptExecutor)webDriver).executeScript(RETURN_MAVNE_COMMAND)).trim();
				
				job.setName(jobName);
				job.setSvnUrl(svnUrl);
				job.setSsh(ssh);
				job.setMvnCmd(mavenCommand);
				job.setJenkinsView(viewStr);
				job.setEnvType(envType);
				jobList.add(job);
			} //循环结束
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(webDriver != null){
				webDriver.close();
			}
		}
		return jobList;
	}
	
	/**
	 * 获取job的ssh信息
	 * @param envTypeId
	 * @param jobName
	 * @param drivers
	 * @return
	 */
	public Ssh getSsh(int envTypeId,String jobName){
		Ssh ssh = new Ssh() ;
		WebDriver webDriver = null;
		try {
			EnvType envType = envTypeDao.findEnvTypeById(envTypeId);
			String loginUrl = envType.getLoginUrl();
			String username  = envType.getLoginName();
			String password = envType.getPassword();
			webDriver = seleUtil.login(loginUrl, username, password);
			
			webDriver = seleUtil.getJobConfigByJobName(jobName, loginUrl, webDriver); //获取某个job配置页面
			
			//获取command命令
			WebElement command = (WebElement)((JavascriptExecutor)webDriver).executeScript(RETURN_SSHSITE_COMMAND_SCRIPT);
			ssh.setSshSiteCommand(command.getText().trim());
			
			//获取source files
			List<WebElement> sourceFiles = webDriver.findElements(By.name("_.sourceFiles"));
			for(WebElement each :sourceFiles){
				if(each.getAttribute("value").contains("target")){			// 可能定位不准确
					ssh.setSourceFiles(each.getAttribute("value").trim());
					break;
				}
			}
			//获取removePrefix
			List<WebElement> removePrefix = webDriver.findElements(By.name("_.removePrefix"));
			for(WebElement each :  removePrefix){   							
				if(each.getAttribute("value").contains("target")){ 					// 可能定位不准确
					ssh.setRemovePrefix(each.getAttribute("value").trim());
					break;
				}
			}
			
			//获取remoteDirectory
			List<WebElement> remoteDirectory = webDriver.findElements(By.name("_.remoteDirectory"));
			for(WebElement each :  remoteDirectory){   							
				if(each.getAttribute("value").contains("/opt/lsmp")){ 					// 可能定位不准确
					ssh.setRemoteDirectory(each.getAttribute("value").trim());
					break;
				}
			}
		} catch (Exception e) {

		}finally{
			if(webDriver != null){
				webDriver.close();
			}
		}
		return ssh;
	}
	
	/*--------------------------写入信息(xml)-------------------------------*/
	/**
	 * 把页面中读取的job信息存入
	 * @param parent  文件目录
	 * @param xml  文件
	 * @param view  jenkins中的视图
	 */
	public void writeJobInfoToXml(Path parent,Path xml,String... view){
		//TODO 获取的job信息写入xml
	}
	
	/*--------------------------写入信息(web)------------------------------*/
	/**
	 * 在jenkins中新建job(暂时能添加的信息:1.svb路径,2.maven命令)
	 * @param jobList  job信息集合
	 * @param envTypeId 读取数据库中环境id
	 * @param view jenkins视图
	 */
	public void addJobsToJenkins(List<Job> jobList ,int envTypeId,String... view){
		WebDriver  driver = null;
		String viewStr = "All";
		try {
			EnvType envType = envTypeDao.findEnvTypeById(envTypeId);
			driver = seleUtil.login(envType.getLoginUrl(), envType.getLoginName(), envType.getPassword()); //登录
			
			if(view!=null && view.length > 0){
				if(!view[0].trim().equals("")){ //view字符不为空
					viewStr=view[0];
					driver = seleUtil.getView(view[0], envType.getLoginUrl(), driver);
				}
			}
			
			String jenkinsUrl = envType.getLoginUrl();
			String baseUrl = jenkinsUrl.substring(0, jenkinsUrl.lastIndexOf("/"));
			String addNewJobUrl = baseUrl+"/view/"+viewStr+"/newJob";  //进入添加job页面
			for(Job job:jobList){
				driver.get(addNewJobUrl);
				
				// 获取"Build a maven2/3 project"单选按钮
				String mavenProjectScript = "var form  = document.getElementsByName('createItem');"
						+ "var tr = form[0].firstChild.firstChild.childNodes[5];"
						+ "var inputRadio = tr.firstChild.firstChild;"
						+ "return inputRadio ;";
				WebElement radio  = (WebElement)((JavascriptExecutor)driver).executeScript(mavenProjectScript);
				radio.click();
				
				//输入工程名
				String jobnameScript = "var input = document.getElementById('name');"
						+ "return input";
				WebElement input = (WebElement)((JavascriptExecutor)driver).executeScript(jobnameScript);
				input.clear();
				input.sendKeys(job.getName());
				
				//保存
				WebElement save = driver.findElement(By.xpath("//button[@id='ok-button']"));
				save.submit();
				
				(new WebDriverWait(driver, 10)).until(ExpectedConditions.titleContains("Config"));
				
				//获取svbVersion单选按钮,并加入svn路径
				String subVersionScript = "var radioes = document.getElementsByClassName('radio-block-control');"
						+ "for(var i=0;i<radioes.length;i++){"
						+ "  var context = radioes[i].nextSibling.nodeValue.trim();"
						+ "  if(context == 'Subversion'){"
						+ "     var svnRadio =  radioes[i]; "
						+ "     var idStr = svnRadio.getAttribute('id');"
						+ "      svnRadio.checked = true;"
						+ "     var trs = document.getElementsByTagName('tr');"
						+ "     for(var j=0;j<trs.length;j++){"
						+ "            if(trs[j].getAttribute('nameref') && trs[j].getAttribute('nameref') == idStr){"
						+ "                   trs[j].style=''; "
						+ "             }"
						+ "     }"
						+ "     document.getElementById('svn.remote.loc').value='"+job.getSvnUrl()+"';"
						+ "   }"
						+ "}";
				((JavascriptExecutor)driver).executeScript(subVersionScript);
				
				//添加maven命令
				String goalScript = "var goalNames = document.getElementsByName('goals');"
						+ "goalNames[0].value='"+job.getMvnCmd()+"';";
				((JavascriptExecutor)driver).executeScript(goalScript);
				
				//勾掉 Build whenever a SNAPSHOT dependency is built
				String buildCheckBoxScript = "document.getElementsByName('maven.triggerByDependency')[0].checked='';";
				((JavascriptExecutor)driver).executeScript(buildCheckBoxScript);
				
				//保存
				String saveScript = "var node = document.getElementsByClassName('bottom-sticker-inner')[0];"
						+ "var buttonNode = node.firstChild.firstChild.firstChild;"
						+ "return buttonNode;";
				WebElement saveButton = (WebElement)((JavascriptExecutor)driver).executeScript(saveScript);
				saveButton.submit();
				
				new WebDriverWait(driver, 10).until(ExpectedConditions.titleContains(job.getName()+" [Jenkins]"));
				driver.get(baseUrl);
				new WebDriverWait(driver,10).until(ExpectedConditions.titleContains("Dashboard"));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(driver!=null){
				driver.close();
			}
		}
	}

	public JobDao getJobDao() {
		return jobDao;
	}

	public void setJobDao(JobDao jobDao) {
		this.jobDao = jobDao;
	}

	public EnvTypeDao getEnvTypeDao() {
		return envTypeDao;
	}

	public void setEnvTypeDao(EnvTypeDao envTypeDao) {
		this.envTypeDao = envTypeDao;
	}

	public SeleUtil getSeleUtil() {
		return seleUtil;
	}

	public void setSeleUtil(SeleUtil seleUtil) {
		this.seleUtil = seleUtil;
	}
}
