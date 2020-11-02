package com.amberlight.blog.svcpost.post.service;

import com.amberlight.blog.struct.log4j2.CustomMessage;
import com.amberlight.blog.struct.log4j2.LogLevel;
import com.amberlight.blog.svcpost.post.model.domain.Post;
import com.amberlight.blog.svcpost.post.repository.elasticsearch.PostElasticRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

@Service
public class PostElasticServiceImpl implements PostElasticService {

    private static final Logger logger = LogManager.getLogger(PostElasticServiceImpl.class);

    private final String POSTS_INDEX_COORDINATES = "posts";

    @Autowired
    private PostElasticRepository postRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticTemplate;

    @Override
    public Post savePostWithId(Post post) {
        if (post.getId() == null) throw new IllegalArgumentException("post id cannot be null");
        final Post postToSave = Post.builder()
                .id(post.getId())
                .content(post.getContent())
                .userId(post.getUserId())
                .title(post.getTitle())
                .createdDate(post.getCreatedDate())
                .modifiedDate(post.getModifiedDate())
                .build();
        return postRepository.save(postToSave);
    }

    @Override
    public List<Post> findByTitleOrContentAllIgnoreCase(String keyword) {
        MultiMatchQueryBuilder fuzzyMmQueryBuilder = multiMatchQuery(keyword, "title", "content")
                                                        .operator(Operator.AND)
                                                        .fuzziness(Fuzziness.ONE)
                                                        .prefixLength(3);
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(fuzzyMmQueryBuilder).build();
        final SearchHits<Post> postsSearchHits = elasticTemplate.search(
                searchQuery, Post.class, IndexCoordinates.of(POSTS_INDEX_COORDINATES)
        );
        List<Post> posts = new ArrayList<>();
        postsSearchHits.forEach(hit -> {
            posts.add(hit.getContent());
        });
        logger.log(LogLevel.BUSINESS,
                new CustomMessage(4, String.format("Searched posts by '%s'. Results found: %d", keyword, posts.size())));
        return posts;
    }

    @Override
    public List<Post> findAllByUserId(Long userId) {

        return postRepository.findAllByUserId(userId);
    }

    @Override
    public void deleteById(String postId) {
        postRepository.deleteById(postId);
    }
}
