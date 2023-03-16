package com.cst438;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentRepository;
import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;


@SpringBootTest
public class EndToEndAddStudent {

	public static final String CHROME_DRIVER_FILE_LOCATION = "C:/chromedriver_win32/chromedriver.exe";

	public static final String URL = "http://localhost:3000";

	public static final String TEST_USER_EMAIL = "test@csumb.edu";

	public static final String TEST_USER_NAME = "Test One"; 

	public static final int SLEEP_DURATION = 1000; // 1 second.
	
	@Autowired
	StudentRepository studentRepository;

	/*
	 * Student add course TEST_COURSE_ID to schedule for 2021 Fall semester.
	 */
	
	@Test
	public void addStudentTest() throws Exception {
		Student s = null;

		/*
		 * if student is already enrolled, then delete the enrollment.
		 */
		
		// set the driver location and start driver
		//@formatter:off
		// browser	property name 				Java Driver Class
		// edge 	webdriver.edge.driver 		EdgeDriver
		// FireFox 	webdriver.firefox.driver 	FirefoxDriver
		// IE 		webdriver.ie.driver 		InternetExplorerDriver
		//@formatter:on

		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
		WebDriver driver = new ChromeDriver();
		// Puts an Implicit wait for 10 seconds before throwing exception
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		try {

			driver.get(URL);
			Thread.sleep(SLEEP_DURATION);

			// find add student and click on it			
			driver.findElement(By.xpath("//a[last()]")).click();
			Thread.sleep(SLEEP_DURATION);
			
			driver.findElement(By.xpath("//button")).click();
			Thread.sleep(SLEEP_DURATION);


            driver.findElement(By.xpath("//input[@name='name']")).sendKeys(TEST_USER_NAME);
            Thread.sleep(SLEEP_DURATION);

			// enter course no and click Add button
			
            driver.findElement(By.xpath("//input[@name='email']")).sendKeys(TEST_USER_EMAIL);
            Thread.sleep(SLEEP_DURATION);
            
            driver.findElement(By.xpath("//button[@id='Add']")).click();
            Thread.sleep(SLEEP_DURATION);
            
            s = studentRepository.findByEmail(TEST_USER_EMAIL);
            assertNotNull(s);

		} catch (Exception ex) {
			throw ex;
		} finally {

			// clean up database.
			s = studentRepository.findByEmail(TEST_USER_EMAIL);
			if(s != null)
				studentRepository.delete(s);

			driver.quit();
		}

	}
}
	