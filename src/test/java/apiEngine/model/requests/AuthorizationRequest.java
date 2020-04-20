package apiEngine.model.requests;

public class AuthorizationRequest {

	public String userName;
	public String password;

	public AuthorizationRequest() {
	}

	public AuthorizationRequest(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

}