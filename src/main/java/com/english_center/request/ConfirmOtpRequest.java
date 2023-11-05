package com.english_center.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfirmOtpRequest {
	@NotEmpty(message = "user_name không được phép để trống")
	@Length(max = 255, message = "Không được phép lớn hơn 255 kí tự")
	@JsonProperty("user_name")
	private String userName;

	@NotEmpty(message = "email không được phép để trống")
	@Length(max = 255, message = "Không được phép lớn hơn 255 kí tự")
	@Email(message = "email chưa đúng định dạng")
	private String email;

	@Min(value = 0, message = "giá trị nhỏ nhất 0.")
	private int otp;

	@Min(value = 0, message = "type nhỏ nhất 0.")
	@Max(value = 1, message = "type lớn nhất 1.")
	private int type;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
