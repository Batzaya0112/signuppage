package com.example.application.views;

import com.example.application.data.model.Role;
import com.example.application.data.model.User;
import com.example.application.data.repository.UserRepository;
import com.example.application.data.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
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
    private final UserRepository userRepository;

    Pattern passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z]).{4}.*$");
    String usernamePattern = "^[a-zA-Z0-9_-]{3,15}$";
    String personalNamePattern = "^[a-zA-Z]{1,15}$";

    TextField firstName = new TextField("First Name");
    TextField lastName = new TextField("Last Name");
    TextField userName = new TextField("Username");
    EmailField email = new EmailField("Email");
    PasswordField newPassword = new PasswordField("Password");
    PasswordField confirmPassword = new PasswordField("Confirm password");
    Button signUpBtn = new Button("Sign Up");
    public SignUpView(UserService userService,
                      UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userService = userService;

        signUpBtn.addClickListener( event -> {
            User signup = new User();
            if(checkForm() && checkUserInfo()){
                    if(checkPassword()){
                        signup = setForm(signup);
                        User result = userService.saveSignUp(signup);
                        if(result != null){
                            notificationShow("Амжилттай бүртгэгдлээ!", 1);
                            removeAll();
                            successView();
                        }
                    }else{
                        notificationShow("Нууц үгээ дахин шалгана уу.", 2);
                    }

            }else{
                notificationShow("Шаардлагын дагуу мэдээллээ оруулна уу.", 2);
            }
        });
        signUpFormView();
    }

    private  boolean checkForm(){
        boolean valid = false;
        int count = 0;
        if(this.userName.getValue().isEmpty()){
            System.out.println("username is empty");
            this.userName.setErrorMessage("Нэвтрэх нэрээ заавал оруулна уу.");
            this.userName.setInvalid(true);
            count++;
        }else{
            this.userName.setInvalid(false);
        }
        if(this.firstName.getValue().isEmpty()){
            System.out.println("firstname is empty");
            this.firstName.setErrorMessage("Өөрийн нэрийг заавал оруулна уу.");
            this.firstName.setInvalid(true);
            count++;
        }else{
            this.firstName.setInvalid(false);
        }
        if(this.lastName.getValue().isEmpty()){
            System.out.println("lastname is empty");
            this.lastName.setErrorMessage("Овог нэрээ заавал оруулна уу.");
            this.lastName.setInvalid(true);
            count++;
        }else{
            this.lastName.setInvalid(false);
        }
        if(this.email.getValue().isEmpty()){
            System.out.println("email is empty");
            this.email.setErrorMessage("Имэйл хаягаа заавал оруулна уу.");
            this.email.setInvalid(true);
            count++;
        }else{
            this.email.setInvalid(false);
        }

        if(this.newPassword.getValue().isEmpty()){
            System.out.println("password1 is empty");
            this.newPassword.setErrorMessage("Нууц үгээ оруулна уу.");
            this.newPassword.setInvalid(true);
            count++;
        }else{
            this.newPassword.setInvalid(false);
        }
        if(this.confirmPassword.getValue().isEmpty()){
            System.out.println("password2 is empty");
            this.confirmPassword.setErrorMessage("Нууц үгээ оруулна уу.");
            this.confirmPassword.setInvalid(true);
            count++;
        }else{
            this.confirmPassword.setInvalid(false);
        }

        if(count == 0) valid = true;

        this.firstName.addValueChangeListener(event -> {
            if(this.firstName.isInvalid()){
                this.firstName.setErrorMessage("Зөвхөн том жижиг үсэг ашиглана уу.");
                this.firstName.setInvalid(true);
            }else{
                this.firstName.setInvalid(false);
            }
        });
        this.lastName.addValueChangeListener(event -> {
            if(this.lastName.isInvalid()){
                this.lastName.setErrorMessage("Зөвхан том жижиг үсэг ашиглана уу.");
                this.lastName.setInvalid(true);
            }else{
                this.lastName.setInvalid(false);
            }
        });
        this.userName.addValueChangeListener(event -> {
            if(this.userName.isInvalid()){
                this.userName.setErrorMessage("Өөр нэр оруулна уу. 3-аас 15 тэмдэгт агуулсан үсэг, тоо, доогуур зураас агуулсан байж болно.");
                this.userName.setInvalid(true);
            }else{
                this.userName.setInvalid(false);
            }
        });
        this.email.addValueChangeListener(event -> {
            if(this.email.isInvalid() && !this.email.getValue().isEmpty()){
                System.out.println("и-мэйлээ шалгана уу.");
                this.email.setErrorMessage("Имэйл хаягаа шалгана уу.");
                this.email.setInvalid(true);
            }else{
                this.email.setInvalid(false);
            }
        });
        System.out.println("Count: " + count);
        return valid;
    }
    private boolean checkPassword(){
        boolean valid = true;
        boolean passCheck = false;
        if(!this.passwordPattern.matcher(this.newPassword.getValue().toString()).matches()
           || !this.passwordPattern.matcher(this.confirmPassword.getValue().toString()).matches()){

            System.out.println("Нууц үг шаадрлага хангахгүй байна.");

            this.newPassword.setErrorMessage("Нууц үг шаардлага хангахгүй байна.");
            this.confirmPassword.setErrorMessage("Нууц үг шаардлага хангахгүй байна.");
            this.newPassword.setHelperText("4-өөс дээш тэмдэгтийн урттай том жижиг тусгай тэмдэгт орсон байна.");
            this.confirmPassword.setHelperText("4-өөс дээш тэмдэгтийн урттай том жижиг тусгай тэмдэгт орсон байна.");
            this.newPassword.setInvalid(true);
            this.confirmPassword.setInvalid(true);
            valid = false;
        } else {
            this.newPassword.setInvalid(false);
            this.confirmPassword.setInvalid(false);
            passCheck = true;
        }
        if(!this.newPassword.getValue().equals(this.confirmPassword.getValue()) && passCheck) {
            System.out.println("Нууц үг таарахгүй байна." + this.passwordPattern.toString());
            this.confirmPassword.setErrorMessage("Нууц үг таарахгүй байна.");
            this.confirmPassword.setInvalid(true);
            valid = false;
        }else{
            this.confirmPassword.setInvalid(false);
        }
        return valid;
    }
    private boolean checkUserInfo(){
        User userInfo = userRepository.findByUsername(this.userName.getValue());
        boolean valid = true;
        if(userInfo != null){
            if(this.userName.getValue().equals(userInfo.getUserName().toString())){
                System.out.println("Хэрэглэгчийн нэр бүртгэлтэй байна. Өөр нэр оруулна уу.");
                this.userName.setErrorMessage("Хэрэглэгчийн нэр бүртгэлтэй байна. Өөр нэр оруулна уу.");
                this.userName.setInvalid(true);
                valid = false;
            }else{
                this.userName.setInvalid(false);
            }
            if(userService.checkEmail(this.email.getValue().toString())){
                System.out.println("Имэйл хаяг бүртгэлтэй байна. Өөр имэйл хаяг оруулна уу.");
                this.email.setErrorMessage("Имэйл хаяг бүртгэлтэй байна. Өөр имэйл хаяг оруулна уу.");
                this.email.setInvalid(true);
                valid = false;
            }else{
                this.email.setInvalid(false);
            }
        }

        return valid;
    }
    private boolean checkAddValueChangeListener(){
        boolean valid = true;
        return valid;
    }
    private User setForm(User signup){
        signup.setFirstName(this.firstName.getValue());
        signup.setLastName(this.lastName.getValue());
        signup.setUserName(this.userName.getValue());
        signup.setEmail(this.email.getValue());
        signup.setPassword(this.confirmPassword.getValue());
        signup.setRoles(Set.of(Role.USER));
        return signup;
    }

    private void signUpFormView(){
        FormLayout formLayout = new FormLayout();
        formLayout.add(
                this.firstName,
                this.lastName,
                this.userName,
                this.email,
                this.newPassword,
                this.confirmPassword
        );
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2)
        );
        formLayout.setColspan(this.userName, 2);
        formLayout.setColspan(this.email, 2);
        formLayout.getStyle().set("max-width", "75%");
        formLayout.getStyle().set("width", "100%");

        HorizontalLayout horizontalLayout = new HorizontalLayout(formLayout);
        horizontalLayout.setAlignItems(Alignment.CENTER);
        horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        this.signUpBtn.setWidth("40%");
        VerticalLayout verticalLayout = new VerticalLayout(horizontalLayout, this.signUpBtn);
        verticalLayout.setAlignItems(Alignment.CENTER);
        verticalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        verticalLayout.getStyle().set("height", "100vh");

        add(verticalLayout);
    }
    private void successView(){
        VerticalLayout layout = new VerticalLayout();
        H1 heading = new H1("Амжилттай бүртгэгдлээ!");
        heading.getStyle().set("color", "green");
        heading.getStyle().set("margin", "0");
        heading.getStyle().set("font-size", "1em");
        layout.add(heading);
        layout.setAlignItems(Alignment.CENTER);
        layout.setSpacing(true);
        layout.getStyle().set("background-color", "#f0f0f0");
        layout.getStyle().set("padding", "20px");
        add(layout);
    }
    private void notificationShow(String message, int id ){
        Notification notification = Notification.show(message);

        switch (id){
            case 1:
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                break;
            case 2:
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                break;
        }
    }
}
