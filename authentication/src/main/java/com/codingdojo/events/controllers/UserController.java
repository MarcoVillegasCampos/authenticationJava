package com.codingdojo.events.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.codingdojo.events.models.User;
import com.codingdojo.events.services.UserService;

public class UserController {
	
	 private final UserService userService;
	    
	    public UserController(UserService userService) {
	        this.userService = userService;
	    }
	    
	    @RequestMapping(value="/registration")
	    public String registerForm(@ModelAttribute("user") User user) {
	        return "registrationPage.jsp";
	    }
	    @RequestMapping("/login")
	    public String login() {
	        return "loginPage.jsp";
	    }
	    
	    @RequestMapping(value="/registration", method=RequestMethod.POST)
	    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
	    	if (result.hasErrors()) {
	    		return "registrationPage.jsp";
	    		
	    	}else {
	    		User usr= userService.registerUser(user);
	    		session.setAttribute("userId", usr.getId());
	    		return "redirect:/home";
	    		
	    	}
	    }
	    
	    @RequestMapping(value="/login", method=RequestMethod.POST)
	    public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession session) {
	    	boolean isAuthenticated = userService.authenticateUser(email, password);
	    	if(isAuthenticated) {
	    		User usr=userService.findByEmail(email);
	    		session.setAttribute("userId", usr.getId());
	    		return "redirect:/home";
	    		
	    	}else {
	    		model.addAttribute("error", "Invalid Credentials, please try again");
	    		return "loginPage.jsp";
	    		
	    	}
	    	
	    	
	    	
	    }
	    
	    @RequestMapping("/home")
	    public String home(HttpSession session, Model model) {
	        Long userId= (Long)session.getAttribute("userId");
	        User usr= userService.findUserById(userId);
	        model.addAttribute("user", usr);
	        return "homePage.jsp";
	        
	    }
	    
	    @RequestMapping("/logout")
	    public String logout(HttpSession session) {
	       session.invalidate();
	       
	       return "redirect:/login";
	       
	    }

}
