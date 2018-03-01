package com.s3a.test.seleTest;
import com.s3a.test.module.SeTestCase;
import com.s3a.test.util.AsciidocConvert;
import com.s3a.test.util.CsvUtil;

import org.junit.Test;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
//import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by samantha on 30/12/2016.
 */


public class WebDriverTest {
	 String destDir;
	    DateFormat dateFormat;
	    private static WebDriver driver;
	    private static String screenshotFolder;
	    private static String testPath;

	    //@Before
	    @BeforeClass
	    public static void setUp() throws Exception {

	        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

	        Properties prop = new Properties();
	        InputStream in = classloader.getResourceAsStream("config/driver.properties");
	        prop.load(in);
	        driver = getDriver("chrome", prop);

	        Properties prop2 = new Properties();
	        InputStream in2 = classloader.getResourceAsStream("config/setting.properties");
	        prop2.load(in2);
	        screenshotFolder = prop2.getProperty("screenshot.folder").trim();

	        driver.manage().window().maximize();
	        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //imWait follow this time
	        WebDriverWait wait = new WebDriverWait(driver, 15);
	    }
	    //@After
	    @AfterClass
	    public static void End() {
	       // driver.close();
	        //driver.quit();
	    }



	    @Test
	    public void startTestCase() throws Exception{
	        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
	        String testPath = classloader.getResource("testcase/1_case1.csv").getPath();

	    	File testfile =  new File(testPath);
	        SeTestCase tcase = CsvUtil.CsvParser(testfile);

	        Date today = new Date();
	        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyMMdd_HHmm");
	        String date = DATE_FORMAT.format(today);
	        System.out.println("Today in yyMMdd_HHmm : " + date);
	        
	        StringBuffer sb2 = new StringBuffer();
	        sb2.append("= Automated Testing for "+ tcase.getCaseName() + System.getProperty("line.separator"));
	        sb2.append("" + System.getProperty("line.separator"));
	        sb2.append(tcase.getCaseName() + " *test* :: " + tcase.getCaseDesc() + System.getProperty("line.separator"));
	        sb2.append("" + System.getProperty("line.separator"));
	        sb2.append("excute time:" + today + System.getProperty("line.separator"));
	        sb2.append("+" + System.getProperty("line.separator"));
	        sb2.append("this is my test"+ System.getProperty("line.separator"));


	        String ret = RunCaseUtil.runcase(driver,tcase, sb2, date);

	        //String path2 = prop.getProperty("path").trim();
	        String path2="src/test/output/";
	        boolean reta = AsciidocConvert.createAsciidoc(sb2, path2+date+"_"+tcase.getCaseName()+".adoc");
	        boolean reth = AsciidocConvert.convertToHtml(path2 +date+"_"+tcase.getCaseName()+".adoc", path2 +tcase.getCaseName()+"h.html");

	        System.out.println("testcase run:  " + ret+" create .adoc :"+reta+" create html: " +reth);

	    }
	    


	    public static WebDriver getDriver(String type, Properties prop){

	        String chormedrivers = prop.getProperty("chrome.driver").trim();
	        String firefoxdrivers = prop.getProperty("firefox.driver").trim();
	        String iedrivers = prop.getProperty("ie.driver").trim();
	        WebDriver driver = null;
	        if("chrome".equals(type)){
	            System.setProperty("webdriver.chrome.driver", chormedrivers);
	            driver = new ChromeDriver();
	        }else if("firefox".equals(type)){
	            System.setProperty("webdriver.gecko.driver",firefoxdrivers);
	            driver = new FirefoxDriver();
	        }else{
	            System.setProperty("webdriver.ie.driver", iedrivers);
	            driver = new InternetExplorerDriver();
	        }
	        driver.manage().window().maximize();
	        //driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	        return driver;
	    }
	
	    

/*
	    @Test
	    public void startChromeWebDriver() throws Exception{
	        //The following code brings opened window to the front in mac
	        //Store the current window handle
	        String currentWindowHandle = driver.getWindowHandle();
	        //run your javascript and alert code
	        JavascriptExecutor jsex = ((JavascriptExecutor)driver);
	        jsex.executeScript("alert('Test')");
	        driver.switchTo().alert().accept();
	        //Switch back to to the window using the handle saved earlier
	        driver.switchTo().window(currentWindowHandle);
*/
	        //This bit allows resizing the window
	        /*
	        jsex.executeScript("window.open('','testwindow','width=400,height=200')");
	        driver.close();
	        driver.switchTo().window("testwindow");
	        jsex.executeScript("window.moveTo(0,0);");
	        jsex.executeScript("window.resizeTo(200,800);");
	*/

	        // driver.manage().window().maximize();
	        //  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
/*
	        driver.navigate().to("http://seleniumsimplified.com/");
	        ScreenCapUtil.takeScreenShot(screenshotFolder, driver);
	        Assert.assertTrue("Title start with selenium simplified",
	                driver.getTitle().startsWith("Selenium Simplified"));
	    }

	    */

	   /*
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	*/

}
