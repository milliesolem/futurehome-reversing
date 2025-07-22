package dev.steenbakker.mobile_scanner.objects

public class MobileScannerStartParameters(width: Double = 0.0, height: Double, currentTorchState: Int, id: Long, numberOfCameras: Int) {
   public final val width: Double
   public final val height: Double
   public final val currentTorchState: Int
   public final val id: Long
   public final val numberOfCameras: Int

   init {
      this.width = var1;
      this.height = var3;
      this.currentTorchState = var5;
      this.id = var6;
      this.numberOfCameras = var8;
   }
}
