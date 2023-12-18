package com.example.tomo_face.repository;

import com.example.member.entity.QMember;
import com.example.tomo_face.entity.Profile;
import com.example.tomo_face.entity.QProfile;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Repository
@Transactional
public class ProfileQueryRepository {
    private final EntityManager em;
    private final JPAQueryFactory query;
    private QProfile profile = QProfile.profile;


    public ProfileQueryRepository(EntityManager em)
    {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public List<Profile> findTomoFaceProfiles() {
        return query.select(profile)
                .from(profile)
                .where(tomoFace())
                .fetch();
    }

    BooleanExpression tomoFace()
    {
        return profile.tomoFace.eq(true);
    }

}
