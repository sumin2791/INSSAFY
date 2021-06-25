package com.ssafy.pjt1;

import java.util.Arrays;

import com.ssafy.pjt1.common.interceptor.JwtInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableScheduling
@SpringBootApplication
@EnableCaching
public class Pjt1Application implements WebMvcConfigurer {

	@Autowired
	public JwtInterceptor jwtInterceptor;

	public static void main(String[] args) {
		SpringApplication.run(Pjt1Application.class, args);

	}

	// JWTInterceptor를 설치한다. 경로 조건을 설정한다.
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor).addPathPatterns("/api/**") // 기본 적용 경로
				.excludePathPatterns(Arrays.asList("/account/confirm/**"))// 회원은 인터셉터 제외
				// 스웨거도 인터셉터 제외
				.excludePathPatterns("/swagger-resources/**").excludePathPatterns("/swagger-ui.html")
				.excludePathPatterns("/v2/api-docs").excludePathPatterns("/webjars/**");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*").maxAge(3600)
				.exposedHeaders("auth_token");
	}
}