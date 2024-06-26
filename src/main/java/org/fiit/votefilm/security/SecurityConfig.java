package org.fiit.votefilm.security;

import org.fiit.votefilm.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.cors.CorsConfiguration;

/**
 * Configuration of security.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private UserServiceImpl userService;

    @Autowired
    private void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    /**
     * Configuration of password encoder.
     *
     * @return PasswordEncoder
     */
    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configuration of authentication manager.
     * @param authenticationConfiguration AuthenticationConfiguration
     * @return AuthenticationManager
     * @throws Exception
     */
    @Bean
    protected AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configuration of authentication manager.
     * @param authenticationManagerBuilder AuthenticationManagerBuilder
     * @return AuthenticationManagerBuilder
     * @throws Exception
     */
    @Bean
    @Primary
    protected AuthenticationManagerBuilder customAuthenticationManagerBuilder(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder;
    }

    /**
     * Configuration of security filter chain.
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.
                addFilterBefore(new SecurityContextPersistenceFilter(), SecurityContextPersistenceFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer ->
                        httpSecurityCorsConfigurer.configurationSource(request ->
                                new CorsConfiguration().applyPermitDefaultValues())
                )
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/auth/login/"))
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                ).
                authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**").permitAll()
                                .requestMatchers("/voting-setup/**").hasAuthority("VOTING_HOST")
                                .requestMatchers("/voting/enter/").permitAll()
                                .requestMatchers("/voting/my-sessions").hasAuthority(("VOTING_HOST"))
                                .requestMatchers("/voting/**").hasAuthority("COMMON_USER")
                                .requestMatchers("/admin/create-admin-user/").permitAll() // TODO:THIS IS FOR TESTING ONLY
                                .requestMatchers("/admin/create-host-user/").permitAll() // TODO:THIS IS FOR TESTING ONLY
                                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                                .anyRequest().authenticated()
                );

        return http.build();
    }
}
