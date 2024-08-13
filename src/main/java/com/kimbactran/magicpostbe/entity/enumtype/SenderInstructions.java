package com.kimbactran.magicpostbe.entity.enumtype;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public enum SenderInstructions {
    SI1("1", "Chuyển hoàn ngay"),
    SI2("2", "Gọi điện cho người gửi/ BC gửi"),
    SI3("3", "Chuyển hoàn trước ngày"),
    SI5("5", " Chuyển hoàn khi hết thời gian lưu trữ"),
    SI4("4", "Chuyển hoàn trước ngày"),;

    private static final Map<String, SenderInstructions> SENDER_INTSTRUCTION = new HashMap<>();

    static {
        for (SenderInstructions e : values()) {
            SENDER_INTSTRUCTION.put(e.getCode(), e);
        }
    }

    private final String code;
    private final String value;

    SenderInstructions(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static Optional<SenderInstructions> getByValue(String value) {
        return Optional.of(SENDER_INTSTRUCTION.get(value));
    }
}
