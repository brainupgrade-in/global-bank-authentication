package in.brainupgrade.authenticationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import in.brainupgrade.authenticationservice.model.AuthenticationResponse;
import in.brainupgrade.authenticationservice.repository.UserRepository;

@Component
public class Validationservice {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Validationservice.class);
	@Autowired
	private JwtUtil jwtutil;
	@Autowired
	private UserRepository userRepo;

	/**
	 * Validates any JWT token
	 * 
	 * @param token
	 * @return
	 */
	public AuthenticationResponse validate(String token) {
		AuthenticationResponse authenticationResponse = new AuthenticationResponse();
		String jwt = token.substring(7);
		if (jwtutil.validateToken(jwt)) {
			log.info("==================Token successfully validated!=================");
			authenticationResponse.setUserid(jwtutil.extractUsername(jwt));
			authenticationResponse.setValid(true);
			authenticationResponse.setName(userRepo.findById(jwtutil.extractUsername(jwt)).get().getUsername());
		} else {
			log.info("==================Token not successfully validated!=================");
			authenticationResponse.setValid(false);
		}
		return authenticationResponse;
	}
}
