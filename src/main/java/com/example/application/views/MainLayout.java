package com.example.application.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

/**
 * The main view is a top-level placeholder for other views.
 */

public class MainLayout extends AppLayout {
    //private final SecurityService securityService;
    Button LoginBtn = new Button("Login");
    Button SignUpBtn = new Button("Sign Up");

    public MainLayout(){
//        this.securityService = securityService;
//        createHeader();
        FormLayout formLayout = new FormLayout();
        formLayout.add(createButtonsLayout());
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2)
        );
        formLayout.setColspan(createButtonsLayout(), 2);
        addToDrawer(formLayout);


    }

//    private void createHeader() {
//        H1 logo = new H1("Main Layout");
//        logo.addClassNames(
//                LumoUtility.FontSize.LARGE,
//                LumoUtility.Margin.MEDIUM);
//
//        String u = securityService.getAuthenticatedUser().getUsername();
//        Button logout = new Button("Log out " + u, e -> securityService.logout());
//
//        var header = new HorizontalLayout(new DrawerToggle(), logo, logout);
//
//        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
//        header.expand(logo);
//        header.setWidthFull();
//        header.addClassNames(
//                LumoUtility.Padding.Vertical.NONE,
//                LumoUtility.Padding.Horizontal.MEDIUM);
//
//        addToNavbar(header);
//
//    }
    private Component createButtonsLayout(){
        LoginBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        SignUpBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return new HorizontalLayout(LoginBtn, SignUpBtn);
    }
}
