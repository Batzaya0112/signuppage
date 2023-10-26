package com.example.application.views;

import com.example.application.security.AuthenticatedUser;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;

/**
 * The main view is a top-level placeholder for other views.
 */

public class MainLayout extends AppLayout {
    private final AuthenticatedUser authenticatedUser;
    Button LoginBtn = new Button("Login");
    Button SignUpBtn = new Button("Sign Up");

    public MainLayout(AuthenticatedUser authenticatedUser){
        this.authenticatedUser = authenticatedUser;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Main Layout");
        logo.addClassNames(
                LumoUtility.FontSize.LARGE,
                LumoUtility.Margin.MEDIUM);

        //String u = String.valueOf(authenticatedUser.get());
        Button logout = new Button("Log out ", e -> authenticatedUser.logout());

        var header = new HorizontalLayout(new DrawerToggle(), logo, logout);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidthFull();
        header.addClassNames(
                LumoUtility.Padding.Vertical.NONE,
                LumoUtility.Padding.Horizontal.MEDIUM);
        if (authenticatedUser.get().isPresent())  addToNavbar(header);

    }
    private void createDrawer(){
        if (authenticatedUser.get().isPresent()) {
            addToDrawer(new VerticalLayout(
                    new RouterLink("Sign up", SignUpView.class)
            ));
        }
        else {
            addToDrawer(new VerticalLayout(
                    new RouterLink("Login", LoginView.class),
                    new RouterLink("Sign up", SignUpView.class)
            ));
        }
    }
}
