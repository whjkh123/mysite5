package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	public void dbCnt() {

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

	}

	public void close() {

		try {

			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

	}

	public List<BoardVo> boardList() {

		List<BoardVo> bList = new ArrayList<BoardVo>();

		dbCnt();

		try {

			String query = "";
			query += " SELECT	b.no, ";
			query += " 			b.title, ";
			query += " 			u.name, ";
			query += " 			b.content, ";
			query += "			b.hit, ";
			query += " 			TO_CHAR(b.reg_date, 'yyyy-mm-dd hh:mi:ss'), ";
			query += "			b.user_no ";
			query += " From		board b, users u ";
			query += " WHERE	b.user_no = u.no ";

			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				int no = rs.getInt(1);
				String title = rs.getString(2);
				String name = rs.getString(3);
				String content = rs.getString(4);
				int hit = rs.getInt(5);
				String reg_date = rs.getString(6);
				int user_no = rs.getInt(7);

				BoardVo bVo = new BoardVo(no, title, name, content, hit, reg_date, user_no);
				bList.add(bVo);

			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return bList;

	}

	public int bIsrt(BoardVo bVo) {

		int count = 0;

		dbCnt();

		try {

			String query = "";
			query += " INSERT INTO board VALUES(seq_board_no.NEXTVAL, ?, ?, default, sysdate, ?) ";

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, bVo.getTitle());
			pstmt.setString(2, bVo.getContent());
			pstmt.setInt(3, bVo.getUser_no());

			count = pstmt.executeUpdate();

			System.out.println("[DAO]: " + count + "건이 저장되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return count;

	}

	public BoardVo readBoard(int no) {

		BoardVo bVo = null;

		dbCnt();

		try {

			String query = "";
			query += " SELECT u.name, ";
			query += "		  b.hit, ";
			query += "		  b.reg_date, ";
			query += "		  b.title, ";
			query += "		  b.content, ";
			query += "		  b.user_no, ";// 글 작성자만 수정가능하게..
			query += "		  b.no ";// 수정 할 게시글 특정
			query += " FROM	  board b, users u ";
			query += " WHERE  b.user_no = u.no ";
			query += "		  and b.no = ? ";

			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				String name = rs.getString(1);
				int hit = rs.getInt(2);
				String reg_date = rs.getString(3);
				String title = rs.getString(4);
				String content = rs.getString(5);
				int user_no = rs.getInt(6);
				int bNo = rs.getInt(7);

				bVo = new BoardVo(name, hit, reg_date, title, content, user_no, bNo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return bVo;

	}

	public int bUpd(BoardVo bVo) {

		int count = 0;

		dbCnt();

		try {

			String query = "";
			query += " UPDATE board SET title = ?, content = ? WHERE no = ? ";

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, bVo.getTitle());
			pstmt.setString(2, bVo.getContent());
			pstmt.setInt(3, bVo.getNo());

			count = pstmt.executeUpdate();

			System.out.println("[DAO]: " + count + "건이 수정되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return count;

	}

	public int bDle(int no) {

		int count = 0;

		dbCnt();

		try {

			String query = "";
			query += " DELETE FROM board WHERE no = ? ";

			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, no);

			count = pstmt.executeUpdate();

			System.out.println("[DAO]: " + count + "건이 삭제되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return count;

	}

	public int bHit(int no) {

		int count = 0;

		dbCnt();

		try {

			String query = "";
			query += " UPDATE board SET hit = hit + 1 WHERE no = ? ";

			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, no);

			count = pstmt.executeUpdate();

			System.out.println("[DAO]: " + count + " + 1");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return count;

	}

	public List<BoardVo> boardSearch(String keyword) {

		List<BoardVo> bList = new ArrayList<BoardVo>();

		dbCnt();

		try {

			String query = "";
			query += " SELECT	b.no, ";
			query += " 			b.title, ";
			query += " 			u.name, ";
			query += " 			b.content, ";
			query += "			b.hit, ";
			query += " 			TO_CHAR(b.reg_date, 'yyyy-mm-dd hh:mi:ss'), ";
			query += "			b.user_no ";
			query += " From		board b, users u ";
			query += " WHERE	b.user_no = u.no ";
			query += "			and b.title like ? ";

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, "%" + keyword + "%");

			rs = pstmt.executeQuery();

			while (rs.next()) {

				int no = rs.getInt(1);
				String title = rs.getString(2);
				String name = rs.getString(3);
				String content = rs.getString(4);
				int hit = rs.getInt(5);
				String reg_date = rs.getString(6);
				int user_no = rs.getInt(7);

				BoardVo bVo = new BoardVo(no, title, name, content, hit, reg_date, user_no);
				bList.add(bVo);

			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return bList;

	}

}