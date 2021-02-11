package it.ambulatoriovetathena.data.domains.structure;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Block {

    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    // Name
    private String name;
    // Position
    private String position;
    // Layout
    private String layout;
    // Slots
    @OneToMany(mappedBy = "block", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Slot> slots = new ArrayList<>();

    public Block() {
    }

    public Block(String name, String position, String layout) {
        super();
        this.name = name;
        this.position = position;
        this.layout = layout;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Block block = (Block) o;

        return id.equals(block.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Block{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", layout='" + layout + '\'' +
                '}';
    }

}
