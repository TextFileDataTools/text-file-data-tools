package stexfires.core.message;

import org.jetbrains.annotations.Nullable;
import stexfires.core.TextRecord;
import stexfires.core.filter.RecordFilter;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ConditionalMessage<T extends TextRecord> implements RecordMessage<T> {

    private final RecordFilter<? super T> condition;
    private final RecordMessage<? super TextRecord> trueMessage;
    private final RecordMessage<? super TextRecord> falseMessage;

    public ConditionalMessage(RecordFilter<? super T> condition,
                              RecordMessage<? super TextRecord> trueMessage,
                              RecordMessage<? super TextRecord> falseMessage) {
        Objects.requireNonNull(condition);
        Objects.requireNonNull(trueMessage);
        Objects.requireNonNull(falseMessage);
        this.condition = condition;
        this.trueMessage = trueMessage;
        this.falseMessage = falseMessage;
    }

    @Override
    public final @Nullable String createMessage(T record) {
        return condition.isValid(record) ? trueMessage.createMessage(record) : falseMessage.createMessage(record);
    }

}
