package tech.neckel.security.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpls implements UserService {

	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User create(User user) {
		
		User existUser = userRepository.findByUsername(user.getUsername());
		
		if(existUser != null) {
			throw new Error("User already exists"); 
		}
		user.setPassword(passwordEncoder().encode(user.getPassword()));
		User createUser = userRepository.save(user);
		
		return createUser;
	}
	
}
