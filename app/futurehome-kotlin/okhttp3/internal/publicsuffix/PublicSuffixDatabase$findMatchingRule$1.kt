package okhttp3.internal.publicsuffix

import kotlin.jvm.internal.MutablePropertyReference0
import kotlin.reflect.KDeclarationContainer

// $VF: Class flags could not be determined
@JvmSynthetic
internal class `PublicSuffixDatabase$findMatchingRule$1` : MutablePropertyReference0 {
   fun `PublicSuffixDatabase$findMatchingRule$1`(var1: PublicSuffixDatabase) {
      super(var1);
   }

   override fun get(): Any {
      return PublicSuffixDatabase.access$getPublicSuffixListBytes$p(this.receiver as PublicSuffixDatabase);
   }

   override fun getName(): java.lang.String {
      return "publicSuffixListBytes";
   }

   override fun getOwner(): KDeclarationContainer {
      return PublicSuffixDatabase::class;
   }

   override fun getSignature(): java.lang.String {
      return "getPublicSuffixListBytes()[B";
   }

   override fun set(var1: Any) {
      PublicSuffixDatabase.access$setPublicSuffixListBytes$p(this.receiver as PublicSuffixDatabase, var1 as ByteArray);
   }
}
