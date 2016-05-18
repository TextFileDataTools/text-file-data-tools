package org.textfiledatatools.core.filter;

import org.textfiledatatools.core.Record;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class CategoriesFilter implements RecordFilter<Record> {

    protected final Collection<String> categories;
    protected final String orElseCategory;

    public CategoriesFilter(Collection<String> categories, String orElseCategory) {
        Objects.requireNonNull(categories);
        this.categories = Collections.synchronizedSet(new HashSet<>(categories));
        this.orElseCategory = orElseCategory;
    }

    @Override
    public boolean isValid(Record record) {
        return categories.contains(record.getCategoryOrElse(orElseCategory));
    }

}
