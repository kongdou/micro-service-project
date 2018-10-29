package fun.deepsky.docker.user.edge.response;

public class LoginResponse extends Response{
	
	private static final long serialVersionUID = 1L;

	private String token;

	public LoginResponse(String token) {
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}
