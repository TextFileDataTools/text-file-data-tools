package stexfires.core.mapper;

import org.jetbrains.annotations.NotNull;
import stexfires.core.TextRecord;
import stexfires.core.record.StandardRecord;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author Mathias Kalb
 * @see stexfires.core.mapper.AddValueMapper
 * @see stexfires.core.mapper.CategoryMapper
 * @see stexfires.core.mapper.RecordIdMapper
 * @see stexfires.core.mapper.ValuesMapper
 * @since 0.1
 */
public class FunctionMapper<T extends TextRecord> implements RecordMapper<T, TextRecord> {

    private final Function<? super T, String> categoryFunction;
    private final Function<? super T, Long> recordIdFunction;
    private final Function<? super T, Collection<String>> valuesFunction;

    public FunctionMapper(Function<? super T, String> categoryFunction,
                          Function<? super T, Long> recordIdFunction,
                          Function<? super T, Collection<String>> valuesFunction) {
        Objects.requireNonNull(categoryFunction);
        Objects.requireNonNull(recordIdFunction);
        Objects.requireNonNull(valuesFunction);
        this.categoryFunction = categoryFunction;
        this.recordIdFunction = recordIdFunction;
        this.valuesFunction = valuesFunction;
    }

    @SuppressWarnings("CallToSimpleGetterFromWithinClass")
    public static <T extends TextRecord> FunctionMapper<T> functionMappers(FunctionMapper<? super T> categoryMapper,
                                                                           FunctionMapper<? super T> recordIdMapper,
                                                                           FunctionMapper<? super T> valuesMapper) {
        return new FunctionMapper<>(
                categoryMapper.getCategoryFunction(),
                recordIdMapper.getRecordIdFunction(),
                valuesMapper.getValuesFunction());
    }

    protected final Function<? super T, String> getCategoryFunction() {
        return categoryFunction;
    }

    protected final Function<? super T, Long> getRecordIdFunction() {
        return recordIdFunction;
    }

    protected final Function<? super T, Collection<String>> getValuesFunction() {
        return valuesFunction;
    }

    @Override
    public final @NotNull TextRecord map(@NotNull T record) {
        return new StandardRecord(
                categoryFunction.apply(record),
                recordIdFunction.apply(record),
                valuesFunction.apply(record));
    }

}
