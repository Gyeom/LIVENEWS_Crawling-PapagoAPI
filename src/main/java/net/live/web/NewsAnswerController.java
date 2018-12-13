package net.live.web;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.live.domain.Answer;
import net.live.domain.AnswerRepository;
import net.live.domain.News;
import net.live.domain.NewsAnswer;
import net.live.domain.NewsAnswerRepository;
import net.live.domain.NewsRepository;
import net.live.domain.Question;
import net.live.domain.QuestionRepository;
import net.live.domain.Result;
import net.live.domain.User;

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
	
	
	
	
	//answer
		@GetMapping("{id}/answerForm")
		public String updateAnswerForm(@PathVariable Long newsId, @PathVariable Long id, Model model, HttpSession session) {
			
			
			NewsAnswer newsAnswer = newsAnswerRepository.findById(id).get();
			Result result = valid(session, newsAnswer);
			if(!result.isValid()) {
				model.addAttribute("errorMessage", result.getErrorMessage());
				return "/user/login";
			}
		
			model.addAttribute("newsAnswer", newsAnswer);
			model.addAttribute("newsId", newsId);
			
				return "/news/answerUpdateForm";

		}	
		
		
		//answer
		private Result valid(HttpSession session, NewsAnswer newsAnswer) {
			if(!HttpSessionUtils.isLoginUser(session)) {
				return Result.fail("로그인이 필요합니다.");
			}
			
			User loginUser = HttpSessionUtils.getUserFromSession(session);
			if(!newsAnswer.isSameWriter(loginUser)) {
				return Result.fail("자신이 쓴 글만 수정, 삭제가 가능합니다.");
						}
			
			
			return Result.ok();
		}
		
		
		//answer
		@PutMapping("{id}")
		public String update(@PathVariable Long newsId, @PathVariable Long id, String contents, Model model, HttpSession session) {
			NewsAnswer newsAnswer = newsAnswerRepository.findById(id).get();
			Result result = valid(session, newsAnswer);
			if(!result.isValid()) {
				model.addAttribute("errorMessage", result.getErrorMessage());
				return "/user/login";
			}
			newsAnswer.update(contents);
			newsAnswerRepository.save(newsAnswer); 
			return String.format("redirect:/mainNews/%d", newsId);
		
		}
}

