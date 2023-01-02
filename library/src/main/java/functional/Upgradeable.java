package functional;

@FunctionalInterface
public interface Upgradeable {
    default String chuj() {
        System.out.println("DAefault chuj");
        return "chuj";
    }
    public String upgrade(String version);
//    public String downgrade(String version);
}
