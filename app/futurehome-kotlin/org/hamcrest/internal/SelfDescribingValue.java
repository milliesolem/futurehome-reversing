package org.hamcrest.internal;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;

public class SelfDescribingValue<T> implements SelfDescribing {
   private T value;

   public SelfDescribingValue(T var1) {
      this.value = (T)var1;
   }

   @Override
   public void describeTo(Description var1) {
      var1.appendValue(this.value);
   }
}
