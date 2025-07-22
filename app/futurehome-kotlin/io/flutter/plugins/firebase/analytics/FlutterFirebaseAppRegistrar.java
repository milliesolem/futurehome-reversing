package io.flutter.plugins.firebase.analytics;

import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.platforminfo.LibraryVersionComponent;
import java.util.Collections;
import java.util.List;

public class FlutterFirebaseAppRegistrar implements ComponentRegistrar {
   public List<Component<?>> getComponents() {
      return Collections.singletonList(LibraryVersionComponent.create("flutter-fire-analytics", "11.4.4"));
   }
}
