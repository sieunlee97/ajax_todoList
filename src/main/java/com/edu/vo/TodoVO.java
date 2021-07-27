package com.edu.vo;

import org.springframework.stereotype.Component;

@Component
public class TodoVO {
	private Integer no;
	private String list;
	
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}
	public String getList() {
		return list;
	}
	public void setList(String list) {
		this.list = list;
	}

	
}
