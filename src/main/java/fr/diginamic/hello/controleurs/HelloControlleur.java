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
		//return helloserv.salutations();
		return "\u05D9\u05D5\u05DD \u05E9\u05D1\u05EA \n \uFEF3\uFEEE\uFEE2 \uFE8D\uFEDF\uFEB4\uFE92\uFE96  \n \u0441\u044A\u0431\u043E\u0442\u0430 \n \u661F\u671F\u516D"+helloserv.salutations();//yom shabbat = ok
	}
	
//

}
