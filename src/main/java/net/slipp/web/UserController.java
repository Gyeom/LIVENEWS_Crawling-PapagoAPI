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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.slipp.domain.News;
import net.slipp.domain.NewsRepository;
import net.slipp.domain.QuestionRepository;
import net.slipp.domain.User;
import net.slipp.domain.UserRepository;



@Controller
@RequestMapping("/users")
public class UserController {
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private NewsRepository newsRepository;
	
	@GetMapping("/news/{pageNum}")
	public String create(@PathVariable int pageNum, Model model, HttpSession session) {
		System.out.println(session.getAttribute("page"+pageNum));
		if(session.getAttribute("page"+pageNum)==null) {
		try {
			Document doc = Jsoup.connect("https://www.ccn.com/?s=IT TREND").get();
			Elements news = doc.select("div.row").select("div.post-thumbnail > a");
			String contents = "";
			for(int i=((pageNum-1)*4); i<(pageNum*4); i++){
			Element e = news.get(i);
				String link = e.attr("href"); // link
				Document docs = Jsoup.connect(link).get();
				
				String headline = docs.select("header > h1").first().text(); // headline
				String image = docs.select("div.entry-content").get(0).select("div.post-thumbnail > a > img").first().attr("src"); // image
				Elements content = docs.select("div.entry-content").get(0).select("div.entry-content > p");
			
				
				
				for (Element e2 : content) {
					contents += e2.text(); //contents
				}
				System.out.println(link);
				System.out.println(image);
				System.out.println(contents);
				System.out.println(headline);
				
				News newsVo = new News(link, image, contents , headline, pageNum);
				System.out.println(newsVo);
				
				newsRepository.save(newsVo);
				session.setAttribute("page"+pageNum, true);
			}
			
		
			
		}catch (IOException e) { //Jsoup의 connect 부분에서 IOException 오류가 날 수 있으므로 사용한다.{
			e.printStackTrace();
		}
		}
		model.addAttribute("newsList", newsRepository.findByPageNum(pageNum));
//		model.addAttribute("newsList", newsRepository.findAll());
		return "/user/news";
		
	}

	
//	@GetMapping("/news")
//	public String create(Model model) {
//		try {
//			Document doc = Jsoup.connect("https://www.ccn.com/?s=IT TREND").get();
//			Elements news = doc.select("div.row").select("div.post-thumbnail > a");
//			String contents = "";
//			for (Element e : news) {
//				String link = e.attr("href"); // link
//				Document docs = Jsoup.connect(link).get();
//				
//				String headline = docs.select("header > h1").first().text(); // headline
//				String image = docs.select("div.entry-content").get(0).select("div.post-thumbnail > a > img").first().attr("src"); // image
//				Elements content = docs.select("div.entry-content").get(0).select("div.entry-content > p");
//				for (Element e2 : content) {
//					contents += e2.text(); //contents
//				}
//				System.out.println(link);
//				System.out.println(image);
//				System.out.println(contents);
//				System.out.println(headline);
//				
//				News newsVo = new News(link, image, contents , headline);
//				System.out.println(newsVo);
//				newsRepository.save(newsVo);
//			}
//			
//		
//			
//		}catch (IOException e) { //Jsoup의 connect 부분에서 IOException 오류가 날 수 있으므로 사용한다.{
//			e.printStackTrace();
//		}
//		model.addAttribute("newsList", newsRepository.findAll());
//		System.out.println(newsRepository.findAll());
//		return "/user/news";
//		
//	}


	
	
	
	@GetMapping("/board")
	public String index(Model model) {
		model.addAttribute("questions", questionRepository.findAll());
		
		return "/user/board";
	}
	@GetMapping("/loginForm")
	public String loginForm() {
		return "/user/login";
	}
	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session) {
		User user = userRepository.findByUserId(userId);
		if(user==null) {
			System.out.println("Login Faliure!");
			return "redirect:/users/loginForm";
		}
		if(!password.equals(user.getPassword())) {
			System.out.println("Login Faliure!");
			return "redirect:/users/loginForm";
		}
		System.out.println("Login Success");
		session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
		
		System.out.println(session.getAttribute("sessionedUser"));
		return "redirect:/";
	}
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
		return "redirect:/";
	}
	
	
	@GetMapping("/login")
	public String login() {
		return "/user/login";
	}
	@GetMapping("/form")
	public String form() {
		return "/user/form";
	}

	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		if(!sessionedUser.matchId(id)) {
			throw new IllegalStateException("You Can not Access");
		}
		
		
		User user = userRepository.findById(id).get();
		model.addAttribute("user", user);
		return "/user/updateForm";
	}
	

	

	@PostMapping("")
	public String create(User user) {
		
		userRepository.save(user);
		
		return "redirect:/users";
	}
	
	@GetMapping("")
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		
		return "/user/list";
	}
	
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, User updatedUser, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		
		User sessionedUser = HttpSessionUtils.getUserFromSession(session);
		if(!sessionedUser.matchId(id)) {
			throw new IllegalStateException("You Can not Access");
		}
		
		User user = userRepository.findById(id).get();
		user.update(updatedUser);
		userRepository.save(user);
		return "redirect:/users";
	}
}
