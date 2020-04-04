package com.qianxu.sunbest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SercuityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userServiceImpl")
    UserDetailsService userService;

    /**
     * 密码编码
     * @return
     */
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/design/**","/windows/**").hasRole("user")
                .anyRequest().permitAll()   //其余都可以访问
                .and()
                .formLogin()
                .loginPage("/toLogin")   //登陆页面
                .loginProcessingUrl("/login") //表单提交处
                .usernameParameter("email") //username参数
                .and()
                .csrf().disable();
        
        //记住我功能 默认两周
        http.rememberMe().rememberMeParameter("remember");
        //登出功能
        http.logout().logoutSuccessUrl("/");
        //允许iframe
        http.headers().frameOptions().disable();
    }

    /**
     * 角色继承
     * 注意换行符
     * @return
     */
    @Bean
    RoleHierarchy roleHierarchy(){
        RoleHierarchyImpl roleHierarchy=new RoleHierarchyImpl();
        String hierarchy="ROLE_admin > ROLE_user \n ROLE_user > ROLE_visitor";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }
}
