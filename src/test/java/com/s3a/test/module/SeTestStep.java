package com.s3a.test.module;

public class SeTestStep implements Comparable<SeTestStep>{

	private Long stepId;
	
	private String stepName;
	
	private String stepDesc;
	
	private String stepNo;
	
	private Integer sequence;
	
	private boolean takePhoto;
	
	private String elementName;
	
	private String elementValue;
	
	private SeGetType getType;
	
	
	private SeDoType doType;
	
	private String excptValue;
	
	private String url;
	
	private SeTestCase testCase;



	public Long getStepId() {
		return stepId;
	}

	public void setStepId(Long stepId) {
		this.stepId = stepId;
	}

	public String getStepName() {
		return stepName;
	}

	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	public String getStepDesc() {
		return stepDesc;
	}

	public void setStepDesc(String stepDesc) {
		this.stepDesc = stepDesc;
	}

	public String getStepNo() {
		return stepNo;
	}

	public void setStepNo(String stepNo) {
		this.stepNo = stepNo;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public boolean getTakePhoto() {
		return takePhoto;
	}

	public void setTakePhoto(boolean takePhoto) {
		this.takePhoto = takePhoto;
	}

	public SeDoType getDoType() {
		return doType;
	}

	public void setDoType(SeDoType doType) {
		this.doType = doType;
	}

	public String getExcptValue() {
		return excptValue;
	}

	public void setExcptValue(String excptValue) {
		this.excptValue = excptValue;
	}

	public SeTestCase getTestCase() {
		return testCase;
	}

	public void setTestCase(SeTestCase testCase) {
		this.testCase = testCase;
	}
	
	

	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	public String getElementValue() {
		return elementValue;
	}

	public void setElementValue(String elementValue) {
		this.elementValue = elementValue;
	}

	public SeGetType getGetType() {
		return getType;
	}

	public void setGetType(SeGetType getType) {
		this.getType = getType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


//	@Override
	public int compareTo(SeTestStep o) {
		  if(this.sequence>o.sequence){
			  return 1;
		  }else{
			  return -1;
		  }
	}



	
	
	
}