package com.xiaohailuo.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xiaohailuo.bean.CommentInfo;
import com.xiaohailuo.bean.Coordinate;
import com.xiaohailuo.bean.CoordinateScope;
import com.xiaohailuo.bean.Like;
import com.xiaohailuo.domain.Comment;
import com.xiaohailuo.domain.CommentMapper;
import com.xiaohailuo.domain.Footprint;
import com.xiaohailuo.domain.FootprintMapper;
import com.xiaohailuo.domain.JointQueryMapper;
import com.xiaohailuo.domain.LikeMapper;
import com.xiaohailuo.domain.Record;
import com.xiaohailuo.domain.RecordMapper;
import com.xiaohailuo.domain.Sign;
import com.xiaohailuo.domain.SignMapper;
import com.xiaohailuo.domain.User;
import com.xiaohailuo.domain.UserMapper;
import com.xiaohailuo.root.Root;
import com.xiaohailuo.util.LocationUtils;
import com.xiaohailuo.util.MapUtils;
import com.xiaohailuo.util.SystemConst;
import com.xiaohailuo.util.UUIDGenerator;
import com.xiaohailuo.webchat.util.Base64ImgEncodeAndDecode;
import com.xiaohailuo.webchat.util.UploadOss;

import Decoder.BASE64Decoder;

//import scala.annotation.implicitNotFound;

@RestController
@RequestMapping(value = "/core")
// @Transactional
public class CoreController extends BaseController {

	Logger log = Logger.getLogger(CoreController.class);

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private RecordMapper recordMapper;

	@Autowired
	private CommentMapper commentMapper;

	@Autowired
	private LikeMapper likeMapper;

	@Autowired
	private SignMapper signMapper;

	@Autowired
	private JointQueryMapper jointQueryMapper;

	@Autowired
	private FootprintMapper footprintMapper;

	// status
	// Map<String, Integer> statusMachine = new
	// ConcurrentHashMap<String,Integer>();
	private static final ConcurrentMap<String, Integer> statusMachine = new ConcurrentHashMap<String, Integer>();

	/**
	 * 根据经纬度获取录音
	 * 
	 * @param coordinates
	 * @return
	 */
	@RequestMapping(value = "/query/record/list", method = RequestMethod.POST)
	public Root/* Map<String, ObjectList<Record>> */ getRecordListByLatAndLng(@RequestBody Coordinate coordinates) {
		// System.out.println(String.format("Coordinates %s",
		// coordinates.toString()));
		System.out.println(coordinates.toString());

		int resultCode = 200;
		String resultMessage = "接口调用正常返回";

		List<Record> arrayList = recordMapper.findAllRecordByLnglat(new BigDecimal(coordinates.getLat()),
				new BigDecimal(coordinates.getLng()));
		/*
		 * Map<String,Object> map= new HashMap<String,Object>();
		 * map.put("resultCode", resultCode); map.put("resultMessage",
		 * resultMessage); map.put("value", arrayList);
		 */

		if (arrayList == null || arrayList.isEmpty()) {
			resultCode = 210;
			resultMessage = "未查询到符合条件的数据";
		}

		for (int i = 0; i < arrayList.size(); i++) {
			Record record = arrayList.get(i);
			System.out.println("getRecordListByLatAndLng 遍历 第" + i + "条  原始record ：" + record);

			System.out.println("getRecordListByLatAndLng 遍历 第" + i + "条  recordid ：" + record.getId());
			List<CommentInfo> arrayCommentsList = jointQueryMapper.findCommentsByRid(record.getId());

			record.setComments(arrayCommentsList);
			record.setReplyCount(arrayCommentsList.size());

			System.out.println("getRecordListByLatAndLng 遍历 第" + i + "条  comments ：" + arrayCommentsList.size());
			int likeCount = likeMapper.findLikeCountByRid(record.getId());

			System.out.println("getRecordListByLatAndLng  遍历 第" + i + "条  likeCount ：" + likeCount);
			record.setLikeCount(likeCount);

			System.out.println("getRecordListByLatAndLng  遍历 第" + i + "条 最终record ：" + record);
			arrayList.set(i, record);
		}

		System.out.println("classpath:" + CoreController.class.getClassLoader().getResource(""));
		System.out.println("classpath:" + CoreController.class.getResource(""));
		System.out.println("classpath:" + CoreController.class.getResource("/"));
		System.out.println("classpath:" + CoreController.class.getResource("../"));
		// String pathTemp =
		java.net.URL urlTemp = CoreController.class.getResource("/");
		System.out.println(urlTemp.getPath());
		System.out.println(urlTemp.getPath().substring(1, urlTemp.getPath().length()));
		System.out.println(urlTemp.getPath().substring(1, urlTemp.getPath().lastIndexOf("/") - 14));
		System.out.println(urlTemp.getPath().substring(1, urlTemp.getPath().length() - 15));

		System.out.println("user.dir:" + System.getProperty("user.dir"));

		Root rootObject = new Root(resultCode, resultMessage, arrayList);

		return /* map */rootObject;

	}

	/**
	 * 根据录音ID获取录音详情
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/query/record/detail/{recordId}", method = RequestMethod.GET)
	// public Map<String,Object> getDetailByRecordId(@PathVariable String
	// recordId){
	public Map<String, Object> getDetailByRecordId(@PathVariable("recordId") String id) {
		// id
		System.out.println(String.format("%s: %s", "id", id));
		int resultCode = 200;
		String resultMessage = "接口调用正常返回";

		Record record = recordMapper.findRecordDetailById(id);

		System.out.println("getDetailByRecordId 原始record: " + record);
		List<CommentInfo> arrayList = jointQueryMapper.findCommentsByRid(id);

		record.setComments(arrayList);
		record.setReplyCount(arrayList.size());

		System.out.println("getDetailByRecordId comments: " + arrayList.size());
		int likeCount = likeMapper.findLikeCountByRid(id);

		System.out.println("getDetailByRecordId likeCount: " + likeCount);
		record.setLikeCount(likeCount);

		System.out.println("getDetailByRecordId 最终record: " + record);

		// if (arrayList == null || arrayList.size() == 0) {
		if (record == null || "".equals(record)) {
			resultCode = 210;
			resultMessage = "未查询到符合条件的数据";
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultCode", resultCode);
		map.put("resultMessage", resultMessage);
		map.put("value", record);

		return map;
	}

	/**
	 * 提交评论
	 * 
	 * @param comment
	 * @return
	 */
	@RequestMapping(value = "/add/record/comment", method = RequestMethod.POST)
	public Map<String, Object> submitComment(@RequestBody Comment comment) {
		System.out.println(comment.toString());
		int resultCode = 200;
		String resultMessage = "接口调用正常返回";

		int result = commentMapper.insert(comment.getRid(), comment.getUid(), comment.getComment());

		if (result == 1) {
			resultCode = 200;
		} else {
			resultCode = 510;
			resultMessage = "提交失败";
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultCode", resultCode);
		map.put("resultMessage", resultMessage);

		return map;
	}

	/**
	 * 点赞
	 * 
	 * @param like
	 * @return
	 */
	@RequestMapping(value = "/add/record/like", method = RequestMethod.POST)
	public Map<String, Object> submitLike(@RequestBody Like like) {
		System.out.println(like.toString());
		int resultCode = 200;
		String resultMessage = "接口调用正常返回";

		int result = likeMapper.insert(like.getRecordId(), like.getuId());

		if (result == 1) {
			resultCode = 200;
		} else {
			resultCode = 510;
			resultMessage = "提交失败";
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultCode", resultCode);
		map.put("resultMessage", resultMessage);

		return map;
	}

	/**
	 * 根据经纬度获取官方录音
	 * 
	 * @param coordinates
	 * @return
	 */
	@RequestMapping(value = "/query/record/official", method = RequestMethod.POST)
	public Map<String, Object> getOfficialRecordByLatLng(@RequestBody Coordinate coordinates) {
		// System.out.println(String.format("Coordinates %s",
		// coordinates.toString()));
		System.out.println(coordinates.toString());

		int resultCode = 200;
		String resultMessage = "接口调用正常返回";

		String recordFile = recordMapper.findOfficialRecordByLnglat(new BigDecimal(coordinates.getLat()),
				new BigDecimal(coordinates.getLng()));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultCode", resultCode);
		map.put("resultMessage", resultMessage);
		List arrayList = new ArrayList();
		Map mapTemp = new HashMap<String, String>();
		mapTemp.put("recordFile", recordFile);
		arrayList.add(mapTemp);
		map.put("value", arrayList);
		// System.out.println("recordFile==" + recordFile);

		// Root rootObject = new Root(resultCode, resultMessage, recordFile);

		return map/* rootObject */;

	}

	/**
	 * 根据经纬度范围获取录音点信息 (待完成)
	 * 
	 * @param coordinates
	 * @return
	 */
	@RequestMapping(value = "/query/record/list/2", method = RequestMethod.POST)
	public Root/* Map<String, ObjectList<Record>> */ getRecordPointsInfoByLatLngScope(
			@RequestBody CoordinateScope coordinateScope) {
		// System.out.println(String.format("Coordinates %s",
		// coordinates.toString()));
		System.out.println(coordinateScope.toString());

		/*
		 * new BigDecimal(coordinateScope.getNortheast().getLng()); new
		 * BigDecimal(coordinateScope.getNortheast().getLat()); new
		 * BigDecimal(coordinateScope.getSouthwest().getLng()); new
		 * BigDecimal(coordinateScope.getSouthwest().getLat());
		 */

		int resultCode = 200;
		String resultMessage = "接口调用正常返回";

		List<Map<String, Object>> arrayList = recordMapper.findAllRecordByCoordinateScope(
				new BigDecimal(coordinateScope.getNortheast().getLng()),
				new BigDecimal(coordinateScope.getNortheast().getLat()),
				new BigDecimal(coordinateScope.getSouthwest().getLng()),
				new BigDecimal(coordinateScope.getSouthwest().getLat()));
		/*
		 * Map<String,Object> map= new HashMap<String,Object>();
		 * map.put("resultCode", resultCode); map.put("resultMessage",
		 * resultMessage); map.put("value", arrayList);
		 */

		Root rootObject = new Root(resultCode, resultMessage, arrayList);

		return /* map */rootObject;

	}

	/**
	 * 签到
	 */
	@RequestMapping(value = "/add/record/sign", method = RequestMethod.POST)
	public Map<String, Object> submitSign(@RequestBody Sign sign) {
		System.out.println(sign.toString());
		String resultMessage = "接口调用正常返回";
		Map<String, Object> map = new HashMap<String, Object>();
		int resultCode = 200;

		Sign signInstance = signMapper.findSignByRid(sign.getRid(), sign.getUm());
		if (signInstance != null && signInstance.getUm().equals(sign.getUm())) {
			resultMessage = "不能重复签到！";
			User user = userMapper.findByName(sign.getUm());
			String uid = user.getId();
			List arrayList = new ArrayList();
			Map subMap = new HashMap<String, String>();
			subMap.put("uid", uid);
			System.out.println("subMap: " + subMap);
			arrayList.add(subMap);
			map.put("resultCode", 210);
			map.put("resultMessage", resultMessage);
			// map.put("value", arrayList);
			map.put("value", subMap);
			return map;
		}

		int result = signMapper.insert(sign.getRid(), sign.getUm());
		System.out.println("insert执行结果：" + result);
		String uid = null;
		if (result == 1) {
			// 写入UM找好到user表
			User user = userMapper.findByName(sign.getUm());
			if (user != null && user.getId() != null && user.getId().length() > 0) {
				uid = user.getId();
				System.out.println("uid==" + uid);
			} else {
				uid = UUID.randomUUID().toString();
				int result1 = userMapper.insert(uid, sign.getUm(), sign.getUm(), "头像", sign.getPassword());
				System.out.println("uid==" + uid);
				System.out.println("result1==" + result1);
			}
		}

		List arrayList = new ArrayList();
		Map subMap = new HashMap<String, String>();
		subMap.put("uid", uid);
		System.out.println("subMap: " + subMap);
		arrayList.add(subMap);

		map.put("resultCode", resultCode);
		map.put("resultMessage", resultMessage);
		// map.put("value", new ArrayList().add(new HashMap().put("uid", uid)));
		// map.put("value", arrayList);
		map.put("value", subMap);

		return map;
	}

	/**
	 * 用户注册 request: { "mobile": "13568836650", "password": "123456" }
	 *
	 */
	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	public Map<String, Object> registerUser(@RequestBody Map<String, String> requestMap) {

		System.out.println(requestMap.toString());
		String resultMessage = "接口调用正常返回";
		Map<String, Object> map = new HashMap<String, Object>();
		int resultCode = 200;

		// 写用户到mobile表
		User user = userMapper.findByName(requestMap.get("mobile"));
		String uid = null;
		if (user != null && user.getId() != null && user.getId().length() > 0) {
			uid = user.getId();
			resultMessage = "注册手机已被占用";
			resultCode = 220;
			System.out.println("uid==" + uid);
		} else {
			uid = UUID.randomUUID().toString();
			// int result = userMapper.insert(uid, requestMap.get("mobile"),
			// requestMap.get("mobile"), "头像");
			// user表： mobile-name 手机号码
			int result = userMapper.insert(uid, requestMap.get("mobile"), requestMap.get("nickname"),
					requestMap.get("profilephoto"), requestMap.get("password"));

			System.out.println("uid==" + uid);
			System.out.println("result==" + result);
			// 注册成功后置为已登陆
			// statusMachine.put(requestMap.get("mobile"), 1);
		}

		Map<String, String> subMap = new HashMap<String, String>();
		subMap.put("uid", uid);
		System.out.println("subMap: " + subMap);
		map.put("resultCode", resultCode);
		map.put("resultMessage", resultMessage);
		map.put("value", subMap);

		return map;
	}

	/**
	 * 用户登录 request: { "mobile": "13568836650", "password": "123456" }
	 * 
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Map<String, Object> signIn(@RequestBody Map<String, String> requestMap) throws Exception {
		System.out.println(requestMap.toString());
		String resultMessage = "接口调用正常返回";
		Map<String, Object> map = new HashMap<String, Object>();
		int resultCode = 200;

		User user = null;
		String uid = null;

		try {
			// user = userMapper.findByName(requestMap.get("mobile"));
			user = userMapper.findByNameAndPassword(requestMap.get("mobile"), requestMap.get("password"));
			if (null != user) {
				uid = user.getId();
				// 判断需要更新的信息

			} else {
				// 未查询到用户信息 不做用户信息更新
				// statusMachine.put(requestMap.get("mobile"), 1);
				resultMessage = "登录成功";
				System.out.println(resultMessage);
			}

			Map<String, String> subMap = new HashMap<String, String>();
			subMap.put("uid", uid);// id 主键信息
			subMap.put("name", user.getName());// 姓名
			subMap.put("nickname", user.getNickname());// 昵称
			subMap.put("profilephoto", user.getProfilephoto());// 头像
			subMap.put("subscribetime", user.getSubscribetime());// 注册时间
			// subMap.put("password", user.getPassword());//密码
			System.out.println("subMap: " + subMap);
			map.put("value", subMap);
		} catch (NullPointerException e) {
			resultMessage = "手机未注册，请注册后再登录:查询数据库无记录，为null.";
			resultCode = 550;
			System.out.println("Exception: " + e.getMessage());
		} catch (Exception e) {
			resultMessage = "手机未注册，请注册后再登录:其他异常.";
			resultCode = 500;
			System.out.println("Exception: " + e.getMessage());
		} finally {
			map.put("resultCode", resultCode);
			map.put("resultMessage", resultMessage);
			return map;
		}

	}

	/**
	 * 更新用户信息 request: { "mobile": "13568836650", "img": "图片信息","image_url":
	 * "头像url", "country": "国籍","province": "省份","city": "城市","nickname": "昵称"}
	 * updateFlag 含义 1 更新头像 image_url 2 更新昵称 nickname 3 更新个性签名 personnotes 4
	 * 更新籍贯 country、province、city 5 更新1-4项所有信息
	 * 
	 */
	@RequestMapping(value = "/update/userinfro", method = RequestMethod.POST)
	public Map<String, Object> upUserInfro(@RequestBody Map<String, Object> requestMap) throws Exception {
		System.out.println(requestMap.toString());
		String resultMessage = "接口调用正常返回";
		Map<String, Object> map = new HashMap<String, Object>();
		int resultCode = 200;

		// User user = userMapper.findByName(requestMap.get("mobile"));
		User user = null;
		String uid = null;

		try {
			user = userMapper.findByNameAndPassword((String) requestMap.get("mobile"),
					(String) requestMap.get("password"));
			if (null != user) {
				uid = user.getId();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
				user.setLastupdatetime(df.format(new Date()).toString());
				System.out.println(df.format(new Date()).toString());// new
																		// Date()为获取当前系统时间

				// user.setImage_url(null!=requestMap.get("image_url")?(String)requestMap.get("image_url"):"");
				user.setNickname(null != requestMap.get("nickname") ? (String) requestMap.get("nickname") : "");
				user.setNickname(null != requestMap.get("personnotes") ? (String) requestMap.get("personnotes") : "");
				user.setCountry(null != requestMap.get("country") ? (String) requestMap.get("country") : "");
				user.setProvince(null != requestMap.get("province") ? (String) requestMap.get("province") : "");
				user.setCity(null != requestMap.get("city") ? (String) requestMap.get("city") : "");

				int result = userMapper.updateAllInfro(user);
				if (result > 0) {
					System.out.println("更新昵称、区域等信息成功！！ result = " + result);
					resultMessage = "更新昵称、区域等信息成功！！";
					resultCode = 200;
				} else {
					System.out.println("更新昵称、区域等信息失败！！ result = " + result);
					resultMessage = "更新昵称、区域等信息失败！！";
					resultCode = 210;
				}

				// 判断图片信息是佛有值
				if (null != requestMap.get("img")) {
					java.net.URL urlImg = CoreController.class.getResource("../");
					List imgList = (ArrayList) requestMap.get("img");
					System.out.println("imgList = " + imgList);
					String imgData = (String) ((Map) imgList.get(0)).get("data");
					System.out.println("imgData = " + imgData);
					String imgSuffix = (String) ((Map) imgList.get(0)).get("suffix");
					System.out.println("imgSuffix = " + imgSuffix);

					byte[] bs = Base64ImgEncodeAndDecode.ImgDecode(imgData);
					System.out.println("bs = " + bs);

					// 上传图片
					int ret = UploadOss.UploadByte("image", (String) requestMap.get("recordId") + imgSuffix, bs);
					// int ret = UploadOss.UploadFile("miniconch",
					// file.getName(), file.getPath());

					// 更新图片url信息
					// if()

					if (ret == 0) {
						System.out.println("图片文件上传image目录成功！！ ret = " + ret);
						resultMessage += "更新图片数据成功";
						resultCode = 200;
					} else {
						System.out.println("图片文件上传OSS失败！！ ret = " + ret);
						resultMessage += "图片上传数据失败！";
						resultCode = 220;
					}

					int resultimg = userMapper.updateImageUrl(user);
				}

			}
			/*
			 * Map<String, String> subMap = new HashMap<String, String>();
			 * subMap.put("uid", uid);// id 主键信息 subMap.put("name",
			 * user.getName());// 姓名 subMap.put("nickname",
			 * user.getNickname());// 昵称 subMap.put("profilephoto",
			 * user.getProfilephoto());// 头像 subMap.put("subscribetime",
			 * user.getSubscribetime());// 注册时间 subMap.put("lastupdatetime",
			 * user.getLastupdatetime());// 最后一次更新时间 // subMap.put("password",
			 * user.getPassword());//密码 System.out.println("subMap: " + subMap);
			 * map.put("value", subMap);
			 */
		} catch (NullPointerException e) {
			resultMessage = "数据更新失败:查询数据库无记录，为null.";
			resultCode = 550;
			System.out.println("Exception: " + e.getMessage());
		} catch (Exception e) {
			resultMessage = "数据更新失败:其他异常.";
			resultCode = 500;
			System.out.println("Exception: " + e.getMessage());
		} finally {
			map.put("resultCode", resultCode);
			map.put("resultMessage", resultMessage);
			return map;
		}

	}

	/**
	 * 录音点创建 request { "lat": "30.66667", "lng": "104.06667",
	 * "description":"这是一个好地方", "recordId": "微信返回的录音id", "img": [ { "data":
	 * "base64字符串","suffix": "图片后缀"}, { "data": "base64字符串", "suffix": "图片后缀"} ]
	 * }
	 */
	@RequestMapping(value = "/add/record/position", method = RequestMethod.POST)
	public Map<String, Object> setRecordingPosition(@RequestBody Map<String, Object> requestMap) {
		// http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID
		// HttpConnectionUtil.downloadFile("http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ias3G9QkVjN4BnNuOkGf2v2oBzG9JOEJgD1mtL_72uXCiYtT5JBbMlQj2yH7_1p3IHQKEh7FR-lBApaUSpdarvfpdddgzEQi6fwMl80_q4NQ8YvW1gju_Yb3B2ktCXTCASBaABAPHQ&media_id=voiceLocalId1234567890123",
		// "./file/record", "voiceLocalId1234567890123");
		// wxLocalResource://voiceLocalId1234567890123
		// 从微信下载录音并上传到阿里服务器
		/*
		 * String accessToken = AccessTokenInWebChat.current_AccessToken; //
		 * String accessToken = requestMap.get("accessToken"); String baseUrl =
		 * "http://file.api.weixin.qq.com/cgi-bin/media/get"; String recordUrl =
		 * String.format("%s?access_token=%s&media_id=%s", baseUrl, accessToken,
		 * requestMap.get("recordId")); //
		 * HttpConnectionUtil.downloadFile(recordUrl, //
		 * System.getProperty("user.dir") + // "/file/record",
		 * requestMap.get("recordId"));
		 * 
		 * java.net.URL urlTemp = CoreController.class.getResource("/"); String
		 * path = urlTemp.getPath().substring(1, urlTemp.getPath().length() -
		 * 16); System.out.println("path:" + path);
		 * 
		 * String recordId = (String) requestMap.get("recordId");
		 * 
		 * File file = HttpConnectionUtil.downloadFile(recordUrl, recordId +
		 * "tmp"); System.out.println("下载录音成功！！");
		 * 
		 * AudioFormat.changeToMp3(recordId + "tmp", recordId);
		 * 
		 * System.out.println("录音格式转换成功！！");
		 * 
		 * int ret = UploadOss.UploadFile("miniconch", recordId + ".mp3",
		 * recordId);
		 * 
		 * System.out.println("文件上传OSS成功！！ ret = " + ret);
		 * 
		 * //删除临时文件 if (file.delete()) { System.out.println(file.getName() +
		 * "is deleted"); } else { System.out.println("Delete failed."); }
		 */

		// 上传图片
		// BufferedInputStream in = new BufferedInputStream(new
		// FileInputStream("F://project//test1.jpg"));

		java.net.URL urlImg = CoreController.class.getResource("../");
		List imgList = (ArrayList) requestMap.get("img");
		System.out.println("imgList = " + imgList);
		String imgData = (String) ((Map) imgList.get(0)).get("data");
		System.out.println("imgData = " + imgData);
		String imgSuffix = (String) ((Map) imgList.get(0)).get("suffix");
		System.out.println("imgSuffix = " + imgSuffix);
		// String filePath = urlImg.getPath() + "/examples/img/" + (String)
		// requestMap.get("recordId") + "." + imgSuffix;
		// String filePath = urlImg + (String) requestMap.get("recordId") + "."
		// + imgSuffix;

		// File file = new File(urlImg + (String) requestMap.get("recordId") +
		// imgSuffix);
		/*
		 * File file = new File("//" + (String) requestMap.get("recordId") +
		 * imgSuffix);
		 * 
		 * System.out.println("file path = " + file.getPath());
		 * 
		 * if(!file.exists()){
		 * 
		 * try{ System.out.println("file 不存在,重新创建"); file.createNewFile(); }
		 * catch(IOException e){ System.out.println("file 不存在,重新创建失败");
		 * e.printStackTrace(); }
		 * 
		 * }
		 */

		/*
		 * System.out.println("file can read  = " + file.canRead());
		 * System.out.println("file can write  = " + file.canWrite());
		 */

		/*
		 * System.out.println("before file length = " + file.length());
		 * Base64ImgEncodeAndDecode.ImgDecode(imgData, file);
		 * System.out.println("after file length = " + file.length());
		 */
		/*
		 * try { BASE64Decoder d = new BASE64Decoder(); byte[] bs =
		 * d.decodeBuffer(imgData); FileOutputStream os = new
		 * FileOutputStream(filePath); os.write(bs); os.close(); } catch
		 * (FileNotFoundException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); }
		 */

		byte[] bs = Base64ImgEncodeAndDecode.ImgDecode(imgData);
		System.out.println("bs = " + bs);

		// 上传图片
		int ret = UploadOss.UploadByte("miniconch", (String) requestMap.get("recordId") + imgSuffix, bs);
		// int ret = UploadOss.UploadFile("miniconch", file.getName(),
		// file.getPath());

		if (ret == 0) {
			System.out.println("文件上传OSS成功！！ ret = " + ret);
		} else {
			System.out.println("文件上传OSS失败！！ ret = " + ret);
		}

		// File file = new File(filePath);
		// 删除临时文件
		/*
		 * if (file.delete()) { System.out.println(file.getName() +
		 * "is deleted"); } else { System.out.println("Delete failed."); }
		 */

		// 将文字描述、摘要、图片名称、微信录音名称插入到mysql数据库
		/**
		 * title为空 coordinates为空 lat lng, officialflag为N, uid为固定值 summary 为空,
		 * icon 图片名称.jpg(图片路径+recordID+.图片后缀), recordfile 语音路径+recordID+.mp3,
		 * replyCount 默认为0, likeCount 默认为0, description 传递, date 默认值, poi 为空,
		 * citycode 为空, url 为空
		 **/
		String id = UUIDGenerator.getUUID();// record表里面的id字段值
		String title = "", coordinates = "", summary = "", poi = "", citycode = "", url = "";
		BigDecimal lat = new BigDecimal((String) requestMap.get("lat"));
		BigDecimal lng = new BigDecimal((String) requestMap.get("lng"));
		String officialflag = "N", uid = "2109c7de-8609-11e6-b6d3-782bcb720a83",
				icon = "http://miniconch.oss-cn-shenzhen.aliyuncs.com/" + (String) requestMap.get("recordId")
						+ imgSuffix,
				// recordfile = "http://www.miniconch.cn:8080/resource/audio/" +
				// (String)requestMap.get("recordId") + ".mp3" ,
				recordfile = (String) requestMap.get("recordId"), description = (String) requestMap.get("description");
		System.out.println("recordfile = " + recordfile);
		System.out.println("description = " + description);
		int replyCount = 0, likeCount = 0;
		int result = recordMapper.InsertNewRecord(id, title, uid, coordinates, lat, lng, officialflag, summary, icon,
				recordfile, replyCount, likeCount, description, poi, citycode, url);

		Map<String, Object> map = new HashMap<String, Object>();
		int resultCode = 200;
		String resultMessage = "接口调用正常返回";
		Map<String, String> subMap = new HashMap<String, String>();

		subMap.put("filepath", "/file/record" + "/" + requestMap.get("recordId"));
		System.out.println("subMap: " + subMap);

		map.put("resultCode", resultCode);
		map.put("resultMessage", resultMessage);
		map.put("value", subMap);

		return map;
	}

	/**
	 * 新增足迹
	 * 
	 * @param comment
	 * @return
	 */
	@RequestMapping(value = "/add/footprint", method = RequestMethod.GET)

		public Map<String, Object> addFootprint(@RequestBody Footprint footprint) {
		log.info("进入新增足迹 addFootprint");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultCode", SystemConst.SUCCESS);
		map.put("resultMessage", SystemConst.SUCCESS_MESSAGE);
		try {
			/*
			Footprint footprint = new Footprint();
			footprint.setUid("ef30d3eb-b106-459f-82ba-7d6457796d7e");
			footprint.setLng(new BigDecimal(Double.valueOf(104.066670)));
			footprint.setLat(new BigDecimal(Double.valueOf(30.666670)));
			footprint.setCity("成都");
			footprint.setCountry("中国");
			footprint.setProvince("四川");*/

			// 查询最近的一次足记
			Footprint lastFootprint = footprintMapper.findLatestFootprintByUID("ef30d3eb-b106-459f-82ba-7d6457796d7e");
			log.info(footprint);

			if (null == lastFootprint) {
				footprintMapper.InsertNewFootprint(footprint);
			} else {
				Date nowTime = new Date();
				// 判断时间
				if (nowTime.getTime() - lastFootprint.getFootprintDate().getTime() > 120 * 1000) {
					footprintMapper.InsertNewFootprint(footprint);
				}
			}
			map.put("resultMessage", "不符合插入条件");
		} catch (Exception e) {
			map.put("resultCode", SystemConst.ERROR);
			map.put("resultMessage", SystemConst.ERROR_MESSAGE);

		}
		return map;
	}

	/**
	 * 从足迹表查询明细
	 * 
	 * @param comment
	 * @return
	 */
	@RequestMapping(value = "/query/footprint/list", method = RequestMethod.POST)
	public Map<String, Object> queryFromFootprint(@RequestBody Footprint footprint) {
		System.out.println("进入查询足迹 queryFromFootprint");
		System.out.println(footprint.toString());
		int resultCode = 200;
		String resultMessage = "接口调用正常返回";

		Map<String, Integer> cityMap = new HashMap<String, Integer>();
		Map<String, Integer> provinceMap = new HashMap<String, Integer>();
		Map<String, Integer> countryMap = new HashMap<String, Integer>();

		List<Footprint> footPrintList = footprintMapper.findAllFootprintByUID(footprint.getUid());

		if (footPrintList == null || footPrintList.size() == 0) {

			resultCode = 210;
			resultMessage = "未查询到符合条件的数据";
		} else {
			for (Footprint obj : footPrintList) {

				if (cityMap.containsKey(obj.getCity())) {// 判断是否已经有该数值，如有，则将次数加1
					cityMap.put(obj.getCity(), cityMap.get(obj.getCity()).intValue() + 1);
				} else {
					cityMap.put(obj.getCity(), 1);
				}

				if (provinceMap.containsKey(obj.getProvince())) {// 判断是否已经有该数值，如有，则将次数加1
					provinceMap.put(obj.getProvince(), provinceMap.get(obj.getProvince()).intValue() + 1);
				} else {
					provinceMap.put(obj.getProvince(), 1);
				}

				if (countryMap.containsKey(obj.getCountry())) {// 判断是否已经有该数值，如有，则将次数加1
					countryMap.put(obj.getCountry(), countryMap.get(obj.getCountry()).intValue() + 1);
				} else {
					countryMap.put(obj.getCountry(), 1);
				}
			}
		}

		System.out.println("进入查询足迹 queryFromFootprint done,result= " + resultCode);

		System.out.println("进入查询足迹 queryFromFootprint done,cityCount= " + cityMap.size());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultCode", resultCode);
		map.put("resultMessage", resultMessage);
		map.put("cityCount", cityMap.size());//
		map.put("provinceCount", provinceMap.size());// 省份足迹数
		map.put("countryCount", countryMap.size());// 国家足迹数
		map.put("defeatClientNum", "84.9");// 百分比
		map.put("value", footPrintList);
		return map;
	}
}
