package test.member;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwillbs.member.db.MemberDAO;
import com.itwillbs.member.db.MemberDTO;

public class MemberJoinAction implements Action{
	//회원가입 처리

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : MemberJoinAction_execute() 호출");
		
		// 한글 처리
		request.setCharacterEncoding("UTF-8");
		// DTO 객체
		MemberDTO dto = new MemberDTO();
		// 전달된 정보를 저장
		dto.setId(request.getParameter("id"));
		dto.setPass(request.getParameter("pass"));
		dto.setName(request.getParameter("name"));
		dto.setAge(Integer.parseInt(request.getParameter("age")));
		dto.setGender(request.getParameter("gender"));
		dto.setEmail(request.getParameter("email"));
		dto.setReg_date(new Timestamp(System.currentTimeMillis()));
		
		System.out.println(" M : "+dto);
		//System.out.println(" M : "+dto.toString());
		
		// DAO 객체 
		MemberDAO dao = new MemberDAO();
		// 전달된 정보를 DB에 저장
		dao.insertMember(dto);
		System.out.println(" M : 회원정보 저장완료! ");
		
		// 페이지 이동 -> 이동정보 저장
		// ActionForward 객체를 생성
		ActionForward forward = new ActionForward();
		forward.setPath("./MemberLogin.me");
		forward.setRedirect(true);		
		return forward;
	}

}
