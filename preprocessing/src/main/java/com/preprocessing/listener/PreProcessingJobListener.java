package com.preprocessing.listener;


import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.preprocessing.dto.PreProcessingJobObjectMessage;
import com.preprocessing.service.BucketStoreService;

@Component
class PreProcessingJobListener{
	
	Logger logger = LoggerFactory.getLogger(PreProcessingJobListener.class);

	@Autowired
	BucketStoreService service;

	@SqsListener(value = "MessageRecevingQueue.fifo", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
	public void loadMessageFromQueue(String message) {
		logger.info("Message received from queue {}" , message);

		ObjectMapper mapper = new ObjectMapper();

		try {
			PreProcessingJobObjectMessage listener = mapper.readValue(message, PreProcessingJobObjectMessage.class);
			service.updateObject(listener);

		} catch (JsonParseException e) {
			logger.error("Exception occure " ,e);
			e.printStackTrace();
		} catch (JsonMappingException e) {
			logger.error("Exception occure " ,e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("Exception occure " ,e);
			e.printStackTrace();
		}

	}
}