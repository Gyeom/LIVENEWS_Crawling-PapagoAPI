package net.live.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import net.live.domain.QuestionRepository;

@Controller
public class HomeController {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	

	@GetMapping("")
	public String home(Model model) {
	
//		model.addAttribute("questions", questionRepository.findAll());
//		return "index";
		return "redirect:/users/news/1";
	}
	
}
