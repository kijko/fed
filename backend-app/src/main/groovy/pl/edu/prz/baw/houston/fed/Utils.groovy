package pl.edu.prz.baw.houston.fed

import java.time.ZoneId
import java.time.ZonedDateTime

static ZonedDateTime sqlTimestampToUTCDateTime(java.sql.Timestamp timestamp) {
    return timestamp.toInstant().atZone(ZoneId.of("UTC"))
}
