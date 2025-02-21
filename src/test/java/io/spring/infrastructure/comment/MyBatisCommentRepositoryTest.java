package io.spring.infrastructure.comment;

import io.spring.core.comment.Comment;
import io.spring.core.comment.CommentRepository;
import io.spring.infrastructure.repository.MyBatisCommentRepository;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MybatisTest
@ExtendWith(SpringExtension.class)
@Import({MyBatisCommentRepository.class})
class MyBatisCommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;

    @Test
    void should_create_and_fetch_comment_success() {
        Comment comment = new Comment("content", "123", "456");
        commentRepository.save(comment);

        Optional<Comment> optional = commentRepository.findById("456", comment.getId());
        assertTrue(optional.isPresent());
        assertEquals(optional.get(), comment);
    }
}
