package com.example.postingapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name ="roles")
@Data
public class Role {
	@Id
	//主キーにしたいフィールドは@GeneratedValueをつけることで主キー指定できる。
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Columnはフィールドにマッピングされるカラム名を指定している。
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name")
	private String name;

}
