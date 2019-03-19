package stexfires.core.mapper;

import stexfires.core.Fields;
import stexfires.core.Record;
import stexfires.core.record.PairRecord;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ToPairMapper<T extends Record> implements RecordMapper<T, PairRecord> {

    private final int firstIndex;
    private final int secondIndex;

    public ToPairMapper() {
        this(Fields.FIRST_FIELD_INDEX, Fields.FIRST_FIELD_INDEX + 1);
    }

    public ToPairMapper(int firstIndex, int secondIndex) {
        this.firstIndex = firstIndex;
        this.secondIndex = secondIndex;
    }

    @Override
    public final PairRecord map(T record) {
        return new PairRecord(record.getCategory(), record.getRecordId(),
                record.getValueAt(firstIndex), record.getValueAt(secondIndex));
    }

}