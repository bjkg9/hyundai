package com.Hyundai.Sso.dto;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.ToString;

@Getter 
@Setter
@Component
@ToString
public class ContentsDTO {

	public int NO = 0;
	public String ROWNUM = "";
	public String TITLE = "";
	public String CATEGORY = "";
	public String CONTENTS = "";
	public String WRITER = "";
	public String REG_DATE = "";
	public String UPDATE_DATE = "";
	public int READ_COUNT = 0;
	public int TOTAL_COUNT =0;
}
