package in.brainupgrade.authenticationservice.securityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import in.brainupgrade.authenticationservice.service.JwtRequestFilter;

/**
 * Spring Security 6 removed WebSecurityConfigurerAdapter, so the same rules are
 * expressed as beans: a SecurityFilterChain in place of configure(HttpSecurity),
 * and a WebSecurityCustomizer in place of configure(WebSecurity).
 *
 * The UserDetailsService is picked up automatically as a bean, which is what the
 * old configure(AuthenticationManagerBuilder) was doing by hand.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfigurer {

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring().requestMatchers("/auth-ms/login", "/h2-console/**", "/validateToken", "/role/**",
				"/v3/api-docs/**");
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth
						// Kubernetes probes are unauthenticated by necessity: the kubelet
						// presents no credentials. "/*" below matches a single path segment
						// only, so it does not cover /actuator/health/liveness.
						.requestMatchers("/actuator/health/**", "/actuator/info").permitAll()
						.requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/webjars/**")
						.permitAll()
						.requestMatchers("/auth-ms/emp").hasRole("EMPLOYEE")
						.requestMatchers("/*").permitAll()
						.anyRequest().authenticated())
				.exceptionHandling(ex -> {
				})
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
}
