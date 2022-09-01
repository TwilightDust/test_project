package kr.co.ts.springboot.domaint.posts;

import kr.co.ts.springboot.domain.posts.Posts;
import kr.co.ts.springboot.domain.posts.PostsRepository;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository repo;

    @After
    public void cleanup(){
        repo.deleteAll();
    }

    @Test
    public void 게시글_로드(){
        //given
        String title ="게시글 테스트";
        String content = "게시글 내용";

        repo.save(Posts.builder().title(title).content(content).author("테스트진행").build());

        //when
        List<Posts> postsList = repo.findAll();

        Posts posts = postsList.get(0);
        Assertions.assertThat(posts.getTitle()).isEqualTo(title);
        Assertions.assertThat(posts.getContent()).isEqualTo(content);

    }
}
