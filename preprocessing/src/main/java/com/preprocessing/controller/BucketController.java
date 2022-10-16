package com.preprocessing.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.preprocessing.entity.PreProcessingJob;
import com.preprocessing.entity.PreProcessingJobObject;
import com.preprocessing.request.PreprocessingRequest;
import com.preprocessing.service.BucketStoreService;

@RestController
//@RequestMapping("/preprocessing")
public class BucketController {

	@Autowired
	BucketStoreService bucketService;
	
	Logger logger = LoggerFactory.getLogger(BucketController.class);

	@PostMapping("/preprocessing/create-preprocessing-job")
	ResponseEntity< PreProcessingJob> getList(@RequestBody PreprocessingRequest uri) {
		//logger.info(null);
		PreProcessingJob preProcessingJob = bucketService.objectList(uri);
		//API for create pre processing job started
		return ResponseEntity.status(HttpStatus.OK).body(preProcessingJob);
	}

	@GetMapping("/find-all/{id}")
	ResponseEntity<PreProcessingJob> getalljobs(@PathVariable Integer id) {
		return ResponseEntity.status(HttpStatus.FOUND).body(bucketService.getbyId(id));
	}
	
	@GetMapping("/objects/{objectId}")
	ResponseEntity<PreProcessingJobObject> getAllObjects(@PathVariable Integer objectId) {
		return ResponseEntity.status(HttpStatus.FOUND).body(bucketService.getAllObjectById(objectId));
	}
	
	
}
