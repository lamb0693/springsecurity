package com.example.securitytest.provider;

import com.example.securitytest.config.MyPasswordEncoder;
import com.example.securitytest.dto.MemberAuthDTO;
import com.example.securitytest.service.MemberService;
import com.example.securitytest.service.MemberUserDetail;
import com.example.securitytest.service.MemberUserDetailService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Log4j2
@AllArgsConstructor
public class CustumAuthenticationProvider implements AuthenticationProvider {
    MyPasswordEncoder myPasswordEncoder;
    MemberUserDetailService memberUserDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("############ I am in authenticate@CustomAuthentication Provier");
        String username = (String) authentication.getName();
        String password = (String) authentication.getCredentials();
        log.info("############ authenticate@CustomAuthenticationProvier : " + username + "," + password);

        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));

        UserDetails userDetail = memberUserDetailService.loadUserByUsername(username);
        log.info("############ authenticate@CustomAuthenticationProvier : " + userDetail.toString());

        if( myPasswordEncoder.passwordEncoder().matches( password, userDetail.getPassword() ) ){
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, userDetail.getPassword(), grantedAuthorities);
            //usernamePasswordAuthenticationToken.setAuthenticated(true);
            return usernamePasswordAuthenticationToken;
        } else {
            throw new RuntimeException("인증 실패");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        log.info("############ I am in supports@CustomAuthentication Provier");
        log.info("############ supports@CustomAuthenticationProvier : " + authentication.toString());
        //return false; 원래
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication); // 로그인 시도시 정보가 authentication 으로 주입됨
    }
}
