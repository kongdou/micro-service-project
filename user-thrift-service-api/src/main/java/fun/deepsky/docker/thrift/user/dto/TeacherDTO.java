package fun.deepsky.docker.thrift.user.dto;

public class TeacherDTO extends UserDTO{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String intro;
	private int stars;
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public int getStars() {
		return stars;
	}
	public void setStars(int stars) {
		this.stars = stars;
	}
	}
