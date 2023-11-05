package com.english_center.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebMvc
public class AdditionalConfig implements WebMvcConfigurer {

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*"); // Cấu hình origins cho phù hợp với yêu cầu của bạn
		config.addAllowedHeader("*"); // Chấp nhận tất cả các header
		config.addAllowedMethod("*"); // Chấp nhận tất cả các phương thức HTTP
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/api/**") // Đường dẫn API của bạn
//				.allowedOrigins("http://localhost:3000") // Địa chỉ ứng dụng React của bạn
//				.allowedMethods("GET", "POST", "PUT", "DELETE").allowedHeaders("*").allowCredentials(true)
//				.exposedHeaders("Authorization");
//	}
	

 

}
