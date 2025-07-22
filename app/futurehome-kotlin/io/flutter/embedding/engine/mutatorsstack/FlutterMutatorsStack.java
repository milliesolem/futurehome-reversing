package io.flutter.embedding.engine.mutatorsstack;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Path.Direction;
import java.util.ArrayList;
import java.util.List;

public class FlutterMutatorsStack {
   private List<Path> finalClippingPaths;
   private Matrix finalMatrix;
   private List<FlutterMutatorsStack.FlutterMutator> mutators = new ArrayList<>();

   public FlutterMutatorsStack() {
      this.finalMatrix = new Matrix();
      this.finalClippingPaths = new ArrayList<>();
   }

   public List<Path> getFinalClippingPaths() {
      return this.finalClippingPaths;
   }

   public Matrix getFinalMatrix() {
      return this.finalMatrix;
   }

   public List<FlutterMutatorsStack.FlutterMutator> getMutators() {
      return this.mutators;
   }

   public void pushClipRRect(int var1, int var2, int var3, int var4, float[] var5) {
      Rect var6 = new Rect(var1, var2, var3, var4);
      FlutterMutatorsStack.FlutterMutator var7 = new FlutterMutatorsStack.FlutterMutator(this, var6, var5);
      this.mutators.add(var7);
      Path var8 = new Path();
      var8.addRoundRect(new RectF(var6), var5, Direction.CCW);
      var8.transform(this.finalMatrix);
      this.finalClippingPaths.add(var8);
   }

   public void pushClipRect(int var1, int var2, int var3, int var4) {
      Rect var5 = new Rect(var1, var2, var3, var4);
      FlutterMutatorsStack.FlutterMutator var6 = new FlutterMutatorsStack.FlutterMutator(this, var5);
      this.mutators.add(var6);
      Path var7 = new Path();
      var7.addRect(new RectF(var5), Direction.CCW);
      var7.transform(this.finalMatrix);
      this.finalClippingPaths.add(var7);
   }

   public void pushTransform(float[] var1) {
      Matrix var2 = new Matrix();
      var2.setValues(var1);
      FlutterMutatorsStack.FlutterMutator var3 = new FlutterMutatorsStack.FlutterMutator(this, var2);
      this.mutators.add(var3);
      this.finalMatrix.preConcat(var3.getMatrix());
   }

   public class FlutterMutator {
      private Matrix matrix;
      private Path path;
      private float[] radiis;
      private Rect rect;
      final FlutterMutatorsStack this$0;
      private FlutterMutatorsStack.FlutterMutatorType type;

      public FlutterMutator(FlutterMutatorsStack var1, Matrix var2) {
         this.this$0 = var1;
         this.type = FlutterMutatorsStack.FlutterMutatorType.TRANSFORM;
         this.matrix = var2;
      }

      public FlutterMutator(FlutterMutatorsStack var1, Path var2) {
         this.this$0 = var1;
         this.type = FlutterMutatorsStack.FlutterMutatorType.CLIP_PATH;
         this.path = var2;
      }

      public FlutterMutator(FlutterMutatorsStack var1, Rect var2) {
         this.this$0 = var1;
         this.type = FlutterMutatorsStack.FlutterMutatorType.CLIP_RECT;
         this.rect = var2;
      }

      public FlutterMutator(FlutterMutatorsStack var1, Rect var2, float[] var3) {
         this.this$0 = var1;
         this.type = FlutterMutatorsStack.FlutterMutatorType.CLIP_RRECT;
         this.rect = var2;
         this.radiis = var3;
      }

      public Matrix getMatrix() {
         return this.matrix;
      }

      public Path getPath() {
         return this.path;
      }

      public Rect getRect() {
         return this.rect;
      }

      public FlutterMutatorsStack.FlutterMutatorType getType() {
         return this.type;
      }
   }

   public static enum FlutterMutatorType {
      CLIP_PATH,
      CLIP_RECT,
      CLIP_RRECT,
      OPACITY,
      TRANSFORM;
      private static final FlutterMutatorsStack.FlutterMutatorType[] $VALUES = $values();
   }
}
