package it.ambulatoriovetathena.data.services.structure;

import it.ambulatoriovetathena.data.domains.structure.Slot;
import it.ambulatoriovetathena.data.repositories.structure.SlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SlotService {

    private SlotRepository slotRepository;

    public SlotService(SlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    public List<Slot> findAll() {
        return slotRepository.findAll();
    }

    public long count() {
        return slotRepository.count();
    }

    public void delete(Slot slot) {
        slotRepository.delete(slot);
    }

    public void save(Slot slot) {
        slotRepository.save(slot);
    }

}
