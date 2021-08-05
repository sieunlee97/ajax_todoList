package com.edu.toy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.edu.dao.ListDAO;
import com.edu.vo.TodoVO;

@RestController
public class ListController {
	@Inject
	ListDAO listDAO;

//todo항목 조회/출력 메소드(아래) ========================================================================================================================
	@RequestMapping(value="/todo/list", method=RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> todo_list() throws Exception{
		ResponseEntity<Map<String,Object>> result = null;
		Map<String,Object> resultMap = new HashMap<String,Object>();//해시맵타입으로 Json저장소생성
		//Map변수=데이터형 [{'key':'List<>'},{'key':'ClassVO'},{'':''},...]
		
		//DB todo 테이블에서 조회된 결과값
		try {
			List<TodoVO> todoList = listDAO.selectAll();
			if(todoList.isEmpty()) { //todo항목이 없을 때
				result = new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.NO_CONTENT); //코드 204
			}else {					 //todo항목이 있을 때
				resultMap.put("todoList", todoList);			
				//resultMap를 Json데이터로 반환하려면, jackson-databind 모듈이 필수(pom.xml)
				result = new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.OK); //코드 200
			}
		} catch (Exception e) {
			result=new ResponseEntity<Map<String,Object>>(HttpStatus.INTERNAL_SERVER_ERROR); //코드 500
		}
		return result;
	}
//todo항목 입력 메소드(아래) ========================================================================================================================
	@RequestMapping(value="/todo/add", method=RequestMethod.POST)
	public ResponseEntity<String> add_list(@RequestBody TodoVO todoVO) {
		//@RequestBody 클래스는 ajax로 보내온 폼데이터를 ReplyVO 클래스에 바인딩시켜주는 어노테이션 클래스	
		ResponseEntity<String> result = null;
		//예외처리를 상위 메소드로 보내지 않는 이유는 RestAPI에서 예외 메세지를 개발자가 제공하기 위해서
		try {
			//최근 입력한 PK(no)값을 jsp로 보내줍니다.
			int table_no = listDAO.insertList(todoVO);
			if(table_no < 1) { table_no = 1; }
			String no =  Integer.toString(table_no);
			result=new ResponseEntity<String>(no, HttpStatus.OK);
		} catch (Exception e) {
			result=new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}	
		return result; //ResponseEntity클래스형 String 값을 Ajax로 호출한 페이지에 반환.
	}
	
}
