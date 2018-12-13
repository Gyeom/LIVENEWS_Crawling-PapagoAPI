package net.live.web;

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

import net.live.domain.News;
import net.live.domain.NewsRepository;
import net.live.domain.Question;
import net.live.domain.QuestionRepository;
import net.live.domain.Result;
import net.live.domain.User;
import net.live.domain.UserRepository;



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