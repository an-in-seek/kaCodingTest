package com.test.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.test.book.service.SearchHistoryService;
import com.test.user.service.SecurityService;
import com.test.user.service.UserService;
import com.test.user.validator.UserValidator;
import com.test.user.vo.User;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private SearchHistoryService searchHistoryService;
	
	@GetMapping("/registration")
	public String registration(Model model) {
		model.addAttribute("userForm", new User());

		return "registration";
	}

	@PostMapping("/registration")
	public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
		userValidator.validate(userForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return "registration";
		}

		userService.save(userForm);
		securityService.autoLogin(userForm.getUsername(),  userForm.getPasswordConfirm());

		return "redirect:/bookList";
	}

	@GetMapping("/login")
	public String login(Model model, String error, String logout) {

		if (error != null)
			model.addAttribute("error", "아이디 또는 비밀번호를 확인해주세요.");

		if (logout != null)
			model.addAttribute("message", "로그아웃 되었습니다.");

		return "login";
	}

	@RequestMapping({ "/", "/bookList" })
	public ModelAndView bookList(Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchRankingList", searchHistoryService.getSearchRankingList());
		return new ModelAndView("bookList", map);
	}

}
