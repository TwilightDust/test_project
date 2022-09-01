package kr.co.ts.springboot.web;

import kr.co.ts.springboot.service.posts.PostsService;
import kr.co.ts.springboot.web.dto.PostsResponseDto;
import kr.co.ts.springboot.web.dto.PostsSaveRequestDto;
import kr.co.ts.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long saveNotice(@RequestBody PostsSaveRequestDto requestDto){

        return postsService.noticeSave(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long updateNotice(@PathVariable Long id,
                             @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.noticeUpdate(id,requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto getNoticeById(@PathVariable Long id){
        return postsService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete( @PathVariable Long id ){
        postsService.delete(id);
        return id;
    }










}
