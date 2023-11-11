package com.example.application.views;

import com.example.application.data.model.Role;
import com.example.application.data.model.User;
import com.example.application.data.service.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.Set;
import java.util.regex.Pattern;

@PageTitle("Sign Up Page")
@Route(value = "sign-up", layout = MainLayout.class)
@AnonymousAllowed
public class SignUpView extends VerticalLayout {
    private final UserService userService;
    Pattern passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z]).{8}.*$");
    //StringLengthValidator = new StringLengthValidator("Password must be at least 8 characters long",8,null);
    TextField firstName = new TextField("First Name");
    TextField lastName = new TextField("Last Name");
    TextField userName = new TextField("Username");
    EmailField email = new EmailField("Email");
    PasswordField newPassword = new PasswordField("Password");
    PasswordField confirmPassword = new PasswordField("Confirm password");
    Button signUpBtn = new Button("Sign Up");
    public SignUpView(UserService userService) {
        this.userService = userService;
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
            User signup = new User();
            if(checkForm()){
                signup = setForm(signup);
                if(checkPassword()){
                    User result = userService.saveSignUp(signup);
                    if(result != null){
                        Notification notification = Notification.show("Амжилттай бүртгэгдлээ!");
                        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    }
                }else{
                    Notification notification = Notification.show("Нууц үгээ дахин шалгана уу.");
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            }else{
                Notification notification = Notification.show("Бүх талбарыг бөглөнө үү.");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
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
        newPassword.setMinLength(4);
        newPassword.setMaxLength(12);
        newPassword.setPattern(passwordPattern.toString());
        confirmPassword.setMinLength(4);
        confirmPassword.setMaxLength(12);
        confirmPassword.setPattern("^(?=.*[0-9])(?=.*[a-zA-Z]).{4}.*$");
        return new HorizontalLayout(newPassword, confirmPassword);
    }
    private  boolean checkForm(){
        boolean valid = false;
        int count = 0;
        if(this.userName.getValue().isEmpty()){
            System.out.println("username is empty");
            this.userName.setErrorMessage("Нэвтрэх нэрээ заавал оруулна уу.");
            count++;
        }
        if(this.firstName.getValue().isEmpty()){
            System.out.println("firstname is empty");
            this.firstName.setErrorMessage("Өөрийн нэрийг заавал оруулна уу.");
            count++;
        }
        if(this.lastName.getValue().isEmpty()){
            System.out.println("lastname is empty");
            count++;
        }
        if(this.email.getValue().isEmpty()){
            System.out.println("email is empty");
            count++;
        }else if(email.isInvalid()){
            System.out.println("и-мэйлээ шалгана уу.");
            count++;
        }
        if(this.newPassword.getValue().isEmpty()||this.confirmPassword.getValue().isEmpty()){
            System.out.println("password is empty");
            count++;
        }

        if(count == 0) valid = true;
        System.out.println("Count: " + count);
        return valid;
    }
    private boolean checkPassword(){
        boolean valid = true;
        if(!this.passwordPattern.matcher(newPassword.getValue().toString()).matches() ||
           !passwordPattern.matcher(confirmPassword.getValue().toString()).matches()){
            System.out.println("password patter: " + passwordPattern.matcher(newPassword.getValue()).matches());
            //if(passwordPattern.matcher(newPassword.getValue()).matches())
            System.out.println("Нууц үг шаадрлага хангахгүй байна.");
            this.newPassword.setErrorMessage("Нууц үг шаардлага хангахгүй байна.");
            this.confirmPassword.setErrorMessage("Нууц үг шаардлага хангахгүй байна.");
            this.confirmPassword.setHelperText("4-өөс 12 тэмдэгтийн урттай том жижиг тэмдэгт орсон байна.");
            valid = false;
        } else if(!this.newPassword.getValue().equals(this.confirmPassword.getValue())) {
            System.out.println("Нууц үг таарахгүй байна.");
            valid = false;
        }
        return valid;
    }
    private User setForm(User signup){
        signup.setFirstName(firstName.getValue());
        signup.setLastName(lastName.getValue());
        signup.setUserName(userName.getValue());
        signup.setEmail(email.getValue());
        signup.setPassword(confirmPassword.getValue());
        signup.setRoles(Set.of(Role.USER));
        return signup;
    }


}
