package test.member;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberFrontController extends HttpServlet{

	protected void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("MemberFrontController_doProcess() 호출! ");
		// 페이지가 GET/POST방식 상관없이 호출될때 실행되는 메서드
		// *.me - 회원정보 처리 동작
		
		// -----------------------1. 가상 주소 계산 --------------------------
		//가상주소 가져오기
		//String url = request.getRequestURL()+"";
		// http://localhost:8088/Model2/test1234.me
		// 프로토콜:// 아이피 : 포트번호 / 프로젝트명 / 가상주소
		//String uri = request.getRequestURI(); 
		// /Model2/test1234.me
		// 프로젝트명/ 가상주소
		//System.out.println("url : "+url);
		//System.out.println("uri : "+uri);
		
		String requestURI = request.getRequestURI();
		System.out.println(" C : requestURI - "+requestURI);
		String ctxPath = request.getContextPath();
		System.out.println(" C : ctxPath - "+ctxPath);
		String command = requestURI.substring(ctxPath.length());
		System.out.println(" C : command - "+command);
		System.out.println(" C : 1. 가상 주소 계산 끝! ");
		// -----------------------1. 가상 주소 계산 --------------------------
		// -----------------------2. 가상 주소 매핑(처리) --------------------
		
		Action action = null;
		ActionForward forward = null;
		
		if(command.equals("/MemberJoin.me")){
			System.out.println(" C : /MemberJoin.me 호출! ");
			// 회원 가입 페이지 보여줘야함. (DBx -> 화면(View-jsp) 출력) 
			
			forward = new ActionForward();
			forward.setPath("./member/insertForm.jsp");
			forward.setRedirect(false);			
		}else if(command.equals("/MemberJoinAction.me")){
			System.out.println(" C : /MemberJoinAction.me 호출! ");
			// 전달정보 저장,DB에 전달,다음페이지로 이동
			// MemberJoinAction 객체 생성
			
			//MemberJoinAction mja = new MemberJoinAction();
			action = new MemberJoinAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/MemberLogin.me")){
			// 로그인 페이지 (view페이지로 이동)
			
			forward = new ActionForward();
			forward.setPath("./member/loginForm.jsp");
			forward.setRedirect(false);			
		}else if(command.equals("/MemberLoginAction.me")){
			// 로그인정보를 전달받아서 처리(DB)
			// MemberLoginAction 객체
			action = new MemberLoginAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/Main.me")){
			// DB정보 없이 view 페이지로 이동
			
			forward = new ActionForward();
			forward.setPath("./member/main.jsp");
			forward.setRedirect(false);			
		}else if(command.equals("/MemberLogout.me")){
			// DB정보 X, view 페이지 X
			// MemberLogoutAction 객체 
			action = new MemberLogoutAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/MemberInfo.me")){
			// DB정보를 가져와서, 해당 view페이지 출력
			// MemberInfoAction() 객체 생성
			action = new MemberInfoAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("/MemberUpdate.me")){
			// DB 정보가져오기, view 페이지에 출력(el) 
			// MemberUpdateAction() 객체 생성
			action = new MemberUpdateAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("/MemberUpdateProAction.me")){
			// DB처리(정보수정), view페이지로 이동(main.jsp)
			// MemberUpdateProAction() 객체
			action = new MemberUpdateProAction();
			
			try {
				action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(command.equals("/MemberList.me")){
			// DB 사용해서 정보를 화면에 바로 출력
			// MemberListAction() 객체 생성
			
			action = new MemberListAction();
			
			try {
				forward = action.execute(request, response);
				
			} catch (Exception e) {

				e.printStackTrace();
			}
			
			
		}
		
		
		
		System.out.println(" C : 2. 가상 주소 매핑(처리) 끝 (페이지이동X)");
		// -----------------------2. 가상 주소 매핑(처리) --------------------
		// -----------------------3. 페이지 이동 -----------------------------
		// 페이지 이동정보가 있을때
		if(forward != null){
			if(forward.isRedirect()){ // true
				response.sendRedirect(forward.getPath());
				System.out.println(" C : 페이지 주소 - "+forward.getPath());
				System.out.println(" C : 페이지 이동 (sendRedirect) ");
			}else{ // false
				RequestDispatcher dis =
						request.getRequestDispatcher(forward.getPath());
				dis.forward(request, response);
				System.out.println(" C : 페이지 주소 - "+forward.getPath());
				System.out.println(" C : 페이지 이동 (forward) ");
			}
		}
		System.out.println(" C : 3. 페이지 이동끝 \n\n\n ");		
		// -----------------------3. 페이지 이동 -----------------------------
	}
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("MemberFrontController_doGet() 호출! ");
		// 페이지가 GET방식으로 호출될때 실행되는 메서드
		doProcess(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("MemberFrontController_doPost() 호출! ");
		// 페이지가 POST방식으로 호출될때 실행되는 메서드
		doProcess(request, response);
	}

}
