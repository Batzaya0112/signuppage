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
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
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

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");
    // Other fields omitted
    Binder<User> binder = new Binder<>(User.class);

    public PersonFormView(UserService userService,
                          AuthenticatedUser authenticatedUser,
                          UserRepository userRepository) {
        this.authenticatedUser = authenticatedUser;
        this.userRepository = userRepository;
        this.userService = userService;
        addClassName("person-form");
        add(
                firstName,
                lastName,
                userName,
                email,
                createButtonsLayout());

        initBinder();
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);
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
    public void setBean(User user){
        binder.setBean(user);
    }
    public Optional<User> getBean(){
        return Optional.ofNullable(binder.getBean());
    }
}
