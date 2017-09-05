//package com.xiaohailuo.web;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.xiaohailuo.domain.UserTest;
//import com.xiaohailuo.domain.UserTestMapper;
//
//@RestController
//@RequestMapping(value = "/xhl")
//@Transactional
//public class UserController {
//	static Map<Long, UserTest> users = Collections.synchronizedMap(new HashMap<Long, UserTest>());
//
//	@Autowired
//	private UserTestMapper userMapper;
//
//	@RequestMapping(value = "/", method = RequestMethod.GET)
//	public List<UserTest> getUserList() {
//		// 处理"/users/"的GET请求，用来获取用户列表
//		// 还可以通过@RequestParam从页面中传递参数来进行查询条件或者翻页信息的传递
//		List<UserTest> r = new ArrayList<UserTest>(users.values());
//		return r;
//	}
//
//	/*
//	@RequestMapping(value="/", method=RequestMethod.POST) 
//	public String postUser(@ModelAttribute User user) { // 处理"/users/"的POST请求，用来创建User 
//	//除了@ModelAttribute绑定参数之外，还可以通过@RequestParam从页面中传递参数
//		users.put(user.getId(), user); return "success"; 
//	}*/
//	@RequestMapping(value = "/", method = RequestMethod.POST)
//	public String postUser(@RequestBody UserTest user) {
//		// 处理"/users/"的POST请求，用来创建User
//		// 除了@ModelAttribute绑定参数之外，还可以通过@RequestParam从页面中传递参数
//		System.out.println(user.toString());
//		users.put(user.getId(), user);
//
//		userMapper.insert(user.getId(), user.getName(), user.getAge(), user.getAddress());
//		UserTest u = userMapper.findByName(user.getName());
//
//		return "success";
//	}
//
//	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
//	public UserTest getUser(@PathVariable Long id) {
//		// 处理"/users/{id}"的GET请求，用来获取url中id值的User信息
//		// url中的id可通过@PathVariable绑定到函数的参数中
//		//return users.get(id);
//		UserTest userTest = userMapper.findById(id);
//		System.out.println(userTest.toString());
//		
//		return userTest;
//	}
//
//	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
//	public String putUser(@PathVariable Long id, @ModelAttribute UserTest user) {
//		// 处理"/users/{id}"的PUT请求，用来更新User信息
//		UserTest u = users.get(id);
//		u.setName(user.getName());
//		u.setAge(user.getAge());
//		users.put(id, u);
//		return "success";
//	}
//
//	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//	public String deleteUser(@PathVariable Long id) {
//		// 处理"/users/{id}"的DELETE请求，用来删除User
//		users.remove(id);
//		return "success";
//	}
//}