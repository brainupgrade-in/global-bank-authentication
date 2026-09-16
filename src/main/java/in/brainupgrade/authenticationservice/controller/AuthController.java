package in.brainupgrade.authenticationservice.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import in.brainupgrade.authenticationservice.exceptionhandling.AppUserNotFoundException;
import in.brainupgrade.authenticationservice.model.AppUser;
import in.brainupgrade.authenticationservice.model.AuthenticationResponse;
import in.brainupgrade.authenticationservice.repository.UserRepository;
import in.brainupgrade.authenticationservice.service.LoginService;
import in.brainupgrade.authenticationservice.service.Validationservice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

/**
 * Controller for Authentication microservice
 */
@RestController
@CrossOrigin(origins = "*")
public class AuthController {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AuthController.class);
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private LoginService loginService;
	@Autowired
	private Validationservice validationService;

	/**
	 * The health method to check app
	 */
	@GetMapping("/health")
	@Operation(summary = "Checks the health of Authentication microservice")
	public ResponseEntity<String> healthCheckup() {
		log.info("Health Check for Authentication Microservice");
		log.info("health checkup ----->{}", "up");
		return new ResponseEntity<>("Up and running...", HttpStatus.OK);
	}

	/**
	 * Authenticate user based on given login credentials
	 * 
	 * @param appUserloginCredentials
	 * @return
	 * @throws UsernameNotFoundException
	 * @throws AppUserNotFoundException
	 */
	@PostMapping("/login")
	@Operation(summary = "Login user", description = "In order to login the user has to provide its credentials")
	public ResponseEntity<AppUser> login(@Parameter(description = "User login credentials", required = true) @RequestBody AppUser appUserloginCredentials) throws UsernameNotFoundException, AppUserNotFoundException {
		AppUser user = loginService.userLogin(appUserloginCredentials);
		log.info("Credentials ----->{}", user);
		return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
	}

	/**
	 * Checks validity of the token
	 * 
	 * @param token
	 * @return
	 */
	@GetMapping("/validateToken")
	@Operation(summary = "Validate token", description = "Validates token ")
	public AuthenticationResponse getValidity(@Parameter(description = "Token for validation", required = true) @RequestHeader("Authorization") final String token) {
		log.info("Token Validation ----->{}", token);
		return validationService.validate(token);
	}

	/**
	 * Creates a new user in UserRepository
	 * 
	 * @param appUserCredentials
	 * @return
	 */
	@PostMapping("/createUser")
	@Operation(summary = "Create a new user", description = "Creates user by providing valid login credentials")
	public ResponseEntity<?> createUser(@Parameter(description = "User credentials", required = true) @RequestBody AppUser appUserCredentials) {
		AppUser createduser = null;
		try {
			createduser = userRepository.save(appUserCredentials);
		} catch (Exception e) {
			return new ResponseEntity<String>("Not created", HttpStatus.NOT_ACCEPTABLE);
		}
		log.info("user creation---->{}", createduser);
		return new ResponseEntity<>(createduser, HttpStatus.CREATED);
	}

	/**
	 * Get all users in DB (Accessible only by user with role EMPLOYEE)
	 * 
	 * @param token
	 * @return
	 */
	@PreAuthorize("hasRole(\'ROLE_EMPLOYEE\')")
	@GetMapping("/find")
	@Operation(summary = "Get all users", description = "EMPLOYEE role required for this operation")
	public ResponseEntity<List<AppUser>> findUsers(@Parameter(description = "Token for authentication passed in header", required = true) @RequestHeader("Authorization") final String token) {
		List<AppUser> createduser = new ArrayList<>();
		List<AppUser> findAll = userRepository.findAll();
		findAll.forEach(emp -> createduser.add(emp));
		log.info("All Users  ----->{}", findAll);
		return new ResponseEntity<>(createduser, HttpStatus.CREATED);
	}

	/**
	 * Get role of the user based on userid
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/role/{id}")
	@Operation(summary = "Get role", description = "Pass id of the user whose role is to be retrieved")
	public String getRole(@Parameter(description = "id of user", required = true) @PathVariable("id") String id) {
		return userRepository.findById(id).get().getRole();
	}
}
