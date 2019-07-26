package com.myproject.homepage.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBCUtil {
	public static Connection getConnection() {
		Connection conn = null;
		
		try {
			/*
			 * 1. 드라이버 로딩
			 * 드라이버 인터페이스를 구현한 클래스 로딩
			 * mysql, oralce 등 각 벤더사마다 클래스 이름이 다르다.
			 * 연동했던 jar 파일을 보면 com.mysql.jdbc 패키지에 Driver 클래스가 있다.
			 * */
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			/*
			 * 2. 연결하기
			 * 드라이버 매니저에게 Connection 객체를 달라고 요청한다.
			 * */
			String url = "jdbc:mysql://localhost:3306/board?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
			
			// @param getConnection(url, userName, password);
			// @return Connection
			conn = DriverManager.getConnection(url, "jsh", "1234");
			
			System.out.println("연결 성공");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void close(PreparedStatement stmt, Connection conn) {
		if (stmt != null) {
			try {
				if(!stmt.isClosed()) {
					stmt.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				stmt = null;
			}
		}
		
		if (conn != null) {
			try {
				if (!conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				conn = null;
			}
		}
	}
	
	public static void close(ResultSet rs, PreparedStatement stmt, Connection conn) {
		if (rs != null) {
			try {
				if (!rs.isClosed()) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				rs = null;
			}
		}
		
		if (stmt != null) {
			try {
				if(!stmt.isClosed()) {
					stmt.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				stmt = null;
			}
		}
		
		if (conn != null) {
			try {
				if (!conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				conn = null;
			}
		}
	}
}
