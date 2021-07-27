package com.edu.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.edu.dao.ListDAO;
import com.edu.vo.TodoVO;

@Service
public class ListServiceImpl implements ListService {
	@Inject
	private ListDAO listDAO;
	
	@Override
	public List<TodoVO> selectAll() throws Exception {
		// todo list 전체 불러오기 DAO 클래스 메소드 호출
		return listDAO.selectAll();
	}

	@Override
	public TodoVO viewList(Integer no) throws Exception {
		// todo list 한 항목보기 DAO 클래스 메소드 호출
		return listDAO.viewList(no);
	}

	@Override
	public void insertList(TodoVO todoVO) throws Exception {
		// todo list 추가하기
		listDAO.insertList(todoVO);
		
	}

	@Override
	public void updateList(TodoVO todoVO) throws Exception {
		// todo list 수정하기
		listDAO.updateList(todoVO);		
	}

	@Override
	public void deleteList(Integer no) throws Exception {
		// todo list 삭제하기
		listDAO.deleteList(no);
	}

}
