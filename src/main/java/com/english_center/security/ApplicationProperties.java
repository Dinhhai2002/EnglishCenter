/**
 * 
 */
package com.english_center.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class ApplicationProperties {

	@Value("${base.url}")
	private String baseUrl;

	@Value("${base.url.frontend}")
	private String baseUrlFe;

	@Value("${key.driver}")
	private String keyDriver;

	@Value("${password.account.google}")
	private String passwordAccountGoogle;

	@Value("${key.folder.upload}")
	private String folderUpload;

	@Value("${vnpay.secretKey}")
	private String secretKey;

	@Value("${vnpay.vnp_TmnCode}")
	private String vnpTmnCode;

	@Value("${vnpay.vnp_PayUrl}")
	private String vnpPayUrl;

	@Value("${vnpay.vnp_ApiUrl}")
	private String vnpApiUrl;

}