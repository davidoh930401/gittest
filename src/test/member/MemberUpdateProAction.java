package test.member;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.member.db.MemberDAO;
import com.itwillbs.member.db.MemberDTO;

public class MemberUpdateProAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : MemberUpdateProAction_execute() 호출");
		
		// 세션체크
		
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		if(id == null){
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;			
		}
		
		
		//post 방식으로 받으니까 한글처리 필수
		request.setCharacterEncoding("UTF-8");
		// 이전페이지에서 전달된 정보 저장(DTO)
		MemberDTO dto = new MemberDTO();
		
		dto.setId(id);
		dto.setPass(request.getParameter("pass"));
		dto.setName(request.getParameter("name"));
		dto.setAge(Integer.parseInt(request.getParameter("age")));
		dto.setEmail(request.getParameter("email"));
		dto.setGender(request.getParameter("gender"));
		
				
		// DAO 객체 생성 - 정보수정 메서드 호출
		MemberDAO dao = new MemberDAO();
		int result = dao.updateMember(dto);
				
		// 처리 결과에 따른 페이지 이동(0,1,-1)-js
		if(result == 0){
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			out.print("<script>");
			out.print(" alert('비밀번호 오류'); ");
			out.print(" history.back(); ");
			out.print("</script>");
			
			out.close();
			return null;
			
		}
		
		if(result == -1){
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			out.print("<script>");
			out.print(" alert('회원정보 없음'); ");
			out.print(" history.back(); ");
			out.print("</script>");
			
			out.close();
			return null;
			
		}
		
		// result == 1
		
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		out.print("<script>");
		out.print(" alert(' 수정 완료! '); ");
		out.print(" location.href='./Main.me';");
		out.print("</script>");
		
		out.close();
		
		return null;
	}

}
