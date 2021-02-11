package it.ambulatoriovetathena.views.impostazioni;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.StringLengthValidator;
import it.ambulatoriovetathena.data.domains.structure.Block;
import it.ambulatoriovetathena.data.domains.structure.Slot;
import it.ambulatoriovetathena.data.services.structure.BlockService;
import it.ambulatoriovetathena.data.services.structure.SlotService;

import java.util.ArrayList;
import java.util.List;

public class BlockDialog extends Dialog {

    private Block block;
    private BlockService blockService;
    private SlotService slotService;
    private Grid<Block> blockGrid;

    private FormLayout formLayout;
    private Binder<Block> binder;
    private TextField name = new TextField();
    private TextField positions = new TextField();
    private ComboBox<String> layout = new ComboBox<>();
    private TextField customLayout = new TextField();


    public BlockDialog(Block block,
                       BlockService blockService,
                       SlotService slotService,
                       Grid<Block> blockGrid) {

        // Injection
        this.block = block;
        this.blockService = blockService;
        this.slotService = slotService;
        this.blockGrid = blockGrid;

        // Dialog
        this.setWidth("800px");

        formLayout = new FormLayout();
        binder = new Binder<>();

        // Layout
        List<String> layoutList = new ArrayList<String>();
        layoutList.add("3x2");
        layoutList.add("2x2");
        layoutList.add("5x3");
        layoutList.add("2x2x2");
        layout.setItems(layoutList);
        customLayout.setVisible(false);

        // Button
        Button save = new Button("Salva");
        Button cancel = new Button("Annulla");

        formLayout.addFormItem(name, "Nome");
        formLayout.addFormItem(positions, "Posizione");
        formLayout.addFormItem(layout, "Layout");

        // Binder
        binder.forField(name)
                .withValidator(new StringLengthValidator("Hai bisogno di un nome!",
                        1, null))
                .bind(Block::getName, Block::setName);
        binder.forField(positions)
                .withValidator(new StringLengthValidator("Indica dove si trova il " +
                        "blocco di gabbie",
                        1, null))
                .bind(Block::getPosition, Block::setPosition);
        binder.forField(layout)
                .bind(Block::getLayout, Block::setLayout);

        // Action for buttons
        save.addClickListener(event -> {
            System.out.println("Saving new block...");
            if (binder.writeBeanIfValid(block)) {
                System.out.println("Validate bean");
                try {
                    blockService.save(block);
                    createSlot(block);
                    blockGrid.setItems(blockService.findAll());
                    this.close();
                } catch (Exception e) {
                    e.getStackTrace();
                    Notification.show("Problemi durante il salvataggio del nuovo blocco");
                    this.close();
                }
            } else {
                Notification.show("Controlla i dati inseriti");
            }
        });

        cancel.addClickListener(buttonClickEvent -> {
            this.close();
        });
        formLayout.add(new HorizontalLayout(save, cancel));

        this.add(formLayout);

        binder.readBean(block);

    }

    private void createSlot(Block block) {
        System.out.println("Creating slots for the block");
        String del = "x";
        String[] layout = block.getLayout().split(del);
        for (int i = 0; i < layout.length; i++) {
            String number = layout[i];
            System.out.println("Number: " + number);
            int slotNumber = Integer.parseInt(number);
            System.out.println("Number: " + slotNumber);
            for (int j = 0; j < slotNumber; j++) {
                System.out.println("Slot: " + j);
                String slotName = block.getName() + j;
                Slot slot = new Slot(slotName, i, false, true, block);
                slotService.save(slot);
                block.getSlots().add(slot);
                blockService.save(block);
            }
        }
    }

    public void editMode() {
        System.out.println("Edit mode");
        layout.setEnabled(false);
    }
}
