package com.me.board.action;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.me.board.action.ActionForward;
import com.me.board.db.BoardDAO;
import com.me.board.db.BoardDTO;

public class BoardWriteAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 한글 처리
		request.setCharacterEncoding("UTF-8");
		// 세션체크
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
				
			
		// DTO 객체 생성
		
		BoardDTO dto = new BoardDTO();
		
		// 전달된 정보를 저장
		
		dto.setWrite_time(new Timestamp(System.currentTimeMillis()));
		dto.setWhen_name("when_name"); // 언제
		dto.setWhat_name("what_name"); // 음식
		dto.setWhere_name("where_name"); // 어디서
		dto.setUpload_image("upload_image"); //첨부파일
		dto.setId(id); // 세션ID가져와서 사용
		//dto.setUpload_image(upload_image);; 
		dto.setContent("content"); // 본문
		
		System.out.println(" BoardAction : "+dto);
		
		BoardDAO dao = new BoardDAO();
		dao.insertBoard(dto);
		
		System.out.println(" B : 글정보 저장완료! ");
		
		ActionForward forward = new ActionForward();
		forward.setPath("./Board_List.me");
		forward.setRedirect(true);		
		return forward;
	}

}
