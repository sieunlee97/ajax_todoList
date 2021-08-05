package com.edu.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.edu.vo.TodoVO;

@Repository
public class ListDAOImpl implements ListDAO {
	@Inject
	private SqlSession sqlSession;
	
	@Override
	public List<TodoVO> selectAll() throws Exception {
		return sqlSession.selectList("todoMapper.selectAll");
		
	}

	@Override
	public int insertList(TodoVO todoVO) throws Exception {
		sqlSession.insert("todoMapper.insertList", todoVO);
		return todoVO.getNo();
	}

	@Override
	public void updateList(TodoVO todoVO) throws Exception {
		sqlSession.update("todoMapper.updateList", todoVO);
		
	}

	@Override
	public void deleteList(Integer no) throws Exception {
		sqlSession.delete("todoMapper.deleteList", no);
	}

}
