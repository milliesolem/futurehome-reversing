package io.sentry;

import io.sentry.util.JsonSerializationUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.InetAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Currency;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerArray;

public final class JsonReflectionObjectSerializer {
   private final int maxDepth;
   private final Set<Object> visiting = new HashSet<>();

   JsonReflectionObjectSerializer(int var1) {
      this.maxDepth = var1;
   }

   private List<Object> list(Collection<?> var1, ILogger var2) throws Exception {
      ArrayList var3 = new ArrayList();
      Iterator var4 = var1.iterator();

      while (var4.hasNext()) {
         var3.add(this.serialize(var4.next(), var2));
      }

      return var3;
   }

   private List<Object> list(Object[] var1, ILogger var2) throws Exception {
      ArrayList var5 = new ArrayList();
      int var4 = var1.length;

      for (int var3 = 0; var3 < var4; var3++) {
         var5.add(this.serialize(var1[var3], var2));
      }

      return var5;
   }

   private Map<String, Object> map(Map<?, ?> var1, ILogger var2) throws Exception {
      HashMap var5 = new HashMap();

      for (Object var6 : var1.keySet()) {
         Object var4 = var1.get(var6);
         if (var4 != null) {
            var5.put(var6.toString(), this.serialize(var4, var2));
         } else {
            var5.put(var6.toString(), null);
         }
      }

      return var5;
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public Object serialize(Object var1, ILogger var2) throws Exception {
      Object var3 = null;
      if (var1 == null) {
         return null;
      } else if (var1 instanceof Character) {
         return var1.toString();
      } else if (var1 instanceof Number) {
         return var1;
      } else if (var1 instanceof Boolean) {
         return var1;
      } else if (var1 instanceof String) {
         return var1;
      } else if (var1 instanceof Locale) {
         return var1.toString();
      } else if (var1 instanceof AtomicIntegerArray) {
         return JsonSerializationUtils.atomicIntegerArrayToList((AtomicIntegerArray)var1);
      } else if (var1 instanceof AtomicBoolean) {
         return ((AtomicBoolean)var1).get();
      } else if (var1 instanceof URI) {
         return var1.toString();
      } else if (var1 instanceof InetAddress) {
         return var1.toString();
      } else if (var1 instanceof UUID) {
         return var1.toString();
      } else if (var1 instanceof Currency) {
         return var1.toString();
      } else if (var1 instanceof Calendar) {
         return JsonSerializationUtils.calendarToMap((Calendar)var1);
      } else if (var1.getClass().isEnum()) {
         return var1.toString();
      } else if (this.visiting.contains(var1)) {
         var2.log(SentryLevel.INFO, "Cyclic reference detected. Calling toString() on object.");
         return var1.toString();
      } else {
         this.visiting.add(var1);
         if (this.visiting.size() > this.maxDepth) {
            this.visiting.remove(var1);
            var2.log(SentryLevel.INFO, "Max depth exceeded. Calling toString() on object.");
            return var1.toString();
         } else {
            boolean var7 = false /* VF: Semaphore variable */;

            label161: {
               List var14;
               label160: {
                  label159: {
                     label158: {
                        label157: {
                           label156: {
                              try {
                                 var7 = true;
                                 if (var1.getClass().isArray()) {
                                    var14 = this.list((Object[])var1, var2);
                                    var7 = false;
                                    break label160;
                                 }

                                 if (var1 instanceof Collection) {
                                    var14 = this.list((Collection<?>)var1, var2);
                                    var7 = false;
                                    break label159;
                                 }

                                 if (var1 instanceof Map) {
                                    var12 = this.map((Map<?, ?>)var1, var2);
                                    var7 = false;
                                    break label158;
                                 }

                                 var4 = this.serializeObject(var1, var2);
                                 if (var4.isEmpty()) {
                                    var11 = var1.toString();
                                    var7 = false;
                                    break label157;
                                 }

                                 var7 = false;
                                 break label156;
                              } catch (Exception var8) {
                                 var2.log(SentryLevel.INFO, "Not serializing object due to throwing sub-path.", var8);
                                 var7 = false;
                              } finally {
                                 if (var7) {
                                    this.visiting.remove(var1);
                                 }
                              }

                              var10 = var3;
                              break label161;
                           }

                           var10 = var4;
                           break label161;
                        }

                        var10 = var11;
                        break label161;
                     }

                     var10 = var12;
                     break label161;
                  }

                  var10 = var14;
                  break label161;
               }

               var10 = var14;
            }

            this.visiting.remove(var1);
            return var10;
         }
      }
   }

   public Map<String, Object> serializeObject(Object var1, ILogger var2) throws Exception {
      Field[] var6 = var1.getClass().getDeclaredFields();
      HashMap var5 = new HashMap();

      for (Field var8 : var6) {
         if (!Modifier.isTransient(var8.getModifiers()) && !Modifier.isStatic(var8.getModifiers())) {
            String var7 = var8.getName();

            try {
               var8.setAccessible(true);
               var5.put(var7, this.serialize(var8.get(var1), var2));
               var8.setAccessible(false);
            } catch (Exception var10) {
               SentryLevel var9 = SentryLevel.INFO;
               StringBuilder var11 = new StringBuilder("Cannot access field ");
               var11.append(var7);
               var11.append(".");
               var2.log(var9, var11.toString());
            }
         }
      }

      return var5;
   }
}
