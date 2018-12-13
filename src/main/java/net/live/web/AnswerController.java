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
import net.live.domain.Question;
import net.live.domain.QuestionRepository;
import net.live.domain.Result;
import net.live.domain.User;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
	
 	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private AnswerRepository answerRepository;
	
	@PostMapping("")
	public String create(@PathVariable Long questionId, String contents, HttpSession session) {
		if( !HttpSessionUtils.isLoginUser(session)) {
			return "/users/loginForm";
		}
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findById(questionId).get(); 
		Answer answer = new Answer(loginUser,question, contents);
		answerRepository.save(answer);
		return String.format("redirect:/questions/%d", questionId);
	}

	
	
	
	//answer
	@GetMapping("/{id}/answerForm")
	public String updateAnswerForm(@PathVariable Long questionId, @PathVariable Long id, Model model, HttpSession session) {
		
		
		Answer answer = answerRepository.findById(id).get();
		Result result = valid(session, answer);
		if(!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "/user/login";
		}
	
		model.addAttribute("answer", answer);
		model.addAttribute("questionId", questionId);
		
			return "/qna/answerUpdateForm";

	}		
	
	//answer
	private Result valid(HttpSession session, Answer answer) {
		if(!HttpSessionUtils.isLoginUser(session)) {
			return Result.fail("로그인이 필요합니다.");
		}
		
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		if(!answer.isSameWriter(loginUser)) {
			return Result.fail("자신이 쓴 글만 수정, 삭제가 가능합니다.");
					}
		
		
		return Result.ok();
	}

	//answer
	@PutMapping("{id}")
	public String update(@PathVariable Long questionId, @PathVariable Long id, String contents, Model model, HttpSession session) {
		Answer answer = answerRepository.findById(id).get();
		Result result = valid(session, answer);
		if(!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "/user/login";
		}
		answer.update(contents);
		answerRepository.save(answer); 
		return String.format("redirect:/questions/%d", questionId);
	
	}
}
