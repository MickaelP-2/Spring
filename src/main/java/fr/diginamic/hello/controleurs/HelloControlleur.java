package fr.diginamic.hello.controleurs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.hello.services.HelloService;

@RestController
@RequestMapping("/hello")
public class HelloControlleur {
//
	@Autowired
	private HelloService helloserv;
	
	@GetMapping
	public String direHello() {
		return helloserv.salutations();
		//return "\u05D9\u05D5\u05DD \u05E9\u05D1\u05EA";//yom shabbat = ok
	}
	
//

}
