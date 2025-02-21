package io.spring.infrastructure.favorite;

import io.spring.core.favorite.ArticleFavorite;
import io.spring.core.favorite.ArticleFavoriteRepository;
import io.spring.infrastructure.repository.MyBatisArticleFavoriteRepository;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringJUnitConfig
@MybatisTest
@Import({MyBatisArticleFavoriteRepository.class})
public class MyBatisArticleFavoriteRepositoryTest {
    @Autowired
    private ArticleFavoriteRepository articleFavoriteRepository;

    @Autowired
    private io.spring.infrastructure.mybatis.mapper.ArticleFavoriteMapper articleFavoriteMapper;

    @Test
    public void should_save_and_fetch_articleFavorite_success() {
        ArticleFavorite articleFavorite = new ArticleFavorite("123", "456");
        articleFavoriteRepository.save(articleFavorite);
        assertNotNull(articleFavoriteMapper.find(articleFavorite.getArticleId(), articleFavorite.getUserId()));
    }

    @Test
    public void should_remove_favorite_success() {
        ArticleFavorite articleFavorite = new ArticleFavorite("123", "456");
        articleFavoriteRepository.save(articleFavorite);
        articleFavoriteRepository.remove(articleFavorite);
        assertFalse(articleFavoriteRepository.find("123", "456").isPresent());
    }
}
