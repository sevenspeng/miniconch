package com.xiaohailuo.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

import javax.sound.sampled.AudioFormat;

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
import com.xiaohailuo.common.util.HttpConnectionUtil;
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
import com.xiaohailuo.util.ChangeAudioFormat;
import com.xiaohailuo.util.LocationUtils;
import com.xiaohailuo.util.MapUtils;
import com.xiaohailuo.util.SystemConst;
import com.xiaohailuo.util.UUIDGenerator;
import com.xiaohailuo.webchat.service.AccessTokenInWebChat;
import com.xiaohailuo.webchat.util.Base64ImgEncodeAndDecode;
import com.xiaohailuo.webchat.util.UploadOss;

import Decoder.BASE64Decoder;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;

//import scala.annotation.implicitNotFound;
//zhangpeng test github submit

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
	 * 根据经纬度获取录音（废弃）
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
	 * 根据经纬度获取录音
	 * 
	 * @param coordinates
	 * @return
	 */
	@RequestMapping(value = "/query/recordList", method = RequestMethod.POST)
	public Root getRecordListByPosition(@RequestBody Coordinate coordinates) {
		int resultCode = 200;
		String resultMessage = "接口调用正常返回";
		List<Map<String, Object>> arrayList = recordMapper.getRecordListByPosition(new BigDecimal(coordinates.getLat()),
				new BigDecimal(coordinates.getLng()));

		if (arrayList == null || arrayList.isEmpty()) {
			resultCode = 210;
			resultMessage = "未查询到符合条件的数据";
		}
		Root rootObject = new Root(resultCode, resultMessage, arrayList);

		return rootObject;

	}

	/**
	 * 根据录音ID获取录音详情
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/query/record/detail/{recordId}", method = RequestMethod.GET)
	public Map<String, Object> getDetailByRecordId(@PathVariable("recordId") String id) {
		// id
		System.out.println(String.format("%s: %s", "id", id));
		int resultCode = 200;
		String resultMessage = "接口调用正常返回";
		List<CommentInfo> arrayList = jointQueryMapper.findCommentsByRid(id);
		System.out.println("getDetailByRecordId comments: " + arrayList.size());
		if (arrayList == null || arrayList.size() == 0) {
			resultCode = 210;
			resultMessage = "未查询到符合条件的数据";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultCode", resultCode);
		map.put("resultMessage", resultMessage);
		map.put("value", arrayList);
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

		int result = commentMapper.insert(comment.getRecordId(), comment.getReplyUid(), comment.getComment());

		// 更新评论数
		if (result == 1) {
			// 更新评论数
			String id = (String) comment.getRecordId();
			int resultUpdate = recordMapper.updateReplyCount(id);
			log.info("updateReplyCount result---->" + resultUpdate);
			resultUpdate = recordMapper.addMessage(comment.getRecordId(), comment.getUid(), comment.getReplyUid(), "1",
					comment.getComment());
			log.info("addMessage result---->" + resultUpdate);

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

		int result = likeMapper.insert(like.getRecordId(), like.getUid());

		if (result == 1) {
			int resultUpdate = recordMapper.addMessage(like.getRecordId(), like.getUid(), like.getReplyUid(), "2", "");
			log.info("addMessage result---->" + resultUpdate);
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
		log.info("request login:" + requestMap.toString());
		int resultCode = 200;
		String resultMessage = "登陆成功！";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			User user = userMapper.findByNameAndPassword(requestMap.get("mobile"), requestMap.get("password"));
			if (user != null) {
				Map<String, String> subMap = new HashMap<String, String>();
				subMap.put("uid", user.getId());// id 主键信息
				subMap.put("name", user.getName() == null ? "" : user.getName());// 姓名
				subMap.put("nickname", user.getNickname() == null ? "" : user.getNickname());// 昵称
				subMap.put("profilephoto", user.getProfilephoto() == null ? "" : user.getProfilephoto());// 头像
				subMap.put("subscribetime", user.getSubscribetime());// 注册时间
				subMap.put("gender", user.getGender() == null ? "" : user.getGender());// 性别
				subMap.put("city", user.getCity() == null ? "" : user.getCity());// 城市
				subMap.put("signature", user.getSignature() == null ? "" : user.getSignature());// 个性签名
				// 查询足迹量
				subMap.put("myfootprint", String.valueOf(userMapper.getFootprintNumByUid(user.getId())));
				// 查询金币量
				subMap.put("myCoin", "0");
				map.put("value", subMap);
			} else {
				resultMessage = "手机未注册或密码错误！";
				resultCode = 550;
			}
		} catch (Exception e) {
			resultMessage = "服务器异常！";
			resultCode = 500;
			log.error("login error:", e);
		}
		map.put("resultCode", resultCode);
		map.put("resultMessage", resultMessage);
		log.info(requestMap.get("mobile") + "login response:" + map.toString());
		return map;
	}

	/**
	 * 更新用户信息 request: { "uid": "73c23999-36b2-413f-a2af-06d1099dcf9f", "img":
	 * "图片信息","image_url": "头像url", "country": "国籍","province": "省份","city":
	 * "城市","nickname": "昵称"} updateFlag 含义 1 更新头像 image_url 2 更新昵称 nickname 3
	 * 更新个性签名 personnotes 4 更新籍贯 country、province、city 5 更新1-4项所有信息
	 * 
	 */
	@RequestMapping(value = "/update/user", method = RequestMethod.POST)
	public Map<String, Object> upUserInfro(@RequestBody Map<String, Object> requestMap) throws Exception {
		log.info("request updateUser:" + requestMap.toString());
		String resultMessage = "接口调用正常返回";
		Map<String, Object> map = new HashMap<String, Object>();
		int resultCode = 200;
		try {
			User user = userMapper.findById(requestMap.get("uid").toString());
			if (null != user) {
				if (null != requestMap.get("nickname") && !"".equals(requestMap.get("nickname")))
					user.setNickname(requestMap.get("nickname").toString());
				if (null != requestMap.get("country") && !"".equals(requestMap.get("country")))
					user.setCountry(requestMap.get("country").toString());
				if (null != requestMap.get("province") && !"".equals(requestMap.get("province")))
					user.setProvince(requestMap.get("province").toString());
				if (null != requestMap.get("city") && !"".equals(requestMap.get("city")))
					user.setCity(requestMap.get("city").toString());
				if (null != requestMap.get("signature") && !"".equals(requestMap.get("signature")))
					user.setSignature(requestMap.get("signature").toString());
				if (null != requestMap.get("gender") && !"".equals(requestMap.get("gender")))
					user.setGender(requestMap.get("gender").toString());

				// 判断图片信息是佛有值
				if (null != requestMap.get("img") && !"".equals(requestMap.get("img"))) {

					List imgList = (ArrayList) requestMap.get("img");
					String imgData = (String) ((Map) imgList.get(0)).get("data");
					String imgSuffix = (String) ((Map) imgList.get(0)).get("suffix");
					byte[] bs = Base64ImgEncodeAndDecode.ImgDecode(imgData);
					// 上传图片
					int ret = UploadOss.UploadByte("miniconch",
							(String) requestMap.get("uid") + "profilephoto" + imgSuffix, bs);

					if (ret == 0) {
						String profilephotoUrl = "http://miniconch.oss-cn-shenzhen.aliyuncs.com/"
								+ (String) requestMap.get("uid") + "_profilephoto" + imgSuffix;
						user.setProfilephoto(profilephotoUrl);
					}

				}
				userMapper.updateAllInfro(user);

			} else {
				resultMessage = SystemConst.NOT_FOUND_MESSAGE;
				resultCode = SystemConst.NOT_FOUND;
			}

		} catch (Exception e) {
			resultMessage = SystemConst.ERROR_MESSAGE;
			resultCode = SystemConst.ERROR;
			log.error(e);
		}
		map.put("resultCode", resultCode);
		map.put("resultMessage", resultMessage);
		return map;
	}

	/**
	 * 录音点创建 request { "lat": "30.66667", "lng": "104.06667",
	 * "description":"这是一个好地方", "recordId": "微信返回的录音id", "img": [ { "data":
	 * "base64字符串","suffix": "图片后缀"}, { "data": "base64字符串", "suffix": "图片后缀"} ]
	 * }
	 */
	@RequestMapping(value = "/add/record/position", method = RequestMethod.POST)
	public Map<String, Object> setRecordingPosition(@RequestBody Map<String, Object> requestMap) {

		String accessToken = AccessTokenInWebChat.getAccessToken();
		String baseUrl = "http://file.api.weixin.qq.com/cgi-bin/media/get";
		String recordUrl = String.format("%s?access_token=%s&media_id=%s", baseUrl, accessToken,
				requestMap.get("recordId"));

		String fileName = (String) requestMap.get("recordId") + ".amr";
		// 下载录音文件
		String downloadDir = System.getProperty("user.dir") + "/file/record";
		File amrFile = HttpConnectionUtil.downloadFile(recordUrl, downloadDir, fileName);
		log.info("downloadFile success---->" + fileName);
		String armFileName = String.format("%s%s%s%s", System.getProperty("user.dir"), "/file/record/",
				(String) requestMap.get("recordId"), ".amr");

		String mp3FileName = String.format("%s%s%s%s", System.getProperty("user.dir"), "/file/record/",
				(String) requestMap.get("recordId"), ".mp3");
		// 转换录音文件格式
		// ChangeAudioFormat.changeToMp3(amrFile, mp3File);
		try {
			try {
				ChangeAudioFormat.amr2mp3(armFileName, mp3FileName);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("changeAudioFormat success---->" + mp3FileName);
		UploadOss.UploadFile("miniconch", (String) requestMap.get("recordId") + ".mp3", mp3FileName);
		log.info("UploadFileToOSS success---->" + mp3FileName);

		// 获取mp3录音时长
		int duration = 0;
		try {
			File mp3File = new File(mp3FileName);
			MP3File f = (MP3File) AudioFileIO.read(mp3File);
			MP3AudioHeader audioHeader = (MP3AudioHeader) f.getAudioHeader();
			duration = (int) Math.round(audioHeader.getPreciseTrackLength());
			log.info("Get Mp3File duration success---->" + duration + "seconds");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.info("Get Mp3File duration fail---->");
			e.printStackTrace();
		}

		java.net.URL urlImg = CoreController.class.getResource("../");
		List imgList = (ArrayList) requestMap.get("img");
		// System.out.println("imgList = " + imgList);
		String imgData = (String) ((Map) imgList.get(0)).get("data");
		// System.out.println("imgData = " + imgData);
		String imgSuffix = (String) ((Map) imgList.get(0)).get("suffix");

		byte[] bs = Base64ImgEncodeAndDecode.ImgDecode(imgData);
		System.out.println("bs = " + bs);

		// 上传图片
		int ret = UploadOss.UploadByte("miniconch", (String) requestMap.get("recordId") + imgSuffix, bs);

		if (ret == 0) {
			System.out.println("文件上传OSS成功！！ ret = " + ret);
		} else {
			System.out.println("文件上传OSS失败！！ ret = " + ret);
		}

		String id = UUIDGenerator.getUUID();// record表里面的id字段值
		String coordinates = "", summary = "", poi = "", citycode = "", url = "";
		BigDecimal lat = new BigDecimal((String) requestMap.get("lat"));
		BigDecimal lng = new BigDecimal((String) requestMap.get("lng"));
		String officialflag = "N", uid = (String) requestMap.get("uid"),
				icon = "http://miniconch.oss-cn-shenzhen.aliyuncs.com/" + (String) requestMap.get("recordId")
						+ imgSuffix,
				// recordfile = "http://www.miniconch.cn:8080/resource/audio/" +
				// (String)requestMap.get("recordId") + ".mp3" ,
				recordfile = "http://miniconch.oss-cn-shenzhen.aliyuncs.com/" + (String) requestMap.get("recordId")
						+ ".mp3",
				description = (String) requestMap.get("description");
		System.out.println("recordfile = " + recordfile);
		System.out.println("description = " + description);
		int replyCount = 0, likeCount = 0;
		String title = (String) requestMap.get("title");
		int result = recordMapper.InsertNewRecord(id, title, uid, coordinates, lat, lng, officialflag, summary, icon,
				recordfile, replyCount, likeCount, description, poi, citycode, url, duration);

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
	@RequestMapping(value = "/add/footprint", method = RequestMethod.POST)

	public Map<String, Object> addFootprint11(@RequestBody Footprint footprint) {
		log.info("进入新增足迹 addFootprint");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultCode", SystemConst.SUCCESS);
		map.put("resultMessage", SystemConst.SUCCESS_MESSAGE);
		try {
			// 查询最近的一次足记
			Footprint lastFootprint = footprintMapper.findLatestFootprintByUID(footprint.getUid());
			log.info(footprint);

			if (null == lastFootprint) {
				footprintMapper.InsertNewFootprint(footprint);
			} else {
				Date nowTime = new Date();
				// 判断时间
				if (nowTime.getTime() - lastFootprint.getFootprintDate().getTime() > 120 * 1000) {
					footprintMapper.InsertNewFootprint(footprint);
				} else {
					map.put("resultMessage", "不符合插入条件");
				}
			}
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
	@RequestMapping(value = "/query/footprint/rank", method = RequestMethod.POST)
	public Map<String, Object> queryRank(@RequestBody Map<String, Object> requestMap) {
		log.info("进入查询排名queryRank");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultCode", SystemConst.SUCCESS);
		map.put("resultMessage", SystemConst.SUCCESS_MESSAGE);
		try {
			// 1、查询总用户数
			int tatalUserNum = footprintMapper.getUserTatalNum();
			// 2、查询用户排名
			Integer rank = footprintMapper.getUserRank(requestMap.get("uid").toString());
			// 3、计算打败人数百分百
			if (null != rank) {
				double pkNum = 1.0 * 100 * (tatalUserNum - rank) / tatalUserNum;
				String result = String.format("%.2f", pkNum);
				map.put("percent", result);

				map.put("rank", rank);
				// 4、足迹国家数量
				map.put("countryNum", footprintMapper.getCountryNumForfootprint(requestMap.get("uid").toString()));
				// 5、足迹省份数量
				map.put("provinceNum", footprintMapper.getProvinceNumForfootprint(requestMap.get("uid").toString()));
				// 6、足迹城市数量
				map.put("cityNum", footprintMapper.getCityNumForfootprint(requestMap.get("uid").toString()));
				// 7、总足迹数
				map.put("tatalNum", footprintMapper.getTotalNumForfootprint(requestMap.get("uid").toString()));
			} else {
				map.put("percent", 0);
				map.put("rank", 0);
				// 4、足迹国家数量
				map.put("countryNum", 0);
				// 5、足迹省份数量
				map.put("provinceNum", 0);
				// 6、足迹城市数量
				map.put("cityNum", 0);
				// 7、总足迹数
				map.put("tatalNum", 0);
			}

		} catch (Exception e) {
			map.put("resultCode", SystemConst.ERROR);
			map.put("resultMessage", SystemConst.ERROR_MESSAGE);

		}
		return map;
	}

	/**
	 * 查询某一用户的所以足迹
	 * 
	 * @param footprint
	 * @return
	 */
	@RequestMapping(value = "/query/footprint/list", method = RequestMethod.POST)
	public Map<String, Object> queryAllFootprintByUid(@RequestBody Map<String, Object> requestMap) {

		log.info("进入查询足迹queryAllFootprintByUid");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultCode", SystemConst.SUCCESS);
		map.put("resultMessage", SystemConst.SUCCESS_MESSAGE);
		try {
			List<Footprint> list = footprintMapper.findAllFootprintByUID(requestMap.get("uid").toString());
			List<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
			for (int i = 0; i < list.size(); i++) {
				HashMap<String, String> item = new HashMap<String, String>();
				item.put("uid", list.get(i).getUid());
				item.put("lat", list.get(i).getLat().toPlainString());
				item.put("lng", list.get(i).getLng().toPlainString());
				item.put("city", list.get(i).getCity());
				item.put("province", list.get(i).getProvince());
				item.put("country", list.get(i).getCountry());
				item.put("footprintDate",
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getFootprintDate()));
				dataList.add(item);
			}
			map.put("data", dataList);
		} catch (Exception e) {
			map.put("resultCode", SystemConst.ERROR);
			map.put("resultMessage", SystemConst.ERROR_MESSAGE);

		}
		return map;
	}

	/**
	 * 根据用户ID查询消息列表
	 * 
	 * @param coordinates
	 * @return
	 */
	@RequestMapping(value = "/query/getMessageList/{uid}", method = RequestMethod.GET)
	public Map<String, Object> getMessageListByUid(@PathVariable("uid") String uid) {
		int resultCode = 200;
		String resultMessage = "接口调用正常返回";
		List<Map<String, Object>> arrayList = commentMapper.getMessageListByUid(uid);

		if (arrayList == null || arrayList.isEmpty()) {
			resultCode = 210;
			resultMessage = "未查询到符合条件的数据";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultCode", resultCode);
		map.put("resultMessage", resultMessage);
		map.put("value", arrayList);
		return map;
	}

	/**
	 * 根据消息ID更新状态
	 * 
	 * @param coordinates
	 * @return
	 */
	@RequestMapping(value = "/updateMessageStatus/{id}", method = RequestMethod.POST)
	public Map<String, Object> updateMessageStatusById(@PathVariable("id") String id) {
		int resultCode = 200;
		String resultMessage = "接口调用正常返回";
		try {
			commentMapper.updateMessageStatusById(id);
		} catch (Exception e) {
			resultCode = 500;
			resultMessage = "更新异常";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultCode", resultCode);
		map.put("resultMessage", resultMessage);		
		return map;
	}

}
