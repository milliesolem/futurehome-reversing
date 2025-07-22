package io.flutter.plugins.firebase.core;

import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.platforminfo.LibraryVersionComponent;
import java.util.Collections;
import java.util.List;

public class FlutterFirebaseCoreRegistrar implements ComponentRegistrar {
   public List<Component<?>> getComponents() {
      return Collections.singletonList(LibraryVersionComponent.create("flutter-fire-core", "3.12.1"));
   }
}
