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
import it.ambulatoriovetathena.data.domains.structure.Block;
import it.ambulatoriovetathena.data.services.structure.BlockService;
import it.ambulatoriovetathena.data.services.structure.SlotService;

public class BlockListView extends VerticalLayout {

    private BlockService blockService;
    private SlotService slotService;

    private Grid<Block> blockGrid;

    public BlockListView(BlockService blockService, SlotService slotService) {

        //Injection
        this.blockService = blockService;
        this.slotService = slotService;

        this.setWidthFull();

        setId("blocklist-view");

        blockGrid = new Grid<>();
        blockGrid.setItems(blockService.findAll());
        
        blockGrid.addColumn(Block::getName)
                .setHeader("Nome");
        blockGrid.addColumn(Block::getPosition)
                .setHeader("Posizione");
        blockGrid.addColumn(Block::getLayout)
                .setHeader("Layout");
        
        blockGrid.addComponentColumn(item -> createActions(blockGrid, item))
                .setHeader("Azioni");
        blockGrid.setWidthFull();

        //Add block
        HorizontalLayout addBlocks = new HorizontalLayout();
        H4 title = new H4("Lista dei blocchi di gabbie");
        Button addBlock = new Button(new Icon(VaadinIcon.PLUS), event -> {
            System.out.println("Add new block");
            BlockDialog blockDialog = new BlockDialog(new Block(),
                    blockService, slotService, blockGrid);
            blockDialog.open();
        });
        addBlocks.add(title, addBlock);

        add(addBlocks, blockGrid);

    }

    private Component createActions(Grid<Block> blockGrid, Block item) {
        @SuppressWarnings("unchecked")
        Div actions = new Div();
        Button edit = new Button(new Icon(VaadinIcon.EDIT), buttonClickEvent -> {
            System.out.println("Edit item");
            BlockDialog blockDialog = new BlockDialog(item, blockService, slotService, blockGrid);
            blockDialog.open();
            blockDialog.editMode();
        });
        Button delete = new Button(new Icon(VaadinIcon.CLOSE), buttonClickEvent -> {
            System.out.println("Delete item");
            Dialog dialog = new Dialog();
            dialog.setCloseOnEsc(false);
            dialog.setCloseOnOutsideClick(false);
            Span message = new Span("Vuoi veramente cancellare il blocco "
                    + item.getName()
                    + "?");
            Button confirmButton = new Button("Conferma", event -> {
                System.out.println("Confirmed!");
                blockService.delete(item);
                blockGrid.setItems(blockService.findAll());
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
        actions.add(edit, delete);
        return actions;
    }

}
