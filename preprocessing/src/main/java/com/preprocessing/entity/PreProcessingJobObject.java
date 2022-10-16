package com.preprocessing.entity;

import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.preprocessing.enums.Status;

@Entity
public class PreProcessingJobObject {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer objectId;

	@Column(name = "input_path")
	private String inputPath;

	@Column(name = "output_path")
	private String outputPath;
	
	@Enumerated(EnumType.STRING)
	private Status status;

	@Column(name = "update_by")
	private String updateBy;

	@Column(name = "insert_by")
	private String insertBy;

	@Column(name = "insertted_at_updated_at")
	private Date insertedAtUpdatedAt;


	public Integer getObjectId() {
		return objectId;
	}

	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}

	public String getInputPath() {
		return inputPath;
	}

	public void setInputPath(String inputPath) {
		this.inputPath = inputPath;
	}

	public String getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getInsertBy() {
		return insertBy;
	}

	public void setInsertBy(String insertBy) {
		this.insertBy = insertBy;
	}

	public Date getInsertedAtUpdatedAt() {
		return insertedAtUpdatedAt;
	}

	public void setInsertedAtUpdatedAt(Date insertedAtUpdatedAt) {
		this.insertedAtUpdatedAt = insertedAtUpdatedAt;
	}

}
