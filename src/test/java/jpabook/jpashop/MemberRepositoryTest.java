package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    void 사용자_저장_찾기() {
        //given
        Member member = new Member();
        member.setUsername("memberA");

        //when
        Long memberId = memberRepository.save(member);
        Member findMember = memberRepository.find(memberId);

        //then
        assertThat(findMember.getId()).isEqualTo(memberId);
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
    }

}