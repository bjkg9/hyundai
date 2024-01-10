package com.Hyundai.Sso;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	public void configure(WebSecurity web)throws Exception
	{
		web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
	}

	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
         .cors().disable()        
        .formLogin().disable() //기본 로그인 페이지 없애기
        .headers().frameOptions().disable().and();
        http.csrf().ignoringAntMatchers().ignoringRequestMatchers()
        .and();
	}
	
	@Bean
    public PasswordEncoder getPasswordEncoder() {
		
        return new BCryptPasswordEncoder();
    }
	
}
