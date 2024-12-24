package com.example.postingapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.postingapp.entity.Post;
import com.example.postingapp.entity.User;
import com.example.postingapp.form.PostEditForm;
import com.example.postingapp.form.PostRegisterForm;
import com.example.postingapp.repository.PostRepository;

@Service
public class PostService {
	//DIコンテナに登録する
	private final PostRepository postRepository;
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	//特定のユーザーに紐づく投稿の一覧を作成日時が古い順で取得する。
	public List<Post> findPostsByUserOrderedByCreatedAtAsc(User user){
		return postRepository.findByUserOrderByCreatedAtAsc(user);
	}
	
	//指定したIdを持つ投稿のみ取得
	public Optional<Post> findPostById(Integer id){
		//Optionalはnullの可能性があるオブジェクトを安全に扱うためのクラス、nullチェックが行える。
		return postRepository.findById(id);
		//JpaRepositoryインターフェースを継承した時点で使えるfindByIdメソッド
	}
	
	@Transactional
	//TransactionalでDBといい感じに関係する機能を使う
	public void createPost(PostRegisterForm postRegisterForm, User user) {
		Post post=new Post();
		
		post.setTitle(postRegisterForm.getTitle());
		post.setContent(postRegisterForm.getContent());
		post.setUser(user);
		//フォームクラスから情報を取得し、セーブする
		
		postRepository.save(post);
	}
	
	//Idが最も大きいものを取得
	public Post findFirstByOrderByIdDesc() {
		return postRepository.findFirstByOrderByIdDesc();
	}
	
	//postsテーブルを更新したい
	@Transactional
	public void updatePost(PostEditForm postEditForm, Post post) {
		post.setTitle(postEditForm.getTitle());
		post.setContent(postEditForm.getContent());
		
		postRepository.save(post);
	}
	//postsテーブルを削除したい
	@Transactional
	public void deletePost(Post post) {
		postRepository.delete(post);
	}

}
