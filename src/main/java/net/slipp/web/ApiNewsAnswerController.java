package net.slipp.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.slipp.domain.Answer;
import net.slipp.domain.News;
import net.slipp.domain.NewsAnswer;
import net.slipp.domain.NewsAnswerRepository;
import net.slipp.domain.NewsRepository;
import net.slipp.domain.Question;
import net.slipp.domain.Result;
import net.slipp.domain.User;

@RestController
@RequestMapping("/api/news/{newsId}/newsAnswers")
public class ApiNewsAnswerController {
	@Autowired
	private NewsRepository newsRepository;
	
	@Autowired
	private NewsAnswerRepository newsAnswerRepository;
	
	@PostMapping("")
	public NewsAnswer create(@PathVariable Long newsId, String contents, HttpSession session) { 
		System.out.println(" create");
		if( !HttpSessionUtils.isLoginUser(session)) {
			return null;
		}
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		System.out.println(" create");
		News news = newsRepository.findById(newsId).get(); 
		System.out.println(" create");
		NewsAnswer newsAnswer = new NewsAnswer(loginUser,news, contents);
		news.addAnswer();
		System.out.println(newsAnswerRepository.save(newsAnswer));
		return newsAnswerRepository.save(newsAnswer);
	}
	
	@DeleteMapping("/{id}")
	public Result delete(@PathVariable Long newsId, @PathVariable Long id, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {
			return Result.fail("로그인해야 합니다.");
		}
	
		NewsAnswer newsAnswer  = newsAnswerRepository.findById(id).get();
	User loginUser = HttpSessionUtils.getUserFromSession(session);
	if(!newsAnswer.isSameWriter(loginUser)) {
		return Result.fail("자신의 글만 삭제할 수 있습니다.");
		
	}
	News news = newsRepository.findById(newsId).get();
	news.deleteAnswer();
	newsAnswerRepository.delete(newsAnswerRepository.findById(id).get());

	return Result.ok();
	
	}
	
	
	

	
}
