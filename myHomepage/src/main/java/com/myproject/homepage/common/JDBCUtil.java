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
			 * 1. ����̹� �ε�
			 * ����̹� �������̽��� ������ Ŭ���� �ε�
			 * mysql, oralce �� �� �����縶�� Ŭ���� �̸��� �ٸ���.
			 * �����ߴ� jar ������ ���� com.mysql.jdbc ��Ű���� Driver Ŭ������ �ִ�.
			 * */
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			/*
			 * 2. �����ϱ�
			 * ����̹� �Ŵ������� Connection ��ü�� �޶�� ��û�Ѵ�.
			 * */
			String url = "jdbc:mysql://localhost:3306/board?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
			
			// @param getConnection(url, userName, password);
			// @return Connection
			conn = DriverManager.getConnection(url, "jsh", "1234");
			
			System.out.println("���� ����");
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
