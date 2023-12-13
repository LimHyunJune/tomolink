package com.example.friend_search.repository;

import com.example.friend_search.entity.FriendSearchComments;
import com.example.friend_search.entity.QFriendSearchComments;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@Transactional
public class FriendSearchCommentQueryRepository {
    private final EntityManager em;
    private final JPAQueryFactory query;
    private final QFriendSearchComments friendSearchComments = QFriendSearchComments.friendSearchComments;

    public FriendSearchCommentQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public List<FriendSearchComments> findByPostId(Long postId, Long memberId){
        List<FriendSearchComments> visibleComments = query
                .select(friendSearchComments)
                .from(friendSearchComments)
                .where(postIdEq(postId).and(isSecretFalse().or(isSecretTrue().and(memberIdEq(memberId))))).fetch();
        visibleComments.forEach(comment-> comment.setIsVisible(true));

        log.info("VISIBLE COMMENTS : {}", visibleComments);

        List<FriendSearchComments> inVisibleComments = query
                .select(friendSearchComments)
                .from(friendSearchComments)
                .where(postIdEq(postId).and(isSecretTrue().and(memberIdEq(memberId).not()))).fetch();
        inVisibleComments.forEach(comment-> comment.setIsVisible(false));

        log.info("INVISIBLE COMMENTS : {}", inVisibleComments);

        List<FriendSearchComments> joined = new ArrayList<>();
        joined.addAll(visibleComments);
        joined.addAll(inVisibleComments);

        log.info("JOINED : {}", joined);
        return joined;
    }

    public BooleanExpression postIdEq(Long id)
    {
        return friendSearchComments.postId.eq(id);
    }

    public BooleanExpression isSecretTrue()
    {
        return friendSearchComments.isSecret.eq(true);
    }

    public BooleanExpression isSecretFalse()
    {
        return friendSearchComments.isSecret.eq(false);
    }

    public BooleanExpression memberIdEq(Long id)
    {
        return friendSearchComments.memberId.eq(id);
    }
}
