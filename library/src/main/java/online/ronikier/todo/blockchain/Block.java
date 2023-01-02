package online.ronikier.todo.blockchain;
import online.ronikier.todo.library.Cryptonite;
import online.ronikier.todo.library.Utilities;

public class Block {

    private long time;
    private String value;
    public String lastKey;
    public String key;

    public Block(String lastKey, String value) {
        this.value = value;
        this.lastKey = lastKey;
        this.time = Utilities.dateCurrent().getTime();
        this.key = generateKey();
    }

    public String generateKey() {
        return Cryptonite.encodeHex(value + lastKey + Long.toString(time));
    }
}
