package test.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.member.db.MemberDAO;

public class MemberListAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println(" M : MemberListAction_execute() 호출 !");
		
		
		// 세션제어
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		if(id == null || !id.equals("admin")){
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		// DAO 객체 생성
		MemberDAO dao = new MemberDAO();
		
		// getMemberList() - 전체 회원목록 가져오기
		request.setAttribute("memberList", dao.getMemberList());
		
		// request 영역에 저장
		
		// ./member/List.jsp
		forward.setPath("./member/list.jsp");
		forward.setRedirect(true);
		return forward;
	}
	
	

}
