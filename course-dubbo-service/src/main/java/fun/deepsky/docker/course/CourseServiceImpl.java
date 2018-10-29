package fun.deepsky.docker.course;

import java.util.List;

import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;

import fun.deepsky.docker.course.mapper.CourseMapper;
import fun.deepsky.docker.dubbo.course.CourseService;
import fun.deepsky.docker.dubbo.course.dto.CourseDTO;
import fun.deepsky.docker.thrift.user.UserInfo;
import fun.deepsky.docker.thrift.user.dto.TeacherDTO;

@Service(
        version = "${course.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class CourseServiceImpl implements CourseService{

	@Autowired
	CourseMapper courseMapper;

	@Autowired
	ServiceProvider serviceProvider;
	
	@Override
	public List<CourseDTO> courseList() {
		List<CourseDTO> courseDTOs = courseMapper.listCourse();
		if(courseDTOs != null) {
			for(CourseDTO courseDto:courseDTOs) {
				int teacherId = courseMapper.getCourseTeacher(courseDto.getId());
				try {
					UserInfo userInfo = serviceProvider.getUserService().getTeacherById(teacherId);
					courseDto.setTeacher(trans2Teacher(userInfo));
				} catch (TException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return courseDTOs;
	}


	private TeacherDTO trans2Teacher(UserInfo userInfo) {
        TeacherDTO teacherDTO = new TeacherDTO();
        BeanUtils.copyProperties(userInfo, teacherDTO);
        return teacherDTO;
	}

}
