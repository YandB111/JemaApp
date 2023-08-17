/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 29-Mar-2023
*
*/

package com.jema.app.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Lazy
@Service
public class AwsS3BucketEngine {

	protected Logger logger = LoggerFactory.getLogger(AwsS3BucketEngine.class);

	@Autowired
	private AmazonS3 s3Client;

	public String uploadDocument(MultipartFile file, String bucket, String folder) {
		ObjectMetadata metaData = new ObjectMetadata();
		metaData.setContentType(file.getContentType());
		metaData.setContentLength(file.getSize());
		String filename = folder + "/" + System.nanoTime() + "_" + file.getOriginalFilename();
		try {
			s3Client.putObject(new PutObjectRequest(bucket, filename, file.getInputStream(), metaData)
					.withCannedAcl(CannedAccessControlList.PublicRead));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s3Client.getUrl(bucket, filename).toString();
	}

}
