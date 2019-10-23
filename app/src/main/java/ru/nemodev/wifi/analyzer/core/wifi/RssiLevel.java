package ru.nemodev.wifi.analyzer.core.wifi;

import android.util.Range;

/**
 * Перечисление для понятной интерпретации уровня принимаемого сигнала
 */
public enum RssiLevel {

    EXCELLENT("#4CAF50"),
    GOOD("#60974A" ),
    NORMAL("#90A036"),
    BAD("#A34A34");

    private String color;

    private static final Range<Integer> EXCELLENT_RSSI_RANGE = Range.create(-50, 0);
    private static final Range<Integer> GOOD_RSSI_RANGE = Range.create(-70, -51);
    private static final Range<Integer> NORMAL_LEVEL_RANGE = Range.create(-85, -71);
    private static final Range<Integer> BAD_LEVEL_RANGE = Range.create(-120, -86);

    RssiLevel(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public static RssiLevel defineRssiLevel(int rssi) {

        if (EXCELLENT_RSSI_RANGE.contains(rssi)) {
            return EXCELLENT;
        } else if (GOOD_RSSI_RANGE.contains(rssi)) {
            return GOOD;
        } else if (NORMAL_LEVEL_RANGE.contains(rssi)) {
            return NORMAL;
        } else {
            return BAD;
        }
    }
}
