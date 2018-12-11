package net.slipp.web;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.slipp.domain.Answer;
import net.slipp.domain.AnswerRepository;
import net.slipp.domain.News;
import net.slipp.domain.NewsAnswer;
import net.slipp.domain.NewsAnswerRepository;
import net.slipp.domain.NewsRepository;
import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import net.slipp.domain.User;

@Controller
@RequestMapping("/news/{newsId}/newsAnswers")
public class NewsAnswerController {
	
 	@Autowired
	private NewsRepository newsRepository;
	
	@Autowired
	private NewsAnswerRepository newsAnswerRepository;
	
	@PostMapping("")
	public String create(@PathVariable Long newsId, String contents, HttpSession session) {
		if( !HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		News news = newsRepository.findById(newsId).get(); 
		NewsAnswer newsAnswer = new NewsAnswer(loginUser, news, contents);
		newsAnswerRepository.save(newsAnswer);
		return String.format("redirect:/news/%d", newsId);
	}
	
}

