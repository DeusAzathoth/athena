package it.ambulatoriovetathena.views.impostazioni;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import it.ambulatoriovetathena.views.main.MainView;
import org.springframework.security.access.annotation.Secured;

@Route(value = "impostazioni", layout = MainView.class)
@PageTitle("Impostazioni")
@Secured("admin")
public class ImpostazioniView extends Div {

    public ImpostazioniView() {
        setId("impostazioni-view");
        add(new Text("Content placeholder"));
    }

}
