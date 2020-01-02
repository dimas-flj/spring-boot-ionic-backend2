package com.learn.cursomc.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.learn.cursomc.services.exceptions.FileException;

@Service
public class S3Service {
	private Logger LOG = LoggerFactory.getLogger(S3Service.class);
	
	@Autowired
	private AmazonS3 s3Client;
	
	@Value("${s3.bucket}")
	private String bucket;
	
	// Método de Teste de upload sem endpoint
	public void uploadFile(String localFilePath) throws IOException {
		try {
			File file = new File(localFilePath);
			LOG.info("Iniciando upload.");
			s3Client.putObject(new PutObjectRequest(bucket, "capturar.jpg", file));
			LOG.info("Upload finalizado.");
		}
		catch(AmazonServiceException e) {
			LOG.info("AmazonServiceException: " + e.getErrorMessage());
			LOG.info("Status code: " + e.getErrorCode());
		}
		catch(AmazonClientException e) {
			LOG.info("AmazonClientException: " + e.getMessage());
		}
	}
	
	public URI uploadFile(MultipartFile multipartFile) {
		try {
			String fileName = multipartFile.getOriginalFilename();
			InputStream is = multipartFile.getInputStream();
			String contentType = multipartFile.getContentType();
			
			return uploadFile(is, fileName, contentType);
		}
		catch(IOException e) {
			LOG.info("IOException: " + e.getMessage());
			throw new FileException("Erro de IOException." + e.getMessage());
		}
	}
	
	public URI uploadFile(InputStream is, String fileName, String contentType) throws IOException {
		try {
			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentType(contentType);
			
			LOG.info("Iniciando upload.");
			s3Client.putObject(bucket, fileName, is, meta);
			LOG.info("Upload finalizado.");
			
			return s3Client.getUrl(bucket, fileName).toURI();
		}
		catch(URISyntaxException e) {
			throw new FileException("Erro ao converter URL pata URI.");
		}
	}
}