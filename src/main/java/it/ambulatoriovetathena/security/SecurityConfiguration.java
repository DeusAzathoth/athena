package it.ambulatoriovetathena.security;

import it.ambulatoriovetathena.security.services.MyUserDetailService;
import it.ambulatoriovetathena.views.login.LoginView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_PROCESSING_URL = "/" + LoginView.ROUTE;
    private static final String LOGIN_FAILURE_URL = "/" + LoginView.ROUTE;
    private static final String LOGIN_URL = "/" + LoginView.ROUTE;
    private static final String LOGOUT_SUCCESS_URL = "/" + LoginView.ROUTE;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private MyUserDetailService myUserDetailService;
    /*@Bean
    public MyUserDetailService myUserDetailService() {
        return new MyUserDetailService();
    }
     */

    /*@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }*/
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {

        System.out.println("SecurityConfiguration - DaoAuthenticationProvider");

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(myUserDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public CustomRequestCache requestCache() {
        System.out.println("SecurityConfiguration - CustomRequestCache");
        return new CustomRequestCache();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
        System.out.println("SecurityConfiguration - config");
        authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider());
    }

    /**
     * Require login to access internal pages and configure login form.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Not using Spring CSRF here to be able to use plain HTML for the login page
        http.csrf().disable()

                // Register our CustomRequestCache, that saves unauthorized access attempts, so
                // the user is redirected after login.
                .requestCache().requestCache(new CustomRequestCache())

                // Restrict access to our application.
                .and().authorizeRequests()

                // Allow all flow internal requests.
                .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()

                // Allow all requests by logged in users.
                .anyRequest().authenticated()

                // Configure the login page.
                .and().formLogin().loginPage(LOGIN_URL).permitAll().loginProcessingUrl(LOGIN_PROCESSING_URL)
                .failureUrl(LOGIN_FAILURE_URL)

                // Configure logout
                .and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        // typical logged in user with some privileges
        UserDetails normalUser =
                User.withUsername("user")
                        .password("{noop}password")
                        .roles("User")
                        .build();

        // admin user with all privileges
        UserDetails adminUser =
                User.withUsername("admin")
                        .password("{noop}password")
                        .roles("User", "Admin")
                        .build();

        return new InMemoryUserDetailsManager(normalUser, adminUser);
    }

    /**
     * Allows access to static resources, bypassing Spring security.
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                // Vaadin Flow static resources
                "/VAADIN/**",

                // the standard favicon URI
                "/favicon.ico",

                // the robots exclusion standard
                "/robots.txt",

                // web application manifest
                "/manifest.webmanifest",
                "/sw.js",
                "/offline-page.html",

                // icons and images
                "/icons/**",
                "/images/**",

                // (development mode) static resources
                "/frontend/**",

                // (development mode) webjars
                "/webjars/**",

                // (development mode) H2 debugging console
                "/h2-console/**",

                // (production mode) static resources
                "/frontend-es5/**", "/frontend-es6/**");
    }

}
