package it.ambulatoriovetathena.data.domains.structure;

import javax.persistence.*;

@Entity
public class Slot {

    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    // Name
    private String name;
    // Level
    private int level;
    // Busy
    private boolean busy;
    //Ready
    private boolean ready;
    // Block
    @ManyToOne
    private Block block;

    public Slot() {
    }

    public Slot(String name, int level, boolean busy, boolean ready) {
        super();
        this.name = name;
        this.level = level;
        this.busy = busy;
        this.ready = ready;
    }

    public Slot(String name, int level, boolean busy, boolean ready, Block block) {
        super();
        this.name = name;
        this.level = level;
        this.busy = busy;
        this.ready = ready;
        this.block = block;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Slot slot = (Slot) o;

        return id.equals(slot.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Slot{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", busy=" + busy +
                ", ready=" + ready +
                '}';
    }

}
