package it.ambulatoriovetathena.views.login;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import it.ambulatoriovetathena.security.CustomRequestCache;
import it.ambulatoriovetathena.views.main.MainView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Tag("sa-login-view")
@Route(value = LoginView.ROUTE)
@PageTitle("Login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    public static final String ROUTE = "login";

    private LoginOverlay login = new LoginOverlay();

    @Autowired
    public LoginView(DaoAuthenticationProvider daoAuthenticationProvider,
                     CustomRequestCache customRequestCache) {
        login.setAction("login");
        login.setOpened(true);
        login.setTitle("Ambulatorio Veterinario Athena");
        login.setDescription("Degenze");
        login.addForgotPasswordListener(forgotPasswordEvent -> {
            System.out.println("Forgot password");
        });
        getElement().appendChild(login.getElement());

        //Login authentication
        login.addLoginListener(loginEvent -> {
           try {
               password();
               // try to authenticate with given credentials, should always return !null or throw an {@link AuthenticationException}
               final Authentication authentication = daoAuthenticationProvider
                       .authenticate(new UsernamePasswordAuthenticationToken(loginEvent.getUsername(),
                               loginEvent.getPassword()));

               // if authentication was successful we will update the security context and redirect to the page requested first
               if(authentication != null ) {
                   login.close();
                   SecurityContextHolder.getContext().setAuthentication(authentication);

                   UI.getCurrent().navigate(customRequestCache.resolveRedirectUrl());

               }
           } catch (AuthenticationException e) {
               e.printStackTrace();
               login.setError(true);
           }
        });
    }

    private void password() {
        System.out.println("Username: user");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println("Password: " + bCryptPasswordEncoder.encode("password"));
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        login.setError(event.getLocation().getQueryParameters().getParameters().containsKey("error"));
    }

}
