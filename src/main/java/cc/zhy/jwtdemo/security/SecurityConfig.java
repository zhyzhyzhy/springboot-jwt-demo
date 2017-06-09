package cc.zhy.jwtdemo.security;

import cc.zhy.jwtdemo.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.Http401AuthenticationEntryPoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by zhy on 6/8/17.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String KEY = "secret";

    private StudentService studentService;
    private PasswordEncoder encoder;

    @Autowired
    public SecurityConfig(StudentService studentService, PasswordEncoder encoder) {
        this.studentService = studentService;
        this.encoder = encoder;
    }




    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(studentService).passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().disable()
                .authorizeRequests()
                .antMatchers("/auth/**","/user").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(new Http401AuthenticationEntryPoint("login"));
        http.addFilterBefore(new JwtFilter(studentService), UsernamePasswordAuthenticationFilter.class);
        http.requestCache().disable();
    }

}
