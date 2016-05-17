package org.textfiledatatools.io;

import org.textfiledatatools.core.Record;

import java.io.IOException;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public interface WritableRecordFile<T extends Record> extends RecordFile {

    WritableRecordConsumer<T> openConsumer() throws IOException;

}
