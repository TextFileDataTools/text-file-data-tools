package stexfires.io.properties;

import org.jetbrains.annotations.Nullable;
import stexfires.core.record.KeyValueRecord;
import stexfires.io.spec.AbstractRecordFileSpec;
import stexfires.util.LineSeparator;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class PropertiesFileSpec extends AbstractRecordFileSpec {

    // DEFAULT - PropertiesFieldSpec
    public static final String DEFAULT_READ_NULL_VALUE_REPLACEMENT = "";
    public static final String DEFAULT_WRITE_NULL_VALUE_REPLACEMENT = "";

    // DEFAULT - read
    public static final boolean DEFAULT_COMMENT_AS_CATEGORY = false;

    // DEFAULT - write
    public static final boolean DEFAULT_ESCAPE_UNICODE = false;
    public static final boolean DEFAULT_DATE_COMMENT = false;
    public static final boolean DEFAULT_CATEGORY_AS_KEY_PREFIX = false;
    public static final String DEFAULT_KEY_PREFIX_DELIMITER = ".";

    // FIELD -  both
    private final List<PropertiesFieldSpec> fieldSpecs;

    // FIELD - read
    private final boolean commentAsCategory;

    // FIELD - write
    private final boolean escapeUnicode;
    private final boolean dateComment;
    private final boolean categoryAsKeyPrefix;
    private final String keyPrefixDelimiter;

    public PropertiesFileSpec(Charset charset, CodingErrorAction codingErrorAction,
                              @Nullable String readNullValueReplacement,
                              boolean commentAsCategory,
                              LineSeparator lineSeparator,
                              String writeNullValueReplacement,
                              boolean escapeUnicode, boolean dateComment,
                              boolean categoryAsKeyPrefix, String keyPrefixDelimiter) {
        super(charset, codingErrorAction, lineSeparator);
        Objects.requireNonNull(writeNullValueReplacement);
        Objects.requireNonNull(keyPrefixDelimiter);

        // both
        fieldSpecs = new ArrayList<>(2);
        fieldSpecs.add(KeyValueRecord.KEY_INDEX,
                new PropertiesFieldSpec(DEFAULT_READ_NULL_VALUE_REPLACEMENT, DEFAULT_WRITE_NULL_VALUE_REPLACEMENT));
        fieldSpecs.add(KeyValueRecord.VALUE_INDEX,
                new PropertiesFieldSpec(readNullValueReplacement, writeNullValueReplacement));

        // read
        this.commentAsCategory = commentAsCategory;

        // write
        this.escapeUnicode = escapeUnicode;
        this.dateComment = dateComment;
        this.categoryAsKeyPrefix = categoryAsKeyPrefix;
        this.keyPrefixDelimiter = keyPrefixDelimiter;
    }

    public static PropertiesFileSpec read(Charset charset, CodingErrorAction codingErrorAction) {
        return new PropertiesFileSpec(charset, codingErrorAction,
                DEFAULT_READ_NULL_VALUE_REPLACEMENT,
                DEFAULT_COMMENT_AS_CATEGORY,
                DEFAULT_LINE_SEPARATOR,
                DEFAULT_WRITE_NULL_VALUE_REPLACEMENT,
                DEFAULT_ESCAPE_UNICODE, DEFAULT_DATE_COMMENT,
                DEFAULT_CATEGORY_AS_KEY_PREFIX, DEFAULT_KEY_PREFIX_DELIMITER);
    }

    public static PropertiesFileSpec read(Charset charset, CodingErrorAction codingErrorAction,
                                          String nullValueReplacement) {
        return new PropertiesFileSpec(charset, codingErrorAction,
                nullValueReplacement,
                DEFAULT_COMMENT_AS_CATEGORY,
                DEFAULT_LINE_SEPARATOR,
                DEFAULT_WRITE_NULL_VALUE_REPLACEMENT,
                DEFAULT_ESCAPE_UNICODE, DEFAULT_DATE_COMMENT,
                DEFAULT_CATEGORY_AS_KEY_PREFIX, DEFAULT_KEY_PREFIX_DELIMITER);
    }

    public static PropertiesFileSpec read(Charset charset, CodingErrorAction codingErrorAction,
                                          String nullValueReplacement,
                                          boolean commentAsCategory) {
        return new PropertiesFileSpec(charset, codingErrorAction,
                nullValueReplacement,
                commentAsCategory,
                DEFAULT_LINE_SEPARATOR,
                DEFAULT_WRITE_NULL_VALUE_REPLACEMENT,
                DEFAULT_ESCAPE_UNICODE, DEFAULT_DATE_COMMENT,
                DEFAULT_CATEGORY_AS_KEY_PREFIX, DEFAULT_KEY_PREFIX_DELIMITER);
    }

    public static PropertiesFileSpec write(Charset charset, CodingErrorAction codingErrorAction,
                                           LineSeparator lineSeparator,
                                           boolean escapeUnicode) {
        return new PropertiesFileSpec(charset, codingErrorAction,
                DEFAULT_READ_NULL_VALUE_REPLACEMENT,
                DEFAULT_COMMENT_AS_CATEGORY,
                lineSeparator,
                DEFAULT_WRITE_NULL_VALUE_REPLACEMENT,
                escapeUnicode, DEFAULT_DATE_COMMENT,
                DEFAULT_CATEGORY_AS_KEY_PREFIX, DEFAULT_KEY_PREFIX_DELIMITER);
    }

    public static PropertiesFileSpec write(Charset charset, CodingErrorAction codingErrorAction,
                                           LineSeparator lineSeparator,
                                           String nullValueReplacement,
                                           boolean escapeUnicode, boolean dateComment,
                                           boolean categoryAsKeyPrefix, String keyPrefixDelimiter) {
        return new PropertiesFileSpec(charset, codingErrorAction,
                DEFAULT_READ_NULL_VALUE_REPLACEMENT,
                DEFAULT_COMMENT_AS_CATEGORY,
                lineSeparator,
                nullValueReplacement,
                escapeUnicode, dateComment,
                categoryAsKeyPrefix, keyPrefixDelimiter);
    }

    @Override
    public PropertiesFile file(Path path) {
        return new PropertiesFile(path, this);
    }

    public PropertiesProducer producer(InputStream inputStream) {
        return new PropertiesProducer(newBufferedReader(inputStream), this);
    }

    public PropertiesConsumer consumer(OutputStream outputStream) {
        return new PropertiesConsumer(newBufferedWriter(outputStream), this);
    }

    public List<PropertiesFieldSpec> getFieldSpecs() {
        return Collections.unmodifiableList(fieldSpecs);
    }

    public PropertiesFieldSpec getValueSpec() {
        return fieldSpecs.get(KeyValueRecord.VALUE_INDEX);
    }

    public boolean isCommentAsCategory() {
        return commentAsCategory;
    }

    public boolean isEscapeUnicode() {
        return escapeUnicode;
    }

    public boolean isDateComment() {
        return dateComment;
    }

    public boolean isCategoryAsKeyPrefix() {
        return categoryAsKeyPrefix;
    }

    public String getKeyPrefixDelimiter() {
        return keyPrefixDelimiter;
    }

}
