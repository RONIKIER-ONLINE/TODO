package online.ronikier.todo.blockchain;// Java implementation to store blocks in an ArrayList


import java.util.ArrayList;
import java.util.List;

@Deprecated
public class ChainTest {

    public static void main(String[] args) {

        System.out.println("IS Chain Valid ? - " + new ChainCheck().isGenuine(
                appendBlock("Fifth block",
                appendBlock("Fourth block",
                appendBlock("Third block",
                appendBlock("Second block",
                appendBlock("First block",
                        null)))))
        ));

    }

    private static List<Block> appendBlock(String blockData, List<Block> blockChainList) {
        if (blockChainList == null) {
            blockChainList = new ArrayList();
            blockChainList.add(new Block(blockData, "666"));
        }
        blockChainList.add(new Block(blockData, blockChainList.get(blockChainList.size() - 1).key));
        return blockChainList;
    }

}
