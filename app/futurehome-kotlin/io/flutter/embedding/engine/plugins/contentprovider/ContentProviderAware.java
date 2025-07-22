package io.flutter.embedding.engine.plugins.contentprovider;

public interface ContentProviderAware {
   void onAttachedToContentProvider(ContentProviderPluginBinding var1);

   void onDetachedFromContentProvider();
}
