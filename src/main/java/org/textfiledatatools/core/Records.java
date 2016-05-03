package org.textfiledatatools.core;

import org.textfiledatatools.core.consumer.RecordConsumer;
import org.textfiledatatools.core.consumer.UncheckedConsumerException;
import org.textfiledatatools.core.filter.RecordFilter;
import org.textfiledatatools.core.logger.RecordLogger;
import org.textfiledatatools.core.mapper.RecordMapper;
import org.textfiledatatools.core.message.RecordMessage;
import org.textfiledatatools.core.record.EmptyRecord;
import org.textfiledatatools.core.record.SingleRecord;
import org.textfiledatatools.core.record.StandardRecord;
import org.textfiledatatools.core.record.ValueRecord;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class consists of {@code static} utility methods for operating on records and fields.
 *
 * @author Mathias Kalb
 * @since 0.1
 */
public final class Records {

    public static final int FIRST_FIELD_INDEX = 0;
    public static final long DEFAULT_INITIAL_RECORD_ID = 1L;
    public static final String DEFAULT_FIELD_VALUE_DELIMITER = ", ";

    public static final EmptyRecord EMPTY_RECORD = new EmptyRecord();

    private Records() {
    }

    public static Record empty() {
        return EMPTY_RECORD;
    }

    public static ValueRecord ofValue(String value) {
        return new SingleRecord(value);
    }

    public static Record ofValues(String... values) {
        return new StandardRecord(values);
    }

    public static Record ofValues(Collection<String> values) {
        return new StandardRecord(values);
    }

    public static Record[] singletonArray(Record record) {
        return new Record[]{record};
    }

    public static List<Record> singletonList(Record record) {
        return Collections.singletonList(record);
    }

    public static Set<Record> singletonSet(Record record) {
        return Collections.singleton(record);
    }

    public static Stream<Record> singletonStream(Record record) {
        return Stream.of(record);
    }

    public static Supplier<Long> recordIdSequence() {
        return recordIdSequence(DEFAULT_INITIAL_RECORD_ID);
    }

    public static Supplier<Long> recordIdSequence(long initialValue) {
        return new AtomicLong(initialValue)::getAndIncrement;
    }

    public static List<String> collectFieldValuesToList(Record record) {
        return record.streamOfFields().map(Field::getValue).collect(Collectors.toList());
    }

    public static String joinFieldValues(Record record) {
        return joinFieldValues(record, DEFAULT_FIELD_VALUE_DELIMITER);
    }

    public static String joinFieldValues(Record record, CharSequence delimiter) {
        return record.streamOfFields().map(Field::getValue).collect(Collectors.joining(delimiter));
    }

    public static String joinFieldValues(Field[] fields) {
        return joinFieldValues(fields, DEFAULT_FIELD_VALUE_DELIMITER);
    }

    public static String joinFieldValues(Field[] fields, CharSequence delimiter) {
        return Arrays.stream(fields).map(Field::getValue).collect(Collectors.joining(delimiter));
    }

    public static <T extends Record> void consume(T record, RecordConsumer<? super T> recordConsumer) throws UncheckedConsumerException {
        recordConsumer.consume(record);
    }

    public static <T extends Record> boolean isValid(T record, RecordFilter<? super T> recordFilter) {
        return recordFilter.isValid(record);
    }

    public static <T extends Record> void log(T record, RecordLogger<? super T> recordLogger) {
        recordLogger.log(record);
    }

    public static <T extends Record, R extends Record> R map(T record, RecordMapper<? super T, ? extends R> recordMapper) {
        return recordMapper.map(record);
    }

    public static <T extends Record> String createMessage(T record, RecordMessage<? super T> recordMessage) {
        return recordMessage.createMessage(record);
    }

}