package fr.diginamic.hello.services;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Service
public class HelloService {
		//
		public String salutations() {
			return "Je suis la classe de service et je vous dis bonjour!";
		}
		//
}

