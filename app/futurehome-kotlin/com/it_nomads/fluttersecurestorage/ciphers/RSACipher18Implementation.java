package com.it_nomads.fluttersecurestorage.ciphers;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.security.KeyPairGeneratorSpec.Builder;
import com.baseflow.geocoding.Geocoding..ExternalSyntheticApiModelOutline0;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Calendar;
import java.util.Locale;
import javax.crypto.Cipher;
import javax.security.auth.x500.X500Principal;

class RSACipher18Implementation implements KeyCipher {
   private static final String KEYSTORE_PROVIDER_ANDROID = "AndroidKeyStore";
   private static final String TYPE_RSA = "RSA";
   protected final Context context;
   protected final String keyAlias;

   public RSACipher18Implementation(Context var1) throws Exception {
      this.context = var1;
      this.keyAlias = this.createKeyAlias();
      this.createRSAKeysIfNeeded(var1);
   }

   private void createKeys(Context param1) throws Exception {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index -1 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1064)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:565)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
      //
      // Bytecode:
      // 00: invokestatic java/util/Locale.getDefault ()Ljava/util/Locale;
      // 03: astore 2
      // 04: aload 0
      // 05: getstatic java/util/Locale.ENGLISH Ljava/util/Locale;
      // 08: invokespecial com/it_nomads/fluttersecurestorage/ciphers/RSACipher18Implementation.setLocale (Ljava/util/Locale;)V
      // 0b: invokestatic java/util/Calendar.getInstance ()Ljava/util/Calendar;
      // 0e: astore 5
      // 10: invokestatic java/util/Calendar.getInstance ()Ljava/util/Calendar;
      // 13: astore 4
      // 15: aload 4
      // 17: bipush 1
      // 18: bipush 25
      // 1a: invokevirtual java/util/Calendar.add (II)V
      // 1d: ldc "RSA"
      // 1f: ldc "AndroidKeyStore"
      // 21: invokestatic java/security/KeyPairGenerator.getInstance (Ljava/lang/String;Ljava/lang/String;)Ljava/security/KeyPairGenerator;
      // 24: astore 3
      // 25: getstatic android/os/Build$VERSION.SDK_INT I
      // 28: bipush 23
      // 2a: if_icmpge 3a
      // 2d: aload 0
      // 2e: aload 1
      // 2f: aload 5
      // 31: aload 4
      // 33: invokespecial com/it_nomads/fluttersecurestorage/ciphers/RSACipher18Implementation.makeAlgorithmParameterSpecLegacy (Landroid/content/Context;Ljava/util/Calendar;Ljava/util/Calendar;)Ljava/security/spec/AlgorithmParameterSpec;
      // 36: astore 1
      // 37: goto 44
      // 3a: aload 0
      // 3b: aload 1
      // 3c: aload 5
      // 3e: aload 4
      // 40: invokevirtual com/it_nomads/fluttersecurestorage/ciphers/RSACipher18Implementation.makeAlgorithmParameterSpec (Landroid/content/Context;Ljava/util/Calendar;Ljava/util/Calendar;)Ljava/security/spec/AlgorithmParameterSpec;
      // 43: astore 1
      // 44: aload 3
      // 45: aload 1
      // 46: invokevirtual java/security/KeyPairGenerator.initialize (Ljava/security/spec/AlgorithmParameterSpec;)V
      // 49: aload 3
      // 4a: invokevirtual java/security/KeyPairGenerator.generateKeyPair ()Ljava/security/KeyPair;
      // 4d: pop
      // 4e: aload 0
      // 4f: aload 2
      // 50: invokespecial com/it_nomads/fluttersecurestorage/ciphers/RSACipher18Implementation.setLocale (Ljava/util/Locale;)V
      // 53: return
      // 54: astore 1
      // 55: aload 0
      // 56: aload 2
      // 57: invokespecial com/it_nomads/fluttersecurestorage/ciphers/RSACipher18Implementation.setLocale (Ljava/util/Locale;)V
      // 5a: aload 1
      // 5b: athrow
   }

   private void createRSAKeysIfNeeded(Context var1) throws Exception {
      KeyStore var2 = KeyStore.getInstance("AndroidKeyStore");
      var2.load(null);
      if (var2.getKey(this.keyAlias, null) == null) {
         this.createKeys(var1);
      }
   }

   private PrivateKey getPrivateKey() throws Exception {
      KeyStore var1 = KeyStore.getInstance("AndroidKeyStore");
      var1.load(null);
      Key var2 = var1.getKey(this.keyAlias, null);
      if (var2 != null) {
         if (var2 instanceof PrivateKey) {
            return (PrivateKey)var2;
         } else {
            throw new Exception("Not an instance of a PrivateKey");
         }
      } else {
         StringBuilder var3 = new StringBuilder("No key found under alias: ");
         var3.append(this.keyAlias);
         throw new Exception(var3.toString());
      }
   }

   private PublicKey getPublicKey() throws Exception {
      KeyStore var1 = KeyStore.getInstance("AndroidKeyStore");
      var1.load(null);
      Certificate var2 = var1.getCertificate(this.keyAlias);
      if (var2 != null) {
         PublicKey var4 = var2.getPublicKey();
         if (var4 != null) {
            return var4;
         } else {
            StringBuilder var5 = new StringBuilder("No key found under alias: ");
            var5.append(this.keyAlias);
            throw new Exception(var5.toString());
         }
      } else {
         StringBuilder var3 = new StringBuilder("No certificate found under alias: ");
         var3.append(this.keyAlias);
         throw new Exception(var3.toString());
      }
   }

   private AlgorithmParameterSpec makeAlgorithmParameterSpecLegacy(Context var1, Calendar var2, Calendar var3) {
      Builder var5 = new Builder(var1).setAlias(this.keyAlias);
      StringBuilder var4 = new StringBuilder("CN=");
      var4.append(this.keyAlias);
      return var5.setSubject(new X500Principal(var4.toString()))
         .setSerialNumber(BigInteger.valueOf(1L))
         .setStartDate(var2.getTime())
         .setEndDate(var3.getTime())
         .build();
   }

   private void setLocale(Locale var1) {
      Locale.setDefault(var1);
      Configuration var2 = this.context.getResources().getConfiguration();
      var2.setLocale(var1);
      this.context.createConfigurationContext(var2);
   }

   protected String createKeyAlias() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.context.getPackageName());
      var1.append(".FlutterSecureStoragePluginKey");
      return var1.toString();
   }

   protected AlgorithmParameterSpec getAlgorithmParameterSpec() {
      return null;
   }

   protected Cipher getRSACipher() throws Exception {
      return VERSION.SDK_INT < 23
         ? Cipher.getInstance("RSA/ECB/PKCS1Padding", "AndroidOpenSSL")
         : Cipher.getInstance("RSA/ECB/PKCS1Padding", "AndroidKeyStoreBCWorkaround");
   }

   protected AlgorithmParameterSpec makeAlgorithmParameterSpec(Context var1, Calendar var2, Calendar var3) {
      ExternalSyntheticApiModelOutline0.m$2();
      android.security.keystore.KeyGenParameterSpec.Builder var4 = ExternalSyntheticApiModelOutline0.m(this.keyAlias, 3);
      StringBuilder var5 = new StringBuilder("CN=");
      var5.append(this.keyAlias);
      return ExternalSyntheticApiModelOutline0.m(
         ExternalSyntheticApiModelOutline0.m$1(
            ExternalSyntheticApiModelOutline0.m(
               ExternalSyntheticApiModelOutline0.m(
                  ExternalSyntheticApiModelOutline0.m(
                     ExternalSyntheticApiModelOutline0.m$1(
                        ExternalSyntheticApiModelOutline0.m$2(
                           ExternalSyntheticApiModelOutline0.m(var4, new X500Principal(var5.toString())), new String[]{"SHA-256"}
                        ),
                        new String[]{"ECB"}
                     ),
                     new String[]{"PKCS1Padding"}
                  ),
                  BigInteger.valueOf(1L)
               ),
               var2.getTime()
            ),
            var3.getTime()
         )
      );
   }

   @Override
   public Key unwrap(byte[] var1, String var2) throws Exception {
      PrivateKey var4 = this.getPrivateKey();
      Cipher var3 = this.getRSACipher();
      var3.init(4, var4, this.getAlgorithmParameterSpec());
      return var3.unwrap(var1, var2, 3);
   }

   @Override
   public byte[] wrap(Key var1) throws Exception {
      PublicKey var3 = this.getPublicKey();
      Cipher var2 = this.getRSACipher();
      var2.init(3, var3, this.getAlgorithmParameterSpec());
      return var2.wrap(var1);
   }
}
