package com.neu.info7205.todo.security;

import com.neu.info7205.todo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        final String[] AUTH_WHITELIST = {
                "/swagger-ui/**",
                "/v3/api-docs",
        };

        // Set unauthorized requests exception handler
        http = http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    ex.getMessage()
                            );
                        }
                )
                .and();
        http.csrf()
                .disable()
                .authorizeRequests()
                //.antMatchers("/v1/user", "/healthz","/v1/user/token")
                .antMatchers(HttpMethod.GET, "/todo/v1/abc", "/healthz123",
                        "/docs/**", "/v3/api-docs/*", "/swagger-ui", "/swagger-ui/",
                        "/swagger-ui.html")
                .permitAll()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers(HttpMethod.POST, "/todo/v1/user").permitAll()
                .antMatchers(HttpMethod.GET, "/todo/v1/user/verify/**").permitAll()
                .antMatchers(HttpMethod.GET, "/todo/v1/user/verify/changeEmail").permitAll()
                .antMatchers(HttpMethod.POST, "/todo/v1/user/resend/email/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic();
        //.and().exceptionHandling().accessDeniedHandler();
        //http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


    }
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new UserService();
//    }
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        final String[] AUTH_WHITELIST = {
//                "/swagger-ui/**",
//                "/v3/api-docs",
//        };
//
//        http = http
//                .exceptionHandling()
//                .authenticationEntryPoint(
//                        (request, response, ex) -> {
//                            response.sendError(
//                                    HttpServletResponse.SC_UNAUTHORIZED,
//                                    ex.getMessage()
//                            );
//                        }
//                )
//                .and();
//        http
//                .authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/todo/v1/healthz","/todo/v1/user/token",
//                        "/docs/**","/v3/api-docs/*","/swagger-ui","/swagger-ui/","/swagger-ui.html")
//                .permitAll()
//                .antMatchers(AUTH_WHITELIST).permitAll()
//                .antMatchers(HttpMethod.POST, "/todo/v1/user").permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .httpBasic();
//
////        http.headers().frameOptions().sameOrigin();
//        http.authenticationProvider(authenticationProvider());
//        return http.build();
//    }
//
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(
//            AuthenticationConfiguration authConfig) throws Exception {
//        return authConfig.getAuthenticationManager();
//    }
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//
//        authProvider.setUserDetailsService(userDetailsService());
//        authProvider.setPasswordEncoder(passwordEncoder());
//
//        return authProvider;
//    }
}
