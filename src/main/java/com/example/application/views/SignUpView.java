package com.example.application.views;

import com.example.application.data.model.Signup;
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
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Sign Up")
@Route(value = "sing up", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class SignUpView extends VerticalLayout {
    Binder<Signup> binder = new BeanValidationBinder<>(Signup.class);
    private final SignupService signupService;
    private Signup signup;
    TextField firstName = new TextField("First Name");
    TextField lastName = new TextField("Last Name");
    TextField userName = new TextField("Username");
    EmailField email = new EmailField("Email");
    PasswordField newPassword = new PasswordField("Password");
    PasswordField confirmPassword = new PasswordField("Confirm password");

    Button signUpBtn = new Button("Sign Up");
    public SignUpView(SignupService signupService) {
        //addClassName("login-view");
        //setSpacing(false);
        //setAlignItems(Alignment.CENTER);
        //setSizeFull();
        this.signupService = signupService;
        FormLayout formLayout = new FormLayout();

        formLayout.add(createNameLayout(),
            createUserNameLayout(),
            createMailLayout(),
            createPasswordLayout(),
            createButtonsLayout()
        );

        this.signup = new Signup();
        signUpBtn.addClickListener(event -> signupService.saveSignUp(this.signup));

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


//    private void signUpSave(){
//        if(binder.isValid()){
//            fireEvent(new SaveEvent(this, binder.getBean()));
//        }
//    }

//    public static abstract class SignUpFormEvent extends ComponentEvent<SignUpView> {
//        private Signup signup;
//
//        protected SignUpFormEvent(SignUpView source, Signup signup) {
//            super(source, false);
//            this.signup = signup;
//        }
//
//        public Signup getSignup() {
//            return signup;
//        }
//    }

//    public static class SaveEvent extends SignUpFormEvent {
//        SaveEvent(SignUpView source, Signup signup) {
//            super(source, signup);
//        }
//    }
//
//    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
//        return addListener(SaveEvent.class, listener);
//    }
}
