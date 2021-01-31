package it.ambulatoriovetathena.views.home;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import it.ambulatoriovetathena.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "home", layout = MainView.class)
@PageTitle("Home")
@CssImport("./styles/views/home/home-view.css")
@RouteAlias(value = "", layout = MainView.class)
public class HomeView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;

    public HomeView() {
        setId("home-view");
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        add(name, sayHello);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
        });
    }

}
