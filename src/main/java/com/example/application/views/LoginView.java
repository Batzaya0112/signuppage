package com.example.application.views;

import com.example.application.security.AuthenticatedUser;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.internal.RouteUtil;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.PermitAll;


@PermitAll
@Route(value = "Login", layout = MainLayout.class)
@PageTitle("Login Page")
//@RouteAlias(value = "", layout = MainLayout.class)
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final AuthenticatedUser authenticatedUser;

    private final LoginForm login = new LoginForm();
    public LoginView(AuthenticatedUser authenticatedUser){
        this.authenticatedUser = authenticatedUser;
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        login.setAction(RouteUtil.getRoutePath(VaadinService.getCurrent().getContext(), getClass()));

        add(login);
    }
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent){
        if (authenticatedUser.get().isPresent()) {
            beforeEnterEvent.forwardTo("");
        }

        login.setError(beforeEnterEvent.getLocation().getQueryParameters().getParameters().containsKey("error"));
    }
}
