package io.flutter.embedding.android;

import android.view.KeyEvent;
import io.flutter.plugin.common.BinaryMessenger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class KeyEmbedderResponder implements KeyboardManager.Responder {
   private static final String TAG = "KeyEmbedderResponder";
   private final KeyboardManager.CharacterCombiner characterCombiner;
   private final BinaryMessenger messenger;
   private final HashMap<Long, Long> pressingRecords = new HashMap<>();
   private final HashMap<Long, KeyboardMap.TogglingGoal> togglingGoals = new HashMap<>();

   public KeyEmbedderResponder(BinaryMessenger var1) {
      this.characterCombiner = new KeyboardManager.CharacterCombiner();
      this.messenger = var1;

      for (KeyboardMap.TogglingGoal var4 : KeyboardMap.getTogglingGoals()) {
         this.togglingGoals.put(var4.logicalKey, var4);
      }
   }

   private static KeyData.Type getEventType(KeyEvent var0) {
      boolean var1;
      if (var0.getRepeatCount() > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      int var2 = var0.getAction();
      if (var2 != 0) {
         if (var2 == 1) {
            return KeyData.Type.kUp;
         } else {
            throw new AssertionError("Unexpected event type");
         }
      } else {
         KeyData.Type var3;
         if (var1) {
            var3 = KeyData.Type.kRepeat;
         } else {
            var3 = KeyData.Type.kDown;
         }

         return var3;
      }
   }

   private Long getLogicalKey(KeyEvent var1) {
      Long var2 = KeyboardMap.keyCodeToLogical.get((long)var1.getKeyCode());
      return var2 != null ? var2 : keyOfPlane(var1.getKeyCode(), 73014444032L);
   }

   private Long getPhysicalKey(KeyEvent var1) {
      long var2 = var1.getScanCode();
      if (var2 == 0L) {
         return keyOfPlane(var1.getKeyCode(), 73014444032L);
      } else {
         Long var4 = KeyboardMap.scanCodeToPhysical.get(var2);
         return var4 != null ? var4 : keyOfPlane(var1.getScanCode(), 73014444032L);
      }
   }

   private boolean handleEventImpl(KeyEvent var1, KeyboardManager.Responder.OnKeyEventHandledCallback var2) {
      if (var1.getScanCode() == 0 && var1.getKeyCode() == 0) {
         return false;
      } else {
         Long var13 = this.getPhysicalKey(var1);
         Long var10 = this.getLogicalKey(var1);
         ArrayList var12 = new ArrayList();

         for (KeyboardMap.PressingGoal var8 : KeyboardMap.pressingGoals) {
            boolean var6;
            if ((var1.getMetaState() & var8.mask) != 0) {
               var6 = true;
            } else {
               var6 = false;
            }

            this.synchronizePressingKey(var8, var6, var10, var13, var1, var12);
         }

         for (KeyboardMap.TogglingGoal var23 : this.togglingGoals.values()) {
            boolean var18;
            if ((var1.getMetaState() & var23.mask) != 0) {
               var18 = true;
            } else {
               var18 = false;
            }

            this.synchronizeTogglingKey(var23, var18, var10, var1);
         }

         int var15 = var1.getAction();
         boolean var16;
         if (var15 != 0) {
            if (var15 != 1) {
               return false;
            }

            var16 = 0;
         } else {
            var16 = 1;
         }

         Long var9;
         String var22;
         KeyData.Type var24;
         label92: {
            Long var20 = this.pressingRecords.get(var13);
            var9 = null;
            if (var16) {
               KeyData.Type var21;
               if (var20 == null) {
                  var21 = KeyData.Type.kDown;
               } else if (var1.getRepeatCount() > 0) {
                  var21 = KeyData.Type.kRepeat;
               } else {
                  this.synthesizeEvent(false, var20, var13, var1.getEventTime());
                  var21 = KeyData.Type.kDown;
               }

               char var3 = this.characterCombiner.applyCombiningCharacterToBaseCharacter(var1.getUnicodeChar());
               var24 = var21;
               if (var3 != 0) {
                  StringBuilder var25 = new StringBuilder("");
                  var25.append(var3);
                  String var11 = var25.toString();
                  var24 = var21;
                  var22 = var11;
                  break label92;
               }
            } else {
               if (var20 == null) {
                  return false;
               }

               var24 = KeyData.Type.kUp;
            }

            var22 = null;
         }

         if (var24 != KeyData.Type.kRepeat) {
            if (var16) {
               var9 = var10;
            }

            this.updatePressingState(var13, var9);
         }

         if (var24 == KeyData.Type.kDown) {
            KeyboardMap.TogglingGoal var26 = this.togglingGoals.get(var10);
            if (var26 != null) {
               var26.enabled ^= true;
            }
         }

         KeyData var27 = new KeyData();
         var16 = var1.getSource();
         if (var16 != 513) {
            if (var16 != 1025) {
               if (var16 != 16777232) {
                  if (var16 != 33554433) {
                     var27.deviceType = KeyData.DeviceType.kKeyboard;
                  } else {
                     var27.deviceType = KeyData.DeviceType.kHdmi;
                  }
               } else {
                  var27.deviceType = KeyData.DeviceType.kJoystick;
               }
            } else {
               var27.deviceType = KeyData.DeviceType.kGamepad;
            }
         } else {
            var27.deviceType = KeyData.DeviceType.kDirectionalPad;
         }

         var27.timestamp = var1.getEventTime();
         var27.type = var24;
         var27.logicalKey = var10;
         var27.physicalKey = var13;
         var27.character = var22;
         var27.synthesized = false;
         this.sendKeyEvent(var27, var2);
         Iterator var14 = var12.iterator();

         while (var14.hasNext()) {
            ((Runnable)var14.next()).run();
         }

         return true;
      }
   }

   private static long keyOfPlane(long var0, long var2) {
      return var0 & 4294967295L | var2;
   }

   private void sendKeyEvent(KeyData var1, KeyboardManager.Responder.OnKeyEventHandledCallback var2) {
      KeyEmbedderResponder$$ExternalSyntheticLambda0 var3;
      if (var2 == null) {
         var3 = null;
      } else {
         var3 = new KeyEmbedderResponder$$ExternalSyntheticLambda0(var2);
      }

      this.messenger.send("flutter/keydata", var1.toBytes(), var3);
   }

   private void synthesizeEvent(boolean var1, Long var2, Long var3, long var4) {
      KeyData var7 = new KeyData();
      var7.timestamp = var4;
      KeyData.Type var6;
      if (var1) {
         var6 = KeyData.Type.kDown;
      } else {
         var6 = KeyData.Type.kUp;
      }

      var7.type = var6;
      var7.logicalKey = var2;
      var7.physicalKey = var3;
      var7.character = null;
      var7.synthesized = true;
      var7.deviceType = KeyData.DeviceType.kKeyboard;
      if (var3 != 0L && var2 != 0L) {
         if (!var1) {
            var2 = null;
         }

         this.updatePressingState(var3, var2);
      }

      this.sendKeyEvent(var7, null);
   }

   public Map<Long, Long> getPressedState() {
      return Collections.unmodifiableMap(this.pressingRecords);
   }

   @Override
   public void handleEvent(KeyEvent var1, KeyboardManager.Responder.OnKeyEventHandledCallback var2) {
      if (!this.handleEventImpl(var1, var2)) {
         this.synthesizeEvent(true, 0L, 0L, 0L);
         var2.onKeyEventHandled(true);
      }
   }

   void synchronizePressingKey(KeyboardMap.PressingGoal var1, boolean var2, long var3, long var5, KeyEvent var7, ArrayList<Runnable> var8) {
      boolean[] var13 = new boolean[var1.keys.length];
      Boolean[] var14 = new Boolean[var1.keys.length];
      Boolean var16 = false;
      boolean var9 = false;
      int var11 = 0;

      while (true) {
         int var10 = var1.keys.length;
         boolean var12 = true;
         if (var11 >= var10) {
            if (var2) {
               for (int var22 = 0; var22 < var1.keys.length; var22++) {
                  if (var14[var22] == null) {
                     if (var9) {
                        var14[var22] = var13[var22];
                     } else {
                        var14[var22] = true;
                        var9 = true;
                     }
                  }
               }

               if (!var9) {
                  var14[0] = true;
               }
            } else {
               for (int var18 = 0; var18 < var1.keys.length; var18++) {
                  if (var14[var18] == null) {
                     var14[var18] = var16;
                  }
               }
            }

            for (int var19 = 0; var19 < var1.keys.length; var19++) {
               if (var13[var19] != var14[var19]) {
                  KeyboardMap.KeyPair var17 = var1.keys[var19];
                  this.synthesizeEvent(var14[var19], var17.logicalKey, var17.physicalKey, var7.getEventTime());
               }
            }

            return;
         }

         KeyboardMap.KeyPair var15 = var1.keys[var11];
         var13[var11] = this.pressingRecords.containsKey(var15.physicalKey);
         label88:
         if (var15.logicalKey == var3) {
            var10 = <unrepresentable>.$SwitchMap$io$flutter$embedding$android$KeyData$Type[getEventType(var7).ordinal()];
            if (var10 != 1) {
               if (var10 == 2) {
                  var14[var11] = var13[var11];
                  break label88;
               }

               if (var10 != 3) {
                  break label88;
               }

               if (!var2) {
                  var8.add(new KeyEmbedderResponder$$ExternalSyntheticLambda2(this, var15, var7));
               }

               var14[var11] = var13[var11];
            } else {
               var14[var11] = var16;
               if (!var2) {
                  var8.add(new KeyEmbedderResponder$$ExternalSyntheticLambda1(this, var15, var5, var7));
               }
            }

            var9 = true;
         } else {
            boolean var21 = var12;
            if (!var9) {
               if (var13[var11]) {
                  var21 = var12;
               } else {
                  var21 = false;
               }
            }

            var9 = var21;
         }

         var11++;
      }
   }

   void synchronizeTogglingKey(KeyboardMap.TogglingGoal var1, boolean var2, long var3, KeyEvent var5) {
      if (var1.logicalKey != var3) {
         if (var1.enabled != var2) {
            var2 = this.pressingRecords.containsKey(var1.physicalKey);
            if (!var2) {
               var1.enabled ^= true;
            }

            this.synthesizeEvent(var2 ^ true, var1.logicalKey, var1.physicalKey, var5.getEventTime());
            if (var2) {
               var1.enabled ^= true;
            }

            this.synthesizeEvent(var2, var1.logicalKey, var1.physicalKey, var5.getEventTime());
         }
      }
   }

   void updatePressingState(Long var1, Long var2) {
      if (var2 != null) {
         if (this.pressingRecords.put(var1, var2) != null) {
            throw new AssertionError("The key was not empty");
         }
      } else if (this.pressingRecords.remove(var1) == null) {
         throw new AssertionError("The key was empty");
      }
   }
}
