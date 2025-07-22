package org.hamcrest;

import org.hamcrest.core.IsEqual;
import org.hamcrest.integration.JMock1Adapter;
import org.jmock.core.Constraint;

public class JMock1Matchers {
   public static Constraint equalTo(String var0) {
      return JMock1Adapter.adapt(IsEqual.equalTo(var0));
   }
}
