<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ include file="./include/header.jsp" %>
<div class="container">
	<h1 style="text-align:center;">TODO LIST</h1>
	<div>
		<form action="todo_list.jsp" name="todo_form" method="post" class="form-group" style="width:80%; margin:0 auto;" >
			<div style="text-align:center; margin-bottom:10px;">
				<input class="form-control" type="text" name="todo" id="todo" style="width:85%;height:50px; display:inline-block;">
				<input type="hidden" name="no" id="no" value="${todoVO.no}">
				<button class="btn btn-primary btn-lg" type="button" id="addBtn">+</button>
			</div>
			<div style="text-align:center; width:100%;">
			<!-- +버튼 눌렀을 때 ajax활용해 todo항목 나타날 위치 -->
				<div id="div_todoList">
				
				<!--<c:forEach items="${todo_list}" var="todo_list">
                <div class="card card-default card-outline"> 
	              <div class="card-header" style="width:100%;">
	                <h5 class="card-title">${todo_list.list}</h5>
	                <div class="card-tools">
	                  <a href="#" class="btn btn-tool">
	                    <i class="fas fa-pen"></i>
	                  </a>
	                </div>
	              </div>
	            </div>
            	</c:forEach> -->
            	
	            </div>			
			</div>	
		</form>	
		
		
	</div>
</div>
<%@ include file="./include/footer.jsp" %>

<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>

<script id="template" type="text/x-handlebars-template">
{{#each .}}
<div class="template-div" data-rno="{{no}}">
<div class="card card-default card-outline"> 
	<div class="card-header" style="width:100%;">
	     <h5 class="card-title">{{list}}</h5>
	     <div class="card-tools">
	          <a href="#" class="btn btn-tool">
	             <i class="fas fa-pen"></i>
	          </a>
	     </div>
	</div>
</div>
{{/each}}
</script>

<!-- 화면을 재구현Representation하는 함수(아래) -->
<script>
var printList = function(data, target, templateObject) {
	var template = Handlebars.compile(templateObject.html()); //html태그로 변환
	var html = template(data); //빅데이터를 리스트탬플릿에 바인딩(데이터결합,묶음) 역할. 변수 html에 저장되었음.
	$(".template-div").remove(); // 화면에 보이는 댓글리스트만 지우기
	//target.after(html); //target은 .time-label 클래스 영역을 가리킨다.
	//target.append(html); //target은 #div_reply 아이디 영역을 가리킨다. append는 내부내용 기존 내용의 뒤에 추가
	target.prepend(html); //prepend 내부 내용 추가시 기존 내용의 앞에 추가한다.
};
</script>

<!-- btn_reply_list버튼에 적용한 ajax로 댓글 리스트를 구하는 함수를 외부로 뺀다.-->
<!-- 왜? btn_reply_list에 토글기능을 적용돼서, 토글기능과 Ajax기능을 분리하는 목적 -->
<script>
var todoList = function() {
	$.ajax({
		type:"post",
		url:"/todo/list", //게시물번호에 대한 댓글 목록을 가져오는 URL
		dataType:"json", //받을 때 JSON데이터를 받는다.
		success:function(result){ //result에는 댓글 목록을 Json데이터로 받는다.
			//alert(result.list);
			if(typeof result=="undefined" || result == null || result == ""){
				$("#div_todoList").empty(); //조회된 값 없을 때, 화면 내용 클리어.
				$("#div_todoList").html('<div></div>');
				alert("조회된 값이 없습니다.");
			}else{				
				//위에서 정의한 printReplyList(Json데이터, 출력위치타켓, 빵틀);  
				
				printList(result.todoList, $("#div_todoList"), $("#template"));//화면에 출력하는 구현함수를 호출하면 실행.
			}		
		},
		error:function(result){
			alert("RestAPI서버에 문제가 발생했습니다. 다음에 이용해주세요.");
		}
	});	
}
</script>

<!-- 댓글 등록 버튼 액션 처리 -->
<script>
	$(document).ready(function(){	
		$("#addBtn").on("click", function() { // 등록 버튼 클릭했을 때 구현 내용
			// Ajax 이용해서, 화면을 Representation (REST-API방식) 부분 화면을 재구현
			// REST API 서버 단에 보낼 변수 값 정의(아래)
			var list =$("#todo").val(); //jquery변수, input태그값
			if(list==""){
				alert("항목을 입력하세요.");
				return false;
			}
			$.ajax({
				//여기서부터는 프론트엔드 개발자 영역
				type:'post',
				url:'/todo/add', //jsp로 가면, ListController에서 지정한 url로 변경
				dataType:'text', //ListController로부터 데이터를 text형식으로 받겠다고 명시.
				// 백엔드로 보내주는 작업 (아래)
				headers:{ //데이터를 json으로 보낼 것이다.
					"Content-Type":"application/json",
					"X-HTTP-Method-Override":"POST"
				},
				data:JSON.stringify({
					list:list,
				}),			
				success:function(result){ //응답이 성공하면(상태값 200 OK), 위 경로에서 반환받은 result(JSON 텍스트 데이터) 이용해서 화면 재구현
					todoList();
					$("#todo").val(""); //등록후 입력 항목 비워주기
				},
				error:function(result){
					alert("RestAPI서버가 작동하지 않습니다.");
				}
			});
		});
	});
</script>