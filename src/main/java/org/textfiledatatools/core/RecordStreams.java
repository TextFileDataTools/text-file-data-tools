package org.textfiledatatools.core;

import org.textfiledatatools.core.producer.RecordProducer;
import org.textfiledatatools.core.producer.UncheckedProducerException;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * This class consists of {@code static} utility methods for operating on record streams.
 *
 * @author Mathias Kalb
 * @since 0.1
 */
public final class RecordStreams {

    private RecordStreams() {
    }

    public static Stream<Record> emptyStream() {
        return Stream.empty();
    }

    public static Stream<Record> newStream(Record record) {
        return Stream.of(record);
    }

    public static Stream<Record> newStream(Record... records) {
        return Stream.of(records);
    }

    public static <T extends Record> Stream<T> newStream(RecordProducer<T> recordProducer) throws UncheckedProducerException {
        return recordProducer.produceStream();
    }

    public static <T extends Record> Stream<T> concat(Stream<T> firstRecordStream, Stream<T> secondRecordStream) {
        return Stream.concat(firstRecordStream, secondRecordStream);
    }

    @SafeVarargs
    public static <T extends Record> Stream<T> concat(Stream<T>... recordStreams) {
        // TODO Validate: concatWithFlatMap or concatWithReduce
        return concatWithFlatMap(recordStreams);
    }

    @SafeVarargs
    static <T extends Record> Stream<T> concatWithFlatMap(Stream<T>... recordStreams) {
        return Stream.of(recordStreams).flatMap(Function.identity());
    }

    @SafeVarargs
    static <T extends Record> Stream<T> concatWithReduce(Stream<T>... recordStreams) {
        return Stream.of(recordStreams).reduce(Stream.empty(), Stream::concat);
    }

}
