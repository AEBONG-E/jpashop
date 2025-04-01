package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Rollback(value = false)
    @Test
    void 회원가입_테스트() throws Exception {
        // given
        Member member = Member.builder()
                .name("kim")
                .build();

        // when
        Long savedId = memberService.join(member);

        // then
        em.flush();
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test()
    void 중복_회원_예외_테스트() throws Exception {
        // given
        Member member1 = Member.builder()
                .name("kim1")
                .build();

        Member member2 = Member.builder()
                .name("kim1")
                .build();

        // when
        memberService.join(member1);

        // then
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
    }

}