package in.brainupgrade.authenticationservice.errorhandling;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;

public class ErrorMessage {
	private HttpStatus status;
	private LocalDateTime timestamp;
	private String message;

	public HttpStatus getStatus() {
		return this.status;
	}

	public LocalDateTime getTimestamp() {
		return this.timestamp;
	}

	public String getMessage() {
		return this.message;
	}

	public void setStatus(final HttpStatus status) {
		this.status = status;
	}

	public void setTimestamp(final LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public ErrorMessage(final HttpStatus status, final LocalDateTime timestamp, final String message) {
		this.status = status;
		this.timestamp = timestamp;
		this.message = message;
	}

	public ErrorMessage() {
	}
}
