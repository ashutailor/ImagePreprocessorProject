package com.preprocessing.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import com.preprocessing.enums.Status;

public class PreProcessingJobObjectMessage {
	
	private Integer jobId;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	private String outputPath;
	
	private Integer objectId;

	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	public Integer getObjectId() {
		return objectId;
	}

	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}
	
	
	
	
}
