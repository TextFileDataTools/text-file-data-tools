package org.textfiledatatools.core.mapper.fieldvalue;

import org.textfiledatatools.core.Field;
import org.textfiledatatools.util.StringUnaryOperatorType;

import java.util.Locale;
import java.util.function.UnaryOperator;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class StringOperationFieldValueMapper implements FieldValueMapper {

    protected final UnaryOperator<String> stringUnaryOperator;

    public StringOperationFieldValueMapper(StringUnaryOperatorType stringUnaryOperatorType) {
        this(stringUnaryOperatorType.stringUnaryOperator());
    }

    public StringOperationFieldValueMapper(StringUnaryOperatorType stringUnaryOperatorType, Locale locale) {
        this(stringUnaryOperatorType.stringUnaryOperator(locale));
    }

    public StringOperationFieldValueMapper(UnaryOperator<String> stringUnaryOperator) {
        this.stringUnaryOperator = stringUnaryOperator;
    }

    @Override
    public String mapToValue(Field field) {
        return stringUnaryOperator.apply(field.getValue());
    }

}
