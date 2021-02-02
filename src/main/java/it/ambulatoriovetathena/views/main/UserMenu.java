package it.ambulatoriovetathena.views.main;

import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.menubar.MenuBar;


public class UserMenu extends MenuBar {

    private Image avatarImage = new Image("images/user.svg", "Avatar");

    public UserMenu() {

        MenuItem profile = this.addItem(avatarImage);

        // Editing profile
        profile.getSubMenu().addItem("Edit Profile",
                menuItemClickEvent -> {
                    System.out.println("Editing Profile");
                    /* TODO
                     Dialog to edit profile preferences
                     */
                });

        // Change password
        profile.getSubMenu().addItem("Change password",
                menuItemClickEvent -> {
                    System.out.println("Change password");
                    /* TODO
                     Dialog to change password
                     */
                });

    }

}
