package com.niit.testcases;

public class RequestDetails {
	
	private Double requirementId;
	private Integer totalTC;
	private Integer totalTCRun;
	private Double failurePercentage;
	private Integer totalPass;
	private Integer totalFail;
	
	
	private Integer moduleQuality;
	private Integer moduleCriticality;

	
	public RequestDetails(Double requirementId,Integer totalTC, Integer totalTCRun, Double failurePercentage,Integer totalPass, Integer totalFail){
		
		this.requirementId = requirementId;
		this.totalTC = totalTC ;
		this.totalTCRun = totalTCRun;
		this.failurePercentage = failurePercentage;
		this.totalPass = totalPass;
		this.totalFail = totalFail;
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

	public Double getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(Double requirementId) {
		this.requirementId = requirementId;
	}

	public Integer getTotalTC() {
		return totalTC;
	}

	public void setTotalTC(Integer totalTC) {
		this.totalTC = totalTC;
	}

	public Integer getTotalTCRun() {
		return totalTCRun;
	}

	public void setTotalTCRun(Integer totalTCRun) {
		this.totalTCRun = totalTCRun;
	}

	public Double getFailurePercentage() {
		return failurePercentage;
	}

	public void setFailurePercentage(Double failurePercentage) {
		this.failurePercentage = failurePercentage;
	}

	public Integer getTotalPass() {
		return totalPass;
	}

	public void setTotalPass(Integer totalPass) {
		this.totalPass = totalPass;
	}

	public Integer getTotalFail() {
		return totalFail;
	}

	public void setTotalFail(Integer totalFail) {
		this.totalFail = totalFail;
	}
	
	
	@Override
    public String toString() {
    	
		return "RequestData{" +
                "ReqId='" + requirementId +
                ", TotalTC=" + totalTC +
                ", TotalTCRun=" + totalTCRun +
                ", FailurePercentage=" + failurePercentage +
                ", TotalPass=" + totalPass +
                ", TotalFail =" + totalFail +
                ", ModuleQuality =" + moduleQuality +
                ", ModuleCriticality =" + moduleCriticality +
                '}';
               
    }

	
	

}
