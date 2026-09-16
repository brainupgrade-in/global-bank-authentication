package in.brainupgrade.authenticationservice.model;

public class AuthenticationResponse {
	private String userid;
	private String name;
	private boolean isValid;

	public String getUserid() {
		return this.userid;
	}

	public String getName() {
		return this.name;
	}

	public boolean isValid() {
		return this.isValid;
	}

	public void setUserid(final String userid) {
		this.userid = userid;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setValid(final boolean isValid) {
		this.isValid = isValid;
	}

	@java.lang.Override
	public java.lang.String toString() {
		return "AuthenticationResponse(userid=" + this.getUserid() + ", name=" + this.getName() + ", isValid=" + this.isValid() + ")";
	}

	public AuthenticationResponse() {
	}

	public AuthenticationResponse(final String userid, final String name, final boolean isValid) {
		this.userid = userid;
		this.name = name;
		this.isValid = isValid;
	}
}
