package junit.framework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public abstract class TestCase extends Assert implements Test {
   private String fName;

   public TestCase() {
      this.fName = null;
   }

   public TestCase(String var1) {
      this.fName = var1;
   }

   public static void assertEquals(byte var0, byte var1) {
      Assert.assertEquals(var0, var1);
   }

   public static void assertEquals(char var0, char var1) {
      Assert.assertEquals(var0, var1);
   }

   public static void assertEquals(double var0, double var2, double var4) {
      Assert.assertEquals(var0, var2, var4);
   }

   public static void assertEquals(float var0, float var1, float var2) {
      Assert.assertEquals(var0, var1, var2);
   }

   public static void assertEquals(int var0, int var1) {
      Assert.assertEquals(var0, var1);
   }

   public static void assertEquals(long var0, long var2) {
      Assert.assertEquals(var0, var2);
   }

   public static void assertEquals(Object var0, Object var1) {
      Assert.assertEquals(var0, var1);
   }

   public static void assertEquals(String var0, byte var1, byte var2) {
      Assert.assertEquals(var0, var1, var2);
   }

   public static void assertEquals(String var0, char var1, char var2) {
      Assert.assertEquals(var0, var1, var2);
   }

   public static void assertEquals(String var0, double var1, double var3, double var5) {
      Assert.assertEquals(var0, var1, var3, var5);
   }

   public static void assertEquals(String var0, float var1, float var2, float var3) {
      Assert.assertEquals(var0, var1, var2, var3);
   }

   public static void assertEquals(String var0, int var1, int var2) {
      Assert.assertEquals(var0, var1, var2);
   }

   public static void assertEquals(String var0, long var1, long var3) {
      Assert.assertEquals(var0, var1, var3);
   }

   public static void assertEquals(String var0, Object var1, Object var2) {
      Assert.assertEquals(var0, var1, var2);
   }

   public static void assertEquals(String var0, String var1) {
      Assert.assertEquals(var0, var1);
   }

   public static void assertEquals(String var0, String var1, String var2) {
      Assert.assertEquals(var0, var1, var2);
   }

   public static void assertEquals(String var0, short var1, short var2) {
      Assert.assertEquals(var0, var1, var2);
   }

   public static void assertEquals(String var0, boolean var1, boolean var2) {
      Assert.assertEquals(var0, var1, var2);
   }

   public static void assertEquals(short var0, short var1) {
      Assert.assertEquals(var0, var1);
   }

   public static void assertEquals(boolean var0, boolean var1) {
      Assert.assertEquals(var0, var1);
   }

   public static void assertFalse(String var0, boolean var1) {
      Assert.assertFalse(var0, var1);
   }

   public static void assertFalse(boolean var0) {
      Assert.assertFalse(var0);
   }

   public static void assertNotNull(Object var0) {
      Assert.assertNotNull(var0);
   }

   public static void assertNotNull(String var0, Object var1) {
      Assert.assertNotNull(var0, var1);
   }

   public static void assertNotSame(Object var0, Object var1) {
      Assert.assertNotSame(var0, var1);
   }

   public static void assertNotSame(String var0, Object var1, Object var2) {
      Assert.assertNotSame(var0, var1, var2);
   }

   public static void assertNull(Object var0) {
      Assert.assertNull(var0);
   }

   public static void assertNull(String var0, Object var1) {
      Assert.assertNull(var0, var1);
   }

   public static void assertSame(Object var0, Object var1) {
      Assert.assertSame(var0, var1);
   }

   public static void assertSame(String var0, Object var1, Object var2) {
      Assert.assertSame(var0, var1, var2);
   }

   public static void assertTrue(String var0, boolean var1) {
      Assert.assertTrue(var0, var1);
   }

   public static void assertTrue(boolean var0) {
      Assert.assertTrue(var0);
   }

   public static void fail() {
      Assert.fail();
   }

   public static void fail(String var0) {
      Assert.fail(var0);
   }

   public static void failNotEquals(String var0, Object var1, Object var2) {
      Assert.failNotEquals(var0, var1, var2);
   }

   public static void failNotSame(String var0, Object var1, Object var2) {
      Assert.failNotSame(var0, var1, var2);
   }

   public static void failSame(String var0) {
      Assert.failSame(var0);
   }

   public static String format(String var0, Object var1, Object var2) {
      return Assert.format(var0, var1, var2);
   }

   @Override
   public int countTestCases() {
      return 1;
   }

   protected TestResult createResult() {
      return new TestResult();
   }

   public String getName() {
      return this.fName;
   }

   public TestResult run() {
      TestResult var1 = this.createResult();
      this.run(var1);
      return var1;
   }

   @Override
   public void run(TestResult var1) {
      var1.run(this);
   }

   // $VF: Could not properly define all variable types!
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public void runBare() throws Throwable {
      this.setUp();

      try {
         this.runTest();
      } finally {
         label77:
         try {
            this.tearDown();
         } finally {
            break label77;
         }

         <unknown> var1;
         if (var1_1 == null) {
            return;
         } else {
            throw var1_1;
         }
      }
   }

   protected void runTest() throws Throwable {
      assertNotNull("TestCase.fName cannot be null", this.fName);

      Method var7;
      try {
         Class var8 = this.getClass();
         String var2 = this.fName;
         Class[] var3 = (Class[])null;
         var7 = var8.getMethod(var2, null);
      } catch (NoSuchMethodException var6) {
         StringBuilder var1 = new StringBuilder("Method \"");
         var1.append(this.fName);
         var1.append("\" not found");
         fail(var1.toString());
         var7 = null;
      }

      if (!Modifier.isPublic(var7.getModifiers())) {
         StringBuilder var9 = new StringBuilder("Method \"");
         var9.append(this.fName);
         var9.append("\" should be public");
         fail(var9.toString());
      }

      try {
         var7.invoke(this, null);
      } catch (InvocationTargetException var4) {
         var4.fillInStackTrace();
         throw var4.getTargetException();
      } catch (IllegalAccessException var5) {
         var5.fillInStackTrace();
         throw var5;
      }
   }

   public void setName(String var1) {
      this.fName = var1;
   }

   protected void setUp() throws Exception {
   }

   protected void tearDown() throws Exception {
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.getName());
      var1.append("(");
      var1.append(this.getClass().getName());
      var1.append(")");
      return var1.toString();
   }
}
