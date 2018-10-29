package fun.deepsky.docker.user.edge.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import fun.deepsky.docker.thrift.user.dto.UserDTO;

public abstract class LoginFilter implements Filter {

	private static Cache<String, UserDTO> cache = CacheBuilder.newBuilder().maximumSize(10000)
			.expireAfterAccess(3, TimeUnit.MINUTES).build();

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
		String token = request.getParameter("token");
		
		if(StringUtils.isBlank(token)) {
			Cookie[] cookies = request.getCookies();
			if(cookies != null) {
				for(Cookie cookie:cookies) {
					if(cookie.getName().equals("token")) {
						token = cookie.getValue();
					}
				}
			}
			
			UserDTO userDTO  = null;
			if(StringUtils.isNoneBlank(token)) {
				userDTO = cache.getIfPresent(token);
				if(userDTO == null) {
					userDTO = requestUserInfo(token);
					cache.put(token, userDTO);
				}
			}
			
			if(userDTO == null) {
				response.sendRedirect("http://127.0.0.1:8082/user/login");
				return ;
			}
			
			
			login(request,response,userDTO);
			
			chain.doFilter(servletRequest, servletResponse);
			
		}
	}

	/**
	 * 用户实现自己的登录
	 * 
	 * @param request
	 * @param response
	 * @param userDTO
	 */
	protected abstract void login(HttpServletRequest request, HttpServletResponse response, UserDTO userDTO);

	/**
	 * 根据token从用户系统获取用户信息
	 * 
	 * @param token
	 * @return
	 */
	private UserDTO requestUserInfo(String token) {
		String url = "http://localhost:8082/user/authentication";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		post.addHeader("token", token);

		InputStream is = null;
		try {
			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				throw new RuntimeException(
						"request user info failed ! status:" + response.getStatusLine().getStatusCode());
			}

			is = response.getEntity().getContent();
			byte[] temp = new byte[1024];
			StringBuilder sb = new StringBuilder();
			int len = 0;
			while ((len = is.read(temp)) > 0) {
				sb.append(new String(temp, 0, len));
			}

			UserDTO userDTO = new ObjectMapper().readValue(sb.toString(), UserDTO.class);

			return userDTO;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
