package tech.neckel.security.security;

import java.io.IOException;
import java.util.Base64;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import tech.neckel.security.user.User;
import tech.neckel.security.user.UserRepository;

@Component
@RequiredArgsConstructor
public class CustomBasicAuthenticationFilter extends OncePerRequestFilter {

	private static final String AUTHORIZATION = "Authorization";
	
	private static final String BASIC = "Basic";
	
	private final UserRepository userRepository;

	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
		if(isBasicAuthentication(request)) {
			String [] crendetials = decodeBase64(getHeader(request).replace(BASIC, ""))
					.split(":");
			
			
			String username = crendetials[0];
			String password = crendetials[1];
			
			
			User user = userRepository.findByUsername(username);
			
			if(user == null) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("User does not exist");
				return ;
			}
			
			boolean valid = checkPassword(user.getPassword(), password);
			
			if(!valid) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write("Password not match");
			}
			
			
			setAuthetication(user);
		}
	}
	
	private void setAuthetication(User user) {

		Authentication authentication = createAuthenticationToken(user);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private Authentication createAuthenticationToken(User user) {
		UserPrincipal userPrincipal = UserPrincipal.create(user);
		return new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
	}

	private boolean checkPassword(String userPassword, String loginPassoword) {
		return passwordEncoder().matches(loginPassoword, userPassword);
	}

	private String decodeBase64(String base64) {
		byte [] decodeBytes = Base64.getDecoder().decode(base64);
		return new String (decodeBytes);
	}
	
	private boolean isBasicAuthentication(HttpServletRequest request) {
		String header = getHeader(request);
		return header != null && header.startsWith(BASIC);
	}
	
	private String getHeader(HttpServletRequest request) {
		return request.getHeader("Authorization");
	}
}
