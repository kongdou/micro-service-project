package fun.deepsky.docker.user.edge.controller;

import java.security.MessageDigest;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fun.deepsky.docker.thrift.user.UserInfo;
import fun.deepsky.docker.thrift.user.dto.UserDTO;
import fun.deepsky.docker.user.edge.redis.RedisClient;
import fun.deepsky.docker.user.edge.response.LoginResponse;
import fun.deepsky.docker.user.edge.response.Response;
import fun.deepsky.docker.user.edge.thrift.ServiceProvider;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private ServiceProvider serviceProvider;
	
	@Autowired
	private RedisClient redisClient;
	
	
	/**
	 * 跳转登录页面
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login() {
		System.out.println("login....");
		return "/login";
	}
	
	/**
	 * 登录验证
	 * @param mobile
	 * @param email
	 * @return
	 */
	@RequestMapping(value="/sendVerifyCode",method = RequestMethod.POST)
	@ResponseBody
	public Response sendVerifyCode(@RequestParam(value="mobile",required=false) String mobile,
			@RequestParam(value="email",required=false) String email) {
		//生成验证码
		String verifyCode = randomCode("0123456789", 6);
		String message = "verify code is:"+verifyCode;
		try {
			boolean result = false;
			if(!StringUtils.isBlank(mobile)) {
				//发送手机验证码
				redisClient.set(mobile, verifyCode);
				result = serviceProvider.getMessageService().sendMobileMessage(mobile, message);
			}else if(!StringUtils.isBlank(email)){
				//发送邮箱验证码
				redisClient.set(email, verifyCode);
				result = serviceProvider.getMessageService().sendMailMessage(email, message);
			}else {
				return Response.MOBILE_OR_EMAIL_REQUIRED;
			}
			if(!result) {
				return Response.SEND_VERIFYCODE_FAILED;
			}
		} catch (TException e) {
			e.printStackTrace();
			return Response.exception(e);
		}
		return Response.SUCCESS;
	}
	
	@RequestMapping(value="/register",method=RequestMethod.POST)
	@ResponseBody
	public Response register(@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam(value="mobile",required=false) String mobile,
			@RequestParam(value="email",required=false) String email,
			@RequestParam("verifyCode") String verifyCode) {
		
		if(StringUtils.isBlank(mobile) && StringUtils.isBlank(email)) {
			return Response.MOBILE_OR_EMAIL_REQUIRED;
		}
		
		if(StringUtils.isNoneBlank(mobile)) {
			String redisCode = redisClient.get(mobile);
			if(!verifyCode.equalsIgnoreCase(redisCode)) {
				return Response.VERIFY_CODE_INVALID;
			}
		}else {
			String redisCode = redisClient.get(email);
			if(!verifyCode.equalsIgnoreCase(redisCode)) {
				return Response.VERIFY_CODE_INVALID;
			}
		}
		
		UserInfo userInfo = new UserInfo();
		userInfo.setUsername(username);
		userInfo.setPassword(md5(password));
		userInfo.setMobile(mobile);
		userInfo.setEmail(email);
		
		try {
			serviceProvider.getUserService().regiserUser(userInfo);
		} catch (TException e) {
			e.printStackTrace();
			return Response.exception(e);
		}
		return Response.SUCCESS;
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public Response login(@RequestParam("username")String username,@RequestParam("password")String password) {
		//验证用户名和密码
		UserInfo userInfo = null;
		try {
			userInfo = serviceProvider.getUserService().getUserByName(username);
		} catch (TException e) {
			e.printStackTrace();
			return Response.USERNAME_PASSWORD_INVALID;
		}
		if(userInfo == null) {
			return Response.USERNAME_PASSWORD_INVALID;
		}
		if(!userInfo.getPassword().equals(md5(password))) {
			return Response.USERNAME_PASSWORD_INVALID;
		}
		//生成token
		String token = genToken();
		
		//缓存用户
		redisClient.set(token, toDto(userInfo), 1800);
		
		return new LoginResponse(token);
	}

	@RequestMapping(value="/authentication",method=RequestMethod.POST)
	@ResponseBody
	public UserDTO authentication(@RequestHeader("token") String token) {
		return redisClient.get(token);
	}
	
	private UserDTO toDto(UserInfo userInfo) {
		UserDTO userDto = new UserDTO();
		BeanUtils.copyProperties(userInfo, userDto);
		return userDto;
	}

	private String genToken() {
		return randomCode("0123456789abcdefghijklmnopqrstuvwxyz",32);
	}

	private String randomCode(String string, int size) {
		StringBuffer result = new StringBuffer(size);
		Random random = new Random();
		for(int i = 0;i<size;i++) {
			int location = random.nextInt(size);
			result.append(string.charAt(location));
		}
		return result.toString();
	}

	private String md5(String password) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] md5bytes = md5.digest(password.getBytes("utf-8"));
			return HexUtils.toHexString(md5bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
