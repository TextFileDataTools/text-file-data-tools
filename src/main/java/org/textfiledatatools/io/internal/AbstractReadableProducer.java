package org.textfiledatatools.io.internal;

import org.textfiledatatools.core.Record;
import org.textfiledatatools.core.producer.ProducerException;
import org.textfiledatatools.core.producer.UncheckedProducerException;
import org.textfiledatatools.io.ReadableRecordProducer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public abstract class AbstractReadableProducer<T extends Record> implements ReadableRecordProducer<T> {

    protected final BufferedReader reader;

    protected AbstractReadableProducer(BufferedReader reader) {
        Objects.requireNonNull(reader);
        this.reader = reader;
    }

    @Override
    public void readBefore() throws IOException {
    }

    protected abstract Iterator<RecordRawData> createIterator() throws UncheckedProducerException;

    protected abstract T createRecord(RecordRawData recordRawData) throws UncheckedProducerException;

    @Override
    public Stream<T> readRecords() throws IOException, ProducerException {
        Stream<T> recordStream;
        Iterator<RecordRawData> iterator = createIterator();
        if (iterator.hasNext()) {
            recordStream = StreamSupport
                    .stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED | Spliterator.NONNULL), false)
                    .map(this::createRecord);
        } else {
            recordStream = Stream.empty();
        }
        return recordStream;
    }

    @Override
    public void readAfter() throws IOException {
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

}