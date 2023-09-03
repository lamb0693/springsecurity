package com.example.securitytest.controller;

import com.example.securitytest.service.MemberService;
import com.example.securitytest.service.TokenService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@AllArgsConstructor
@Log4j2
public class BaseController {
    MemberService memberService;
    TokenService tokenService;

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<String> conTest(){
        return ResponseEntity.ok("Connected");
    }

    @GetMapping("/admin")
    @ResponseBody
    public ResponseEntity<String> conTestAdmin(){
        return ResponseEntity.ok("Admin onnected");
    }

    @GetMapping("/save")
    @ResponseBody
    public ResponseEntity<String> save2Member(){
        memberService.saveMember();

        return ResponseEntity.ok("saved");
    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }

    @GetMapping("/token")
    @ResponseBody
    public String token(){
        return "token";
    }

    @GetMapping("/createtoken")
    @ResponseBody
    public String createToken() {
        String token = tokenService.createToken(1, "pjh");
        return token;
    }

    @GetMapping("/idfromtoken")
    @ResponseBody
    public String idFromToken(){
        String username = tokenService.getIdFromToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwamgiLCJleHAiOjE2OTM3MzI5ODB9.otKnGUQIERIYMdXPZB5y3nllharHypOYK89Xq80IKhs");
        return username;
    }

    @GetMapping("/validatetoken")
    @ResponseBody
    public String validateToken(){
        Map<String, Object> claims =  tokenService.validate("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwamgiLCJleHAiOjE2OTM3MzI5ODB9.otKnGUQIERIYMdXPZB5y3nllharHypOYK89Xq80IKhs");
        log.info(claims);
        return claims.toString();
    }

}
