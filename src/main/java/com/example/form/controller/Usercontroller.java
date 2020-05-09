package com.example.form.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
		if (result.hasErrors()) {
			return "edit";}
		
		ifrepo.save(user);
				
	return "redirect:/view";
	}
	
}