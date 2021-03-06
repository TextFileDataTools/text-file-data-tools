package stexfires.core.mapper;

import org.jetbrains.annotations.NotNull;
import stexfires.core.TextRecord;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SupplierMapper<T extends TextRecord, R extends TextRecord> implements RecordMapper<T, R> {

    private final Supplier<? extends R> recordSupplier;

    /**
     * @param recordSupplier must be thread-safe
     */
    public SupplierMapper(Supplier<? extends R> recordSupplier) {
        Objects.requireNonNull(recordSupplier);
        this.recordSupplier = recordSupplier;
    }

    @Override
    public final @NotNull R map(@NotNull T record) {
        return recordSupplier.get();
    }

}
