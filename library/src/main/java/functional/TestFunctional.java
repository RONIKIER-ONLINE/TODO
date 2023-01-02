package functional;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestFunctional {

    public static void main(String[] args) {

//        var manNamer = new Function<String, String>() {
//            @Override
//            public String apply(String number) {
//                return "Man " + number;
//            }
//        };

//        var manualNamer = new Upgradeable() {
//            @Override
//            public String upgrade(String version) {
//                return "Version:" + version;
//            }
//
////            @Override
////            public String downgrade(String version) {
////                return "Chuj:" + version;
////            }
//        };

    Upgradeable upgradeable = (String version) -> "Wersja " + version;
//
//
//
//        upgradeable.upgrade("chuj");


    Stream<Object> streamOfArrayAB = Stream.of("a", "b", "c","d","e").filter(letter -> !letter.contains("c")).map(l -> upgradeable);

        streamOfArrayAB.collect(Collectors.toList()).forEach(System.out::println);

    /**

     //List<String> listOfAB = streamOfArrayAB.collect(Collectors.toList());

     //listOfAB.forEach(upgradeable::upgrade);


     Map<String, String> nameMap1 = new HashMap<>();
     Map<String, String> nameMap2 = new HashMap<>();
     Map<String, String> nameMap3 = new HashMap<>();

     String value = nameMap1.computeIfAbsent("John", s -> String.valueOf(s.length()));
     System.out.println("value:" + value);

     String v2 = nameMap2.computeIfAbsent("John",manNamer);
     System.out.println("v2:" + v2);

     //        String v3 = nameMap3.computeIfAbsent("John",manualNamer);
     System.out.println("v2:" + v2);

     Function<Integer, String> intToString = Object::toString;
     Function<String, String> quote = s -> "'" + s + "'";

     Function<Integer, String> quoteIntToString = quote.compose(intToString);

     //        assertEquals("'5'", quoteIntToString.apply(5));

     **/

    }

}
