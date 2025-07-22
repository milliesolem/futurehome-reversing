package com.polidea.rxandroidble2.internal.util;

import android.util.Pair;
import java.util.UUID;

public class CharacteristicNotificationId extends Pair<UUID, Integer> {
   public CharacteristicNotificationId(UUID var1, Integer var2) {
      super(var1, var2);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder("CharacteristicNotificationId{UUID=");
      var1.append(((UUID)this.first).toString());
      var1.append(", instanceId=");
      var1.append(((Integer)this.second).toString());
      var1.append('}');
      return var1.toString();
   }
}
