package com.me.board.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.me.board.db.BoardDTO;

public class BoardDAO { // BoardDAO 시작
	
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
	} // 디비연결 끝

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
	} //자원해재 끝
	
	// insertBoard 시작    //쿼리부분 수정필요
	public void insertBoard(BoardDTO dto) {
		try {
			con = getCon();
			// sql 작성
			sql = "insert into [??](id,pass,name,age,gender,email,reg_date) " + "values(?,?,?,?,?,?,?)";
			// pstmt 객체 생성
			pstmt = con.prepareStatement(sql);

			// ??
			//pstmt.setString(1, dto.getId());
			//pstmt.setString(2, dto.getPass());
			//pstmt.setString(3, dto.getName());
			//pstmt.setInt(4, dto.getAge());
			//pstmt.setString(5, dto.getGender());
			//pstmt.setString(6, dto.getEmail());
			//pstmt.setTimestamp(7, dto.getReg_date());

			// sql 실행
			pstmt.executeUpdate();

			System.out.println(" DAO : 글작성 완료! ");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseDB();
		}
	}  // insertBoard 끝
	
	// getBoardCount()
		public int getBoardCount(){
			int cnt = 0;
			
			try {
				// 1,2 디비연결
				con = getCon();
				// 3 sql 작성(select) & pstmt 객체
				sql = "select count(*) from itwill_board";
				pstmt = con.prepareStatement(sql);
				// 4 sql 실행
				rs = pstmt.executeQuery();
				// 5 데이터 처리
				if(rs.next()){
					//데이터 있을때 (글개수)
					cnt = rs.getInt(1);	
					//cnt = rs.getInt("count(*)");
				}
				
				System.out.println(" DAO : 글개수 ("+cnt+")");
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				CloseDB();
			}
			
			return cnt;
		}
		// getBoardCount()
		
		// getBoardList()
		public List getBoardList(){
			List boardList = new ArrayList();
			//List<Object> boardList = new ArrayList<Object>();
			
			try {
				// 1,2 디비연결
				con = getCon();
				// 3 sql 작성 & pstmt 객체 생성
				//sql = "select * from itwill_board";
				pstmt = con.prepareStatement(sql);
				// 4 sql 실행
				rs = pstmt.executeQuery();
				// 5 데이터처리 (글 1개의 정보 -> DTO 1개 -> ArrayList 1칸)
				while(rs.next()){
					//데이터 있을때 마다 글 1개의 정보를 저장하는 객체 생성
					BoardDTO dto = new BoardDTO();
					
					dto.setWhat_name(what_name);
					dto.setWhen_name(when_name);
					dto.setWhere_name(where_name);
					dto.setWrite_time(write_time);
					
					
					// DTO 객체를 ArrayList 한칸에 저장
					boardList.add(dto);				
					
				}//while
				
				System.out.println(" DAO : 글 정보 저장완료! "+boardList.size());
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				CloseDB();
			}
			
			return boardList;
		}
		// getBoardList()
	
} //BoardDAO 끝
