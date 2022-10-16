package com.preprocessing.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.preprocessing.entity.PreProcessingJob;

@Repository
public interface PreProcessingJobRepo extends JpaRepository<PreProcessingJob, Integer>{
	
}
