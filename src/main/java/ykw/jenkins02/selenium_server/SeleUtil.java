package ykw.jenkins02.selenium_server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

@Component("seleUtil")
public  class SeleUtil {
	
	private String fireFoxPath = "E:\\Software\\Mozilla Firefox\\firefox.exe";
	
	/**
	 * 初始化WebDriver
	 * @return
	 */
	public WebDriver webDriver(){
		return firefoxDriver();
	}
	
	/**
	 * 登录操作
	 * @param loginUrl
	 * @param usernameStr
	 * @param passwordStr
	 * @return WebDriver
	 */
	public WebDriver login(String loginUrl,String usernameStr,String passwordStr){
		WebDriver driver = webDriver();
		driver.get(loginUrl);
		
		WebElement username = driver.findElement(By.xpath("//input[@id='j_username']"));
		username.clear();
		username.sendKeys(usernameStr);
		WebElement password = driver.findElement(By.xpath("//input[@name='j_password']"));
		password.clear();
		password.sendKeys(passwordStr);
		WebElement login = driver.findElement(By.xpath("//button[@id='yui-gen1-button']"));
		login.click();
		
		WebDriverWait driverWait = new WebDriverWait(driver,10);
		driverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("navigation")));
		return driver;
	}
	
	/**
	 * 获取一个job配置页面
	 * @param jobName
	 * @param loginUrl
	 * @param webDriver
	 * @return
	 */
	public WebDriver getJobConfigByJobName(String jobName,String loginUrl,WebDriver webDriver){
			String configUrl = loginUrl.substring(0,loginUrl.lastIndexOf("/"))+"/job/"+jobName+"/configure";
			webDriver.get(configUrl);
		return webDriver;
	}
	
	/**
	 * 进入页面某个视图
	 * @param view
	 * @param loginUrl
	 * @param webDriver
	 * @return
	 */
	public WebDriver getView(String view,String loginUrl,WebDriver webDriver){
		String viewUrl = loginUrl.substring(0,loginUrl.lastIndexOf("/"))+"/view/"+view;
		webDriver.get(viewUrl);
		return webDriver;
	}
	
	private WebDriver firefoxDriver() {
		System.setProperty("webdriver.firefox.bin", fireFoxPath);
		WebDriver webDriver =  new FirefoxDriver();
		return webDriver;
	}
	
	/**
	 * 深层复制
	 */
	public static Object deepClone(Object obj){
		try {
			ByteArrayOutputStream buf =
					new ByteArrayOutputStream();
			ObjectOutputStream out =
					new ObjectOutputStream(buf);
			out.writeObject(obj);
			out.close();
			
			byte[] ary = buf.toByteArray();
			ByteArrayInputStream bais = new ByteArrayInputStream(ary);
			ObjectInputStream in = new ObjectInputStream(bais);
			Object o = in.readObject();
			in.close();
			
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
}
