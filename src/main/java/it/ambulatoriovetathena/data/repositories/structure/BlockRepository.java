package it.ambulatoriovetathena.data.repositories.structure;

import it.ambulatoriovetathena.data.domains.structure.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, Long> {
}
