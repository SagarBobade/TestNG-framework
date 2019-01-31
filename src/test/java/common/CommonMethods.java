package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebElement;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import pageObjects.PageObjects;

public class CommonMethods implements IRetryAnalyzer {

	public boolean retry(ITestResult result) {

		if (PageObjects.counterOfTry <= PageObjects.retryLimit) {
			System.out.println("Trying : " + PageObjects.counterOfTry + "time");
			PageObjects.counterOfTry++;
			return true;
		}
		return false;
	}

	public static String getValue(String key) throws IOException {

		int i = 0;
		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;
		String valuefromExcel = null;

		File file = new File("C:\\Users\\sagarb\\Desktop\\Test.xlsx");
		FileInputStream inputStream = new FileInputStream(file);

		workbook = new XSSFWorkbook(inputStream);
		sheet = (XSSFSheet) workbook.getSheet(PageObjects.QuestionAddSheetNameForKeyValue);

		while (i < sheet.getLastRowNum()) {
			if (sheet.getRow(i).getCell(0).toString().equalsIgnoreCase(key)) {
				valuefromExcel = sheet.getRow(i).getCell(1).toString();
				workbook.close();
				inputStream.close();
				return valuefromExcel;
			} else {
				i++;
			}
		}
		workbook.close();
		inputStream.close();
		return null;
	}

	public static void TestfindElement(WebElement data, String values, String action) throws Exception {

		if (data != null) {

			if (action.equalsIgnoreCase("type")) {

				data.click();
				data.clear();
				data.sendKeys(values);

			} else if (action.equalsIgnoreCase("click")) { //
				data.click();
			} else if (action.equalsIgnoreCase("tick")) { // checkbox - code for multi selection
				data.click();
			} else if (action.equalsIgnoreCase("selectRadio")) { // radio
				data.click();
			}

		} else {
			throw new Exception("locatorType is not from defined set");
		}
		return;
	}
}
