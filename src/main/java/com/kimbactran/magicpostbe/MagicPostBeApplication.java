package com.kimbactran.magicpostbe;

import com.kimbactran.magicpostbe.entity.User;
import com.kimbactran.magicpostbe.entity.UserRole;
import com.kimbactran.magicpostbe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MagicPostBeApplication implements CommandLineRunner {
	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(MagicPostBeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User adminAccount = userRepository.findByRole(UserRole.ADMIN);
		if(adminAccount == null) {
			User user = new User();
			user.setEmail("admin@gmail.com");
			user.setFirstName("Admin");
			user.setLastName("Account");
			user.setRole(UserRole.ADMIN);
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			userRepository.save(user);
		}
	}
}
