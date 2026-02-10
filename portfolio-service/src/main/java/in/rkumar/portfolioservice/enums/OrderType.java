package in.rkumar.portfolioservice.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderType {
    BUY('B'),
    SELL('S');

    private final char code;

    OrderType(char code) {
        this.code = code;
    }

    // This method makes sure 'B' is stored in the DB (via Converter below)
    public char getCode() {
        return code;
    }

    // This annotation makes sure 'B' is used in JSON (API responses)
    @JsonValue
    public char toValue() {
        return code;
    }
    
    // Helper to create Enum from char
    public static OrderType fromCode(char code) {
        for (OrderType type : OrderType.values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown OrderType code: " + code);
    }
}