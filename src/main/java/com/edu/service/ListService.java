package com.edu.service;

import java.util.List;

import com.edu.vo.TodoVO;

public interface ListService {
	public List<TodoVO> selectAll() throws Exception;
	public int insertList(TodoVO todoVO) throws Exception;
	public void updateList(TodoVO todoVO) throws Exception;
	public void deleteList(Integer no) throws Exception;
}
