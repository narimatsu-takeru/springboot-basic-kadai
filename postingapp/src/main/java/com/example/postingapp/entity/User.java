package com.example.postingapp.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
	//一つのユーザーは一つのロールだが、一つのロールは多数のユーザーなので@ManyToOneが必要
	@ManyToOne
	//外部キーのカラム名指定は@JoinColumn、フィールドは相手のエンティティのクラス型で指定する。
	@JoinColumn(name="role_id")
	private Role role;
	
	@Column(name="enabled")
	private Boolean enabled;
	
	//ローワーキャメル(繋げる単語を大文字で)にする
	/*insertable そのカラムにインサートできるか updateble　そのカラムを更新できるか。 
	 　デフォルトはtrueでfalseにすると、デフォルト値が自動で挿入される。(勝手に)*/
	@Column(name="created_at",insertable = false,updatable = false)
	private Timestamp createdAt;
	
	@Column(name="updated_at" ,insertable = false,updatable = false)
	private Timestamp updatedAt;

}
