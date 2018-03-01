package com.s3a.test.seleTest;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class test {

	@Test
	public void test() {
		
		 Date today = new Date();

	        //If you print Date, you will get un formatted output
	        System.out.println("Today is : " + today);

	        //formatting date in Java using SimpleDateFormat
	        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
	        String date = DATE_FORMAT.format(today);
	        System.out.println("Today in dd-MM-yyyy format : " + date);

	        //Another Example of formatting Date in Java using SimpleDateFormat
	        DATE_FORMAT = new SimpleDateFormat("dd/MM/yy");
	        date = DATE_FORMAT.format(today);
	        System.out.println("Today in dd/MM/yy pattern : " + date);

	        //formatting Date with time information
	        DATE_FORMAT = new SimpleDateFormat("dd-MM-yy:HH:mm:SS");
	        date = DATE_FORMAT.format(today);
	        System.out.println("Today in dd-MM-yy:HH:mm:SS : " + date);

	        //SimpleDateFormat example - Date with timezone information
	        DATE_FORMAT = new SimpleDateFormat("dd-MM-yy:HH:mm:SS Z");
	        date = DATE_FORMAT.format(today);
	        System.out.println("Today in dd-MM-yy:HH:mm:SSZ : " + date);

	        //SimpleDateFormat example - Date with timezone information
	        DATE_FORMAT = new SimpleDateFormat("yyMMdd_HHmm");
	        date = DATE_FORMAT.format(today);
	        System.out.println("Today in yyMMdd_HHmm : " + date);
		fail("Not yet implemented");
	}

}
