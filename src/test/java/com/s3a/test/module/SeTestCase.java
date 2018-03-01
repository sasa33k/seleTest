package com.s3a.test.module;


import java.util.List;

public class SeTestCase {
	
	private Long caseId;
	
	private String caseName;
	
	private String caseDesc;
	
	private String caseNo;
	
	private Integer sequence;

	private List<String> subCaseNames;
	
	
	private List<SeTestStep> steps;


	private List<List<SeTestStep>> listOfSteps;



	public Long getCaseId() {
		return caseId;
	}

	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getCaseDesc() {
		return caseDesc;
	}

	public void setCaseDesc(String caseDesc) {
		this.caseDesc = caseDesc;
	}

	public String getCaseNo() {
		return caseNo;
	}

	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}


	public Long getSubCaseId() {
		return caseId;
	}

	public void setSubCaseId(Long caseId) {
		this.caseId = caseId;
	}

	public List<String> getSubCaseNames() {
		return subCaseNames;
	}

	public void setSubCaseNames(List<String> subCaseNames) {
		this.subCaseNames = subCaseNames;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}


	public List<SeTestStep> getSteps() {
		return steps;
	}

	public void setSteps(List<SeTestStep> steps) {
		this.steps = steps;
	}



	public List<List<SeTestStep>> getListOfsteps() {
		return listOfSteps;
	}

	public void setListOfsteps(List<List<SeTestStep>> listOfsteps) {
		this.listOfSteps = listOfsteps;
	}
	

}
