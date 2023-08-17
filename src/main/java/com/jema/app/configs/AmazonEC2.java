/* 
*  Project : Jema
*  Author  : Raj Khatri
*  Date    : 28-Mar-2023
*
*/

package com.jema.app.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;

@Configuration
public class AmazonEC2 {

	@Value("${AWSID}")
	String awsId;

	@Value("${AWSKEY}")
	String awsKey;

	@Value("${AWSREGION}")
	String region;

	@Bean
	public AmazonS3 amazonEx2() {

		BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsId, awsKey);

		return AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(region))
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();

	}

	@Bean
	TransferManager transferManager() {
		return TransferManagerBuilder.standard().withS3Client(amazonEx2()).build();
	}

}
