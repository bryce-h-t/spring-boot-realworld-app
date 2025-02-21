package io.spring.application.tag;

import io.spring.application.TagsQueryService;
import io.spring.core.article.Article;
import io.spring.core.article.ArticleRepository;
import io.spring.infrastructure.repository.MyBatisArticleRepository;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertTrue;


@MybatisTest
@Import({TagsQueryService.class, MyBatisArticleRepository.class})
public class TagsQueryServiceTest {
    @Autowired
    private TagsQueryService tagsQueryService;

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    public void should_get_all_tags() {
        articleRepository.save(new Article("test", "test", "test", new String[]{"java"}, "123"));
        assertTrue(tagsQueryService.allTags().contains("java"));
    }
}
