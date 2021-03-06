package stexfires.io.properties;

import stexfires.core.record.KeyValueRecord;
import stexfires.io.BaseRecordFile;
import stexfires.io.ReadableRecordProducer;
import stexfires.io.WritableRecordConsumer;

import java.io.IOException;
import java.nio.file.OpenOption;
import java.nio.file.Path;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class PropertiesFile extends BaseRecordFile<KeyValueRecord, KeyValueRecord, PropertiesFileSpec> {

    public PropertiesFile(Path path, PropertiesFileSpec fileSpec) {
        super(path, fileSpec);
    }

    @Override
    public ReadableRecordProducer<KeyValueRecord> openProducer() throws IOException {
        return fileSpec.producer(newInputStream());
    }

    @Override
    public WritableRecordConsumer<KeyValueRecord> openConsumer(OpenOption... writeOptions) throws IOException {
        return fileSpec.consumer(newOutputStream(writeOptions));
    }

}
