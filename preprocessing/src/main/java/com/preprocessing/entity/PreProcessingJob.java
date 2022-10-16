package com.preprocessing.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class PreProcessingJob {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int jobId;
	
	@Column(name="input_folder")
	private String inputFolder;
	
	@Column(name="output_folder")
	private String outputFolder;
	
	@Column(name="job_name")
	private String jobName;
	
	
	@Column(name="pre_processing_job_object")
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="job_object_id")
	private List<PreProcessingJobObject> preProcessingJObOject;
	
	
	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public String getInputFolder() {
		return inputFolder;
	}

	public void setInputFolder(String inputFolder) {
		this.inputFolder = inputFolder;
	}

	public String getOutputFolder() {
		return outputFolder;
	}

	public void setOutputFolder(String outputFolder) {
		this.outputFolder = outputFolder;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public List<PreProcessingJobObject> getPreProcessingJObOject() {
		return preProcessingJObOject;
	}

	public void setPreProcessingJObOject(List<PreProcessingJobObject> preProcessingJObOject) {
		this.preProcessingJObOject = preProcessingJObOject;
	}	
}
