package fun.deepsky.course.edge.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import fun.deepsky.docker.dubbo.course.CourseService;
import fun.deepsky.docker.dubbo.course.dto.CourseDTO;

@RestController
@RequestMapping("/course")
public class CourseController {
	
    @Reference(version = "${course.service.version}",
            application = "${dubbo.application.id}",
            url = "dubbo://localhost:12345")
	CourseService courseService;
	
	@RequestMapping(value="/getCourseList",method=RequestMethod.GET)
	@ResponseBody
	public List<CourseDTO> getCourseList() {
		return courseService.courseList();
	}
}
