package com.example.jangboo.univ.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UnivService {
	@Value("${jangboo.front_end.url}")
	private String pageUrl;

	public String getDeptSignUpLink(Long deptId){
		return String.format(pageUrl+"/pages/signup.html?&deptId=%d&role=%s",deptId,"STUDENT");
	}
}
