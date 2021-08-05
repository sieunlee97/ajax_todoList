package com.edu.dao;

import java.util.List;

import com.edu.vo.TodoVO;

public interface ListDAO {
	public List<TodoVO> selectAll() throws Exception;
	public int insertList(TodoVO todoVO) throws Exception;
	public void updateList(TodoVO todoVO) throws Exception;
	public void deleteList(Integer no) throws Exception;
}
