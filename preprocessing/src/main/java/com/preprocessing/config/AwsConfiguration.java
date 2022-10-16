package com.preprocessing.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;

@Configuration
@EnableSqs
public class AwsConfiguration {

	@Value("${cloud.aws.region.static}")
	private String awsRegion;

	@Value("${cloud.aws.credentials.access-key}")
	private String awsAccessKey;

	@Value("${cloud.aws.credentials.secret-key}")
	private String awsSecretKey;

	@Bean
	public AmazonS3 client() {
		AWSCredentials credentials = new BasicAWSCredentials(awsAccessKey, awsSecretKey);

		AmazonS3 s3client = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.US_EAST_1).build();
		return s3client;

	}

	@Bean
	public QueueMessagingTemplate queueMessage() {
		return new QueueMessagingTemplate(amazonSQAsync());

	}

	@Bean
	@Primary
	public AmazonSQSAsync amazonSQAsync() {
		AWSCredentials credentials = new BasicAWSCredentials(awsAccessKey, awsSecretKey);

		return AmazonSQSAsyncClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(Regions.US_EAST_1).build();
	}
}
