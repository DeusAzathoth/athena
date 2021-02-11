package it.ambulatoriovetathena.views.impostazioni;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import it.ambulatoriovetathena.security.domains.User;
import it.ambulatoriovetathena.security.services.UserService;

public class PasswordDialog extends Dialog {

    private User user;
    private UserService userService;

    //Fields
    private PasswordField passwordField = new PasswordField("Nuova Password");
    private PasswordField confirmPassword = new PasswordField("Conferma la nuova password");

    public PasswordDialog(User item, UserService userService) {

        this.user = item;
        this.userService = userService;

        VerticalLayout body = new VerticalLayout();
        Span message = new Span("Inserisci la nuova password per l'utente "
                + user.getUsername());
        Button confirmButton = new Button("Conferma", event -> {
            if (!passwordField.isEmpty() &&
                passwordField.getValue().equals(confirmPassword.getValue())) {
                item.setPassword(passwordField.getValue());
                userService.saveUser(item);
            }
        });
        Button cancelButton = new Button("Annulla", event -> {
           this.close();
        });
        body.add(message,
                passwordField, confirmPassword,
                new HorizontalLayout(confirmButton, cancelButton));

        add(body);

    }
}
