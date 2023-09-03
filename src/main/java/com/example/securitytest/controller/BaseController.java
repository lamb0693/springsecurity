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

    @GetMapping("/token/token")
    @ResponseBody
    public String token(){
        return "token";
    }

    @GetMapping("/token/createtoken1")
    @ResponseBody
    public String createToken() {
        String token = tokenService.createToken(1, "pjh");
        return token;
    }

    @GetMapping("/token/createtoken10")
    @ResponseBody
    public String createToken10() {
        String token = tokenService.createToken(10, "pjh");
        return token;
    }

    @GetMapping("/token/idfromtoken")
    @ResponseBody
    public String idFromToken(){
        String username;
        try{
            username= tokenService.getIdFromToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwamgiLCJleHAiOjE2OTM3MzI5ODB9.otKnGUQIERIYMdXPZB5y3nllharHypOYK89Xq80IKhs");
        } catch( Exception e ){
            username = e.getMessage();
        }

        return username;
    }

    @GetMapping("/validatetoken")
    @ResponseBody
    public String validateToken(){
        String msg;
        try{
            msg =  tokenService.validate("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwamgiLCJleHAiOjE2OTM3MzI5ODB9.otKnGUQIERIYMdXPZB5y3nllharHypOYK89Xq80IKhs");
        } catch (Exception e){
            msg= e.getMessage();
        }
        log.info(msg);
        return msg;
    }

}
