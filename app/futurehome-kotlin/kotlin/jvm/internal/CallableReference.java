package kotlin.jvm.internal;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import kotlin.jvm.KotlinReflectionNotSupportedError;
import kotlin.reflect.KCallable;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KParameter;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeParameter;
import kotlin.reflect.KVisibility;

public abstract class CallableReference implements KCallable, Serializable {
   public static final Object NO_RECEIVER = CallableReference.NoReceiver.INSTANCE;
   private final boolean isTopLevel;
   private final String name;
   private final Class owner;
   protected final Object receiver;
   private transient KCallable reflected;
   private final String signature;

   public CallableReference() {
      this(NO_RECEIVER);
   }

   protected CallableReference(Object var1) {
      this(var1, null, null, null, false);
   }

   protected CallableReference(Object var1, Class var2, String var3, String var4, boolean var5) {
      this.receiver = var1;
      this.owner = var2;
      this.name = var3;
      this.signature = var4;
      this.isTopLevel = var5;
   }

   @Override
   public Object call(Object... var1) {
      return this.getReflected().call(var1);
   }

   @Override
   public Object callBy(Map var1) {
      return this.getReflected().callBy(var1);
   }

   public KCallable compute() {
      KCallable var2 = this.reflected;
      KCallable var1 = var2;
      if (var2 == null) {
         var1 = this.computeReflected();
         this.reflected = var1;
      }

      return var1;
   }

   protected abstract KCallable computeReflected();

   @Override
   public List<Annotation> getAnnotations() {
      return this.getReflected().getAnnotations();
   }

   public Object getBoundReceiver() {
      return this.receiver;
   }

   @Override
   public String getName() {
      return this.name;
   }

   public KDeclarationContainer getOwner() {
      Class var1 = this.owner;
      Object var2;
      if (var1 == null) {
         var2 = null;
      } else if (this.isTopLevel) {
         var2 = Reflection.getOrCreateKotlinPackage(var1);
      } else {
         var2 = Reflection.getOrCreateKotlinClass(var1);
      }

      return (KDeclarationContainer)var2;
   }

   @Override
   public List<KParameter> getParameters() {
      return this.getReflected().getParameters();
   }

   protected KCallable getReflected() {
      KCallable var1 = this.compute();
      if (var1 != this) {
         return var1;
      } else {
         throw new KotlinReflectionNotSupportedError();
      }
   }

   @Override
   public KType getReturnType() {
      return this.getReflected().getReturnType();
   }

   public String getSignature() {
      return this.signature;
   }

   @Override
   public List<KTypeParameter> getTypeParameters() {
      return this.getReflected().getTypeParameters();
   }

   @Override
   public KVisibility getVisibility() {
      return this.getReflected().getVisibility();
   }

   @Override
   public boolean isAbstract() {
      return this.getReflected().isAbstract();
   }

   @Override
   public boolean isFinal() {
      return this.getReflected().isFinal();
   }

   @Override
   public boolean isOpen() {
      return this.getReflected().isOpen();
   }

   @Override
   public boolean isSuspend() {
      return this.getReflected().isSuspend();
   }

   private static class NoReceiver implements Serializable {
      private static final CallableReference.NoReceiver INSTANCE = new CallableReference.NoReceiver();

      private Object readResolve() throws ObjectStreamException {
         return INSTANCE;
      }
   }
}
