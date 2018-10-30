package fun.deepsky.course.edge.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import fun.deepsky.docker.thrift.user.dto.UserDTO;
import fun.deepsky.docker.user.edge.filter.LoginFilter;

public class CourseFilter extends LoginFilter{

	@Override
	protected void login(HttpServletRequest request, HttpServletResponse response, UserDTO userDTO) {

		request.setAttribute("userInfo", userDTO);
		
	}

}
