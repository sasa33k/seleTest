# Selenium Testing - Web Driver Automated Testing

This project is clone and updated from the old s3a-selenium Testing Project

Testcase are put in resources > testcase
output report with screen captures

Tested on Windows & mac
STS & intellij


still working on alert handling,
properties setting, output files

## run com.s3a.test > seleTest > webdriver test

To be included - mobile / application testing

---


| | |
| :--- | :--- |
| Objective | Automate web browser testing with report and screen shots record<br>Allow QA team member write test cases in excel format|
| Enhancements | Alert Handling, App integrations, loop for testcases in folder, Auto Seq no. , properties setting|
| Limitation | Asian Character in MS Excel cannot use Utf8, validations |


csv columns

| # | Columns | Description |
| :--- | :--- | :--- |
| 0 | stepNo | Show in Report & screen cap file name <br>**Don't use special characters here <br>** use #### as a new subcase, show as new section for report, use with following columns only(stepNo, elementName) |
| 1 | name | Show in terminal log? |
| 2 | sequence | **No duplicates** |
| 3 | desc | comment for testcases csv only, not used? |
| 4 | url | for "FetchingPage" doType, use with following columns (stepNo, name, sequence, url, doType, takePhoto) |
| 5 | elementname | ?? |
| 6 | elementvalue | Correspond to "GET" type |
| 7 | elementgetType | Correspond to "GET" type |
| 8 | doType | "DO" something for the elements "GOT"|
| 9 | exceptvalue | Expected value for "getText" & Alert? |
| 10 | takePhoto | TRUE / FALSE|
