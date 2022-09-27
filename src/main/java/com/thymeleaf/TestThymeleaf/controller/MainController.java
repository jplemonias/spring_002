package com.thymeleaf.TestThymeleaf.controller;

import com.thymeleaf.TestThymeleaf.form.UserForm;
import com.thymeleaf.TestThymeleaf.model.User;
import com.thymeleaf.TestThymeleaf.dao.UserDao;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.client.RestTemplate;


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

	@Value("${api.rest.port}")
	private String apiPort;

	final String URL = "http://localhost:6066/";
	final String URL_USER = URL+"users";

	RestTemplate restTemplate = new RestTemplate();


	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("message", messageWelcom);
		return "index";
	}

	@RequestMapping(value = { "/users" }, method = RequestMethod.GET)
	public String userList(Model model) {
		User[] users = restTemplate.getForObject(URL_USER, User[].class);
		model.addAttribute("message", messageSelect);
		model.addAttribute("users", users);
		return "users";
	}

	@GetMapping("/users/{id}")
	public String displayACharacter(Model model, @PathVariable int id) {


		User user = restTemplate.getForObject(URL_USER+"/"+id, User.class);
		
		// User user = userDoa.findAll()
		// 		.stream()
		// 		.filter(x -> id == x.getId())
		// 		.findFirst()
		// 		.orElse(null);

		if (user == null) {
			// throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			return "redirect:/";
		}

		model.addAttribute("user", user);
		return "user";
	}

	@GetMapping(value = { "/users/add" })
	public String showAddUserPage(Model model) {
		UserForm UserForm = new UserForm();
		model.addAttribute("userForm", UserForm);
		return "addUser";
	}

	@PostMapping(value = { "/users/add" })
	public String saveUser(Model model, @ModelAttribute("UserForm") UserForm UserForm) {



		int id = UserForm.getId();
		String name = UserForm.getName();
		String type = UserForm.getChampionType();
		int hp = UserForm.getHp();

		Boolean nameIsValid = name != null && name.length() > 0;
		Boolean typeIsValid = type != null && type.length() > 0;

		if (nameIsValid) {
			if (typeIsValid) {
				User newUser = new User(id, name, type, hp);
				System.out.println("--------------------");
				System.out.println(id);
				System.out.println(name);
				System.out.println(type);
				System.out.println(hp);
				System.out.println(newUser);
				System.out.println("--------------------");


				System.out.println(restTemplate.postForLocation(URL_USER+"/add", newUser));
				
				// userDoa.saveUser(new User(id, name, type, hp));
				return "redirect:/users/"+id;
			}
		}

		model.addAttribute("errorMessage", errorMessage);
		return "addUser";
	}

	@GetMapping(value = { "/users/edit/{idUser}" })
	public String showAddEditPage(Model model, @PathVariable int idUser) {
		
		UserForm UserForm = new UserForm();
		User user = userDoa.findById(idUser);

		UserForm.setId(user.getId());
		UserForm.setName(user.getName());
		UserForm.setChampionType(user.getChampionType());
		UserForm.setHp(user.getHp());

		// System.out.println(UserForm.getId());
		// System.out.println(UserForm.getName());
		// System.out.println(UserForm.getChampionType());
		// System.out.println(UserForm.getHp());

		model.addAttribute("userForm", UserForm);

		return "editUser";
	}

	@PutMapping(value = { "/users/edit/{idUser}" })
	public String editUser(Model model, @ModelAttribute("UserForm") UserForm UserForm, @PathVariable int idUser) {

		// System.out.println(UserForm.getId());
		// System.out.println(UserForm.getName());
		// System.out.println(UserForm.getChampionType());
		// System.out.println(UserForm.getHp());

		List<User> users = userDoa.findAll();
		
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
						return "redirect:/users/edit/"+id;
					}
				}
			}
		}

		model.addAttribute("errorMessage", errorMessage);
		return "redirect:/users";
	}

	@DeleteMapping(value = { "/users/delete/{id}" })
	public String deletUser(Model model, @PathVariable int id) {
		// List<User> users = userDoa.findAll();
		User user = userDoa.findAll().stream()
			.filter(x -> id == x.getId()).findFirst()
			.orElse(null);

		if (user == null) {
			model.addAttribute("errorMessage", userNotFound);
			return "redirect:/users";
		}
		// if(users.indexOf(user) != -1) {
			// userDoa.deleteUser(users.indexOf(user));
			restTemplate.delete(URL_USER + "/delete/" + id);
			// model.addAttribute("errorMessage", userNotFound);
			// model.addAttribute("errorMessage", wtf);
			return "redirect:/users";
			// ...
		// }
		// return "redirect:/users";
	}

}
