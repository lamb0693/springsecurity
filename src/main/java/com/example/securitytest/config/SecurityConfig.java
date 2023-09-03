package com.example.securitytest.config;

import com.example.securitytest.filter.TokenCheckFilter;
import com.example.securitytest.provider.CustumAuthenticationProvider;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
@Log4j2
public class SecurityConfig {

    CustumAuthenticationProvider custumAuthenticationProvider;
    MyPasswordEncoder myPasswordEncoder;

    @Bean
    @Order(3)
    SecurityFilterChain httpBasicFilterChain(HttpSecurity http) throws Exception {
        log.info("####httpBasicFilterChain");
        http.authorizeHttpRequests((request) -> {
            request.requestMatchers("/save").permitAll()
                    .anyRequest().authenticated();
        });
        http.httpBasic(Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);
        http.authenticationProvider(custumAuthenticationProvider);

        return http.build();
    }

    @Bean
    @Order(2)
    SecurityFilterChain formLoginFilterChain1(HttpSecurity http) throws Exception {
        log.info("####formLoginFilterChain");
        http.securityMatcher(AntPathRequestMatcher.antMatcher("/"));
        http.authorizeHttpRequests((request) -> {
            request.requestMatchers("/").authenticated();
        });
        http.formLogin((form)->{
            form.loginPage("/loginForm").permitAll();
        });
        http.authenticationProvider(custumAuthenticationProvider);

        return http.build();
    }

    @Bean
    @Order(1)
    SecurityFilterChain tokenFilterChain2(HttpSecurity http) throws Exception {
        log.info("####tokenFilterChain2");
        http.securityMatcher(AntPathRequestMatcher.antMatcher("/token/**"));
        http.authorizeHttpRequests((request) -> {
            request.requestMatchers("/token/**").authenticated();
        })
        .addFilterBefore(tokenCheckFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails userDetails = User.builder().username("ldw").password(myPasswordEncoder.passwordEncoder().encode("0000")).authorities("USER").build();
        UserDetails userDetails2 = User.builder().username("pjh").password(myPasswordEncoder.passwordEncoder().encode("0000")).authorities("ADMIN").build();

        return new InMemoryUserDetailsManager(userDetails, userDetails2);
    }

    private TokenCheckFilter tokenCheckFilter(){
        return new TokenCheckFilter();
    }
}
