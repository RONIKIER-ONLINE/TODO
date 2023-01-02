package functional;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.*;

@Slf4j
public class Test {

    private static long counter;

    private static void wasCalled() {
        counter++;
    }

    public static void main(String[] args) throws IOException {



        Stream<String> streamEmpty = Stream.empty();

        Collection<String> collection = Arrays.asList("a", "b", "c");
        Stream<String> streamOfCollection = collection.stream();

        Stream<String> streamOfArray = Stream.of("a", "b", "c");

        Stream<String> streamOfArrayAB = Stream.of("a", "b", "c").filter(letter -> !letter.contains("c"));

        Stream<String> streamOfArrayUppercase = Stream.of("a", "b", "c").map(letter -> letter.toUpperCase());

        Stream<String> streamOfString = Pattern.compile(", ").splitAsStream("a, b, c");

        String[] arr = new String[]{"a", "b", "c"};
        Stream<String> streamOfArrayFull = Arrays.stream(arr);
        Stream<String> streamOfArrayPart = Arrays.stream(arr, 1, 3);

        Stream<String> streamBuilder = Stream.<String>builder().add("a").add("b").add("c").build();

        Stream<String> streamGenerated = Stream.generate(() -> "element").limit(10);

        Stream<Integer> streamIterated = Stream.iterate(40, n -> n + 2).limit(20);

        IntStream intStream = IntStream.range(1, 3);
        LongStream longStream = LongStream.rangeClosed(1, 3);

        IntStream streamOfChars = "abc".chars();

        Random random = new Random();
        DoubleStream doubleStream = random.doubles(3);

        Path path = Paths.get("C:\\file.txt");
        Stream<String> streamOfStrings = Files.lines(path);
        Stream<String> streamWithCharset = Files.lines(path, Charset.forName("UTF-8"));

        Stream<String> stream = Stream.of("a", "b", "c").filter(element -> element.contains("b"));
        List<String> elements = Stream.of("a", "b", "c").filter(element -> element.contains("b")).collect(Collectors.toList());
        Optional<String> anyElement = stream.findAny();
        Optional<String> firstElement = stream.findFirst();

        Stream<String> onceModifiedStream = Stream.of("abcd", "bbcd", "cbcd").skip(1);
        Stream<String> twiceModifiedSortedStream = Stream.of("abcd", "bbcd", "cbcd").skip(1).map(element -> element.substring(0, 3)).sorted();

        List<String> list = Arrays.asList("abc1", "abc2", "abc3");
        long size = list.stream().skip(1).map(element -> element.substring(0, 3)).count();

        List<String> list2 = Arrays.asList("abc1", "abc2", "abc3");
        counter = 0;
        Stream<String> stream2 = list.stream().filter(element -> {
            wasCalled();
            return element.contains("2");
        });

        OptionalInt reduced = IntStream.range(1, 4).reduce((a, b) -> a + b);

        int reducedParams = Stream.of(1, 2, 3)
                .reduce(10, (a, b) -> a + b, (a, b) -> {
                    log.info("combiner was called");
                    return a + b;
                });

        int reducedParallel = Arrays.asList(1, 2, 3).parallelStream()
                .reduce(10, (a, b) -> a + b, (a, b) -> {
                    log.info("combiner was called");
                    return a + b;
                });

        Stream<Dupa> dupoStream = Stream.of(new Dupa(100,"czarna"),new Dupa(200,"niezla"), new Dupa(300,"blada"));

        List<String> dupyJasie = dupoStream.map(Dupa::getJasiu).collect(Collectors.toList());

        // EXCECPTIOn dupoStream already traversed CANT BE USED AGAIN
        String listToString = dupoStream.map(Dupa::getJasiu).collect(Collectors.joining(", ", "[", "]"));

        double averagePrice = dupoStream.collect(Collectors.averagingDouble(Dupa::getCena));
        double summingPrice = dupoStream.collect(Collectors.summingDouble(Dupa::getCena));

        Map<Double, List<Dupa>> collectorMapOfLists = dupoStream.collect(Collectors.groupingBy(Dupa::getCena));
        Map<Boolean, List<Dupa>> mapPartioned = dupoStream.collect(Collectors.partitioningBy(dupa -> dupa.getCena() > 100));

        Collector<Dupa, ?, LinkedList<Dupa>> toLinkedList = Collector.of(LinkedList::new, LinkedList::add,(first, second) -> {
                            first.addAll(second);
                            return first;
                        });

        LinkedList<Dupa> linkedListOfDupas = dupoStream.collect(toLinkedList);


        // PARRALLELL STREAMS
        Stream<Dupa> streamOfParallelDupas = Arrays.asList(new Dupa(100,"czarna"),new Dupa(200,"niezla"), new Dupa(300,"blada")).parallelStream();
        boolean isParallel = streamOfParallelDupas.isParallel();
        boolean bigPrice = streamOfParallelDupas
                .map(dupa -> dupa.getCena() * 12)
                .anyMatch(cena -> cena > 200);

    }

    public Stream<String> streamOf(List<String> list) {
        return list == null || list.isEmpty() ? Stream.empty() : list.stream();
    }

}
