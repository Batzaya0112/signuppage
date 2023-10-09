package com.example.application.views;

import com.example.application.data.model.Signup;
import com.example.application.data.repository.SignUpRepository;
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
import com.vaadin.flow.router.RouteAlias;

import java.util.logging.Level;
import java.util.logging.Logger;

@PageTitle("Sign Up")
@Route(value = "sing up", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class SignUpView extends VerticalLayout {
    Logger logger = Logger.getLogger(SignUpView.class.getName());
    //Binder<Signup> binder = new BeanValidationBinder<>(Signup.class);
    private SignupService signupService;
    private SignUpRepository signUpRepository;
    private Signup signup;
    TextField firstName = new TextField("First Name");
    TextField lastName = new TextField("Last Name");
    TextField userName = new TextField("Username");
    EmailField email = new EmailField("Email");
    PasswordField newPassword = new PasswordField("Password");
    PasswordField confirmPassword = new PasswordField("Confirm password");
    Button signUpBtn = new Button("Sign Up");
    public SignUpView(SignupService signupService) {

        this.signupService = signupService;
        this.signup = new Signup();
        FormLayout formLayout = new FormLayout();

        formLayout.add(createNameLayout(),
            createUserNameLayout(),
            createMailLayout(),
            createPasswordLayout(),
            createButtonsLayout()
        );

        signUpBtn.addClickListener( event -> {
            logger.log(Level.INFO, "signup button =========>" + firstName.getValue());
            this.signup.setFirstName(firstName.getValue());
            this.signup.setLastName(lastName.getValue());
            this.signup.setUserName(userName.getValue());
            this.signup.setEmail(email.getValue());
            this.signup.setPassword(confirmPassword.getValue());
            signupService.saveSignUp(this.signup);
        });

        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2)
        );
        formLayout.setColspan(createUserNameLayout(), 2);
        formLayout.setColspan(createMailLayout(), 2);
        add(formLayout);
    }

    private Button createButtonsLayout(){
        signUpBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        //signUpBtn.addClickListener(event -> signUpSave());
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
