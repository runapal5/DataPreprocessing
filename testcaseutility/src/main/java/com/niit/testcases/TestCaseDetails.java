package com.niit.testcases;

import java.util.Comparator;
import java.util.Date;

public class TestCaseDetails implements Comparable<TestCaseDetails>{
	
	private Double requirementId;
	private Double testCaseId;
	private Double runID;
	private Integer runStatus;
	private Date testExecutedOn;
	private String testCreatedBy;
	private String testCaseName;
	
	private Integer runCycle;
	private Integer regWt;
	
	//At Request Level
	private Integer moduleQuality;
	private Integer moduleCriticality;
	
	

	public TestCaseDetails(Double requirementId,Double testCaseId,Double runID,Integer runStatus,Date testExecutedOn,String testCreatedBy,String testCaseName){
		
		this.requirementId = requirementId ;
		this.testCaseId = testCaseId;
		this.runID = runID;
		this.runStatus = runStatus;
		this.testExecutedOn = testExecutedOn;
		this.testCreatedBy = testCreatedBy;
		this.testCaseName = testCaseName;
	    	
	}
	
	
	public Integer getModuleQuality() {
		return moduleQuality;
	}


	public void setModuleQuality(Integer moduleQuality) {
		this.moduleQuality = moduleQuality;
	}


	public Integer getModuleCriticality() {
		return moduleCriticality;
	}


	public void setModuleCriticality(Integer moduleCriticality) {
		this.moduleCriticality = moduleCriticality;
	}


	public Integer getRegWt() {
		return regWt;
	}


	public void setRegWt(Integer regWt) {
		this.regWt = regWt;
	}

	
	public Integer getRunCycle() {
		return runCycle;
	}


	public void setRunCycle(Integer runCycle) {
		this.runCycle = runCycle;
	}

	
	public Double getRequirementId() {
		return requirementId;
	}


	public void setRequirementId(Double requirementId) {
		this.requirementId = requirementId;
	}


	public Double getTestCaseId() {
		return testCaseId;
	}
	public void setTestCaseId(Double testCaseId) {
		this.testCaseId = testCaseId;
	}
	public Double getRunID() {
		return runID;
	}
	public void setRunID(Double runID) {
		this.runID = runID;
	}
	public Integer getRunStatus() {
		return runStatus;
	}
	public void setRunStatus(Integer runStatus) {
		this.runStatus = runStatus;
	}
	public Date getTestExecutedOn() {
		return testExecutedOn;
	}
	public void setTestExecutedOn(Date testExecutedOn) {
		this.testExecutedOn = testExecutedOn;
	}
	public String getTestCreatedBy() {
		return testCreatedBy;
	}
	public void setTestCreatedBy(String testCreatedBy) {
		this.testCreatedBy = testCreatedBy;
	}
	public String getTestCaseName() {
		return testCaseName;
	}
	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}
	
	
	
	@Override
    public String toString() {
    	
		return "TestData{" +
                "ReqId='" + requirementId +
                ", TestId=" + testCaseId +
                ", RunID=" + runID +
                ", RunStatus=" + runStatus +
                ", RunCycle=" + getRunCycle() +
                ", RegWt=" + getRegWt() +
                ", ModuleQuality=" + moduleQuality +
                ", ModuleCriticality=" + moduleCriticality +
                '}';
               
    }

	
	public int compareTo(TestCaseDetails testDetail) {

		int compareRunID = ((TestCaseDetails) testDetail).getRunID().intValue();

		//ascending order
		return this.runID.intValue() - compareRunID;

		//descending order
		//return compareRunID - this.runID.intValue();

	}
	
	
	

	

}
