package com.amberlight.cloud.svcpost.post.service;

import com.amberlight.cloud.svcpost.post.model.domain.Post;
import com.amberlight.cloud.svcpost.post.repository.elasticsearch.PostElasticRepository;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.search.MultiMatchQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

@Service
public class PostElasticServiceImpl implements PostElasticService {

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
//        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(matchQuery("content", keyword)
//                        .operator(Operator.AND)
//                        .fuzziness(Fuzziness.ONE)
//                        .prefixLength(3)
//                )
//                .build();

//        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(new BoolQueryBuilder()
//                        .must(QueryBuilders.matchQuery("title", keyword)
//                                .operator(Operator.AND).fuzziness(Fuzziness.ONE).prefixLength(3))
//                        .must(QueryBuilders.matchQuery("content", keyword)
//                                .operator(Operator.AND).fuzziness(Fuzziness.ONE).prefixLength(3))
//
//
//                )
//                .build();


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
        return posts;

//        return postRepository.findByTitleOrContentAllIgnoreCase(keyword);
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
