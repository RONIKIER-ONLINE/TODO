package online.ronikier.todo.blockchain;

import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.Messages;

import java.util.List;

@Slf4j
public class ChainCheck {


    private Boolean blockValid;
    private Block previousBlock;


    public Boolean isGenuine(List<Block> blockChain) {
        blockChain.forEach(block -> checkBlock(block));
        return blockValid;
    }
    private void checkBlock(Block block) {

        if (block == null) {
            log.error(Messages.BLOCKCHAIN_NULL_BLOCK);
            blockValid = false;
        }

        if (!block.key.equals(block.generateKey())) {
            log.error(Messages.BLOCKCHAIN_KEY_INVALID);
            blockValid = false;
        }

        if (!previousBlock.key.equals(block.lastKey)) {
            log.error(Messages.BLOCKCHAIN_PARENT_INVALID);
            blockValid = false;
        }

        blockValid = true;
        previousBlock = block;

    }

}
