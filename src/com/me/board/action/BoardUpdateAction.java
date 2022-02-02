package com.me.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.member.action.ActionForward;
import com.itwillbs.member.db.MemberDAO;
import com.itwillbs.member.db.MemberDTO;
import com.me.board.db.BoardDAO;
import com.me.board.db.BoardDTO;

public class BoardUpdateAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
				
				// 한글 처리
				request.setCharacterEncoding("UTF-8");
				// 세션체크
				HttpSession session = request.getSession();
				String id = (String) session.getAttribute("id");
				
				// DAO 객체 생성 - id에 해당하는 회원정보 가져오기
				BoardDAO dao = new BoardDAO();
				//	BoardDTO dto = dao.getMember(id);

				// 해당정보 request 영역에 저장
				request.setAttribute("dto", dto);
				
				// 페이지 이동 (updateForm.jsp)
				ActionForward forward = new ActionForward();
				forward.setPath("./Board/Board_Update.jsp");
				forward.setRedirect(false);
				return forward;
	}

}
