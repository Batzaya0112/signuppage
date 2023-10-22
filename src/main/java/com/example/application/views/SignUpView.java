package com.example.application.views;

import com.example.application.data.model.Role;
import com.example.application.data.model.User;
import com.example.application.data.service.SignupService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;
import java.util.logging.Logger;

@PageTitle("Sign Up Page")
@Route(value = "sign-up", layout = MainLayout.class)
@AnonymousAllowed
public class SignUpView extends VerticalLayout {
    Logger logger = Logger.getLogger(SignUpView.class.getName());
    private SignupService signupService;
    @NotEmpty
    TextField firstName = new TextField("First Name");
    @NotEmpty
    TextField lastName = new TextField("Last Name");
    @NotEmpty
    TextField userName = new TextField("Username");
    @NotEmpty
    EmailField email = new EmailField("Email");
    @NotEmpty
    PasswordField newPassword = new PasswordField("Password");
    @NotEmpty
    PasswordField confirmPassword = new PasswordField("Confirm password");
    Button signUpBtn = new Button("Sign Up");
    public SignUpView(SignupService signupService) {

        this.signupService = signupService;
        FormLayout formLayout = new FormLayout();

        formLayout.add(createNameLayout(),
            createUserNameLayout(),
            createMailLayout(),
            createPasswordLayout(),
            createButtonsLayout()
        );

        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2)
        );

        formLayout.setColspan(createUserNameLayout(), 2);
        formLayout.setColspan(createMailLayout(), 2);

        signUpBtn.addClickListener( event -> {
            final User signup = new User();
            signup.setFirstName(firstName.getValue());
            signup.setLastName(lastName.getValue());
            signup.setUserName(userName.getValue());
            signup.setEmail(email.getValue());
            signup.setPassword(confirmPassword.getValue());
            signup.setRoles(Set.of(Role.USER));
            signupService.saveSignUp(signup);
        });

        add(formLayout);
    }

    private Button createButtonsLayout(){
        signUpBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return signUpBtn;
    }
    private Component createNameLayout(){
        return new HorizontalLayout(firstName, lastName);
    }
    private Component createUserNameLayout(){return userName;}
    private Component createMailLayout(){return email;}
    private Component createPasswordLayout(){
        return new HorizontalLayout(newPassword, confirmPassword);
    }
    private  boolean passwordCheck(){
        return this.newPassword.getValue() == this.confirmPassword.getValue();
    }

}
