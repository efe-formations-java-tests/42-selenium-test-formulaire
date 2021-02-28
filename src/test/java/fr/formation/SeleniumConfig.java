package fr.formation;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumConfig {
	 private WebDriver driver;
	 
	 
	 static {
		 System.setProperty("webdriver.chrome.driver", "../seleniumDrivers/chromedriver");	 
	 }
	 
	 public SeleniumConfig() {
		 
		 driver = new ChromeDriver();
		 driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
		 }

	public WebDriver getDriver() {
		return driver;
	}
	 
}
