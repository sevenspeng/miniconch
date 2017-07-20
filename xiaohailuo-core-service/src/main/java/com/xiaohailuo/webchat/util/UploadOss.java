package com.xiaohailuo.webchat.util;

import java.io.ByteArrayInputStream;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CompleteMultipartUploadResult;
import com.aliyun.oss.model.UploadFileRequest;
import com.aliyun.oss.model.UploadFileResult;

/**
 * 断点续传上传用法示例
 *
 */
public class UploadOss {

	private static String endpoint = "http://oss-cn-shenzhen.aliyuncs.com";
	private static String accessKeyId = "LTAIGe4ekN0x7tf7";
	private static String accessKeySecret = "n2M3HO9wZtP3OQcRTT9jfkS3U3nVVh";

	public static int UploadFile(String bucketName, String key, String uploadFile) {
		int ret = -1;
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

		try {
			UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName, key);
			// 待上传的本地文件
			System.out.println("UploadFile:   step1 ");
			uploadFileRequest.setUploadFile(uploadFile);
			System.out.println("UploadFile:   step2 ");
			// 设置并发下载数，默认1
			uploadFileRequest.setTaskNum(5);
			// 设置分片大小，默�?100KB
			uploadFileRequest.setPartSize(1024 * 1024 * 1);
			// �?启断点续传，默认关闭
			uploadFileRequest.setEnableCheckpoint(true);
			System.out.println("UploadFile:   step3 ");
			UploadFileResult uploadResult = ossClient.uploadFile(uploadFileRequest);
			System.out.println("UploadFile:   step4 ");
			CompleteMultipartUploadResult multipartUploadResult = uploadResult.getMultipartUploadResult();
			System.out.println("UploadFile:   step5 ");
			System.out.println(multipartUploadResult.getETag());
			ret = 0;
			return ret;

		} catch (OSSException oe) {
			System.out.println("Caught an OSSException, which means your request made it to OSS, "
					+ "but was rejected with an error response for some reason.");
			System.out.println("Error Message: " + oe.getErrorCode());
			System.out.println("Error Code:       " + oe.getErrorCode());
			System.out.println("Request ID:      " + oe.getRequestId());
			System.out.println("Host ID:           " + oe.getHostId());
			return ret;
		} catch (ClientException ce) {
			System.out.println("Caught an ClientException, which means the client encountered "
					+ "a serious internal problem while trying to communicate with OSS, "
					+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ce.getMessage());
			return ret;
		} catch (Throwable e) {
			e.printStackTrace();
			return ret;
		} finally {
			ossClient.shutdown();
		}
	}
	
	public static int UploadByte(String bucketName, String key, byte[] uploadByte){
		int ret = -1;
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId,accessKeySecret);
		try {
			//ossClient = new OSSClient(endpoint, accessKeyId,accessKeySecret);
			ossClient.putObject(bucketName, key, new ByteArrayInputStream(uploadByte));
			ret = 0;
		} catch (OSSException oe) {
			// TODO Auto-generated catch block
			oe.printStackTrace();
			System.out.println("Caught an OSSException, which means your request made it to OSS, "
					+ "but was rejected with an error response for some reason.");
			System.out.println("Error Message: " + oe.getErrorCode());
			System.out.println("Error Code:       " + oe.getErrorCode());
			System.out.println("Request ID:      " + oe.getRequestId());
			System.out.println("Host ID:           " + oe.getHostId());
		} catch (ClientException ce) {
			// TODO Auto-generated catch block
			ce.printStackTrace();
			System.out.println("Caught an ClientException, which means the client encountered "
					+ "a serious internal problem while trying to communicate with OSS, "
					+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ce.getMessage());
		}finally {
			ossClient.shutdown();
		}
		return ret;
	}

	/*
	 * public static void main(String[] args) throws IOException { String
	 * bucketName = "miniconch"; String key = "安澜桥.wav"; String uploadFile =
	 * "安澜桥.wav"; int ret = UploadFile(bucketName,key,uploadFile);
	 * System.out.println(ret); }
	 */
}
