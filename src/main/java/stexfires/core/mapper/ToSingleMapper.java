package stexfires.core.mapper;

import stexfires.core.Fields;
import stexfires.core.Record;
import stexfires.core.record.SingleRecord;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ToSingleMapper<T extends Record> implements RecordMapper<T, SingleRecord> {

    private final int valueIndex;

    public ToSingleMapper() {
        this(Fields.FIRST_FIELD_INDEX);
    }

    public ToSingleMapper(int valueIndex) {
        this.valueIndex = valueIndex;
    }

    @Override
    public final SingleRecord map(T record) {
        return new SingleRecord(record.getCategory(), record.getRecordId(),
                record.getValueAt(valueIndex));
    }

}