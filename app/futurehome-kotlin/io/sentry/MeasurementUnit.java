package io.sentry;

import java.util.Locale;

public interface MeasurementUnit {
   String NONE = "none";

   String apiName();

   String name();

   public static final class Custom implements MeasurementUnit {
      private final String name;

      public Custom(String var1) {
         this.name = var1;
      }

      @Override
      public String apiName() {
         return this.name().toLowerCase(Locale.ROOT);
      }

      @Override
      public String name() {
         return this.name;
      }
   }

   public static enum Duration implements MeasurementUnit {
      DAY,
      HOUR,
      MICROSECOND,
      MILLISECOND,
      MINUTE,
      NANOSECOND,
      SECOND,
      WEEK;
      private static final MeasurementUnit.Duration[] $VALUES = $values();

      @Override
      public String apiName() {
         return this.name().toLowerCase(Locale.ROOT);
      }
   }

   public static enum Fraction implements MeasurementUnit {
      PERCENT,
      RATIO;
      private static final MeasurementUnit.Fraction[] $VALUES = $values();

      @Override
      public String apiName() {
         return this.name().toLowerCase(Locale.ROOT);
      }
   }

   public static enum Information implements MeasurementUnit {
      BIT,
      BYTE,
      EXABYTE,
      EXBIBYTE,
      GIBIBYTE,
      GIGABYTE,
      KIBIBYTE,
      KILOBYTE,
      MEBIBYTE,
      MEGABYTE,
      PEBIBYTE,
      PETABYTE,
      TEBIBYTE,
      TERABYTE;
      private static final MeasurementUnit.Information[] $VALUES = $values();

      @Override
      public String apiName() {
         return this.name().toLowerCase(Locale.ROOT);
      }
   }
}
