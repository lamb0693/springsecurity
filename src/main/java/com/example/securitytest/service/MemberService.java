package com.example.securitytest.service;

import com.example.securitytest.config.MyPasswordEncoder;
import com.example.securitytest.dto.MemberAuthDTO;
import com.example.securitytest.entity.MemberEntity;
import com.example.securitytest.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberService {
    MemberRepository memberRepository;
    MyPasswordEncoder myPasswordEncoder;

    public MemberAuthDTO getMemberAuthDTO(String username){
        Optional<MemberEntity> optional = memberRepository.findById(username);
        MemberAuthDTO memberAuthDTO;
        MemberEntity memberEntity = null;
        memberEntity = optional.orElse(null);

        if(memberEntity!=null){
            memberAuthDTO = new MemberAuthDTO();
            memberAuthDTO.setUsername(memberEntity.getUsername());
            memberAuthDTO.setPassword(memberEntity.getPassword());
            return memberAuthDTO;
        } else {
            throw new RuntimeException("username Not exist") ;
        }
    }

    public void saveMember(){
        MemberEntity memberEntity1 = new MemberEntity();
        memberEntity1.setUsername("ldw");
        memberEntity1.setPassword(myPasswordEncoder.passwordEncoder().encode("0000"));

        MemberEntity memberEntity2 = new MemberEntity();
        memberEntity2.setUsername("pjh");
        memberEntity2.setPassword(myPasswordEncoder.passwordEncoder().encode("0000"));

        memberRepository.save(memberEntity1);
        memberRepository.save(memberEntity2);
    }
}
