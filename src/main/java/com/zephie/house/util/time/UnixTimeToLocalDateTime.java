package com.zephie.house.util.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class UnixTimeToLocalDateTime {
    public static LocalDateTime convert(long unixTime) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(unixTime), TimeZone.getDefault().toZoneId());
    }
}