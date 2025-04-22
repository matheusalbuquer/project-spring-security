package tech.neckel.security.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class Controller {
	
	
	private final UserService userService;
	
	@PostMapping
	public User create (@RequestBody User user) {
		return userService.create(user);
	}
}
