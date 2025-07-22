package io.flutter.plugin.common;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSONUtil {
   private JSONUtil() {
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public static Object unwrap(Object var0) {
      if (JSONObject.NULL.equals(var0) || var0 == null) {
         return null;
      } else if (!(var0 instanceof Boolean)
         && !(var0 instanceof Byte)
         && !(var0 instanceof Character)
         && !(var0 instanceof Double)
         && !(var0 instanceof Float)
         && !(var0 instanceof Integer)
         && !(var0 instanceof Long)
         && !(var0 instanceof Short)
         && !(var0 instanceof String)) {
         ArrayList var11;
         label88: {
            try {
               if (var0 instanceof JSONArray) {
                  var11 = new ArrayList();
                  var0 = (JSONArray)var0;
                  break label88;
               }
            } catch (Exception var8) {
               return null;
            }

            JSONObject var4;
            try {
               if (!(var0 instanceof JSONObject)) {
                  return null;
               }

               var2 = new HashMap();
               var4 = (JSONObject)var0;
               var0 = var4.keys();
            } catch (Exception var7) {
               return null;
            }

            while (true) {
               try {
                  if (!var0.hasNext()) {
                     return var2;
                  }

                  String var3 = (String)var0.next();
                  var2.put(var3, unwrap(var4.get(var3)));
               } catch (Exception var5) {
                  return null;
               }
            }
         }

         int var1 = 0;

         while (true) {
            try {
               if (var1 >= var0.length()) {
                  return var11;
               }

               var11.add(unwrap(var0.get(var1)));
            } catch (Exception var6) {
               return null;
            }

            var1++;
         }
      } else {
         return var0;
      }
   }

   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public static Object wrap(Object var0) {
      if (var0 == null) {
         return JSONObject.NULL;
      } else {
         JSONArray var3 = var0;
         if (!(var0 instanceof JSONArray)) {
            if (var0 instanceof JSONObject) {
               var3 = var0;
            } else {
               if (var0.equals(JSONObject.NULL)) {
                  return var0;
               }

               label200: {
                  try {
                     if (var0 instanceof Collection) {
                        var3 = new JSONArray();
                        var0 = ((Collection)var0).iterator();
                        break label200;
                     }
                  } catch (Exception var19) {
                     return null;
                  }

                  int var2;
                  label189: {
                     try {
                        if (var0.getClass().isArray()) {
                           var3 = new JSONArray();
                           var2 = Array.getLength(var0);
                           break label189;
                        }
                     } catch (Exception var18) {
                        return null;
                     }

                     Iterator var4;
                     label251: {
                        try {
                           if (var0 instanceof Map) {
                              var23 = new JSONObject();
                              var4 = ((Map)var0).entrySet().iterator();
                              break label251;
                           }
                        } catch (Exception var17) {
                           return null;
                        }

                        var3 = var0;

                        try {
                           if (var0 instanceof Boolean) {
                              return var3;
                           }
                        } catch (Exception var16) {
                           return null;
                        }

                        var3 = var0;

                        try {
                           if (var0 instanceof Byte) {
                              return var3;
                           }
                        } catch (Exception var15) {
                           return null;
                        }

                        var3 = var0;

                        try {
                           if (var0 instanceof Character) {
                              return var3;
                           }
                        } catch (Exception var14) {
                           return null;
                        }

                        var3 = var0;

                        try {
                           if (var0 instanceof Double) {
                              return var3;
                           }
                        } catch (Exception var13) {
                           return null;
                        }

                        var3 = var0;

                        try {
                           if (var0 instanceof Float) {
                              return var3;
                           }
                        } catch (Exception var12) {
                           return null;
                        }

                        var3 = var0;

                        try {
                           if (var0 instanceof Integer) {
                              return var3;
                           }
                        } catch (Exception var11) {
                           return null;
                        }

                        var3 = var0;

                        try {
                           if (var0 instanceof Long) {
                              return var3;
                           }
                        } catch (Exception var10) {
                           return null;
                        }

                        var3 = var0;

                        try {
                           if (var0 instanceof Short) {
                              return var3;
                           }

                           if (var0 instanceof String) {
                              return var0;
                           }
                        } catch (Exception var9) {
                           return null;
                        }

                        try {
                           if (var0.getClass().getPackage().getName().startsWith("java.")) {
                              return var0.toString();
                           }
                        } catch (Exception var8) {
                        }

                        return null;
                     }

                     while (true) {
                        try {
                           if (!var4.hasNext()) {
                              return var23;
                           }

                           Entry var20 = (Entry)var4.next();
                           var23.put((String)var20.getKey(), wrap(var20.getValue()));
                        } catch (Exception var5) {
                           return null;
                        }
                     }
                  }

                  for (int var1 = 0; var1 < var2; var1++) {
                     try {
                        var3.put(wrap(Array.get(var0, var1)));
                     } catch (Exception var6) {
                        return null;
                     }
                  }

                  return var3;
               }

               while (true) {
                  try {
                     if (!var0.hasNext()) {
                        return var3;
                     }

                     var3.put(wrap(var0.next()));
                  } catch (Exception var7) {
                     return null;
                  }
               }
            }
         }

         return var3;
      }
   }
}
