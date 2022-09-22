package com.thymeleaf.TestThymeleaf.controller;

import com.thymeleaf.TestThymeleaf.form.UserForm;
import com.thymeleaf.TestThymeleaf.model.User;
import com.thymeleaf.TestThymeleaf.dao.UserDao;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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

	@Value("${message.error.user4o4}")
	private String userNotFound;

	@Value("${message.error.wtf}")
	private String wtf;

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

		Boolean nameIsValid = name != null && name.length() > 0;
		Boolean typeIsValid = type != null && type.length() > 0;

		if (nameIsValid) {
			if (typeIsValid) {
				userDoa.saveUser(new User(id, name, type, hp));
				return "redirect:/user/"+id;
			}
		}

		model.addAttribute("errorMessage", errorMessage);
		return "addUser";
	}

	@RequestMapping(value = { "/deleteUser/{id}" }, method = RequestMethod.GET)
	public String deletUser(Model model, @PathVariable int id) {
		List users = userDoa.findAll();
		User user = userDoa.findAll().stream()
			.filter(x -> id == x.getId()).findFirst()
			.orElse(null);

		if (user == null) {
			model.addAttribute("errorMessage", userNotFound);
			return "redirect:/users";
		}
		// if(users.indexOf(user) != -1) {
			userDoa.deleteUser(users.indexOf(user));
			// model.addAttribute("errorMessage", userNotFound);
			// model.addAttribute("errorMessage", wtf);
			return "redirect:/users";
			// ...
		// }
		// return "redirect:/users";
	}

	@GetMapping(value = { "/editUser/{idUser}" })
	public String showAddEditPage(Model model, @PathVariable int idUser) {
		
		UserForm UserForm = new UserForm();
		User user = userDoa.findById(idUser);

		UserForm.setId(user.getId());
		UserForm.setName(user.getName());
		UserForm.setChampionType(user.getChampionType());
		UserForm.setHp(user.getHp());

		model.addAttribute("userForm", UserForm);

		return "editUser";
	}

	@PutMapping(value = { "/editUser/{idUser}" })
	public String editUser(Model model, @ModelAttribute("UserForm") UserForm UserForm, @PathVariable int idUser) {
		System.out.println("/editUser/{idUser}");
		List users = userDoa.findAll();
		
		int id = UserForm.getId();
		String name = UserForm.getName();
		String type = UserForm.getChampionType();
		int hp = UserForm.getHp();

		Boolean nameIsValid = name != null && name.length() > 0;
		Boolean typeIsValid = type != null && type.length() > 0;

		if (nameIsValid) {
			if (typeIsValid) {
				for (User user : userDoa.findAll()){
					if (user.getId() == id){
						userDoa.editUser(user, UserForm);
						return "redirect:editUser/"+id;
					}
				}
			}
		}

		model.addAttribute("errorMessage", errorMessage);
		return "redirect:/users";
	}

}
