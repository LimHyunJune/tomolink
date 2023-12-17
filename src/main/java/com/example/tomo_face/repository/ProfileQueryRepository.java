package com.example.tomo_face.repository;

import com.example.member.entity.QMember;
import com.example.tomo_face.entity.Profile;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
@Transactional
public class ProfileQueryRepository {
    private final EntityManager em;
    private final JPAQueryFactory query;
    private final QMember member = QMember.member;

    public ProfileQueryRepository(EntityManager em)
    {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }



}
