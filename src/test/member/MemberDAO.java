package test.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class MemberDAO {
	// 회원정보를 처리하는 DAO

	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private String sql = "";

	// 디비연결
	private Connection getCon() throws Exception {
		Context initCTX = new InitialContext();
		DataSource ds = (DataSource) initCTX.lookup("java:comp/env/jdbc/model2");
		con = ds.getConnection();
		return con;
	}

	// 자원해제
	public void CloseDB() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// insertMember(dto)
	public void insertMember(MemberDTO dto) {
		try {
			con = getCon();
			// sql 작성
			sql = "insert into itwill_member(id,pass,name,age,gender,email,reg_date) " + "values(?,?,?,?,?,?,?)";
			// pstmt 객체 생성
			pstmt = con.prepareStatement(sql);

			// ??
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPass());
			pstmt.setString(3, dto.getName());
			pstmt.setInt(4, dto.getAge());
			pstmt.setString(5, dto.getGender());
			pstmt.setString(6, dto.getEmail());
			pstmt.setTimestamp(7, dto.getReg_date());

			// sql 실행
			pstmt.executeUpdate();

			System.out.println(" DAO : 회원가입 완료! ");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseDB();
		}
	}
	// insertMember(dto)

	// loginCheck(id,pass)
	public int loginCheck(String id, String pass) {
		int result = -1;
		try {
			// 1. 드라이버로드
			// 2. 디비연결
			// => getCon() 실행
			con = getCon();

			// 3. sql 작성 & pstmt 객체 생성
			sql = "select pass from itwill_member where id=?";
			pstmt = con.prepareStatement(sql);

			// ?
			pstmt.setString(1, id);

			// 4. sql 실행
			rs = pstmt.executeQuery();

			// 아이디가 없는경우 (-1)리턴
			// 아이디는 있지만, 비밀번호 오류 (0)리턴
			// 아이디 있으면서, 비밀번호도 맞는경우 (1)리턴
			// 5. 최종 결과 리턴
			if (rs.next()) {
				// 회원정보 있음
				if (pass.equals(rs.getString("pass"))) {
					// 회원정보가 있으면서, 비밀번호 동일
					result = 1;
				} else {
					// 회원정보가 있음, 비밀번호 다름
					result = 0;
				}
			} else {
				// 회원정보 없음
				result = -1;
			}

			System.out.println(" SQL구문 실행 완료! ");
			System.out.println(" 실행결과 : " + result);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseDB();
		}

		return result;
	}
	// loginCheck(id,pass)

	// getMember(id)
	public MemberDTO getMember(String id) {

		MemberDTO dto = null;

		try {
			// 1.2. 디비연결
			con = getCon();
			// 3. sql 작성(select) & pstmt 객체
			// sql- 전달받은 아이디에 해당하는 회원정보 모두가져오기
			sql = "select * from itwill_member where id=?";
			pstmt = con.prepareStatement(sql);

			// ?
			pstmt.setString(1, id);

			// 4. sql 실행
			rs = pstmt.executeQuery();
			// 5. 데이터처리
			if (rs.next()) {
				// 데이터가 있음 -> 회원정보를 저장
				dto = new MemberDTO();

				dto.setId(rs.getString("id"));
				dto.setPass(rs.getString("pass"));
				dto.setName(rs.getString("name"));
				dto.setAge(rs.getInt("age"));
				dto.setGender(rs.getString("gender"));
				dto.setEmail(rs.getString("email"));
				dto.setReg_date(rs.getTimestamp("reg_date"));
			}
			System.out.println(" SQL구문 실행완료! ");
			System.out.println(" 회원정보 : " + dto);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseDB();
		}

		return dto;
	}
	// getMember(id)

	// updateMember(mb)
	public int updateMember(MemberDTO dto) {
		int result = -1;

		try {
			// 1.2.디비연결
			con = getCon();
			// 3. sql 작성(select) & pstmt 객체생성
			sql = "select pass from itwill_member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			// 4. sql 실행
			rs = pstmt.executeQuery();
			// 5. 데이터 처리
			if (rs.next()) { // 데이터 있을때

				if (dto.getPass().equals(rs.getString("pass"))) { // 본인
					// 3. sql 작성 (update) & pstmt 객체 생성
					sql = "update itwill_member set name=?, age=?, gender=?, email=? where id=?";
					pstmt = con.prepareStatement(sql);

					pstmt.setString(1,dto.getName());
					pstmt.setInt(2, dto.getAge());
					pstmt.setString(3, dto.getGender());
					pstmt.setString(4, dto.getEmail());
					pstmt.setString(5, dto.getId());

					// 4. sql 실행
					result = pstmt.executeUpdate();
					// -> sql구문이 실행했을때 영향을 준 row수 리턴

					System.out.println(" 회원정보 수정완료~! ");
					// result = 1;

				} else {
					// 비밀번호 오류
					result = 0;
				}
			} else {
				// 데이터 없을때
				result = -1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseDB();
		}

		return result;
	}
	// updateMember(mb)
	
	
	// getMemberList()
		public ArrayList getMemberList(){
			// 가변길이 배열
			ArrayList memberList = new ArrayList();
			
			try {
				// 1.2. 디비연결
				con = getCon();
				// 3. sql 작성 & pstmt 객체생성
				//sql = "select * from itwill_member";
				sql = "select * from itwill_member where id != 'admin'";
				pstmt = con.prepareStatement(sql);
				// 4. sql 실행
				rs = pstmt.executeQuery();
				// 5. 데이터 처리
				
				while(rs.next()){
					// 회원 1명의 정보 => MemberDTOs저장
					MemberDTO dto = new MemberDTO();
					dto.setAge(rs.getInt("age"));
					dto.setEmail(rs.getString("email"));
					dto.setGender(rs.getString("gender"));
					dto.setId(rs.getString("id"));
					dto.setName(rs.getString("name"));
					dto.setPass(rs.getString("pass"));
					dto.setReg_date(rs.getTimestamp("reg_date"));
					
					// MemberBean의 정보를 => ArrayList 1칸에 저장
					memberList.add(dto);
				}
				System.out.println("회원 목록 저장완료!");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				CloseDB();
			}
			return memberList;
		}
		// getMemberList()

}
