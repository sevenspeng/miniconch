package com.xiaohailuo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class ChangeAudioFormat {

	/*
	public static void changeToMp3(File source, File target) {
		AudioAttributes audio = new AudioAttributes();
		Encoder encoder = new Encoder();

		audio.setCodec("libmp3lame");
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat("mp3");
		attrs.setAudioAttributes(audio);

		try {
			encoder.encode(source, target, attrs);

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InputFormatException e) {
			e.printStackTrace();
		} catch (EncoderException e) {
			e.printStackTrace();
		}
	}*/

	public static void amr2mp3(String amrFileName, String mp3FileName) throws IOException, InterruptedException {
		Runtime runtime = Runtime.getRuntime();
		Process process = runtime
				.exec("/usr/local/ffmpeg/ffmpeg" + " -i " + amrFileName + " -ar 8000 -ac 1 -y -ab 12.4k " + mp3FileName);
		process.waitFor();
		InputStream in = process.getErrorStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		try {
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			
			if (process.exitValue() != 0) {
				// 如果转换失败，这里需要删除这个文件（因为有时转换失败后的文件大小为0）
				new File(mp3FileName).delete();
				throw new RuntimeException("转换失败！");
			}
		} finally {
			// 为了避免这里抛出的异常会覆盖上面抛出的异常，这里需要用捕获异常。
			try {
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}