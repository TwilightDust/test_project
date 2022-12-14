package kr.co.ts.springboot.web;

import kr.co.ts.springboot.config.auth.LoginUser;
import kr.co.ts.springboot.config.auth.dto.SessionUser;
import kr.co.ts.springboot.service.posts.PostsService;
import kr.co.ts.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.mail.Session;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession session;

    @GetMapping("/")
    public String index(Model model,
                        @LoginUser SessionUser user){
        model.addAttribute("posts",postsService.findAllDesc());

        if( user != null ){
            System.out.println(user.toString());
            model.addAttribute("userName",user.getName());
            model.addAttribute("userEmail",user.getEmail());
        }

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id,
                              Model model){

        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post",dto);

        return "posts-update";

    }



}
