package test.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.member.db.MemberDAO;
import com.itwillbs.member.db.MemberDTO;

public class MemberUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : MemberUpdateAction_execute() ");
		
		// 세션체크
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		if(id == null){
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;			
		}
		
		// DAO 객체 생성 - id에 해당하는 회원정보 가져오기
		MemberDAO dao = new MemberDAO();
		
		// 추가적으로 dto 객체를 사용하는 경우
		MemberDTO dto = dao.getMember(id);
		//dto.setReg_date("2022-01-20");
		// 해당정보 request 영역에 저장
		request.setAttribute("dto", dto);
		
		// DB에서 전달된 정보를 처리없이 바로 view페이지 전달 하는 경우
		//request.setAttribute("dto", dao.getMember(id));
		
		// 페이지 이동 (updateForm.jsp)
		forward.setPath("./member/updateForm.jsp");
		forward.setRedirect(false);
		return forward;
	}

}
