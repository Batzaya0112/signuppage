package com.example.application.views;

import com.example.application.data.model.User;
import com.example.application.data.repository.UserRepository;
import com.example.application.data.service.UserService;
import com.example.application.security.AuthenticatedUser;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@PermitAll
@PageTitle("Person form")
@Route(value = "", layout = MainLayout.class)
public class PersonFormView extends FormLayout {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthenticatedUser authenticatedUser;
    private final UserService userService;
    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    TextField userName = new TextField("User name");
    EmailField email = new EmailField("Email");
    String clickBtnName;

    Button save = new Button("Save");
    Button edit = new Button("Edit");
    Button close = new Button("Cancel");

    public PersonFormView(UserService userService,
                          AuthenticatedUser authenticatedUser,
                          UserRepository userRepository) {
        this.authenticatedUser = authenticatedUser;
        this.userRepository = userRepository;
        this.userService = userService;
        this.clickBtnName = close.getText().toString();
        clickBtnListener();
        removeAll();
        textFiedSetReadOnly(true);
        formView();
        initBinder();
    }

    private void initBinder(){
        Optional<UserDetails> u = authenticatedUser.getAutenticatedUser();
        System.out.println("Person username: " + u.get().getUsername());
        User userInfo = userRepository.findByUsername(u.get().getUsername());
        System.out.println("Person userInfo: " + u.get().getUsername());
        this.firstName.setValue(userInfo.getFirstName());
        this.lastName.setValue(userInfo.getLastName());
        this.userName.setValue(userInfo.getUserName());
        this.email.setValue(userInfo.getEmail());
    }
    private void clickBtnListener(){
        close.addClickListener(e -> {
           clickBtnName = close.getText().toString();
           removeAll();
           textFiedSetReadOnly(true);
           formView();
        });
        edit.addClickListener(e -> {
            clickBtnName = edit.getText().toString();
            removeAll();
            textFiedSetReadOnly(false);
            formView();
        });
    }
    private void textFiedSetReadOnly(boolean val){
        firstName.setReadOnly(val);
        lastName.setReadOnly(val);
        userName.setReadOnly(val);
        email.setReadOnly(val);
    }
    private Component createButtonsLayout(String btnName) {

        if(edit.getText().toString().equals(btnName)){
            save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            save.addClickShortcut(Key.ENTER);
            close.addClickShortcut(Key.ESCAPE);
            return new HorizontalLayout(save, close);
        }else if(close.getText().toString().equals(btnName)){
            edit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            return new HorizontalLayout(edit);
        }else {
           return new HorizontalLayout();
        }
    }
    private void formView(){
        addClassName("person-form");
        FormLayout formLayout = new FormLayout();
        formLayout.add(
                this.firstName,
                this.lastName,
                this.userName,
                this.email
        );
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("600px", 2)
        );

        formLayout.getStyle().set("max-width", "75%");
        formLayout.getStyle().set("width", "100%");

        HorizontalLayout horizontalLayout = new HorizontalLayout(formLayout);
        horizontalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        VerticalLayout verticalLayout = new VerticalLayout(horizontalLayout, createButtonsLayout(clickBtnName));
        verticalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        verticalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        add(verticalLayout);
    }
}

