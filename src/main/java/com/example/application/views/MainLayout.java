package com.example.application.views;

import com.example.application.security.AuthenticatedUser;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * The main view is a top-level placeholder for other views.
 */

public class MainLayout extends AppLayout {
    @Autowired
    private AuthenticatedUser authenticatedUser;

    public MainLayout(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
        createHeader();
    }

    private void createHeader() {
        Optional<UserDetails> u = authenticatedUser.getAutenticatedUser();
        if (u.isPresent()) {
            H1 logo = new H1("Main Layout");
            logo.addClassNames(
                    LumoUtility.FontSize.LARGE,
                    LumoUtility.Margin.MEDIUM);

            Text username = new Text("Hello, " + u.get().getUsername());
            Button logout = new Button("Log out ", e -> authenticatedUser.logout());

            var header = new HorizontalLayout(new DrawerToggle(), logo, username, logout);

            header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
            header.expand(logo);
            header.setWidthFull();
            header.addClassNames(
                    LumoUtility.Padding.Vertical.NONE,
                    LumoUtility.Padding.Horizontal.MEDIUM);

            addToNavbar(header);

            addToDrawer(new VerticalLayout(
                    new RouterLink("Person form", PersonFormView.class),
                    new RouterLink("File Upload", FileUploadView.class),
                    new RouterLink("Files List", FilesListView.class)));
        } else {
            addToDrawer(new VerticalLayout(
                    new RouterLink("Login", LoginView.class),
                    new RouterLink("Sign up", SignUpView.class)));
        }
    }
}
