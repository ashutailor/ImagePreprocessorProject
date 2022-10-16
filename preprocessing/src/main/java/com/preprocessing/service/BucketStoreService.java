package com.preprocessing.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Service;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3URI;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.preprocessing.dto.PreProcessingJobObjectMessage;
import com.preprocessing.dto.OutgoingSQSMessage;
import com.preprocessing.entity.PreProcessingJob;
import com.preprocessing.entity.PreProcessingJobObject;
import com.preprocessing.enums.Status;
import com.preprocessing.repositry.PreProcessingJobObjectRepo;
import com.preprocessing.repositry.PreProcessingJobRepo;
import com.preprocessing.request.PreprocessingRequest;

@Service
public class BucketStoreService {

	private static final String MESSAGE_GROUP_ID = "message-group-id";
	private static final String HELLO_SQS1 = "hello-SQS1";
	private static final String JAYSHREE = "jayshree";

	Logger logger = LoggerFactory.getLogger(BucketStoreService.class);

	@Autowired
	private AmazonS3 amazonS3;

	@Autowired
	private QueueMessagingTemplate queueMessage;

	@Value("${cloud.aws.end-point.uri}")
	private String endPoint;

	@Autowired
	PreProcessingJobRepo preProcessingRepo;

	@Autowired
	PreProcessingJobObjectRepo jobObjectRepo;

	public PreProcessingJob objectList(PreprocessingRequest uri) {

		AmazonS3URI url = new AmazonS3URI(uri.getInputFolder());

		ListObjectsRequest rangeObjectRequest = new ListObjectsRequest();
		rangeObjectRequest.setBucketName(url.getBucket());
		rangeObjectRequest.setPrefix(url.getKey());
		logger.info("Bucket and keys found{}", rangeObjectRequest);

		ObjectListing objectListing = amazonS3.listObjects(rangeObjectRequest);
		List<S3ObjectSummary> summaries = objectListing.getObjectSummaries();

		while (objectListing.isTruncated()) {
			objectListing = amazonS3.listNextBatchOfObjects(objectListing);
			summaries.addAll(objectListing.getObjectSummaries());
		}

		List<PreProcessingJobObject> preProcessingJobObjects = new ArrayList<PreProcessingJobObject>();
		for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {

			PreProcessingJobObject objectPreprocessing = new PreProcessingJobObject();
			objectPreprocessing.setInputPath(uri.getInputFolder() + objectSummary.getKey());
			objectPreprocessing.setStatus(Status.PENDING);
			objectPreprocessing.setInsertBy(JAYSHREE);
			preProcessingJobObjects.add(objectPreprocessing);
		}

		PreProcessingJob preProcessingjob = new PreProcessingJob();

		preProcessingjob.setInputFolder(uri.getInputFolder());
		preProcessingjob.setOutputFolder(uri.getInputFolder());
		preProcessingjob.setJobName(uri.getJobName());
		preProcessingjob.setPreProcessingJObOject(preProcessingJobObjects);
		logger.info("Data Save succesfully {}", preProcessingjob);
		PreProcessingJob preJob = preProcessingRepo.save(preProcessingjob);

		PreProcessingJob jobObjects = preProcessingRepo.getById(preJob.getJobId());
		List<PreProcessingJobObject> list = jobObjects.getPreProcessingJObOject();

		OutgoingSQSMessage outgoingSQS = new OutgoingSQSMessage();
		outgoingSQS.setJobId(preProcessingjob.getJobId());
		outgoingSQS.setOutputFolder(preProcessingjob.getOutputFolder());

		int numberOfBunch = 10;

		int size = list.size();
		int m = size / numberOfBunch;
		if (size % numberOfBunch != 0) {
			m++;
		}

		Map<String, Object> message = new HashMap<>();
		message.put(MESSAGE_GROUP_ID, HELLO_SQS1);

		for (int i = 0; i < m; i++) {
			int fromIndex = i * numberOfBunch;
			int toIndex = (i * numberOfBunch + numberOfBunch < size) ? (i * numberOfBunch + numberOfBunch) : size;
			outgoingSQS.setPreProcessingJobObject(list.subList(fromIndex, toIndex));
			logger.info("Data send in Queue {}", message);
			queueMessage.convertAndSend(endPoint, outgoingSQS, message);
		}

		return preJob;
	}

	public PreProcessingJob getbyId(Integer jobId) {
		return preProcessingRepo.getById(jobId);
	}

	public PreProcessingJobObject getAllObjectById(Integer objectId) {
		return jobObjectRepo.findByObjectId(objectId);
	}

	public void updateObject(PreProcessingJobObjectMessage listener) {

		Optional<PreProcessingJobObject> objectResult = jobObjectRepo.findById(listener.getObjectId());
		PreProcessingJobObject preProcessingJobObject = null;

		if (objectResult.isPresent()) {
			preProcessingJobObject = objectResult.get();
		}
		preProcessingJobObject.setStatus(listener.getStatus());
		preProcessingJobObject.setOutputPath(listener.getOutputPath());
		logger.info("Update sucessfully {}", preProcessingJobObject);
		jobObjectRepo.save(preProcessingJobObject);

	}

}
