package com.Hyundai.Sso.dto;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.ToString;

@Getter 
@Setter
@Component
@ToString
public class ManagerDTO {

	 public String ROWNUM ="";
	 public String id ="";
	 public String ip = "";
	 public String ip_old = "";
	 public String email = "";
	 public String info = "";
}
