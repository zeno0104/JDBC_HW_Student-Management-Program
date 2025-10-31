package com.student.jdbc.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class LoadXMLFile {
	public static void main(String[] args) {
		FileInputStream fis = null;
		Connection conn = null;
		
		try {
			Properties prop = new Properties();
			
			fis = new FileInputStream("driver.xml");
			
			prop.loadFromXML(fis);
			
			String driver = prop.getProperty("driver");
			String url = prop.getProperty("url");
			String userId = prop.getProperty("userId");
			String userPw = prop.getProperty("userPw");
			
			Class.forName(driver);
			
			conn = DriverManager.getConnection(url, userId, userPw);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn != null)
					conn.close();
				if(fis != null)
					fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
