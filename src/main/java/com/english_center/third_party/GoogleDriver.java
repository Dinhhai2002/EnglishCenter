package com.english_center.third_party;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;

@Configuration
public class GoogleDriver {
	@SuppressWarnings("deprecation")
	@Autowired
	private GoogleCredential googleCredential;

	@Value("${key.driver}")
	private String keyDriver;

	@Bean
	public Drive getService() throws GeneralSecurityException, IOException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		return new Drive.Builder(HTTP_TRANSPORT, JacksonFactory.getDefaultInstance(), googleCredential).build();
	}

	@SuppressWarnings("deprecation")
	@Bean
	public GoogleCredential googleCredential() throws GeneralSecurityException, IOException {
		System.out.println(keyDriver);
		Collection<String> elenco = new ArrayList<>();
		elenco.add("https://www.googleapis.com/auth/drive");
		HttpTransport httpTransport = new NetHttpTransport();
		JacksonFactory jsonFactory = new JacksonFactory();
		return new GoogleCredential.Builder().setTransport(httpTransport).setJsonFactory(jsonFactory)
				.setServiceAccountId("video-driver@englishcenter-398815.iam.gserviceaccount.com")
				.setServiceAccountScopes(elenco).setServiceAccountPrivateKeyFromP12File(
						new File("E:\\Java\\aloapp\\english_center\\src\\main\\resources\\englishcenter.p12"))
				.build();
	}
}
