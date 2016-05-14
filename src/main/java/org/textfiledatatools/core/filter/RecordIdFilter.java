package org.textfiledatatools.core.filter;

import org.textfiledatatools.core.Record;
import org.textfiledatatools.util.NumberCheckType;
import org.textfiledatatools.util.NumberComparisonType;

import java.util.Objects;
import java.util.function.LongPredicate;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class RecordIdFilter implements RecordFilter<Record> {

    protected final LongPredicate longPredicate;

    public RecordIdFilter(long equalRecordId) {
        this(NumberComparisonType.longPredicate(NumberComparisonType.EQUAL_TO, equalRecordId));
    }

    public RecordIdFilter(NumberComparisonType numberComparisonType, long compareRecordId) {
        this(numberComparisonType.longPredicate(compareRecordId));
    }

    public RecordIdFilter(NumberCheckType numberCheckType) {
        this(numberCheckType.longPredicate());
    }

    public RecordIdFilter(LongPredicate longPredicate) {
        Objects.requireNonNull(longPredicate);
        this.longPredicate = longPredicate;
    }

    public static RecordFilter<Record> getRecordIdIsPresentFilter() {
        return new RecordIdFilter((long value) -> true);
    }

    public static RecordFilter<Record> getRecordIdBetweenFilter(int from, int to) {
        return new RecordIdFilter(NumberComparisonType.GREATER_THAN_OR_EQUAL_TO, from).and(new RecordIdFilter(NumberComparisonType.LESS_THAN, to));
    }

    @Override
    public boolean isValid(Record record) {
        return (record.getRecordId() != null) && longPredicate.test(record.getRecordId());
    }

}
