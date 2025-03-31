package jpabook.jpashop.repository;

import jpabook.jpashop.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Transactional
    @Rollback(value = false)
    @Test
    void saveTest() {
        //given
        Member memberA = Member.builder()
                .username("memberA")
                .build();
        //when
        Long savedId = memberRepository.save(memberA);
        Member findMember = memberRepository.find(savedId);

        //then
        assertThat(findMember.getId()).isEqualTo(memberA.getId());
        assertThat(findMember.getUsername()).isEqualTo(memberA.getUsername());
        assertThat(findMember).isEqualTo(memberA);
    }



}