package it.ambulatoriovetathena.views.impostazioni;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import it.ambulatoriovetathena.security.domains.Role;
import it.ambulatoriovetathena.security.domains.User;
import it.ambulatoriovetathena.security.services.RoleService;
import it.ambulatoriovetathena.security.services.UserService;

import java.util.Set;


public class UserDialog extends Dialog {

    private FormLayout formLayout;
    private Binder<User> binder;
    // Fields
    private TextField username = new TextField();
    private TextField name = new TextField();
    private TextField lastname = new TextField();
    private TextField email = new TextField();
    private PasswordField passwordField = new PasswordField();
    private PasswordField confirmPwd = new PasswordField();

    private User user;
    private UserService userService;
    private RoleService roleService;
    private Grid<User> userGrid;

    public UserDialog(User user,
                      UserService userService,
                      RoleService roleService,
                      Grid<User> userGrid) {

        // Injection
        this.user = user;
        this.userService = userService;
        this.roleService = roleService;
        this.userGrid = userGrid;

        // Dialog
        this.setWidth("800px");

        formLayout = new FormLayout();
        binder = new Binder<>();

        // Roles
        CheckboxGroup<String> roles = new CheckboxGroup<>();
        //roles.setLabel("Ruoli");
        roles.setItems("admin", "user");
        roles.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
        roles.addValueChangeListener(checkboxGroupSetComponentValueChangeEvent -> {
            System.out.println("Changed value");
            Set<String> selected = checkboxGroupSetComponentValueChangeEvent.getValue();
            for (String value : selected) {
                if (value.equals("admin")) {
                    roles.select("user");
                }
            }
        });
        //add(roles);

        // Button
        Button save = new Button("Salva");
        Button cancel = new Button("Annulla");

        formLayout.addFormItem(username, "Username");
        formLayout.addFormItem(name, "Nome");
        formLayout.addFormItem(lastname, "Cognome");
        formLayout.addFormItem(email, "Email");
        formLayout.addFormItem(passwordField, "Password");
        formLayout.addFormItem(confirmPwd, "Conferma la password");
        formLayout.addFormItem(roles, "Ruoli");

        // Binder
        binder.forField(username)
                .withValidator(new StringLengthValidator("Hai bisogno di un username!",
                        1, null))
                .bind(User::getUsername, User::setUsername);
        binder.forField(name)
                .withValidator(new StringLengthValidator("Ti sei dimenticato il nome!",
                        1, null))
                .bind(User::getName, User::setName);
        binder.forField(lastname)
                .withValidator(new StringLengthValidator("Ti sei dimenticato il cognome!",
                        1, null))
                .bind(User::getLastname, User::setLastname);
        binder.forField(email)
                .withValidator(new EmailValidator("Indirizzo mail non corretto!"))
                .bind(User::getEmail, User::setEmail);
        binder.bind(passwordField, User::getPassword, User::setPassword);
        /*
        binder.forField(confirmPwd)
                .withValidator((Validator<? super String>) (value, context) -> {
                    if (value.equals(passwordField.getValue())) {
                        System.out.println("Password coincidono!");
                        return ValidationResult.ok();
                    } else {
                        System.out.println("Le password non coincidono!");
                        return ValidationResult.error("Le password non coincidono!");
                    }
                });
        binder.forField(roles)
                .withValidator((Validator<? super Set<String>>) (value, context) -> {
                    if (!value.isEmpty()) {
                        System.out.println("C'è almeno un ruolo");
                        return ValidationResult.ok();
                    } else {
                        System.out.println("Nessun ruolo selezionato!");
                        return ValidationResult.error("Nessun ruolo selezionato!");
                    }
        });
        */

        // Action for buttons
        save.addClickListener(buttonClickEvent -> {
            System.out.println("Saving new user...");
            if (binder.writeBeanIfValid(user)) {
                System.out.println("Validate bean...");
                if (passwordField.getValue().equals(confirmPwd.getValue())
                    && !passwordField.isEmpty()) {
                    System.out.println("Password coincide...");
                    Set<String> rolesSet = roles.getValue();
                    if (!rolesSet.isEmpty()) {
                        for (String role : rolesSet) {
                            Role r = new Role(role);
                            roleService.saveRole(r);
                            user.getRoles().add(r);
                        }
                        try {
                            userService.saveUser(user);
                            userGrid.setItems(userService.findAllUser());
                            this.close();
                        } catch (Exception e) {
                            e.getStackTrace();
                            System.out.println("Errore nel salvataggio del nuovo utente");
                        }
                    } else {
                        Notification.show("Non hai selezionato nessun ruolo!");
                    }
                } else {
                    Notification.show("Controlla la password!!");
                }
            } else {
                Notification.show("Controlla i dati del nuovo utente!");
            }
        });
        cancel.addClickListener(buttonClickEvent -> {
            this.close();
        });
        formLayout.add(new HorizontalLayout(save, cancel));

        this.add(formLayout);

        binder.readBean(user);

    }

    public void editMode() {

        passwordField.setVisible(false);
        confirmPwd.setVisible(false);

    }

}
