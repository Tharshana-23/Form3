package com.example.form.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.form.model.User;

import com.example.form.repository.IuserRepo;



@Controller

public class Usercontroller {
	@Autowired
	IuserRepo ifrepo;

	@GetMapping("")
	public String showform(User user) {
		return "form";
		
	}
		@PostMapping("/update")
		public String addForm(@Valid User user, BindingResult result,Model model) {
			if (result.hasErrors()) {
				return "form";
			
			}
			ifrepo.save(user);
			String success_message="Success!!!Your form has been submitted.";
			model.addAttribute("message", success_message);
			return "form";
	}
	
	@GetMapping("/view")
	public String viewtable(Model model) {
		model.addAttribute("user",ifrepo.findAll());
		return "view";
	}
	
	@GetMapping("/remove/{id}")
	public String remove(Model model,@PathVariable("id") Integer id) {
		
		ifrepo.delete(ifrepo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id)));
		//model.addAttribute("user", ifrepo.findAll());
		return"redirect:/view";
	}
	
	@GetMapping("/edit/{id}")
	public String redirectUpdatePage(@PathVariable("id") Integer id,Model model) {
		
		model.addAttribute("user", ifrepo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id)));
		return"edit";}
	
	
	@PostMapping("/update{id}")
	public String redirecttoview(@Valid User user, BindingResult result) {
		
		if (result.hasErrors() &&! result.hasFieldErrors("password") &&! result.hasFieldErrors("username")) {
			return "edit";
			}
		 User u=ifrepo.findById(user.getId())
				 .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + user.getId()));
		 
			u.setDateofbirth(user.getDateofbirth());
			u.setEmail(user.getEmail());
			u.setGender(user.getGender());
			
			u.setMobileno(user.getMobileno());
			u.setName(user.getName());
			
			u.setSurname(user.getSurname());
			
		
		ifrepo.save(u);
				
	return "redirect:/view";
	}
	
	
	// show login page
	@GetMapping("/login")
	public String loginpage(User user) {
		return "login";
	}
	
	
	@PostMapping("/home")
	public String redirect(User user ,Model model) {
		
		if(ifrepo.findByUsernameAndPassword(user.getUsername(), user.getPassword())== null) {
			model.addAttribute("error","Enter valid Password & Username");
			return "login";
			
		}
		model.addAttribute("sucess", (ifrepo.findByUsernameAndPassword(user.getUsername(), user.getPassword()).getName()));
		
		return"home";
	}
	
	// To go Home page
/*	@PostMapping("/home")
	public String redirecttoHome(@ModelAttribute ("password") String password,BindingResult presult,@ModelAttribute ("username") String username, BindingResult result,Model model) {
		
		
	
	
		
		if(ifrepo.findByusername(username)  ==null ) {
			result.rejectValue("username", "error.user", "Enter valid username ");
		return "login";
            
		} else
			if(password != ifrepo.findByusername(username).getPassword()) {
				presult.rejectValue("password", "error.user", "Enter valid password ");
				return "login";
			}
		
		
		User u1=ifrepo.findByusername(username);	
           // model.addAttribute("sucess", u1.getName()); 
    		return "home";
		
		
	/*	if(ifrepo.findByusername(username) !=null && password == ifrepo.findByusername(username).getPassword()) {
				
			//model.addAttribute("sucess", u.getName());
			return "home";
			
				
		}
		throw new IllegalStateException("Neither BindingResult nor plain target object for bean name '" +
				username + "' available as request attribute");
		//model.addAttribute("error", "password is not match");
		//return "login";
		
		//User u1=ifrepo.findByusername(username);
				
		
		//User u2=ifrepo.findBypassword(password);
		/*if(ifrepo.findByusername(username) == null) {
			model.addAttribute("error", "Username is invalid");
			return "login";
		}
		
		else if(password != ifrepo.findByusername(username).getPassword()) {
			model.addAttribute("pwerror", "password is not match");
			return "login";
		}
		
		model.addAttribute("sucess", ifrepo.findByusername(username).getName());
		return "home";
		
		/;*/
	
	
	
}
