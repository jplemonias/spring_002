package com.thymeleaf.TestThymeleaf.controller;

import com.thymeleaf.TestThymeleaf.form.UserForm;
import com.thymeleaf.TestThymeleaf.model.User;
import com.thymeleaf.TestThymeleaf.dao.UserDao;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

	private final UserDao userDoa;

	public MainController(UserDao userDoa) {
		this.userDoa = userDoa;
	}

	@Value("${message.welcome}")
	private String messageWelcom;

	@Value("${message.select}")
	private String messageSelect;

	@Value("${message.error}")
	private String errorMessage;

	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("message", messageWelcom);
		return "index";
	}

	@RequestMapping(value = { "/users" }, method = RequestMethod.GET)
	public String userList(Model model) {
		model.addAttribute("message", messageSelect);
		model.addAttribute("users", userDoa.findAll());
		return "users";
	}

	@RequestMapping("/user/{id}")
	public String displayACharacter(Model model, @PathVariable int id) {

		// User user = userDoa.findById(id);
		User user = userDoa.findAll()
				.stream()
				.filter(x -> id == x.getId())
				.findFirst()
				.orElse(null);
		if (user == null) {
			// throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			return "redirect:/";
		}
		model.addAttribute("user", user);
		return "user";
	}

	@RequestMapping(value = { "/addUser" }, method = RequestMethod.GET)
	public String showAddUserPage(Model model) {

		UserForm UserForm = new UserForm();
		model.addAttribute("userForm", UserForm);

		return "addUser";
	}

	@RequestMapping(value = { "/addUser" }, method = RequestMethod.POST)
	public String saveUser(Model model, @ModelAttribute("UserForm") UserForm UserForm) {

		int id = UserForm.getId();
		String name = UserForm.getName();
		String type = UserForm.getChampionType();
		int hp = UserForm.getHp();

		if (name != null && name.length() > 0 && name != null && name.length() > 0) {
			userDoa.save(new User(id, name, type, hp));
			return "redirect:/user/"+id;
		}

		model.addAttribute("errorMessage", errorMessage);
		return "addUser";
	}

}
