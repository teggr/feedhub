package dev.feedhub.app.web.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeUtils {

  public static String formatInstant(Instant instant) {
    ZoneId zone = ZoneId.systemDefault();
    LocalDate inputDate = instant.atZone(zone).toLocalDate();
    LocalDate today = LocalDate.now(zone);
    LocalDate yesterday = today.minusDays(1);

    if (inputDate.equals(today)) {
      DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h.mma");
      return instant.atZone(zone).toLocalTime().truncatedTo(java.time.temporal.ChronoUnit.MINUTES).format(timeFormatter);
    } else if (inputDate.equals(yesterday)) {
      DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
      return instant.atZone(zone).toLocalDate().format(dateFormatter);
    } else {
      DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
      return instant.atZone(zone).toLocalDate().format(dateFormatter);
    }
  }

}
