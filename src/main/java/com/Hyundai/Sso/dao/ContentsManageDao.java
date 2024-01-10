package com.Hyundai.Sso.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.Hyundai.Sso.dto.ContentsDTO;
import com.Hyundai.Sso.dto.ManagerDTO;

@Mapper
public interface ContentsManageDao {

	List<ContentsDTO>ContentPhysicalList(HashMap<String,String> mp)throws Exception;
	int ContentListToTalCnt(HashMap<String,String> mp)throws Exception;
	int ContentInsert(ContentsDTO conts)throws Exception;
	int ContentUpdate(ContentsDTO conts)throws Exception;
	/*조회수 증가*/
	int ReadCountUpdate(HashMap<String,String> mp)throws Exception;
	void ContentDelete(List<String> ls)throws Exception;
	ContentsDTO ContentView(HashMap<String,String> mp)throws Exception;
	/*사용자 홈페이지*/
	List<ContentsDTO>ContentUserList(HashMap<String,String> mp)throws Exception;
	List<ContentsDTO>ContentSearchList(HashMap<String,String> mp)throws Exception;
	/*ip*/
	int ManagerInsert(ManagerDTO conts)throws Exception;
	int ManagerUpdate(ManagerDTO conts)throws Exception;
	ManagerDTO MemberInfo(HashMap<String,String> mp)throws Exception;
	List<ManagerDTO>ManagerList(HashMap<String,String> mp)throws Exception;
	void ManagerDelete(List<String> ls)throws Exception;
	
	///////////////////////////////////////////////////////////////////////////////
	/**
	 * 영문
	 * 
	 **/
	List<ContentsDTO>ContentPhysicalList_EN(HashMap<String,String> mp)throws Exception;
	int ContentListToTalCnt_EN(HashMap<String,String> mp)throws Exception;
	int ContentInsert_EN(ContentsDTO conts)throws Exception;
	int ContentUpdate_EN(ContentsDTO conts)throws Exception;
	
	/*조회수 증가*/
	int ReadCountUpdate_EN(HashMap<String,String> mp)throws Exception;
	void ContentDelete_EN(List<String> ls)throws Exception;
	ContentsDTO ContentView_EN(HashMap<String,String> mp)throws Exception;
	/*사용자 홈페이지*/
	List<ContentsDTO>ContentUserList_EN(HashMap<String,String> mp)throws Exception;
	List<ContentsDTO>ContentSearchList_EN(HashMap<String,String> mp)throws Exception;
}
