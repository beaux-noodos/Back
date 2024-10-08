package com.example.templet.security;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import com.example.templet.model.enums.Role;
import com.example.templet.repository.UserAuthRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private final AuthenticationFilter jwtAuthenticationFilter;
  private final AuthEntryPointJwt unauthorizedHandler;
  private final UserAuthRepository userAuthRepository;
  private final JwtUtils jwtUtils;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .cors(Customizer.withDefaults())
        .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
        .sessionManagement(
            manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            rq ->
                rq.requestMatchers(OPTIONS, "/**") // todo: endpoint
                    .permitAll()
                    .requestMatchers(GET, "/ping")
                    .permitAll()
                    .requestMatchers(GET, "/whoami")
                    .authenticated()
                    .requestMatchers(GET, "/pdf")
                    .authenticated()
                    .requestMatchers("/users")
                    .permitAll()
                    .requestMatchers(
                        new UserOfUserMatcher(
                            userAuthRepository,
                            jwtUtils,
                            GET,
                            "/users/*",
                            List.of(Role.MANAGER.name())))
                    .authenticated()
                    // --- --- GET --- ---
                    .requestMatchers(GET, "/users/*/chat")
                    .authenticated()
                    .requestMatchers(GET, "/users/*/technicalSolution/*/chat")
                    .authenticated()
                    .requestMatchers(GET, "/technicalSolution/{id}/prompts")
                    .authenticated()
                    .requestMatchers(GET, "users/*/solve/projects") // todo: fini
                    .authenticated()
                    .requestMatchers(GET, "users/*/invested/projects") // todo: fini
                    .authenticated()
                    .requestMatchers(GET, "users/*/notification") // todo: endpoint
                    .authenticated()
                    .requestMatchers(GET, "/users/*/pictures")
                    .authenticated()
                    .requestMatchers(
                        new UserOfUserMatcher(
                            userAuthRepository,
                            jwtUtils,
                            GET,
                            "/users/*/projectSessions",
                            List.of(Role.MANAGER.name())))
                    .authenticated()
                    .requestMatchers(GET, "/projects")
                    .permitAll()
                    .requestMatchers(GET, "/projects/*")
                    .authenticated()
                    .requestMatchers(GET, "/projects/*/react")
                    .authenticated()
                    .requestMatchers(GET, "/projects/*/projectSessions")
                    .permitAll()
                    .requestMatchers(GET, "/projects/*/projectSessions/*")
                    .authenticated()
                    .requestMatchers(GET, "/projects/*/projectSessions/*/react")
                    .authenticated()
                    .requestMatchers(GET, "/locations")
                    .permitAll()
                    .requestMatchers(GET, "/locations/*")
                    .authenticated()
                    .requestMatchers(GET, "/locations/*/react")
                    .authenticated()
                    .requestMatchers(GET, "/categories")
                    .permitAll()

                    // --- --- PUT --- ---
                    .requestMatchers(PUT, "/users/*/projects/*/projectSessions/*/react")
                    .authenticated()
                    .requestMatchers(PUT, "/technicalSolution/*/chat")
                    .authenticated()
                    .requestMatchers(
                        new UserOfUserMatcher(
                            userAuthRepository,
                            jwtUtils,
                            PUT,
                            "/users/*",
                            List.of(Role.MANAGER.name())))
                    .authenticated()
                    .requestMatchers(PUT, "users/*/locations/*/react")
                    .authenticated()
                    .requestMatchers(PUT, "/users/*/pictures")
                    .hasAuthority(Role.MANAGER.name())
                    .requestMatchers(
                        new UserOfUserMatcher(
                            userAuthRepository,
                            jwtUtils,
                            PUT,
                            "/users/*/projects/*/follow/*",
                            List.of(Role.MANAGER.name()))) // todo: fini
                    .authenticated()
                    .requestMatchers(PUT, "users/*/projects/*/react")
                    .authenticated()
                    .requestMatchers(
                        new UserOfUserMatcher(
                            userAuthRepository,
                            jwtUtils,
                            PUT,
                            "/projects/*/pictures",
                            List.of(Role.MANAGER.name()))) // todo: fini
                    .authenticated()
                    .requestMatchers(PUT, "/projects/*/projectSessions/*")
                    .authenticated()
                    .requestMatchers(PUT, "/locations/*")
                    .authenticated()
                    .requestMatchers(PUT, "/projects/*")
                    .authenticated()

                    // --- --- POST --- ---
                    .requestMatchers(POST, "/signup")
                    .permitAll()
                    .requestMatchers(POST, "/signin")
                    .permitAll()

                    // --- --- DELETE --- ---

                    .anyRequest()
                    .denyAll())
        .authenticationProvider(authenticationProvider())
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedHeader("*");
    configuration.addAllowedMethod("*");
    configuration.addAllowedOrigin("*");
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  private UserDetailsService userDetailsService() {
    return new UserDetailsService() {
      @Override
      public UserDetails loadUserByUsername(String subject) throws UsernameNotFoundException {
        return userAuthRepository
            .findByUserMail(subject)
            .orElseThrow(() -> new UsernameNotFoundException(subject));
      }
    };
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(userDetailsService());
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    return daoAuthenticationProvider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
      throws Exception {
    return configuration.getAuthenticationManager();
  }
}
