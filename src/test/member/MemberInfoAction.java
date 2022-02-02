package test.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.member.db.MemberDAO;
import com.itwillbs.member.db.MemberDTO;

public class MemberInfoAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : MemberInfoAction_execute() ");
		
		// ID정보를 가져오기(세션)
		// 아이디정보를 체크(비로그인시에 로그인페이지로 이동)
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		if(id == null){
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;			
		}
		
		// DAO객체 생성 - ID에 해당하는 사용자 정보(DTO)를 모두 가져오기
		MemberDAO dao = new MemberDAO();
		MemberDTO dto = dao.getMember(id);
		
		// view페이지로 정보를 전달하기 위해서 DTO정보 저장(request 영역)
		request.setAttribute("dto", dto);		
		
		// info.jsp 페이지로 이동
		forward.setPath("./member/info.jsp");
		forward.setRedirect(false);
		
		return forward;
	}
	
	

}
