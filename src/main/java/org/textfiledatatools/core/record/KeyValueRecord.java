package org.textfiledatatools.core.record;

import org.textfiledatatools.core.Field;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class KeyValueRecord extends PairRecord implements KeyRecord, ValueRecord {

    private static final long serialVersionUID = 1L;

    public KeyValueRecord(String key, String value) {
        super(Objects.requireNonNull(key), value);
    }

    public KeyValueRecord(String category, Long recordId, String key, String value) {
        super(category, recordId, Objects.requireNonNull(key), value);
    }

    @Override
    public KeyValueRecord newValueRecord(String value) {
        return new KeyValueRecord(getCategory(), getRecordId(), getValueOfKeyField(), value);
    }

    @Override
    public final Field getKeyField() {
        return getFirstField();
    }

    @Override
    public final Field getValueField() {
        return getSecondField();
    }

    @Override
    public String toString() {
        return "KeyValueRecord{" +
                "category=" + getCategory() +
                ", recordId=" + getRecordId() +
                ", key=" + getFirstField().getValue() +
                ", value=" + getSecondField().getValue() +
                '}';
    }

}
