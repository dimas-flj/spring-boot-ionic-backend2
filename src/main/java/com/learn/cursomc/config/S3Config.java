package com.learn.cursomc.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Config {
	@Value("${aws.access_key_id}")
	private String aws_access_key_id;
	
	@Value("${aws.secret_access_key}")
	private String aws_secret_access_key;
	
	@Value("${s3.region}")
	private String s3_region;
	
	@Bean
	public AmazonS3 s3Client() throws IOException {
		BasicAWSCredentials awsCred = new BasicAWSCredentials(aws_access_key_id, aws_secret_access_key);
		AmazonS3 s3Client = 
				AmazonS3ClientBuilder.
				standard().
				withRegion(Regions.fromName(s3_region)).
				withCredentials(new AWSStaticCredentialsProvider(awsCred)).
				build();
		
		return s3Client;
	}
}