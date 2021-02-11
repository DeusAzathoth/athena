package it.ambulatoriovetathena.data.repositories.structure;

import it.ambulatoriovetathena.data.domains.structure.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlotRepository extends JpaRepository<Slot, Long> {
}
