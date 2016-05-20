package org.textfiledatatools.io.singlevalue;

import org.textfiledatatools.core.Field;
import org.textfiledatatools.core.consumer.ConsumerException;
import org.textfiledatatools.core.record.ValueRecord;
import org.textfiledatatools.io.internal.AbstractWritableConsumer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SingleValueConsumer extends AbstractWritableConsumer<ValueRecord> {

    private final SingleValueFileSpec fileSpec;

    public SingleValueConsumer(BufferedWriter writer, SingleValueFileSpec fileSpec) {
        super(writer);
        Objects.requireNonNull(fileSpec);
        this.fileSpec = fileSpec;
    }

    @Override
    public void writeRecord(ValueRecord record) throws IOException, ConsumerException {
        Field field = record.getValueField();
        // TODO Change value (substitute)
        String value = field.getValue();
        if (value != null) {
            write(value);
            write(fileSpec.getLineSeparator().getSeparator());
        }
    }

}
