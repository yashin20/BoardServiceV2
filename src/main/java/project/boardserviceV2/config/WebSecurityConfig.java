package project.boardserviceV2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;


    // PasswordEncoder Bean 등록 - password 암호화 (방식 - BCryptPasswordEncoder)
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


/*
    // password 아무런 암호화도 걸지 않음.
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
*/

    // WebSecurityCustomizer Bean 등록 - 정적 resources 접근을 위함
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 정적 리소스가 위치한 파일의 보안 처리를 무시 (누구든 접근 가능)
        return (web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()));
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 요청되는 모든 URL 요청을 허용
        http
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers("/", "/member/login", "/member/new", "/post/**").permitAll()
                                .requestMatchers("/member/private/**").authenticated()
                                .requestMatchers("/api/comments/**").authenticated()
                                .anyRequest().authenticated()
                )
                .formLogin((form) ->
                        form
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .loginPage("/member/login")
                                .loginProcessingUrl("/login")
                                .failureHandler(customAuthenticationFailureHandler)
                                .defaultSuccessUrl("/", true)
                                .permitAll()
                )
                .userDetailsService(customUserDetailsService)
                .logout(logout ->
                        logout
                                .logoutUrl("/logout") //로그아웃 처리 URL
                                .logoutSuccessUrl("/") //로그아웃 성공 후 리다이렉트 할 URL
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .permitAll()
                )
                .csrf(csrf ->
                        csrf
                                .ignoringRequestMatchers("/api/**") // /api/** 경로에 대한 CSRF 보호를 비활성화
                )
        ;

        return http.build();
    }


}
