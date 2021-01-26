package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestBookVo;

public class GuestBookDao {

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

	public int dbIsrt(GuestBookVo gVo) {

		dbCnt();

		int count = 0;

		try {
			// INSERT INTO guestbook VALUES(seq_no.nextval, 'name', 'password', 'content' ,
			// sysdate);
			String query = "INSERT INTO guestbook VALUES(seq_no.nextval, ?, ?, ? , sysdate)";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, gVo.getName());
			pstmt.setString(2, gVo.getPassword());
			pstmt.setString(3, gVo.getContent());

			count = pstmt.executeUpdate();

			System.out.println("[DAO]: " + count + "건이 저장되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return count;

	}

	public List<GuestBookVo> dbList() {

		List<GuestBookVo> gList = new ArrayList<GuestBookVo>();

		dbCnt();

		try {

			String query = "";
			query += " select no, ";
			query += " 		  name, ";
			query += "        password, ";
			query += "        content, ";
			query += "		  TO_CHAR(reg_date, 'yyyy-mm-dd hh:mi:ss') ";
			query += " from   guestbook ";
			query += " order by no asc ";
			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				int no = rs.getInt(1);
				String name = rs.getString(2);
				String password = rs.getString(3);
				String content = rs.getString(4);
				String reg_date = rs.getString(5);

				GuestBookVo gVo = new GuestBookVo(no, name, password, content, reg_date);

				gList.add(gVo);

			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return gList;

	}

	public GuestBookVo getOne(int num) {

		GuestBookVo gVo = null;

		dbCnt();

		try {
			String query = "";
			query += " select no, ";
			query += " 		  name, ";
			query += "        password, ";
			query += "        content, ";
			query += "		  TO_CHAR(reg_date, 'yyyy-mm-dd hh:mi:ss') ";
			query += " from   guestbook ";
			query += " where  no = ? ";
			query += " order by no asc ";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, num);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				int no = rs.getInt(1);
				String name = rs.getString(2);
				String password = rs.getString(3);
				String content = rs.getString(4);
				String reg_date = rs.getString(5);

				gVo = new GuestBookVo(no, name, password, content, reg_date);

			}
		} catch (SQLException e) {
			// TODO: handle exception
		}

		close();

		return gVo;

	}

	public int dbDle(GuestBookVo gVo) {

		dbCnt();

		int count = 0;

		try {

			String query = " DELETE FROM guestbook WHERE no = ? and password = ? ";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, gVo.getNo());
			pstmt.setString(2, gVo.getPassword());

			count = pstmt.executeUpdate();

			System.out.println("[DAO]: " + count + "건이 삭제되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return count;

	}

}
