package com.english_center.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "users")
public class Users extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "full_name")
	private String fullName;

	private String email;

	@Column(name = "avatar_id")
	private int avatarId;

	@Column(name = "avatar_url")
	private String avatarUrl;

	private String phone;

	private String password;

	private int gender;

	private String birthday;

	@Column(name = "ward_id")
	private int wardId;

	@Column(name = "city_id")
	private int cityId;

	@Column(name = "district_id")
	private int districtId;

	@Column(name = "full_address")
	private String fullAddress;

	@Column(name = "access_token")
	private String accessToken;

	@Column(name = "is_login")
	private int isLogin;

	private int role;

	private int otp;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "otp_created_at")
	@CreationTimestamp
	private Date otpCreatedAt;

	@Column(name = "is_confirm_otp")
	private int isConfirmOtp;

	@Column(name = "is_active")
	private int isActive;

	@Column(name = "is_google")
	private int isGoogle;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(int avatarId) {
		this.avatarId = avatarId;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public int getWardId() {
		return wardId;
	}

	public void setWardId(int wardId) {
		this.wardId = wardId;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getDistrictId() {
		return districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(int isLogin) {
		this.isLogin = isLogin;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public int getIsGoogle() {
		return isGoogle;
	}

	public void setIsGoogle(int isGoogle) {
		this.isGoogle = isGoogle;
	}

	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

	public Date getOtpCreatedAt() {
		return otpCreatedAt;
	}

	public void setOtpCreatedAt(Date otpCreatedAt) {
		this.otpCreatedAt = otpCreatedAt;
	}

	public int getIsConfirmOtp() {
		return isConfirmOtp;
	}

	public void setIsConfirmOtp(int isConfirmOtp) {
		this.isConfirmOtp = isConfirmOtp;
	}

}
