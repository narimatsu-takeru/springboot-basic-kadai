package com.example.springkadaiform.controller;

import org.springframework.core.Conventions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springkadaiform.form.ContactForm;

@Controller
public class ContactFormController {
	@GetMapping("/form")
	public String formContact(Model model) {
		
		// すでにインスタンスが存在する場合は行わない
        if (!model.containsAttribute("contactForm")) {
		//ビューにフォームクラスのインスタンスを渡す
		model.addAttribute("contactForm",new ContactForm());
        }return "contactFormView";
	}


	@PostMapping("/confirm")
	public String confirmContents(RedirectAttributes redirectAttributes,
	@Validated ContactForm form, BindingResult result) {
	//validationチェック
	if(result.hasErrors()) {
	 
	            redirectAttributes.addFlashAttribute("contactForm", form);

	            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX
	                    + Conventions.getVariableName(form), result);

	            return "redirect:/form";
	}
	redirectAttributes.addFlashAttribute("contactForm", form);

    return "confirmView";
	}
}
