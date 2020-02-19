package hr.fer.opp.projekt.AudioVodic;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import hr.fer.opp.projekt.audioVodic.Util;

@SpringBootTest
class AudioVodicApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Test
	void emailTest() {
		
		String dobarEmail1= "domagoj.jurenec1@fer.hr";
		String dobarEmail2= "domagoj@fer.hr";
		String dobarEmail3= "jurenec3@fer.hr";
		String dobarEmail4= "domagoj.jurenec4@fer.hr";
		
		
		String losEmail1= "nesto";
		String losEmail2= "!@#$!";
		
		
		
		assertEquals(true, Util.checkMail(dobarEmail1));
		assertEquals(true, Util.checkMail(dobarEmail2));
		assertEquals(true, Util.checkMail(dobarEmail3));
		assertEquals(true, Util.checkMail(dobarEmail4));
		
		assertEquals(false, Util.checkMail(losEmail1));
		assertEquals(false, Util.checkMail(losEmail2));
		
		
	}
	
	@Test
	void passwordTest() {
		String losPass="12345";
		String losPass2="samomala";
		String losPass3="SAMOVELIKA";
		String dobarPass="MalaIVelikaIDovoljnoDug";
		assertEquals(false, Util.checkPassword(losPass));
		assertEquals(false, Util.checkPassword(losPass2));
		assertEquals(false, Util.checkPassword(losPass3));
		assertEquals(true, Util.checkPassword(dobarPass));
	}
	
	
	@Test
	void checkDescriptionTest() {
		String losD="^^^&*^#$";
		
		String dobarD="DobarDan!";
		String dobarD2="()";
		
		assertNotNull(Util.checkDescription(losD));
		
		assertNull(Util.checkDescription(dobarD));
		assertNull(Util.checkDescription(dobarD2));
		
		
		
	}
	@Test
	void checkTitleTest() {
		String a="moze razmak";
		String b="moze.";
		String c="moze 123";
		String d="ne mogu cudni znakovi: %^&*";
		
		assertEquals(false, Util.checkTitle(d));
		assertEquals(true, Util.checkTitle(a));
		assertEquals(true, Util.checkTitle(b));
		assertEquals(true, Util.checkTitle(c));
		
	}
	
	
	@Test
	void checkUserNameTest() {
		String a="ne moze razmak";
		String c="moze123";
		String d="nemogucudniznakovi:%^&*";
		assertEquals(false, Util.checkUsername(a));
		assertEquals(true, Util.checkUsername(c));
		assertEquals(false, Util.checkUsername(d));
		
		
	}
	
	
	@Test
	void logInTestPass() {
		System.setProperty("webdriver.chrome.driver", "/home/filip/Chrome Driver/chromedriver");
		WebDriver driver = new ChromeDriver();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		driver.get("https://audiovodic1.herokuapp.com/logInForm");
		WebElement element = driver.findElement(By.name("username"));
		element.sendKeys("filip");
		element = driver.findElement(By.name("password"));
		//Correct password
		element.sendKeys("qwertzuI");
		driver.findElement(By.name("metoda")).click();
		String URL = driver.getCurrentUrl();
		
		driver.quit();
		
		assertEquals("https://audiovodic1.herokuapp.com/museumObjects", URL);
	}
	
	@Test
	void logInTestFail() {
		System.setProperty("webdriver.chrome.driver", "/home/filip/Chrome Driver/chromedriver");
		WebDriver driver = new ChromeDriver();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		driver.get("https://audiovodic1.herokuapp.com/logInForm");
		WebElement element = driver.findElement(By.name("username"));
		element.sendKeys("filip");
		element = driver.findElement(By.name("password"));
		//Wrong password
		element.sendKeys("aaa");
		driver.findElement(By.name("metoda")).click();
		String URL = driver.getCurrentUrl();
		
		driver.quit();
		
		assertEquals("https://audiovodic1.herokuapp.com/logInForm", URL);
	}
	
	@Test
	void registrationTestFail() {
		System.setProperty("webdriver.chrome.driver", "/home/filip/Chrome Driver/chromedriver");
		WebDriver driver = new ChromeDriver();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		driver.get("https://audiovodic1.herokuapp.com/registrationForm");
		WebElement element = driver.findElement(By.name("firstName"));
		element.sendKeys("Pero");
		element = driver.findElement(By.name("lastName"));
		element.sendKeys("Perić");
		element = driver.findElement(By.name("username"));
		element.sendKeys("ivo98");
		element = driver.findElement(By.name("email"));
		element.sendKeys("ivo@gmail.com");
		element = driver.findElement(By.name("password"));
		element.sendKeys("qwertzuI");
		element = driver.findElement(By.name("confirmPassword"));
		element.sendKeys("qwertzuIa");
		driver.findElement(By.name("metoda")).click();
		String URL = driver.getCurrentUrl();
		
		driver.quit();
		
		assertEquals("https://audiovodic1.herokuapp.com/registrationForm", URL);
	}
	
	@Test
	void checkMuseumObjectTest() {
		System.setProperty("webdriver.chrome.driver", "/home/filip/Chrome Driver/chromedriver");
		WebDriver driver = new ChromeDriver();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		driver.get("https://audiovodic1.herokuapp.com/museumObjects");
		driver.findElement(By.xpath("//a[@href='museumObject/3']")).click();
		String URL = driver.getCurrentUrl();
		
		driver.quit();
		
		assertEquals("https://audiovodic1.herokuapp.com/museumObject/3", URL);
	}
	
}
