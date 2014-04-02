package ykw.jenkins02.action;

import java.util.List;
import java.util.Map;

import ykw.jenkins.serverold.JenkinsServer;
import ykw.jenkins.serverold.JobSvnUrlServer;
import ykw.jenkins.serverold.impl.JenkinsServerImpl;
import ykw.jenkins.serverold.impl.JobSvnUrlServerImpl;
import ykw.util.properties.ReadPros;
import ykw.util.xml.Dom4XML;

/**
 * 从jenkins 1.0中获取的对jenkins操作的方法
 * @author YinKewei
 */
public class OldAction {
	public static JenkinsServer jServer = new JenkinsServerImpl();
	
	public static JobSvnUrlServer urlServer = new JobSvnUrlServerImpl();
	
	public static JobAction jobAction = new JobAction();
	
	//1.读取文件的路径jobs.xml  2.创建文件的路径jobs02.xml
	public static  Dom4XML dx = new Dom4XML(ReadPros.getMisc().getProperty("pathJobsXml"),ReadPros.getMisc().getProperty("pathJobs02Xml"));
	
	public static void main(String[] args) throws Exception {
		//获取所有工程名,写入jobnames.txt
//		jServer.writeListToFile(jServer.getAllJobName(),"jobnames.txt");
		//----------------------------------------------------------------------------------------------
		//新增一个job,举例
//		jServer.addNewJob("lsmp-bank-server", 
//				"http://10.1.7.3/repos/ilsmprep/bcg/code/bank/branches/bank-branch-3.1.0/lsmp-bank-server", 
//				"$maven_50");
		//----------------------------------------------------------------------------------------------
		//新增多个job,读取jobs.xml 
//		jServer.addNewJobs(dx);
		//---------------------------------------------------------------------------------------------
		//读取jobs信息并存入创建的 jobs02.xml
		Map<String,List<String>>jobMap = jServer.getAllJobAndInfo(); //获取工程和工程信息
		dx.createXml(jobMap); 
		//---------------------------------------------------------------------------------------------
		//读取jenkins系统变量,写入jenkinsEnv.properties
//		jServer.readEnvironmentVariables();
		//---------------------------------------------------------------------------------------------
		//重新加载jenkins系统变量,读取jenkinsEnv.propreties
//		jServer.ReloadEnvironmentVariables(Paths.get(ReadPros.getMisc().getProperty("pathReadEvn"))); 
		//----------------------------------------------------------------------------------------------
		//重新加载所有工程的svn路径,读取jobs03.xml
//		jServer.ReloadSvnUrl(ReadPros.getMisc().getProperty("pathJobs03Xml"));  
		//----------------------------------------------------------------------------------------------
		//删除所有job!!!
//		jServer.deleteAllJobs();
		//=========================================================
		//打印出所有job的svn路径.路径参数在misc.properties中的svnUrlXml
//		urlServer.getAndWriteAllSvnUrls(jServer.login(), true);
		//读取urlToJenkins.xml文件,把新svnUrl信息加载到每个对应的job
//		urlServer.modifySvnUrlByXml(ReadPros.getMisc().getProperty("urlToJenkins"),jServer.login(), true);
		//-------------------------------------------------------------------------------------------------
		//获取所有工程名或某个视图的所有工程名
//		jobAction.getJobNameList(jobAction.getLoginDriver(), true, "View1");
		//-------------------------------------------------------------------------------------------------
		//读取read.xml文件中每个job工程,,把新svnUrl信息加载到对应的指定job中
//		jobAction.addSvnUrl("lsmp-business-ejb-310", jobAction.getLoginDriver(), true);
		//-------------------------------------------------------------------------------------------------
		//批量为整个或某个view下的job工程添加 丢弃工程最大构建数数量
//		jobAction.discard(jobAction.getLoginDriver(), true);
	}
}
