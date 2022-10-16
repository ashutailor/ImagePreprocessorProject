package com.preprocessing.dto;

import java.util.List;

import com.preprocessing.entity.PreProcessingJobObject;

public class OutgoingSQSMessage {
	
	private Integer jobId;
	private String outputFolder;
	private List<PreProcessingJobObject> preProcessingJobObject;
	
	
	public Integer getJobId() {
		return jobId;
	}
	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}
	public String getOutputFolder() {
		return outputFolder;
	}
	public void setOutputFolder(String outputFolder) {
		this.outputFolder = outputFolder;
	}
	public List<PreProcessingJobObject> getPreProcessingJobObject() {
		return preProcessingJobObject;
	}
	public void setPreProcessingJobObject(List<PreProcessingJobObject> preProcessingJobObject) {
		this.preProcessingJobObject = preProcessingJobObject;
	}
	
}