package com.example.springkadaitodo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.springkadaitodo.entity.ToDo;
import com.example.springkadaitodo.repository.ToDoRepository;

@Service
public class ToDoService {
	//このtoDoRepositoryは空っぽのフィールド
	private final ToDoRepository toDoRepository;
	
	//DIを行う
	public ToDoService(ToDoRepository toDoRepository) {
	//上のtoDoRepositoryはコンストラクタの引数としてのインスタンスなので中身がある
		this.toDoRepository= toDoRepository;
	}
	
	//新規タスク登録
	public void createToDos(Integer id,String title, String priority, String status) {
		//titleの未入力チェック(空欄はNG)
		if(title == null || title.isEmpty()) {
			throw new IllegalArgumentException("タイトルを入力してください");
		}
		//登録用のエンティティを作成
		ToDo toDo = new ToDo();
		toDo.setId(id);
		toDo.setTitle(title);
		toDo.setPriority(priority);
		toDo.setStatus(status);
		
		//登録！！
		toDoRepository.save(toDo);
	}
	
    //ToDoに登録されている内容を取得
    public List<ToDo> getAlltoDos(){
        return toDoRepository.findAll();
    }
}
