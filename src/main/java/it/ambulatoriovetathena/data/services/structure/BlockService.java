package it.ambulatoriovetathena.data.services.structure;

import it.ambulatoriovetathena.data.domains.structure.Block;
import it.ambulatoriovetathena.data.repositories.structure.BlockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockService {

    private BlockRepository blockRepository;

    public BlockService(BlockRepository blockRepository) {
        this.blockRepository = blockRepository;
    }

    public List<Block> findAll() {
        return blockRepository.findAll();
    }

    public long count() {
        return blockRepository.count();
    }

    public void delete(Block block) {
        blockRepository.delete(block);
    }

    public void save(Block block) {
        blockRepository.save(block);
    }

}
