package com.polidea.rxandroidble2.internal.cache;

import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble2.ClientScope;
import com.polidea.rxandroidble2.internal.DeviceComponent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

@ClientScope
public class DeviceComponentCache implements Map<String, DeviceComponent> {
   private final HashMap<String, DeviceComponentWeakReference> cache = new HashMap<>();
   private final DeviceComponentWeakReference.Provider deviceComponentReferenceProvider;

   @Inject
   public DeviceComponentCache() {
      this(new DeviceComponentWeakReference.Provider() {
         @Override
         public DeviceComponentWeakReference provide(DeviceComponent var1) {
            return new DeviceComponentWeakReference(var1);
         }
      });
   }

   DeviceComponentCache(DeviceComponentWeakReference.Provider var1) {
      this.deviceComponentReferenceProvider = var1;
   }

   private void evictEmptyReferences() {
      Iterator var1 = this.cache.entrySet().iterator();

      while (var1.hasNext()) {
         if (((DeviceComponentWeakReference)((Entry)var1.next()).getValue()).isEmpty()) {
            var1.remove();
         }
      }
   }

   @Override
   public void clear() {
      this.cache.clear();
   }

   @Override
   public boolean containsKey(Object var1) {
      boolean var2;
      if (this.cache.containsKey(var1) && this.get(var1) != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @Override
   public boolean containsValue(Object var1) {
      Iterator var2 = this.cache.values().iterator();

      while (var2.hasNext()) {
         if (((DeviceComponentWeakReference)var2.next()).contains(var1)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public Set<Entry<String, DeviceComponent>> entrySet() {
      HashSet var1 = new HashSet();

      for (Entry var2 : this.cache.entrySet()) {
         DeviceComponentWeakReference var3 = (DeviceComponentWeakReference)var2.getValue();
         if (!var3.isEmpty()) {
            var1.add(new CacheEntry((String)var2.getKey(), this.deviceComponentReferenceProvider.provide(var3.get())));
         }
      }

      return var1;
   }

   public DeviceComponent get(Object var1) {
      var1 = this.cache.get(var1);
      DeviceComponent var3;
      if (var1 != null) {
         var3 = var1.get();
      } else {
         var3 = null;
      }

      return var3;
   }

   @Override
   public boolean isEmpty() {
      this.evictEmptyReferences();
      return this.cache.isEmpty();
   }

   @Override
   public Set<String> keySet() {
      return this.cache.keySet();
   }

   public DeviceComponent put(String var1, DeviceComponent var2) {
      this.cache.put(var1, this.deviceComponentReferenceProvider.provide(var2));
      this.evictEmptyReferences();
      return var2;
   }

   @Override
   public void putAll(Map<? extends String, ? extends DeviceComponent> var1) {
      for (Entry var3 : var1.entrySet()) {
         this.put((String)var3.getKey(), (DeviceComponent)var3.getValue());
      }
   }

   public DeviceComponent remove(Object var1) {
      var1 = this.cache.remove(var1);
      this.evictEmptyReferences();
      DeviceComponent var3;
      if (var1 != null) {
         var3 = var1.get();
      } else {
         var3 = null;
      }

      return var3;
   }

   @Override
   public int size() {
      this.evictEmptyReferences();
      return this.cache.size();
   }

   @Override
   public Collection<DeviceComponent> values() {
      ArrayList var3 = new ArrayList();

      for (DeviceComponentWeakReference var1 : this.cache.values()) {
         if (!var1.isEmpty()) {
            var3.add(var1.get());
         }
      }

      return var3;
   }
}
