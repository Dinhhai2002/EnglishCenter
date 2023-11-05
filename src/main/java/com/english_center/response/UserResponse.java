package com.english_center.response;

import java.util.List;
import java.util.stream.Collectors;

import com.english_center.common.utils.Utils;
import com.english_center.entity.Users;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserResponse {
	private int id;

	@JsonProperty("user_name")
	private String userName;

	@JsonProperty("full_name")
	private String fullName;

	private String email;

	private int avatarId;

	@JsonProperty("avatar_url")
	private String avatarUrl;

	private String phone;

	private int gender;

	private String birthday;

	@JsonProperty("ward_id")
	private int wardId;

	@JsonProperty("city_id")
	private int cityId;

	@JsonProperty("district_id")
	private int districtId;

	@JsonProperty("full_address")
	private String fullAddress;

	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("is_login")
	private int isLogin;

	private int role;

	@JsonProperty("is_active")
	private int isActive;

	@JsonProperty("is_google")
	private int isGoogle;

	public UserResponse() {

	}

	public UserResponse(Users entity) {
		this.id = entity.getId();
		this.userName = entity.getUserName();
		this.fullName = entity.getFullName();
		this.gender = entity.getGender();
		this.birthday = entity.getBirthday() != null ? Utils.getDateStringSupplier(entity.getBirthday()) : "";
		this.email = entity.getEmail();
		this.avatarId = entity.getAvatarId();
		this.avatarUrl = entity.getAvatarUrl();
		this.wardId = entity.getWardId();
		this.cityId = entity.getCityId();
		this.districtId = entity.getDistrictId();
		this.phone = entity.getPhone();
		this.fullAddress = entity.getFullAddress();
		this.accessToken = entity.getAccessToken();
		this.isLogin = entity.getIsLogin();
		this.isActive = entity.getIsActive();
		this.role = entity.getRole();
		this.isGoogle = entity.getIsGoogle();
	}

	public List<UserResponse> mapToList(List<Users> entities) {
		return entities.stream().map(x -> new UserResponse(x)).collect(Collectors.toList());
	}

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
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

}