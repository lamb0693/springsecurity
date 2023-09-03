package com.example.securitytest.service;

import com.example.securitytest.dto.MemberAuthDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MemberUserDetailService implements UserDetailsService {
    MemberService memberService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberAuthDTO memberAuthDTO = memberService.getMemberAuthDTO(username);

        if(memberAuthDTO ==null) throw(new RuntimeException("member not present" + username));

        return new MemberUserDetail(memberAuthDTO.getUsername(), memberAuthDTO.getPassword());
    }
}
