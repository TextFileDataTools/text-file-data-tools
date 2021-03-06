package stexfires.io.html.table;

import org.jetbrains.annotations.Nullable;
import stexfires.io.spec.AbstractRecordFileSpec;
import stexfires.util.LineSeparator;

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
public final class HtmlTableFileSpec extends AbstractRecordFileSpec {

    public static final String TABLE_BEGIN = "<table>";
    public static final String TABLE_END = "</table>";
    public static final String TABLE_ROW_BEGIN = "<tr>";
    public static final String TABLE_ROW_END = "</tr>";
    public static final String TABLE_HEADER_BEGIN = "<th>";
    public static final String TABLE_HEADER_END = "</th>";
    public static final String TABLE_DATA_BEGIN = "<td>";
    public static final String TABLE_DATA_END = "</td>";
    public static final String NON_BREAKING_SPACE = "&nbsp;";

    // FIELD - both
    private final List<HtmlTableFieldSpec> fieldSpecs;

    // FIELD - write
    private final String beforeTable;
    private final String afterTable;
    private final String indentation;

    public HtmlTableFileSpec(Charset charset, CodingErrorAction codingErrorAction,
                             List<HtmlTableFieldSpec> fieldSpecs,
                             LineSeparator lineSeparator,
                             @Nullable String beforeTable, @Nullable String afterTable,
                             @Nullable String indentation) {
        super(charset, codingErrorAction, lineSeparator);
        Objects.requireNonNull(fieldSpecs);

        // both
        this.fieldSpecs = new ArrayList<>(fieldSpecs);

        // write
        this.beforeTable = beforeTable;
        this.afterTable = afterTable;
        this.indentation = indentation;
    }

    public static HtmlTableFileSpec write(Charset charset,
                                          List<HtmlTableFieldSpec> fieldSpecs,
                                          LineSeparator lineSeparator) {
        return new HtmlTableFileSpec(charset, DEFAULT_CODING_ERROR_ACTION,
                fieldSpecs,
                lineSeparator,
                null, null, null);
    }

    public static HtmlTableFileSpec write(Charset charset, CodingErrorAction codingErrorAction,
                                          List<HtmlTableFieldSpec> fieldSpecs,
                                          LineSeparator lineSeparator) {
        return new HtmlTableFileSpec(charset, codingErrorAction,
                fieldSpecs,
                lineSeparator,
                null, null, null);
    }

    public static HtmlTableFileSpec write(Charset charset,
                                          List<HtmlTableFieldSpec> fieldSpecs,
                                          LineSeparator lineSeparator,
                                          @Nullable String beforeTable, @Nullable String afterTable,
                                          @Nullable String indentation) {
        return new HtmlTableFileSpec(charset, DEFAULT_CODING_ERROR_ACTION,
                fieldSpecs,
                lineSeparator,
                beforeTable, afterTable, indentation);
    }

    public static HtmlTableFileSpec write(Charset charset, CodingErrorAction codingErrorAction,
                                          List<HtmlTableFieldSpec> fieldSpecs,
                                          LineSeparator lineSeparator,
                                          @Nullable String beforeTable, @Nullable String afterTable,
                                          @Nullable String indentation) {
        return new HtmlTableFileSpec(charset, codingErrorAction,
                fieldSpecs,
                lineSeparator,
                beforeTable, afterTable, indentation);
    }

    @Override
    public HtmlTableFile file(Path path) {
        return new HtmlTableFile(path, this);
    }

    public HtmlTableConsumer consumer(OutputStream outputStream) {
        return new HtmlTableConsumer(newBufferedWriter(outputStream), this);
    }

    public List<HtmlTableFieldSpec> getFieldSpecs() {
        return Collections.unmodifiableList(fieldSpecs);
    }

    public @Nullable String getBeforeTable() {
        return beforeTable;
    }

    public @Nullable String getAfterTable() {
        return afterTable;
    }

    public @Nullable String getIndentation() {
        return indentation;
    }

}

