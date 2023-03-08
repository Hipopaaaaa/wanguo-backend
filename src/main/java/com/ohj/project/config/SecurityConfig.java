package com.ohj.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    //注入UserDtailsService的实现类
    @Autowired
    protected UserDetailsService userDetailsService;

  	//自定义登陆页面和认证路径
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //自定义自己编写的登陆页面
        http.formLogin()
                .defaultSuccessUrl("/doc.html").permitAll()
                .and().authorizeRequests()
                .antMatchers("/user/register","/user/login","/user/logout","/user/delet","/user/update","/user/judgement","/user/delete","/user/delete/one","/user/list","/user/list/page").permitAll()      //设置哪些路径可以直接访问
                .and().csrf().disable();    //关闭crsf防护

    }
}