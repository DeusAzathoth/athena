package it.ambulatoriovetathena.views.impostazioni;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import it.ambulatoriovetathena.security.services.RoleService;
import it.ambulatoriovetathena.security.services.UserService;
import it.ambulatoriovetathena.views.main.MainView;
import org.springframework.security.access.annotation.Secured;

@Route(value = "impostazioni", layout = MainView.class)
@PageTitle("Impostazioni")
@Secured("admin")
public class ImpostazioniView extends VerticalLayout {

    private UserService userService;
    private RoleService roleService;

    public ImpostazioniView(UserService userService,
                            RoleService roleService) {

        // Injection
        this.userService = userService;

        setId("impostazioni-view");
        setWidthFull();

        // User List
        add(new UserListView(userService, roleService));

    }

}
