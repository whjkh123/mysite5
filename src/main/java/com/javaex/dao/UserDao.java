package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

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

	public int dbIsrt(UserVo uVo) {

		dbCnt();

		int count = 0;

		try {

			// INSERT INTO users VALUES(SEQ_NO.nextval, 'whjkh123', '1234', '조경환', '남성');
			String query = "INSERT INTO users VALUES(SEQ_NO.nextval, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, uVo.getId());
			pstmt.setString(2, uVo.getPassword());
			pstmt.setString(3, uVo.getName());
			pstmt.setString(4, uVo.getGender());

			count = pstmt.executeUpdate();

			System.out.println("[DAO]: " + count + "건이 저장되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return count;

	}

	public UserVo getUser(String id, String psw) {

		UserVo uVo = null;

		dbCnt();

		try {
			String query = "";
			query += " select no, ";
			query += " 		  name ";
			query += " from   users ";
			query += " where  id = ? ";
			query += " 		  and password = ? ";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, psw);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");

				uVo = new UserVo(no, name);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return uVo;

	}

	public UserVo getOne(int no) {// >> public UserVo getUser(int no) → method overriding

		UserVo uVo = null;

		dbCnt();

		try {
			String query = "";
			query += " select no, ";
			query += " 		  id, ";
			query += " 		  password, ";
			query += " 		  name, ";
			query += " 		  gender ";
			query += " from   users ";
			query += " where  no = ? ";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int user_no = rs.getInt("no");
				String id = rs.getString("id");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String gender = rs.getString("gender");

				uVo = new UserVo(user_no, id, password, name, gender);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return uVo;

	}

	public int dbUpd(UserVo uVo) {

		dbCnt();

		int count = 0;

		try {

			String query = "";
			query += " update users ";
			query += " SET password = ?, ";
			query += "     name = ?, ";
			query += "     gender = ? ";
			query += " WHERE no = ? ";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, uVo.getPassword());
			pstmt.setString(2, uVo.getName());
			pstmt.setString(3, uVo.getGender());
			pstmt.setInt(4, uVo.getNo());

			count = pstmt.executeUpdate();

			System.out.println("[DAO]: " + count + "건이 수정되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return count;

	}

}
