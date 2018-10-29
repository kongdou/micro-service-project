package fun.deepsky.docker.dubbo.course.dto;

import java.io.Serializable;

import fun.deepsky.docker.thrift.user.dto.TeacherDTO;

public class CourseDTO implements Serializable{

	private int id;
	private String title;
	private String description;
	private TeacherDTO teacher;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public TeacherDTO getTeacher() {
		return teacher;
	}
	public void setTeacher(TeacherDTO teacher) {
		this.teacher = teacher;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	
}
