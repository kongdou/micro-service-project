package fun.deepsky.docker.course.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import fun.deepsky.docker.dubbo.course.dto.CourseDTO;

@Mapper
public interface CourseMapper {

	@Select("select * from pe_course")
	List<CourseDTO> listCourse();
	
	@Select("select user_id from pr_user_course  where user_id=#{courseId}")
	Integer getCourseTeacher(@Param("courseId") int id);
}
