package fun.deepsky.docker.dubbo.course;

import java.util.List;

import fun.deepsky.docker.dubbo.course.dto.CourseDTO;

public interface CourseService {

	public List<CourseDTO>  courseList();
}
