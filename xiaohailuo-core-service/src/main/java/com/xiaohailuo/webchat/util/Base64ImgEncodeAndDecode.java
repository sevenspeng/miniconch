package com.xiaohailuo.webchat.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

public class Base64ImgEncodeAndDecode {

	public static String ImgEncode(File file) {
		InputStream inputStream = null;
		// File file = new File("F:\\project\\test1.jpg");
		String fileString = "";
		try {
			inputStream = new FileInputStream(file);
			byte[] fileByte = new byte[inputStream.available()];
			inputStream.read(fileByte);
			inputStream.close();
			fileString = new BASE64Encoder().encode(fileByte);
			return fileString;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return fileString;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return fileString;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static void ImgDecode(String fileString, File file) {
		// File file = null;
		// File file = new File("/testtest2.jpg");
		// String filePath = "/testtest.jpg";
		BufferedOutputStream stream = null;
		try {
			System.out.println("ImgDecode  step1!!");
			byte[] bs = new BASE64Decoder().decodeBuffer(fileString);
			System.out.println("ImgDecode  step2!!");
			for (int i = 0; i < bs.length; ++i) {
				if (bs[i] < 0) {
					bs[i] += 256;
				}
			}
			System.out.println("ImgDecode  step3!!");
			FileOutputStream fos = new FileOutputStream(file);
			System.out.println("ImgDecode  step4!!");
			stream = new BufferedOutputStream(fos);
			System.out.println("ImgDecode  step5!!");
			stream.write(bs);
			System.out.println("decode success!!");
		} catch (FileNotFoundException e) { // TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("fail ! FileNotFoundException");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("fail ! IOException");
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static byte[] ImgDecode(String fileString) {
		// File file = null;
		// File file = new File("/testtest2.jpg");
		// String filePath = "/testtest.jpg";
		//BufferedOutputStream stream = null;
		try {
			System.out.println("ImgDecode  step1!!");
			byte[] bs = new BASE64Decoder().decodeBuffer(fileString);
			System.out.println("ImgDecode  step2!!");
			for (int i = 0; i < bs.length; ++i) {
				if (bs[i] < 0) {
					bs[i] += 256;
				}
			}
			System.out.println("decode success!!");
			return bs;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("fail ! IOException");
			return null;
		}
	}
	
	
/*	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file1 = new File("F:\\project\\test1.jpg");
		String fileString = ImgEncode(file1);
		File file2 = new File("/testtestssssssssss2.jpg");
		ImgDecode(fileString, file2);
		System.out.println("file2 path = " + file2.getPath());
	}*/

}
