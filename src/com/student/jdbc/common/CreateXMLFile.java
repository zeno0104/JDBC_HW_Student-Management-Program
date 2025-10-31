package com.student.jdbc.common;

import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Scanner;

public class CreateXMLFile {
	public static void main(String[] args) {
		Scanner sc = null;
		FileOutputStream fos = null;

		try {
			
			System.out.print("생성할 파일 이름 : ");
			sc = new Scanner(System.in);
			
			String fileName = sc.next();
			
			Properties prop = new Properties();
			
			fos = new FileOutputStream(fileName + ".xml");
			
			prop.storeToXML(fos, fileName + ".xml 파일");
			
			System.out.println(fileName + ".xml 파일 생성 완료");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (sc != null)
					sc.close();
				if (fos != null)
					fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
