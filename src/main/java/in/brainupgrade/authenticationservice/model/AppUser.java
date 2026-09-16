package in.brainupgrade.authenticationservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "appuser")
public class AppUser {
	@Id
	@Column(name = "userid", length = 20, unique = true)
	@NotNull
	private String userid;
	@Column(name = "username", length = 20)
	private String username;
	@Column(name = "password")
	private String password;
	private String authToken;
	private String role;

	@NotNull
	public String getUserid() {
		return this.userid;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	public String getAuthToken() {
		return this.authToken;
	}

	public String getRole() {
		return this.role;
	}

	public void setUserid(@NotNull final String userid) {
		if (userid == null) {
			throw new java.lang.NullPointerException("userid is marked non-null but is null");
		}
		this.userid = userid;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setAuthToken(final String authToken) {
		this.authToken = authToken;
	}

	public void setRole(final String role) {
		this.role = role;
	}

	public AppUser(@NotNull final String userid, final String username, final String password, final String authToken, final String role) {
		if (userid == null) {
			throw new java.lang.NullPointerException("userid is marked non-null but is null");
		}
		this.userid = userid;
		this.username = username;
		this.password = password;
		this.authToken = authToken;
		this.role = role;
	}

	public AppUser() {
	}
}
