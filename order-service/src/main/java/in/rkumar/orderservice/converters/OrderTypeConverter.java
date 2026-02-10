package in.rkumar.orderservice.converters;

import in.rkumar.orderservice.enums.OrderType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true) // <--- Automatically applies to all OrderType fields
public class OrderTypeConverter implements AttributeConverter<OrderType, Character> {

    @Override
    public Character convertToDatabaseColumn(OrderType attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode(); // Saves 'B' or 'S'
    }

    @Override
    public OrderType convertToEntityAttribute(Character dbData) {
        if (dbData == null) {
            return null;
        }
        return OrderType.fromCode(dbData); // Reads 'B' -> BUY
    }
}