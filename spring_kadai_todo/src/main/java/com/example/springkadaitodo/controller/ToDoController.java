package com.example.springkadaitodo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.springkadaitodo.entity.ToDo;
import com.example.springkadaitodo.service.ToDoService;


@Controller
public class ToDoController {
	private final ToDoService toDoService;
	
	public ToDoController(ToDoService toDoService){
		this.toDoService = toDoService;
	}
	
	@GetMapping("/toDoList")
	public  String toDoList(Model model) {
		
		List<ToDo> todos = toDoService.getAlltoDos();
		
		model.addAttribute("todos",todos);
		return "todoView";
	}
}
