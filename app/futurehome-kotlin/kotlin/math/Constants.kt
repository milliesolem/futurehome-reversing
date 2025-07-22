package kotlin.math

private object Constants {
   internal final val LN2: Double = Math.log(2.0)
   internal final val epsilon: Double
   internal final val taylor_2_bound: Double
   internal final val taylor_n_bound: Double
   internal final val upper_taylor_2_bound: Double
   internal final val upper_taylor_n_bound: Double

   @JvmStatic
   fun {
      val var0: Double = Math.ulp(1.0);
      epsilon = var0;
      val var2: Double = Math.sqrt(var0);
      taylor_2_bound = var2;
      val var4: Double = Math.sqrt(var2);
      taylor_n_bound = var4;
      upper_taylor_2_bound = 1 / var2;
      upper_taylor_n_bound = 1 / var4;
   }
}
