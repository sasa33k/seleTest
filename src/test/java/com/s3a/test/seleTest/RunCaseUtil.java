package com.s3a.test.seleTest;


import java.io.File;
import java.util.List;

import com.s3a.test.module.SeDoType;
import com.s3a.test.module.SeTestCase;
import com.s3a.test.module.SeTestStep;
import org.apache.commons.io.FileUtils;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;



public class RunCaseUtil {
	static String actualVal ="";

	public static String runcase(WebDriver driver, SeTestCase tcase, StringBuffer sb, String date) {

		String caseRet = "";

		List<List<SeTestStep>> ListofSteps = tcase.getListOfsteps();
		//Collections.sort(steps);

		// getpath
		String path = "src/test/output/" + tcase.getCaseName();

		System.out.println("the size of case : " + ListofSteps.size());
		for (int j = 0; j < ListofSteps.size(); j++) {
			List<SeTestStep> steps = ListofSteps.get(j);
			sb.append("" + System.getProperty("line.separator"));
			sb.append("." + tcase.getSubCaseNames().get(j) + System.getProperty("line.separator"));
			//sb.append("+" + System.getProperty("line.separator"));
			sb.append("[options=\"header\",cols=\"8*\"]" + System.getProperty("line.separator"));
			sb.append("|===" + System.getProperty("line.separator"));
			sb.append("|stepno" + System.getProperty("line.separator"));
			sb.append("|name" + System.getProperty("line.separator"));
			//sb.append("|url" + System.getProperty("line.separator"));
			//sb.append("|getType" + System.getProperty("line.separator"));
			sb.append("|Command" + System.getProperty("line.separator"));
			sb.append("|Test" + System.getProperty("line.separator"));
			sb.append("|expectedValue" + System.getProperty("line.separator"));
			sb.append("|actualValue" + System.getProperty("line.separator"));
			sb.append("|elasped" + System.getProperty("line.separator"));
			sb.append("|result" + System.getProperty("line.separator"));
			//sb.append("|===" + System.getProperty("line.separator"));
					System.out.println("the size of step : " + steps.size());
			for (int i = 0; i < steps.size(); i++) {
				try {
					Thread.sleep(500);

				} catch (Exception e) {

				}

				SeTestStep step = steps.get(i);
				if (sb != null) {
					sb.append("" + System.getProperty("line.separator"));
					sb.append("|" + step.getStepNo() + System.getProperty("line.separator"));
					sb.append("|" + step.getStepName() + System.getProperty("line.separator"));
					//sb.append("|" + step.getUrl() + System.getProperty("line.separator"));
					String elementVal = step.getElementValue();
					if (elementVal==null) {
						sb.append("|GoTo: " + step.getUrl() + System.getProperty("line.separator"));
					}else{
						sb.append("|Get " + step.getGetType() +": " + elementVal + System.getProperty("line.separator"));

					}
					//sb.append("|" + step.getGetType() + System.getProperty("line.separator"));
					sb.append("|" + step.getDoType() + System.getProperty("line.separator"));
					sb.append("|" + step.getExcptValue() + System.getProperty("line.separator"));
				}
				long startTime = System.nanoTime();
				boolean ret = RunCaseUtil.testCaseProcessor(driver, step);
				long endTime = System.nanoTime();
				sb.append("|" + actualVal + System.getProperty("line.separator"));
				actualVal="";

				long duration = (endTime - startTime)/1000000;
				sb.append("|" + duration + " ms"+ System.getProperty("line.separator"));

				if (!ret) {
					caseRet += step.getStepNo() + "_" + step.getStepName() + " has error test failure;";
					if (sb != null) {
						sb.append("|failure" + System.getProperty("line.separator"));
						//sb.append("|===" + System.getProperty("line.separator"));
					}
				} else {
					if (sb != null) {
						sb.append("|success" + System.getProperty("line.separator"));
						//sb.append("|===" + System.getProperty("line.separator"));
					}
				}

				if (!ret || step.getTakePhoto()) {
					RunCaseUtil.getScreen(driver, step, path, sb, date);
				}


			}

			sb.append("|===" + System.getProperty("line.separator"));
			System.out.print(sb);
			System.out.println("=======********========= ");
		}

		driver.quit();
		return caseRet;
	}

	public static boolean getScreen(WebDriver driver, SeTestStep step, String path, StringBuffer sb, String date) {
		try {
			//File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			//String filePath = path + "\\screenshot_" + step.getTestCase().getCaseName() + "_" + step.getStepName() + ".png";
			String filePath = "src/test/output/screenshots/" + date + "_" +step.getStepNo() + ".png";
			
			 File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	           //The below method will save the screen shot in d drive with name "screenshot.png"
	          FileUtils.copyFile(scrFile, new File(filePath));
	         /*     
			if (sb != null) {
				sb.append("" + System.getProperty("line.separator"));
				sb.append("*** image." + System.getProperty("line.separator"));
				sb.append("+" + System.getProperty("line.separator"));
				sb.append("image::sunset.jpg[caption=\"Figure 1: \", title=\"" + "xxx" + "\", alt=\"img\", width=\"150\", " + "height=\"100\", link=\"" + filePath
						+ "\"]" + System.getProperty("line.separator"));
				//sb.append("image::sunset.jpg[caption=\"Figure 1: \", title=\"" + step.getStepName() + "\", alt=\"img\", width=\"150\", " + "height=\"100\", link=\"" + filePath
				//		+ "\"]" + System.getProperty("line.separator"));
			}
			*/
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
		System.out.println("****************======================");
		System.out.print(sb);
		return true;
	}

	public static boolean testCaseProcessor(WebDriver driver, SeTestStep step) {

		try {
			List<WebElement> elements = null;
			WebElement element = null;
			boolean result = true;

			// get test element
			if (step.getGetType() != null) {
				switch (step.getGetType()) {
				case byClass:
					elements = driver.findElements(By.className(step.getElementValue()));
					break;
				default:
					element = RunCaseUtil.getWebElemet(step, driver);
					break;
				}

			}

			// do something
			if (step.getDoType() == SeDoType.imWait) {
				try {
					Thread.sleep(1500);

				} catch (Exception e) {

				}
			}
			
			// do something
			if (step.getDoType() == SeDoType.alert) {
				Alert alert = driver.switchTo().alert();
				//System.out.println(alert.getText()); //Print Alert popup
				actualVal = alert.getText();
				alert.accept(); //Close Alert popup
				try {
					Thread.sleep(1000);

				} catch (Exception e) {

				}
				 result = true;
			}
			

			// do something
			if (step.getDoType() != null && step.getDoType() != SeDoType.noThing && step.getDoType() != SeDoType.imWait) {
				result = doSomething(step, element, elements, driver);
			}
			

			return result;
		} catch (Exception e) {
			return false;
		}
	}

	private static WebElement getWebElemet(SeTestStep e, WebDriver driver) {
		WebElement element = null;
		switch (e.getGetType()) {
		case byId:
			element = (new WebDriverWait(driver, 10))
			   .until(ExpectedConditions.presenceOfElementLocated(By.id(e.getElementValue())));
			//element = driver.findElement(By.id(e.getElementValue()));
			break;
		case byTag:
			element = (new WebDriverWait(driver, 10))
			   .until(ExpectedConditions.presenceOfElementLocated(By.tagName(e.getElementValue())));
			//element = driver.findElement(By.tagName(e.getElementValue()));
			break;
		case byName:
			element = (new WebDriverWait(driver, 10))
			   .until(ExpectedConditions.presenceOfElementLocated(By.name(e.getElementValue())));
			//element = driver.findElement(By.name(e.getElementValue()));
			break;
		case byLink:
			element = (new WebDriverWait(driver, 10))
			   .until(ExpectedConditions.presenceOfElementLocated(By.linkText(e.getElementValue())));
			//element = driver.findElement(By.linkText(e.getElementValue()));
			break;
		case byPartialLink:
			element = (new WebDriverWait(driver, 10))
			   .until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(e.getElementValue())));
			//element = driver.findElement(By.partialLinkText(e.getElementValue()));
			break;
		case byCss:
			element = (new WebDriverWait(driver, 10))
			   .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(e.getElementValue())));
			//element = driver.findElement(By.cssSelector(e.getElementValue()));
			break;
		case byXpath:
			element = (new WebDriverWait(driver, 10))
			   .until(ExpectedConditions.presenceOfElementLocated(By.xpath(e.getElementValue())));
			//element = driver.findElement(By.xpath(e.getElementValue()));
			break;

		default:
			break;
		}
		return element;
	}

	private static boolean doSomething(SeTestStep step, WebElement element, List<WebElement> elements, WebDriver driver) {
		boolean result = true;
		switch (step.getDoType()) {
		case FetchingPage:
			driver.get(step.getUrl());
			break;
		case getText:
			actualVal = element.getText();
			result = step.getExcptValue().equals(element.getText());
			break;
		case click:
			element.click();
			break;
		case sendKeys:
			element.sendKeys(step.getExcptValue());
			break;
		case clear:
			element.clear();
			break;
		case selectByIndex:
			Select select = new Select(element);
			select.selectByIndex(Integer.parseInt(step.getExcptValue()));
			break;
		case selectByVisibleText:
			Select select2 = new Select(element);
			select2.selectByVisibleText(step.getExcptValue());
			break;
		case selectByValue:
			Select select3 = new Select(element);
			select3.selectByValue(step.getExcptValue());
			break;
		case isSelected:

			result = (element.isSelected() == Boolean.parseBoolean(step.getExcptValue()));
			break;
		case isEnabled:
			result = (element.isEnabled() == Boolean.parseBoolean(step.getExcptValue()));
			break;
		case isDisplayed:
			result = (element.isDisplayed() == Boolean.parseBoolean(step.getExcptValue()));
			break;
		default:
			break;
		}
		return result;
	}

	
	
	/*
 public static void makeFullScreenshot(WebDriver driver,String strFilename) throws IOException, InterruptedException {
    //Scroll to bottom to make sure all elements loaded correctly
    // scrollVerticallyTo(driver, (int) longScrollHeight);
    // scroll up first to start taking screenshots

    scrollVerticallyTo(driver, 0);
    hideScroll(driver);

    //No need to hide elements for first attempt
    byte[] bytes = getScreenShot(driver);

   // showHideElements(driver, true, skipElements);
    long longScrollHeight = (Long) ((JavascriptExecutor) driver).executeScript("return Math.max("
            + "document.body.scrollHeight, document.documentElement.scrollHeight,"
            + "document.body.offsetHeight, document.documentElement.offsetHeight,"
            + "document.body.clientHeight, document.documentElement.clientHeight);"

    );

    BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
    int capturedWidth = image.getWidth();
    int capturedHeight = image.getHeight();
    Double devicePixelRatio = ((Number) ((JavascriptExecutor) driver).executeScript(JS_RETRIEVE_DEVICE_PIXEL_RATIO)).doubleValue();
    int scrollHeight = (int) longScrollHeight;

   // File file = File.createTempFile("screenshot", ".png");
    int adaptedCapturedHeight = (int) (((double) capturedHeight) / devicePixelRatio);
    BufferedImage resultingImage;

    if (Math.abs(adaptedCapturedHeight - scrollHeight) > 40) {
        int scrollOffset = adaptedCapturedHeight;
        int times = scrollHeight / adaptedCapturedHeight;
        int leftover = scrollHeight % adaptedCapturedHeight;

        final BufferedImage tiledImage = new BufferedImage(capturedWidth, (int) (((double) scrollHeight) * devicePixelRatio), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2dTile = tiledImage.createGraphics();
        g2dTile.drawImage(image, 0, 0, null);

        int scroll = 0;

        for (int i = 0; i < times - 1; i++) {
            scroll += scrollOffset;
            scrollVerticallyTo(driver, scroll);
            BufferedImage nextImage = ImageIO.read(new ByteArrayInputStream(getScreenShot(driver)));
            g2dTile.drawImage(nextImage, 0, (i + 1) * capturedHeight, null);
        }

        if (leftover > 0) {
            scroll += scrollOffset;
            scrollVerticallyTo(driver, scroll);
            BufferedImage nextImage = ImageIO.read(new ByteArrayInputStream(getScreenShot(driver)));
            BufferedImage lastPart = nextImage.getSubimage(0, nextImage.getHeight() - (int) (((double) leftover) * devicePixelRatio), nextImage.getWidth(), leftover);
            g2dTile.drawImage(lastPart, 0, times * capturedHeight, null);

        }

        scrollVerticallyTo(driver, 0);
        resultingImage = tiledImage;

    } else {

        resultingImage = image;

    }

    showScroll(driver);
   // showHideElements(driver, false, skipElements);
    ImageIO.write(resultingImage, "png", new File(strFilename));
    //return file;

 }

 private static void scrollVerticallyTo(WebDriver driver, int scroll) {
    ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, " + scroll + ");");
    try {
        waitUntilItIsScrolledToPosition(driver, scroll);
    } catch (InterruptedException e) {
       // LOG.trace("Interrupt error during scrolling occurred.", e);
    }

 }

 private static void waitUntilItIsScrolledToPosition(WebDriver driver, int scrollPosition) throws InterruptedException {
    int hardTime = 0;//SCREENSHOT_FULLPAGE_SCROLLWAIT
    if (hardTime > 0) {
        Thread.sleep(hardTime);
    }
    int time = 250;//SCREENSHOT_FULLPAGE_SCROLLTIMEOUT
    boolean isScrolledToPosition = false;
    while (time >= 0 && !isScrolledToPosition) {
        Thread.sleep(50);
        time -= 50;
        isScrolledToPosition = Math.abs(obtainVerticalScrollPosition(driver) - scrollPosition) < 3;
    }

 }

 private static int obtainVerticalScrollPosition(WebDriver driver) {
    Long scrollLong = (Long) ((JavascriptExecutor) driver).executeScript("return (window.pageYOffset !== undefined) ? window.pageYOffset : (document.documentElement || document.body.parentNode || document.body).scrollTop;");
    return scrollLong.intValue();
 }

}
	 * 
	 */
}

