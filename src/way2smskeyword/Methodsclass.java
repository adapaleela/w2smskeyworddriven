package way2smskeyword;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Methodsclass 
{
	public WebDriver driver;
	public WebDriverWait wait;
	
	public String launch(String l,String d,String c) throws Exception
	{
		if(l.equals("chrome"))
		{
			System.setProperty("webdriver.chrome.driver","E:\\leelajava\\chromedriver.exe");
			driver=new ChromeDriver();	
		}
		else if(l.equals("firefox"))
		{
			System.setProperty("webdriver.gecko.driver","E:\\leelajava\\geckodriver.exe");
			driver=new FirefoxDriver();	
		}
		else if(l.equalsIgnoreCase("ie"))
		{
			System.setProperty("webdriver.ie.driver","E:\\leelajava\\iedriverserver.exe");
			driver=new InternetExplorerDriver();	
		}
		else
		{
			return("unknown browser");
		}
		driver.get(d);
		wait=new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("mobileNo")));
		driver.manage().window().maximize();
		return("done");
	}
	
	public String fill(String l,String d,String c) throws Exception
	{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(l)));
		driver.findElement(By.xpath(l)).sendKeys(d);
		return("done");
	}
	public String click(String l,String d,String c) throws Exception
	{
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(l)));
		driver.findElement(By.xpath(l)).click();
		return("done");
	}
	public String validateLogin(String l,String d,String c) throws Exception
	{
		 try
		 {
			 if(c.equalsIgnoreCase("all_valid") && driver.findElement(By.xpath("//*[text()='SendSMS']")).isDisplayed())
			 {
				return("valid data test passed"); 
			 }
			 else if(c.equalsIgnoreCase("mobileno_blank") && driver.findElement(By.xpath("//*[text()='Enter your mobile number']")).isDisplayed())
			 {
				return("blank mobile number test passed");
			 }
			 else if(c.equalsIgnoreCase("wrongsize_mobileno") && driver.findElement(By.xpath("//*[text()='Enter valid mobile number']")).isDisplayed())
			 {
				return("wrong size mobile number test passed"); 
			 }
			 else if(c.equalsIgnoreCase("invalid_mobileno") && driver.findElement(By.xpath("//*[contains(text(),'not register with us')]")).isDisplayed())
			 {
				 return("invalid mobile number test passed");
			 }
			 else if(c.equalsIgnoreCase("password_blank") && driver.findElement(By.xpath("(//*[text()='Enter password'])[2]")).isDisplayed())
			 {
				 return("blank password test passed");
			 }
			 else if(c.equalsIgnoreCase("invalid_password") && driver.findElement(By.xpath("(//*[contains(text(),'Try Again')])[1]")).isDisplayed())
			 {
				 return("invalid password test passed");
			 }
			 else
			 {
				 String temp=this.screenshot();
				 return("test failed"+temp);
			 }
		 }
		 catch(Exception ex)
		 {
			 String temp=this.screenshot();
			 return("test interrupted"+temp);
		 }
	}
	public String closeSite(String l,String d,String c) throws Exception
	{
		driver.close();
		return("done");
	}
	public String screenshot() throws Exception
	{
		SimpleDateFormat sf=new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
		Date d=new Date();
		String fname=sf.format(d)+".png";
		File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File dest=new File(fname);
		FileHandler.copy(src,dest);
		return(fname);
	}
}

