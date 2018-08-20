package courseapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import courseapp.models.User;
import courseapp.repositories.UserRepository;
import courseapp.exceptions.UsernameConflictException;
import courseapp.exceptions.InvalidLoginException;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserService {
	@Autowired
	UserRepository userRepository;

	@DeleteMapping("/api/user/{userId}")
	public void deleteUser(@PathVariable("userId") int id) {
		userRepository.deleteById(id);
	}

	@PostMapping("/api/user")
	public User createUser(@RequestBody User user) {
		return userRepository.save(user);
	}


	@GetMapping("/api/user")
	public List<User> findAllUsers() {
		return (List<User>) userRepository.findAll();
	}

	@PutMapping("/api/user/{userId}")
	public User updateUser(@PathVariable("userId") int userId, @RequestBody User newUser) {
		Optional<User> data = userRepository.findById(userId);
		if(data.isPresent()) {
			User user = data.get();
			user.setUsername(newUser.getUsername());
			user.setPassword(newUser.getPassword());
			user.setFirstName(newUser.getFirstName());
			user.setLastName(newUser.getLastName());
			user.setRole(newUser.getRole());
			user.setDateOfBirth(newUser.getDateOfBirth());
			user.setEmail(newUser.getEmail());
			user.setPhone(newUser.getPhone());
			user.setRole(newUser.getRole());
			
			userRepository.save(user);
			return user;
		}
		return null;
	}

	@PutMapping("/api/profile")
	public User updateProfile(@RequestBody User newUser) {
		Optional<User> data = userRepository.findUserByUsername(newUser.getUsername());
		if(data.isPresent()) {
			User user = data.get();
			user.setUsername(newUser.getUsername());
			user.setPassword(newUser.getPassword());
			user.setFirstName(newUser.getFirstName());
			user.setLastName(newUser.getLastName());
			user.setRole(newUser.getRole());
			user.setDateOfBirth(newUser.getDateOfBirth());
			user.setEmail(newUser.getEmail());
			user.setPhone(newUser.getPhone());
			user.setRole(newUser.getRole());
			userRepository.save(user);
			return user;
		}
		return null;		
	}

	
	
	@GetMapping("/api/user/{userId}")
	public User findUserById(@PathVariable("userId") int userId) {
		Optional<User> data = userRepository.findById(userId);
		if(data.isPresent()) {
			return data.get();
		}
		return null;
	}
	
	
	@GetMapping("/api/profile/{username}")
	public User findUserByUsername(@PathVariable("username") String username) {
		Optional<User> data = userRepository.findUserByUsername(username);
		if(data.isPresent()) {
			return data.get();
		}
		return null;
	}
	
	

	@PostMapping("/api/register")
	public User register(@RequestBody User user) {
		Optional<User> data = userRepository.findUserByUsername(user.getUsername());
		if(data.isPresent()) {
			throw new  UsernameConflictException("username :" + user.getUsername());
		}
		else 
		{
			return userRepository.save(user);
		}
	}

	@PostMapping("/api/login")
	public User login(@RequestBody User user) {
		Optional<User> data = userRepository.findUserByCredentials(user.getUsername(),user.getPassword());
		if(!data.isPresent()) {
			throw new  InvalidLoginException("username :" + user.getUsername());
		}
		else 
		{
			return data.get();
		}
	}

	
}
