package it.ambulatoriovetathena.views.impostazioni;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import it.ambulatoriovetathena.security.domains.Role;
import it.ambulatoriovetathena.security.domains.User;
import it.ambulatoriovetathena.security.services.RoleService;
import it.ambulatoriovetathena.security.services.UserService;

import java.util.Set;

public class UserListView extends VerticalLayout {

    private UserService userService;
    private RoleService roleService;

    private Grid<User> userGrid;

    public UserListView(UserService userService,
                        RoleService roleService) {

        // Injection
        this.userService = userService;
        this.roleService = roleService;

        this.setWidthFull();

        setId("userlist-view");

        // Grid users
        userGrid = new Grid<>();
        userGrid.setItems(userService.findAllUser());

        userGrid.addColumn(User::getUsername)
                .setHeader("Username");
        userGrid.addColumn(User::getLastname)
                .setHeader("Cognome");
        userGrid.addColumn(User::getName)
                .setHeader("Nome");
        userGrid.addColumn(User::getEmail)
                .setHeader("Email");
        userGrid.addColumn(TemplateRenderer.<User>of(
                "<div>[[item.roles]]</div>")
                .withProperty("roles", user -> {
                    Set<Role> roleSet = user.getRoles();
                    String roles = "";
                    if (!roleSet.isEmpty()) {
                        System.out.println("Lista dei ruoli");
                        for (Role role : roleSet) {
                        roles = roles + role.getRole() + " ";
                        }
                    }
                    return roles;
                }))
                .setHeader("Ruoli");
        // Action column
        userGrid.addComponentColumn(item -> createActions(userGrid, item))
                .setHeader("Azioni");
        userGrid.setWidthFull();

        // Add user
        HorizontalLayout addUsers = new HorizontalLayout();
        H4 title = new H4("Lista degli utenti");
        Button addUser = new Button(new Icon(VaadinIcon.PLUS), buttonClickEvent -> {
            System.out.println("Add user");
            UserDialog userDialog = new UserDialog(new User(), userService, roleService, userGrid);
            userDialog.open();
        });
        addUsers.add(title, addUser);

        add(addUsers, userGrid);

    }

    private Component createActions(Grid<User> userGrid, User item) {
        @SuppressWarnings("unchecked")
        Div actions = new Div();
        Button edit = new Button(new Icon(VaadinIcon.EDIT), buttonClickEvent -> {
            System.out.println("Edit item");
            UserDialog userDialog = new UserDialog(item, userService, roleService, userGrid);
            userDialog.editMode();
            userDialog.open();
        });
        Button resetPassword = new Button(new Icon(VaadinIcon.ERASER), event -> {
            System.out.println("Reset password");
            PasswordDialog passwordDialog = new PasswordDialog(item, userService);
            passwordDialog.open();
        });
        Button delete = new Button(new Icon(VaadinIcon.CLOSE), buttonClickEvent -> {
            System.out.println("Delete item");
            Dialog dialog = new Dialog();
            dialog.setCloseOnEsc(false);
            dialog.setCloseOnOutsideClick(false);
            Span message = new Span("Vuoi veramente cancellare l'utente "
                    + item.getName()
                    + "?");
            Button confirmButton = new Button("Conferma", event -> {
                System.out.println("Confirmed!");
                userService.delete(item);
                userGrid.setItems(userService.findAllUser());
                dialog.close();
            });
            Button cancelButton = new Button("Annulla", event -> {
                System.out.println("Cancel!");
                dialog.close();
            });
            VerticalLayout body = new VerticalLayout();
            body.add(message, new HorizontalLayout(confirmButton, cancelButton));
            dialog.add(body);
            dialog.open();
        });
        actions.add(edit, resetPassword, delete);
        return actions;
    }

}
