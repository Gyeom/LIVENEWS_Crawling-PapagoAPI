package net.slipp.web;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.slipp.domain.News;
import net.slipp.domain.NewsRepository;
import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import net.slipp.domain.Result;
import net.slipp.domain.User;
import net.slipp.domain.UserRepository;



@Controller
@RequestMapping("/mainNews")
public class NewsController {
	
	
	
	@Autowired
	private NewsRepository newsRepository;
	
	
	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {
		News news = newsRepository.findById(id).get();
		model.addAttribute("newsContents", news);
		return "/news/show";
	}
	

	
}