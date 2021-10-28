package com.backend.backend.configs;

import com.backend.backend.filter.CorsFilter;
import com.backend.backend.filter.CustomAuthenticationFilter;
import com.backend.backend.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

        private final UserDetailsService userDetailsService;
        private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");// muda url de baseUrl/login

        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()); // libera o cors da aplicação
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/api/login/**", "/api/token/refresh/**").permitAll();
        http.authorizeRequests().antMatchers(GET, "/api/user/**","/server/list/**").hasAnyAuthority("ROLE_USER"); // Role user só pode fazer get no endpoint 'api/user/o que vier depois'
        http.authorizeRequests().antMatchers(POST, "/server/save").hasAnyAuthority("ROLE_ADMIN"); // Role admin só pode fazer post no endpoint 'api/user/save o que vier depois'
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    //TODO: Não bloqueia certas pastas  no security
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/style/**", "/css/**", "/js/**", "/server/list/**"
                ,"/server/image/**", "/server/ping/**", "/server/save", "/api/save", "/api/list");
        //web.ignoring().antMatchers("/style/**", "/css/**", "/js/**");
    }
}


// refs:
// https://www.youtube.com/watch?v=mYKf4pufQWA&t=4912s&ab_channel=GetArrays
// https://github.dev/getarrays/userservice
// https://www.youtube.com/watch?v=1zCvBCqmUuo&t=7094s&ab_channel=GetArrays