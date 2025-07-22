package com.it_nomads.fluttersecurestorage.ciphers;

import android.content.Context;
import android.security.keystore.KeyGenParameterSpec.Builder;
import com.baseflow.geocoding.Geocoding..ExternalSyntheticApiModelOutline0;
import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.MGF1ParameterSpec;
import java.util.Calendar;
import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource.PSpecified;
import javax.security.auth.x500.X500Principal;

public class RSACipherOAEPImplementation extends RSACipher18Implementation {
   public RSACipherOAEPImplementation(Context var1) throws Exception {
      super(var1);
   }

   @Override
   protected String createKeyAlias() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.context.getPackageName());
      var1.append(".FlutterSecureStoragePluginKeyOAEP");
      return var1.toString();
   }

   @Override
   protected AlgorithmParameterSpec getAlgorithmParameterSpec() {
      return new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA1, PSpecified.DEFAULT);
   }

   @Override
   protected Cipher getRSACipher() throws Exception {
      return Cipher.getInstance("RSA/ECB/OAEPPadding", "AndroidKeyStoreBCWorkaround");
   }

   @Override
   protected AlgorithmParameterSpec makeAlgorithmParameterSpec(Context var1, Calendar var2, Calendar var3) {
      ExternalSyntheticApiModelOutline0.m$2();
      Builder var4 = ExternalSyntheticApiModelOutline0.m(this.keyAlias, 3);
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
                     new String[]{"OAEPPadding"}
                  ),
                  BigInteger.valueOf(1L)
               ),
               var2.getTime()
            ),
            var3.getTime()
         )
      );
   }
}
