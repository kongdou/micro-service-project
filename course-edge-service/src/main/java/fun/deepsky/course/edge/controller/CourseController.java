package fun.deepsky.course.edge.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import fun.deepsky.docker.dubbo.course.CourseService;
import fun.deepsky.docker.dubbo.course.dto.CourseDTO;
import fun.deepsky.docker.thrift.user.dto.UserDTO;

@RestController
@RequestMapping("/course")
public class CourseController {
	
    @Reference(version = "${course.service.version}",
            application = "${dubbo.application.id}",
            url = "dubbo://localhost:12345")
	CourseService courseService;
	
	@RequestMapping(value="/getCourseList",method=RequestMethod.GET)
	@ResponseBody
	public List<CourseDTO> getCourseList(HttpServletRequest request) {
		//单点登录
		UserDTO userDTO = (UserDTO) request.getAttribute("userInfo");
		//System.out.println(userDTO.toString());
		return courseService.courseList();
	}
	
	
}
