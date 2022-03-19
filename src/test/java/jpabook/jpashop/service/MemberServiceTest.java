package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("Jinwoo");

        //when
        Long savedId = memberService.join(member);
        Member findMember = memberRepository.findOne(savedId);

        //then
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    void 중복_회원_예외() {
        //given
        Member memberA = new Member();
        memberA.setName("Jinwoo");

        Member memberB = new Member();
        memberB.setName("Jinwoo");

        //when
        memberService.join(memberA);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(memberB));

        /* try catch 방식
        try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원 입니다.");
        }
*/
        //then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

}