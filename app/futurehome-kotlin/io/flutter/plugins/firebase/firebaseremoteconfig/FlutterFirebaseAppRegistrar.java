package io.flutter.plugins.firebase.firebaseremoteconfig;

import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.platforminfo.LibraryVersionComponent;
import java.util.Collections;
import java.util.List;

public class FlutterFirebaseAppRegistrar implements ComponentRegistrar {
   public List<Component<?>> getComponents() {
      return Collections.singletonList(LibraryVersionComponent.create("flutter-fire-rc", "5.4.2"));
   }
}
