package com.english_center.response;

import lombok.Data;

@Data
public class GoogleResponse {
	private String username;

	private String email;

	private String image;

	public GoogleResponse() {
	}

	public GoogleResponse(String username, String email, String image) {
		this.username = username;
		this.email = email;
		this.image = image;
	}

}
