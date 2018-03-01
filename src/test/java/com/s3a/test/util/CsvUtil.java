package com.s3a.test.util;


import com.s3a.test.module.SeDoType;
import com.s3a.test.module.SeGetType;
import com.s3a.test.module.SeTestCase;
import com.s3a.test.module.SeTestStep;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by samantha on 7/1/2017.
 */
public class CsvUtil {

    @Test
    public static SeTestCase CsvParser(File file) throws IOException {
    	//public void CsvParser() throws IOException {
        //ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        //String path = classloader.getResource("testcase/1_case1.csv").getPath();
        //File file =  new File(path);

        SeTestCase tcase = new SeTestCase();
        String fileName = file.getName();
        String[] names = fileName.split("_");
        if(names.length >=2){

            String name = names[1].substring(0,names[1].lastIndexOf("."));
            tcase.setCaseName(name);
            tcase.setCaseNo(names[0]);
        }
        Reader in =  new FileReader(file);
        CSVParser parser = new CSVParser(in, CSVFormat.EXCEL.withHeader());
        List<SeTestStep> steps = new ArrayList<SeTestStep>();
        List<List<SeTestStep>> listofsteps = new ArrayList<List<SeTestStep>>();
        List<String> subcases = new ArrayList<String>();

        for (CSVRecord record : parser) {
            String stepNo = record.get("stepNo").trim();
            String name = record.get("name").trim();
            System.out.println(stepNo);
            if (!"####".equals(stepNo)) {
                System.out.println("=======1=======");
                String sequence = record.get("sequence").trim();
                String desc = record.get("desc").trim();
                String url = record.get("url").trim();
                String takePhoto = record.get("takePhoto").trim();
                String doTypes = record.get("doType").trim();
                SeDoType dotype = (SeDoType) Enum.valueOf(SeDoType.class, doTypes);
                String exceptvalue = record.get("exceptvalue").trim();
                SeTestStep step = new SeTestStep();
                step.setStepName(name);
                step.setStepNo(stepNo);
                step.setSequence(Integer.parseInt(sequence));
                step.setStepDesc(desc);
                step.setTakePhoto(Boolean.parseBoolean(takePhoto));
                step.setDoType(dotype);
                step.setExcptValue(exceptvalue);
                step.setUrl(url);
                String elementname = record.get("elementname").trim();
                if (elementname != null && !"".equals(elementname)) {
                    String elementvalue = record.get("elementvalue").trim();
                    String elementgetType = record.get("elementgetType").trim();
                    SeGetType type = null;
                    if (elementgetType != null) {
                        type = (SeGetType) Enum.valueOf(SeGetType.class, elementgetType);
                    }
                    step.setElementName(elementname);
                    step.setGetType(type);
                    step.setElementValue(elementvalue);

                    System.out.println("Testing:  " + elementname + " | " + type + " | " + elementvalue);
                }

                steps.add(step);
            } else if (steps.size()>0){
                subcases.add(name);
                listofsteps.add(steps);
                steps= new ArrayList<SeTestStep>();

            } else {
                subcases.add(name);
            }

            tcase.setSubCaseNames(subcases);
            tcase.setListOfsteps(listofsteps);
        }

        System.out.println("=======3=======");
        listofsteps.add(steps);
        steps= new ArrayList<SeTestStep>();

        printCases(listofsteps);

        return tcase;
    }

    public static void printCases(List<List<SeTestStep>> listofsteps){
        System.out.println();
        System.out.println("=======**********=======");
        System.out.println("=======**********=======");
        System.out.println();

        System.out.println("size : " + listofsteps.size());

        for(List obj:listofsteps){
            System.out.println("Case : " + listofsteps.indexOf(obj));

            List<SeTestStep> temp = obj;

            for(SeTestStep stepText : temp){
                System.out.println(temp.indexOf(stepText)+"  :  " +stepText.getElementName());
            }
            System.out.println();
        }
    }
}
