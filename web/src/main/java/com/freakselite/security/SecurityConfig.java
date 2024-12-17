package com.freakselite.security;

import com.freakselite.util.PageMappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // == fields ==
    private CustomUserDetailsService userDetailsService;

    // == constructors ==
    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // == beans ==
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf
                .csrfTokenRepository(csrfTokenRepository())
        );

        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers(PageMappings.LOGIN,
                        PageMappings.HOME,
                        PageMappings.NEWS,
                        PageMappings.CONCERTS,
                        PageMappings.CONTACT,
                        PageMappings.GALLERY,
                        PageMappings.MUSIC,
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/files/**"
                ).permitAll()
                .requestMatchers(PageMappings.ADMIN_PANEL,
                        PageMappings.ADMIN_PANEL_ADD_POST,
                        PageMappings.ADMIN_PANEL_ADD_CONCERT,
                        PageMappings.ADMIN_PANEL_BAND_SETTINGS,
                        PageMappings.ADMIN_PANEL_BAND_SETTINGS + "/delete",
                        PageMappings.ADMIN_PANEl_BAND_ARRANGEMENT,
                        PageMappings.ADMIN_PANEL +"/{id}/edytuj-członka-zespołu",
                        PageMappings.ADMIN_PANEL +"/{id}/edytuj-post",
                        PageMappings.ADMIN_PANEL +"/{id}/edytuj-wydarzenie",
                        PageMappings.ADMIN_PANEL_BAND_MEMBER,
                        PageMappings.ADMIN_PANEL_LINKS,
                        PageMappings.CONCERTS + "/delete",
                        PageMappings.NEWS + "/delete"
                ).hasRole("ADMIN")
        ).formLogin((login) -> login
                .loginPage("/" + PageMappings.LOGIN)
                .defaultSuccessUrl("/" + PageMappings.ADMIN_PANEL)
                .loginProcessingUrl("/" + PageMappings.LOGIN)
                .failureUrl("/" + PageMappings.LOGIN + "?error=true")
                .permitAll()
        ).logout((logout) -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl(PageMappings.HOME)
                .permitAll()
        );

        return http.build();
    }


    // == public methods ==
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
