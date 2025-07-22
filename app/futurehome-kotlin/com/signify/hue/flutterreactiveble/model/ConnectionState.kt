package com.signify.hue.flutterreactiveble.model

import kotlin.enums.EnumEntries
import kotlin.enums.EnumEntriesKt

public enum class ConnectionState(code: Int) {
   CONNECTED(1),
   CONNECTING(0),
   DISCONNECTED(3),
   DISCONNECTING(2),
   UNKNOWN(4)
   public final val code: Int
   @JvmStatic
   private EnumEntries $ENTRIES;
   @JvmStatic
   private ConnectionState[] $VALUES;

   @JvmStatic
   fun {
      val var0: Array<ConnectionState> = $values();
      $VALUES = var0;
      $ENTRIES = EnumEntriesKt.enumEntries(var0);
   }

   init {
      this.code = var3;
   }

   @JvmStatic
   fun getEntries(): EnumEntries<ConnectionState> {
      return $ENTRIES;
   }
}
