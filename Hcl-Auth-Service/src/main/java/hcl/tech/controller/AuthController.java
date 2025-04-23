package hcl.tech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hcl.tech.entity.User;
import hcl.tech.repo.UserRepository;
import hcl.tech.service.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired 
    private JwtService jwtService;
    @Autowired 
    private PasswordEncoder passwordEncoder;

    @GetMapping("/welcome")
	public String getWelcome() {
		String msg="Welcome to UserAPI......";
		return msg;
	}
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered!";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User found = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (passwordEncoder.matches(user.getPassword(), found.getPassword())) {
            return jwtService.generateToken(user.getUsername());
        } else {
            return "Invalid credentials";
        }
    }
}
