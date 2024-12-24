package com.example.postingapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.postingapp.entity.Post;
import com.example.postingapp.entity.User;
import com.example.postingapp.form.PostEditForm;
import com.example.postingapp.form.PostRegisterForm;
import com.example.postingapp.security.UserDetailsImpl;
import com.example.postingapp.service.PostService;

@Controller
@RequestMapping("/posts")
//をつけることで、各メソッドにおいてルートパスの基準値「https://ドメイン名/posts」までは必ず共通する。
public class PostController {
	//DIコンテナにサービスを登録
	private final PostService postService;
	public PostController(PostService postService) {
		this.postService = postService;
	}
	@GetMapping
	public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,Model model) {
		User user = userDetailsImpl.getUser();
		List<Post> posts = postService.findPostsByUserOrderedByCreatedAtAsc(user);
		model.addAttribute("posts",posts);
		return "posts/index";
	}
	
	@GetMapping("/{id}")
	//@PathVariableをつけると、URLの一部をその引数で割り当てられる。
	public String show(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes, Model model) {
		//@GetMappingのidの部分がname="id"に割り当てられるので、それを使って処理を行うことが出来る。
	
		Optional<Post> optionalPost = postService.findPostById(id);
		//id=0など、存在しない場合の処理
		if(optionalPost.isEmpty()) {
			redirectAttributes.addFlashAttribute("errorMessage","投稿が存在しません。");
			//Flashメッセージは一時的に表示されるメッセージのこと
			
			return "redirect:/posts";
		}
		//Post型に変換することで、エンティティの各フィールドにアクセスすることが出来る。
		Post post = optionalPost.get();
		model.addAttribute("post",post);
		return "posts/show";
	}	
	
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("postRegisterForm", new PostRegisterForm());
		//入力内容とフォームクラスのフィールドを紐づけるために、ビューに向かってフォームクラスのインスタンスを投げている。
		//new　クラス名でインスタンス化、設計図から実際に生み出すことが出来ている。
		return "posts/register";
	}
	
	@PostMapping("/create")
	public String create(@ModelAttribute @Validated PostRegisterForm postRegisterForm, BindingResult bindingResult,
	                     @AuthenticationPrincipal UserDetailsImpl userDetailsImpl, RedirectAttributes redirectAttributes, Model model) {
	    if (bindingResult.hasErrors()) {
	        model.addAttribute("postRegisterForm", postRegisterForm);
	        return "posts/register";
	    }

	    User user = userDetailsImpl.getUser();
	    postService.createPost(postRegisterForm, user); 
	    redirectAttributes.addFlashAttribute("successMessage", "投稿が完了しました");
	    return "redirect:/posts";
	}

	
	@GetMapping("/{id}/edit")
	public String edit(@PathVariable(name="id") Integer id,RedirectAttributes redirectAttributes,
			@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
		Optional<Post> optionalPost = postService.findPostById(id);
		User user = userDetailsImpl.getUser();
		
		if (optionalPost.isEmpty()||!optionalPost.get().getUser().equals(user)) {
			redirectAttributes.addFlashAttribute("errorMessage","不正なアクセスです。");
			//こっちは次のリクエスト終了まで存続するので、リダイレクト後のメッセージの表示に使用される。
			return "redirect:/posts";
					//:が入っていることによって、リダイレクトが行われる。
		}
		
		Post post = optionalPost.get();
		model.addAttribute("post",post);
		model.addAttribute("postEditForm", new PostEditForm(post.getTitle(),post.getContent()));
		//こっちは現在のリクエストの間でのみ情報が保持されるので、データをビューに受け渡すのに使用される。
		return "posts/edit";
	}
	
	@PostMapping("/{id}/update")
	public String update(
			@ModelAttribute @Validated PostEditForm postEditForm,
			//@ModelAttributeはリクエストパラメータ、(URLの末尾ここではid)を引数の対象オブジェクトに与える
			BindingResult bindingResult,
			//バリデーション結果を受け取るやつ
			@PathVariable(name = "id") Integer id,RedirectAttributes redirectAttributes,
			@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			//認証済みのユーザー情報を取得する
			Model model
			//modelはビューをデータに渡すために使用する
			) {
		Optional<Post> optionalPost = postService.findPostById(id);
		//nullの可能性を踏まえてoptional型で対応
		User user = userDetailsImpl.getUser();
		
		if(optionalPost.isEmpty()||!optionalPost.get().getUser().equals(user)) {
			redirectAttributes.addFlashAttribute("errorMessage","不正なアクセスです。");
			return "redirect:/posts";
		}
		
		  Post post = optionalPost.get();
		  if(bindingResult.hasErrors()) {
		  model.addAttribute("post",post);
		  model.addAttribute("postEditForm", postEditForm);
		  return "posts/edit";
		  }
	      
		  postService.updatePost(postEditForm, post);
	      redirectAttributes.addFlashAttribute("successMessage", "投稿を更新しました");
	      return "redirect:/posts/"+id;
	}
	
	@PostMapping("/{id}/delete")
	public String delete(@PathVariable(name = "id") Integer id,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            RedirectAttributes redirectAttributes,
            Model model){
		Optional<Post> optionalPost = postService.findPostById(id);
		User user = userDetailsImpl.getUser();
		
		if(optionalPost.isEmpty()||!optionalPost.get().getUser().equals(user)) {
			redirectAttributes.addFlashAttribute("errorMessage","不正なアクセスです。");
			return "redirect:/posts";
		}
		Post post = optionalPost.get();
		postService.deletePost(post);
		redirectAttributes.addFlashAttribute("successMessage","投稿を削除しました");
		
		return "redirect:/posts";
	}
}


