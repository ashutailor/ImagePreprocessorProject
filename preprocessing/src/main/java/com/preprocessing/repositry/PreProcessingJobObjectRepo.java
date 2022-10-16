package com.preprocessing.repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.preprocessing.entity.PreProcessingJobObject;

public interface PreProcessingJobObjectRepo extends JpaRepository<PreProcessingJobObject,Integer> {

	public PreProcessingJobObject findByObjectId(Integer objectId);
	
}
