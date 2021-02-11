package it.ambulatoriovetathena.views.impostazioni;

import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import it.ambulatoriovetathena.data.services.structure.BlockService;
import it.ambulatoriovetathena.data.services.structure.SlotService;
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
    private BlockService blockService;
    private SlotService slotService;

    public ImpostazioniView(UserService userService, RoleService roleService,
                            BlockService blockService, SlotService slotService) {

        // Injection
        this.userService = userService;
        this.roleService = roleService;
        this.blockService = blockService;
        this.slotService = slotService;

        // TODO
        // Tabs instead of Vertical Layout

        setId("impostazioni-view");
        setWidthFull();

        // User List
        Scroller userListScroller = new Scroller();
        userListScroller.setHeight("300px");
        userListScroller.setWidthFull();
        UserListView userListView = new UserListView(userService, roleService);
        userListView.setAlignItems(Alignment.STRETCH);
        userListScroller.setContent(userListView);

        // Block list
        Scroller blockListScroller = new Scroller();
        blockListScroller.setHeight("300px");
        blockListScroller.setWidthFull();
        BlockListView blockListView = new BlockListView(blockService, slotService);
        blockListView.setAlignItems(Alignment.STRETCH);
        blockListScroller.setContent(blockListView);

        // Body
        Scroller bodyScroller = new Scroller();
        bodyScroller.setHeightFull();
        bodyScroller.setWidthFull();
        VerticalLayout body = new VerticalLayout();
        body.setSizeFull();
        body.add(userListScroller, blockListScroller);
        bodyScroller.setContent(body);
        add(bodyScroller);

    }

}
