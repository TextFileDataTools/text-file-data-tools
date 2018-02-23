package stexfires.core.modifier;

import stexfires.core.Record;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class PivotModifier<T extends Record> extends GroupModifier<T, Record> {

    public PivotModifier(Function<? super T, ?> groupByFunction,
                         Function<? super T, String> newCategoryFunction,
                         Function<List<T>, List<String>> pivotValuesFunction) {
        super(groupByFunction,
                GroupModifier.aggregateToValues(
                        list -> newCategoryFunction.apply(list.get(0)),
                        pivotValuesFunction));
        Objects.requireNonNull(newCategoryFunction);
    }

    public PivotModifier(Function<? super T, ?> groupByFunction,
                         Function<? super T, String> newCategoryFunction,
                         Function<? super T, Stream<String>> newFirstValuesFunction,
                         Function<? super T, String> valueFunction,
                         String nullValue,
                         Function<? super T, String> valueClassificationFunction,
                         List<String> valueClassifications) {
        this(groupByFunction,
                newCategoryFunction,
                pivotValuesFunctionWithClassifications(
                        newFirstValuesFunction,
                        valueFunction,
                        nullValue,
                        valueClassificationFunction,
                        valueClassifications));
    }

    public PivotModifier(Function<? super T, ?> groupByFunction,
                         Function<? super T, String> newCategoryFunction,
                         Function<? super T, Stream<String>> newFirstValuesFunction,
                         int newRecordSize,
                         String nullValue,
                         List<Integer> valueIndexes) {
        this(groupByFunction,
                newCategoryFunction,
                pivotValuesFunctionWithIndexes(
                        newFirstValuesFunction,
                        newRecordSize,
                        nullValue,
                        valueIndexes));
    }

    public static <T extends Record> Function<? super T, String> withoutCategory() {
        return r -> null;
    }

    public static <T extends Record> PivotModifier<T> pivotWithClassifications(int keyIndex,
                                                                               int valueIndex,
                                                                               String nullValue,
                                                                               int valueClassificationIndex,
                                                                               List<String> valueClassifications) {
        return new PivotModifier<>(
                GroupModifier.groupByValueAt(keyIndex),
                withoutCategory(),
                r -> Stream.of(r.getValueAt(keyIndex)),
                r -> r.getValueAt(valueIndex),
                nullValue,
                r -> r.getValueAt(valueClassificationIndex),
                valueClassifications);
    }

    public static <T extends Record> PivotModifier<T> pivotWithClassifications(Function<? super T, String> keyFunction,
                                                                               Function<? super T, String> valueFunction,
                                                                               String nullValue,
                                                                               Function<? super T, String> valueClassificationFunction,
                                                                               List<String> valueClassifications) {
        return new PivotModifier<>(keyFunction,
                withoutCategory(),
                r -> Stream.of(keyFunction.apply(r)),
                valueFunction,
                nullValue,
                valueClassificationFunction,
                valueClassifications);
    }

    public static <T extends Record> PivotModifier<T> pivotWithIndexes(int keyIndex,
                                                                       int recordsPerKey,
                                                                       String nullValue,
                                                                       Integer... valueIndexes) {
        return pivotWithIndexes(keyIndex, recordsPerKey, nullValue,
                Arrays.asList(valueIndexes));
    }

    public static <T extends Record> PivotModifier<T> pivotWithIndexes(int keyIndex,
                                                                       int recordsPerKey,
                                                                       String nullValue,
                                                                       List<Integer> valueIndexes) {
        return new PivotModifier<>(
                GroupModifier.groupByValueAt(keyIndex),
                withoutCategory(),
                r -> Stream.of(r.getValueAt(keyIndex)),
                1 + recordsPerKey * valueIndexes.size(),
                nullValue,
                valueIndexes);
    }

    public static <T extends Record> Function<List<T>, List<String>> pivotValuesFunctionWithClassifications(
            Function<? super T, Stream<String>> newFirstValuesFunction,
            Function<? super T, String> valueFunction,
            String nullValue,
            Function<? super T, String> valueClassificationFunction,
            List<String> valueClassifications) {
        Objects.requireNonNull(newFirstValuesFunction);
        Objects.requireNonNull(valueFunction);
        Objects.requireNonNull(valueClassificationFunction);
        Objects.requireNonNull(valueClassifications);
        Function<List<T>, Stream<String>> newValues = list ->
                valueClassifications.stream()
                                    .map(vc ->
                                            list.stream()
                                                .filter(r -> Objects.equals(vc, valueClassificationFunction.apply(r)))
                                                .map(valueFunction)
                                                .map(v -> v == null ? nullValue : v)
                                                .findFirst()
                                                .orElse(nullValue));
        return list ->
                Stream.concat(
                        newFirstValuesFunction.apply(list.get(0)),
                        newValues.apply(list))
                      .collect(Collectors.toList());
    }

    @SuppressWarnings("ImplicitNumericConversion")
    public static <T extends Record> Function<List<T>, List<String>> pivotValuesFunctionWithIndexes(
            Function<? super T, Stream<String>> newFirstValuesFunction,
            int newRecordSize,
            String nullValue,
            List<Integer> valueIndexes) {
        Objects.requireNonNull(newFirstValuesFunction);
        if (newRecordSize < 0) {
            throw new IllegalArgumentException("newRecordSize=" + newRecordSize);
        }
        Objects.requireNonNull(valueIndexes);
        Function<List<T>, Stream<String>> newValues = list ->
                list.stream()
                    .map(r -> valueIndexes.stream()
                                          .map(i -> r.getValueAtOrElse(i, nullValue)))
                    .flatMap(Function.identity());
        return list ->
                Stream.concat(Stream.concat(
                        newFirstValuesFunction.apply(list.get(0)),
                        newValues.apply(list)),
                        Stream.generate(() -> nullValue))
                      .limit(newRecordSize)
                      .collect(Collectors.toList());
    }

}
