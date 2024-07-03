package fr.diginamic.hello.controleurs;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloControlleur {
//
	@GetMapping
	public String direHello() {
		return "Hello";
	}
//
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String direHello();
	}

}
