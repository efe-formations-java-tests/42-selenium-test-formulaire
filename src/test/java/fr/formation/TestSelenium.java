package fr.formation;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestSelenium {

	private String titreHome = "Gestion des livres";
	private String titreListe = "Liste des livres";
	private String titreAjout = "Ajout d'un livre";

	private static SeleniumConfig config;
	private static WebDriver driver;;
	private static String url = "http://localhost:8080";
	
	@BeforeAll
	public static void setUp() {
		config = new SeleniumConfig();
		driver = config.getDriver();
	}
	
	
	@AfterAll
	public static void tearDown() {
		driver.close();
	}

	
	@BeforeEach
	public  void beforeEach() {
		driver.get(url);
		waitForLoadingPage();
	}
	
	@Test
	public void testTitre() {
		String titre = getTitre();
		Assertions.assertEquals(titreHome, titre);
	}

	@Test
	public void testPageListe() {
		driver.findElement(By.partialLinkText("Liste des")).click();
		String titre = getTitre();
		Assertions.assertEquals(titreListe, titre);
	}

	@Test
	public void testPageAjout() {
		driver.findElement(By.partialLinkText("Ajout d")).click();
		String titre = getTitre();
		Assertions.assertEquals(titreAjout, titre);
	}
	
	
	@Test
	public void testAlert() {
		driver.findElement(By.id("alerte")).click();
		Alert alert = new WebDriverWait(driver, 15).until(ExpectedConditions.alertIsPresent());
		String text = alert.getText();
		alert.accept();
		Assertions.assertEquals("Hello", text);
	}

	@Test
	public void testAjoutLivre() throws InterruptedException {
		driver.findElement(By.partialLinkText("Ajout d")).click();
		waitForLoadingPage();
		
		WebElement titreL = driver.findElement(By.id("titre"));
		WebElement auteurL = driver.findElement(By.id("auteur"));
		WebElement submitL = driver.findElement(By.tagName("button"));

		titreL.click(); titreL.clear(); titreL.sendKeys("Les Fleurs du Mal");
		auteurL.click(); auteurL.clear(); auteurL.sendKeys("Baudelaire");
		
		submitL.submit();
		
		waitForLoadingPage();
		
		String titre = getTitre();
		Assertions.assertEquals(titreListe, titre);

		int nbLivres = driver.findElements(By.tagName("li")).size();
		Assertions.assertEquals(1, nbLivres);
		
		driver.findElement(By.linkText("x")).click();
		waitForLoadingPage();

		WebElement ul = driver.findElement(By.tagName("ul"));
		
		Assertions.assertEquals(0, ul.getText().trim().length());
		
	}


	private void waitForLoadingPage() {
		
		new WebDriverWait(driver, 15).until(
			      webDriver -> ((JavascriptExecutor) webDriver)
			      					.executeScript("return document.readyState")
			      					.equals("complete"));
	}

	private String getTitre() {
		return driver.getTitle();
	}

}
