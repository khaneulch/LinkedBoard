package com.linkedboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.linkedboard.user.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
    private UserService userService;
    
    @Autowired
    void UserService(UserService userService) {
        this.userService = userService;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        web.ignoring().antMatchers("/content/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        		.antMatchers("/admin/**").hasRole("ADMIN")
        		.antMatchers("/board/**").authenticated()
        		.antMatchers("/user/**").permitAll()
        		.antMatchers("/error").permitAll()
        		.antMatchers("/community/**").permitAll()
        	.and()
        		.formLogin()
        		.loginPage("/user/login")
        		.loginProcessingUrl("/user/loginProc")
        		.successHandler(new LoginSuccessHandler(userService))
        		.failureHandler(new LoginFailureHandler(userService))
        		.permitAll()
        	.and()
        		.logout()
        		.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
        		.logoutSuccessUrl("/user/login")
        		.invalidateHttpSession(true)
        	.and()
        	.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());
        http.headers().frameOptions().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }
}
