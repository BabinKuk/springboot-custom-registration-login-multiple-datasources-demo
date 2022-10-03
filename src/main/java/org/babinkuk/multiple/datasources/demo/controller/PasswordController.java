package org.babinkuk.multiple.datasources.demo.controller;

import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.babinkuk.multiple.datasources.demo.password.dao.PasswordDao;
import org.babinkuk.multiple.datasources.demo.password.service.PasswordService;
import org.babinkuk.multiple.datasources.demo.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import net.bytebuddy.utility.RandomString;

@Controller
public class PasswordController {
	
	@Autowired
	private JavaMailSender mailSender;
	 
	@Autowired
	private PasswordService passwordService;
	
	@GetMapping("/forgot_password")
	public String showForgotPasswordForm() {
		return "forgot-password-form";
	}
	
	@PostMapping("/forgot_password")
	public String processForgotPassword(HttpServletRequest request, Model model) {
		
		String email = request.getParameter("email");
		String token = RandomString.make(30);
		
		//System.out.println("email : "+ email + " ; " + token);
		
		try {
			passwordService.updateResetPasswordToken(token, email);
			String resetPasswordLink = getSiteURL(request) + "/reset_password?token=" + token;
			sendEmail(email, resetPasswordLink);
			model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			model.addAttribute("error", "Error while sending email");
		}
		
		return "forgot-password-form";
	}
	
	private static String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}
	
	public void sendEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException {
		
		//System.out.println("sendEmail : " + recipientEmail + " ; " + link);
		MimeMessage message = mailSender.createMimeMessage();              
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		// set your app credentals
		helper.setFrom("nikola.babic@gmail.com", "Nikola Babic");
		helper.setTo(recipientEmail);
		
		String subject = "Here's the link to reset your password";
		
		String content = "<p>Hello,</p>"
						+ "<p>You have requested to reset your password.</p>"
						+ "<p>Click the link below to change your password:</p>"
						+ "<p><a href=\"" + link + "\">Change my password</a></p>"
						+ "<br>"
						+ "<p>Ignore this email if you do remember your password, "
						+ "or you have not made the request.</p>";
		
		helper.setSubject(subject);
		helper.setText(content, true);
		
		mailSender.send(message);
	}
	
	@GetMapping("/reset_password")
	public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
		
		//System.out.println("reset_password");
		
		// create model object to store form data
		PasswordDao password = new PasswordDao();
		password.setToken(token);
		
		User user = passwordService.getByResetPasswordToken(token);
		
		model.addAttribute("password", password);
		//model.addAttribute("token", token);
		
		if (user == null) {
			model.addAttribute("error", "There is no user to with that reset token.");
			return "reset-password-invalid";
		}
		
		return "reset-password-form";
	}
	
	@PostMapping("/reset_password")
	public String processResetPassword(@Valid @ModelAttribute("password") PasswordDao passwordDao, BindingResult result, Model model) {
		
		//System.out.println("processResetPassword : " + passwordDao.toString());
		 
		User user = passwordService.getByResetPasswordToken(passwordDao.getToken());
		System.out.println(user.toString());
		
		if (user == null || user.getUsername() == null || user.getUsername().isEmpty()) {
        	result.rejectValue("token", null, "There is no user to with that reset token.");
        }
        
		//System.out.println(result.toString());
        if (result.hasErrors()) {
			model.addAttribute("password", passwordDao);
			return "reset-password-form";
        }
        
        passwordService.updatePassword(user, passwordDao.getNewPassword());
		
		return "reset-password-confirmation";
	}
}