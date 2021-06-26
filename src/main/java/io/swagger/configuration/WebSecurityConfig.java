package io.swagger.configuration;

import io.swagger.annotations.ApiKeyAuthDefinition;
import io.swagger.annotations.SecurityDefinition;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.filter.JwtTokenFilter;
import io.swagger.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SwaggerDefinition(securityDefinition = @SecurityDefinition(apiKeyAuthDefinitions = {@ApiKeyAuthDefinition(key = "X-Auth-Token",
        in = ApiKeyAuthDefinition.ApiKeyLocation.HEADER, name = "X-Auth-Token")}))
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class); // add the filter

        http.authorizeRequests()
                .antMatchers("/login")
                .permitAll()
                .antMatchers("/swagger-ui/**/**")
                .permitAll()
                .antMatchers("/h2-console/**/**")
                .permitAll()
                .antMatchers("/swagger-resources/**")
                .permitAll()
                .antMatchers("/swagger-ui.html")
                .permitAll()
                .antMatchers("/api-docs")
                .permitAll()
                .anyRequest().authenticated();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**/**")
                .antMatchers(HttpMethod.POST, "/Login")
                .antMatchers(HttpMethod.POST, "/api/Login");
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}