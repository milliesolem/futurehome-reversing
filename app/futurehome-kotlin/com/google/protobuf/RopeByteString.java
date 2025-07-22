package com.google.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

final class RopeByteString extends ByteString {
   static final int[] minLengthByDepth = new int[]{
      1,
      1,
      2,
      3,
      5,
      8,
      13,
      21,
      34,
      55,
      89,
      144,
      233,
      377,
      610,
      987,
      1597,
      2584,
      4181,
      6765,
      10946,
      17711,
      28657,
      46368,
      75025,
      121393,
      196418,
      317811,
      514229,
      832040,
      1346269,
      2178309,
      3524578,
      5702887,
      9227465,
      14930352,
      24157817,
      39088169,
      63245986,
      102334155,
      165580141,
      267914296,
      433494437,
      701408733,
      1134903170,
      1836311903,
      Integer.MAX_VALUE
   };
   private static final long serialVersionUID = 1L;
   private final ByteString left;
   private final int leftLength;
   private final ByteString right;
   private final int totalLength;
   private final int treeDepth;

   private RopeByteString(ByteString var1, ByteString var2) {
      this.left = var1;
      this.right = var2;
      int var3 = var1.size();
      this.leftLength = var3;
      this.totalLength = var3 + var2.size();
      this.treeDepth = Math.max(var1.getTreeDepth(), var2.getTreeDepth()) + 1;
   }

   static ByteString concatenate(ByteString var0, ByteString var1) {
      if (var1.size() == 0) {
         return var0;
      } else if (var0.size() == 0) {
         return var1;
      } else {
         int var2 = var0.size() + var1.size();
         if (var2 < 128) {
            return concatenateBytes(var0, var1);
         } else {
            if (var0 instanceof RopeByteString) {
               RopeByteString var3 = (RopeByteString)var0;
               if (var3.right.size() + var1.size() < 128) {
                  var0 = concatenateBytes(var3.right, var1);
                  return new RopeByteString(var3.left, var0);
               }

               if (var3.left.getTreeDepth() > var3.right.getTreeDepth() && var3.getTreeDepth() > var1.getTreeDepth()) {
                  RopeByteString var4 = new RopeByteString(var3.right, var1);
                  return new RopeByteString(var3.left, var4);
               }
            }

            return (ByteString)(var2 >= minLength(Math.max(var0.getTreeDepth(), var1.getTreeDepth()) + 1)
               ? new RopeByteString(var0, var1)
               : new RopeByteString.Balancer().balance(var0, var1));
         }
      }
   }

   private static ByteString concatenateBytes(ByteString var0, ByteString var1) {
      int var2 = var0.size();
      int var3 = var1.size();
      byte[] var4 = new byte[var2 + var3];
      var0.copyTo(var4, 0, 0, var2);
      var1.copyTo(var4, 0, var2, var3);
      return ByteString.wrap(var4);
   }

   private boolean equalsFragments(ByteString var1) {
      RopeByteString.PieceIterator var11 = new RopeByteString.PieceIterator(this);
      ByteString.LeafByteString var10 = (ByteString.LeafByteString)var11.next();
      RopeByteString.PieceIterator var12 = new RopeByteString.PieceIterator(var1);
      ByteString.LeafByteString var13 = (ByteString.LeafByteString)var12.next();
      int var3 = 0;
      int var2 = 0;
      int var4 = 0;

      while (true) {
         int var7 = var10.size() - var3;
         int var5 = var13.size() - var2;
         int var6 = Math.min(var7, var5);
         boolean var9;
         if (var3 == 0) {
            var9 = var10.equalsRange(var13, var2, var6);
         } else {
            var9 = var13.equalsRange(var10, var3, var6);
         }

         if (!var9) {
            return false;
         }

         var4 += var6;
         int var8 = this.totalLength;
         if (var4 >= var8) {
            if (var4 == var8) {
               return true;
            }

            throw new IllegalStateException();
         }

         if (var6 == var7) {
            var10 = (ByteString.LeafByteString)var11.next();
            var3 = 0;
         } else {
            var3 += var6;
         }

         if (var6 == var5) {
            var13 = (ByteString.LeafByteString)var12.next();
            var2 = 0;
         } else {
            var2 += var6;
         }
      }
   }

   static int minLength(int var0) {
      int[] var1 = minLengthByDepth;
      return var0 >= var1.length ? Integer.MAX_VALUE : var1[var0];
   }

   static RopeByteString newInstanceForTest(ByteString var0, ByteString var1) {
      return new RopeByteString(var0, var1);
   }

   private void readObject(ObjectInputStream var1) throws IOException {
      throw new InvalidObjectException("RopeByteStream instances are not to be serialized directly");
   }

   @Override
   public ByteBuffer asReadOnlyByteBuffer() {
      return ByteBuffer.wrap(this.toByteArray()).asReadOnlyBuffer();
   }

   @Override
   public List<ByteBuffer> asReadOnlyByteBufferList() {
      ArrayList var2 = new ArrayList();
      RopeByteString.PieceIterator var1 = new RopeByteString.PieceIterator(this);

      while (var1.hasNext()) {
         var2.add(var1.next().asReadOnlyByteBuffer());
      }

      return var2;
   }

   @Override
   public byte byteAt(int var1) {
      checkIndex(var1, this.totalLength);
      return this.internalByteAt(var1);
   }

   @Override
   public void copyTo(ByteBuffer var1) {
      this.left.copyTo(var1);
      this.right.copyTo(var1);
   }

   @Override
   protected void copyToInternal(byte[] var1, int var2, int var3, int var4) {
      int var5 = this.leftLength;
      if (var2 + var4 <= var5) {
         this.left.copyToInternal(var1, var2, var3, var4);
      } else if (var2 >= var5) {
         this.right.copyToInternal(var1, var2 - var5, var3, var4);
      } else {
         var5 -= var2;
         this.left.copyToInternal(var1, var2, var3, var5);
         this.right.copyToInternal(var1, 0, var3 + var5, var4 - var5);
      }
   }

   @Override
   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof ByteString)) {
         return false;
      } else {
         var1 = var1;
         if (this.totalLength != var1.size()) {
            return false;
         } else if (this.totalLength == 0) {
            return true;
         } else {
            int var3 = this.peekCachedHashCode();
            int var2 = var1.peekCachedHashCode();
            return var3 != 0 && var2 != 0 && var3 != var2 ? false : this.equalsFragments(var1);
         }
      }
   }

   @Override
   protected int getTreeDepth() {
      return this.treeDepth;
   }

   @Override
   byte internalByteAt(int var1) {
      int var2 = this.leftLength;
      return var1 < var2 ? this.left.internalByteAt(var1) : this.right.internalByteAt(var1 - var2);
   }

   @Override
   protected boolean isBalanced() {
      boolean var1;
      if (this.totalLength >= minLength(this.treeDepth)) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   @Override
   public boolean isValidUtf8() {
      ByteString var3 = this.left;
      int var1 = this.leftLength;
      boolean var2 = false;
      var1 = var3.partialIsValidUtf8(0, 0, var1);
      var3 = this.right;
      if (var3.partialIsValidUtf8(var1, 0, var3.size()) == 0) {
         var2 = true;
      }

      return var2;
   }

   @Override
   public ByteString.ByteIterator iterator() {
      return new ByteString.AbstractByteIterator(this) {
         ByteString.ByteIterator current;
         final RopeByteString.PieceIterator pieces;
         final RopeByteString this$0;

         {
            this.this$0 = var1;
            this.pieces = new RopeByteString.PieceIterator(var1);
            this.current = this.nextPiece();
         }

         private ByteString.ByteIterator nextPiece() {
            ByteString.ByteIterator var1;
            if (this.pieces.hasNext()) {
               var1 = this.pieces.next().iterator();
            } else {
               var1 = null;
            }

            return var1;
         }

         @Override
         public boolean hasNext() {
            boolean var1;
            if (this.current != null) {
               var1 = true;
            } else {
               var1 = false;
            }

            return var1;
         }

         @Override
         public byte nextByte() {
            ByteString.ByteIterator var2 = this.current;
            if (var2 != null) {
               byte var1 = var2.nextByte();
               if (!this.current.hasNext()) {
                  this.current = this.nextPiece();
               }

               return var1;
            } else {
               throw new NoSuchElementException();
            }
         }
      };
   }

   @Override
   public CodedInputStream newCodedInput() {
      return CodedInputStream.newInstance(this.asReadOnlyByteBufferList(), true);
   }

   @Override
   public InputStream newInput() {
      return new RopeByteString.RopeInputStream(this);
   }

   @Override
   protected int partialHash(int var1, int var2, int var3) {
      int var4 = this.leftLength;
      if (var2 + var3 <= var4) {
         return this.left.partialHash(var1, var2, var3);
      } else if (var2 >= var4) {
         return this.right.partialHash(var1, var2 - var4, var3);
      } else {
         var4 -= var2;
         var1 = this.left.partialHash(var1, var2, var4);
         return this.right.partialHash(var1, 0, var3 - var4);
      }
   }

   @Override
   protected int partialIsValidUtf8(int var1, int var2, int var3) {
      int var4 = this.leftLength;
      if (var2 + var3 <= var4) {
         return this.left.partialIsValidUtf8(var1, var2, var3);
      } else if (var2 >= var4) {
         return this.right.partialIsValidUtf8(var1, var2 - var4, var3);
      } else {
         var4 -= var2;
         var1 = this.left.partialIsValidUtf8(var1, var2, var4);
         return this.right.partialIsValidUtf8(var1, 0, var3 - var4);
      }
   }

   @Override
   public int size() {
      return this.totalLength;
   }

   @Override
   public ByteString substring(int var1, int var2) {
      int var3 = checkRange(var1, var2, this.totalLength);
      if (var3 == 0) {
         return ByteString.EMPTY;
      } else if (var3 == this.totalLength) {
         return this;
      } else {
         var3 = this.leftLength;
         if (var2 <= var3) {
            return this.left.substring(var1, var2);
         } else {
            return (ByteString)(var1 >= var3
               ? this.right.substring(var1 - var3, var2 - var3)
               : new RopeByteString(this.left.substring(var1), this.right.substring(0, var2 - this.leftLength)));
         }
      }
   }

   @Override
   protected String toStringInternal(Charset var1) {
      return new String(this.toByteArray(), var1);
   }

   Object writeReplace() {
      return ByteString.wrap(this.toByteArray());
   }

   @Override
   void writeTo(ByteOutput var1) throws IOException {
      this.left.writeTo(var1);
      this.right.writeTo(var1);
   }

   @Override
   public void writeTo(OutputStream var1) throws IOException {
      this.left.writeTo(var1);
      this.right.writeTo(var1);
   }

   @Override
   void writeToInternal(OutputStream var1, int var2, int var3) throws IOException {
      int var4 = this.leftLength;
      if (var2 + var3 <= var4) {
         this.left.writeToInternal(var1, var2, var3);
      } else if (var2 >= var4) {
         this.right.writeToInternal(var1, var2 - var4, var3);
      } else {
         var4 -= var2;
         this.left.writeToInternal(var1, var2, var4);
         this.right.writeToInternal(var1, 0, var3 - var4);
      }
   }

   @Override
   void writeToReverse(ByteOutput var1) throws IOException {
      this.right.writeToReverse(var1);
      this.left.writeToReverse(var1);
   }

   private static class Balancer {
      private final ArrayDeque<ByteString> prefixesStack = new ArrayDeque<>();

      private Balancer() {
      }

      private ByteString balance(ByteString var1, ByteString var2) {
         this.doBalance(var1);
         this.doBalance(var2);
         var1 = this.prefixesStack.pop();

         while (!this.prefixesStack.isEmpty()) {
            var1 = new RopeByteString(this.prefixesStack.pop(), var1);
         }

         return var1;
      }

      private void doBalance(ByteString var1) {
         if (var1.isBalanced()) {
            this.insert(var1);
         } else {
            if (!(var1 instanceof RopeByteString)) {
               StringBuilder var2 = new StringBuilder("Has a new type of ByteString been created? Found ");
               var2.append(var1.getClass());
               throw new IllegalArgumentException(var2.toString());
            }

            RopeByteString var3 = (RopeByteString)var1;
            this.doBalance(var3.left);
            this.doBalance(var3.right);
         }
      }

      private int getDepthBinForLength(int var1) {
         int var2 = Arrays.binarySearch(RopeByteString.minLengthByDepth, var1);
         var1 = var2;
         if (var2 < 0) {
            var1 = -(var2 + 1) - 1;
         }

         return var1;
      }

      private void insert(ByteString var1) {
         int var2 = this.getDepthBinForLength(var1.size());
         int var3 = RopeByteString.minLength(var2 + 1);
         if (!this.prefixesStack.isEmpty() && this.prefixesStack.peek().size() < var3) {
            var2 = RopeByteString.minLength(var2);
            Object var4 = this.prefixesStack.pop();

            while (!this.prefixesStack.isEmpty() && this.prefixesStack.peek().size() < var2) {
               var4 = new RopeByteString(this.prefixesStack.pop(), (ByteString)var4);
            }

            for (var1 = new RopeByteString((ByteString)var4, var1); !this.prefixesStack.isEmpty(); var1 = new RopeByteString(this.prefixesStack.pop(), var1)) {
               var2 = RopeByteString.minLength(this.getDepthBinForLength(var1.size()) + 1);
               if (this.prefixesStack.peek().size() >= var2) {
                  break;
               }
            }

            this.prefixesStack.push(var1);
         } else {
            this.prefixesStack.push(var1);
         }
      }
   }

   private static final class PieceIterator implements Iterator<ByteString.LeafByteString> {
      private final ArrayDeque<RopeByteString> breadCrumbs;
      private ByteString.LeafByteString next;

      private PieceIterator(ByteString var1) {
         if (var1 instanceof RopeByteString) {
            RopeByteString var2 = (RopeByteString)var1;
            ArrayDeque var3 = new ArrayDeque(var2.getTreeDepth());
            this.breadCrumbs = var3;
            var3.push(var2);
            this.next = this.getLeafByLeft(var2.left);
         } else {
            this.breadCrumbs = null;
            this.next = (ByteString.LeafByteString)var1;
         }
      }

      private ByteString.LeafByteString getLeafByLeft(ByteString var1) {
         while (var1 instanceof RopeByteString) {
            RopeByteString var2 = (RopeByteString)var1;
            this.breadCrumbs.push(var2);
            var1 = var2.left;
         }

         return (ByteString.LeafByteString)var1;
      }

      private ByteString.LeafByteString getNextNonEmptyLeaf() {
         while (true) {
            ArrayDeque var1 = this.breadCrumbs;
            if (var1 != null && !var1.isEmpty()) {
               ByteString.LeafByteString var2 = this.getLeafByLeft(this.breadCrumbs.pop().right);
               if (var2.isEmpty()) {
                  continue;
               }

               return var2;
            }

            return null;
         }
      }

      @Override
      public boolean hasNext() {
         boolean var1;
         if (this.next != null) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public ByteString.LeafByteString next() {
         ByteString.LeafByteString var1 = this.next;
         if (var1 != null) {
            this.next = this.getNextNonEmptyLeaf();
            return var1;
         } else {
            throw new NoSuchElementException();
         }
      }

      @Override
      public void remove() {
         throw new UnsupportedOperationException();
      }
   }

   private class RopeInputStream extends InputStream {
      private ByteString.LeafByteString currentPiece;
      private int currentPieceIndex;
      private int currentPieceOffsetInRope;
      private int currentPieceSize;
      private int mark;
      private RopeByteString.PieceIterator pieceIterator;
      final RopeByteString this$0;

      public RopeInputStream(RopeByteString var1) {
         this.this$0 = var1;
         this.initialize();
      }

      private void advanceIfCurrentPieceFullyRead() {
         if (this.currentPiece != null) {
            int var1 = this.currentPieceIndex;
            int var2 = this.currentPieceSize;
            if (var1 == var2) {
               this.currentPieceOffsetInRope += var2;
               this.currentPieceIndex = 0;
               if (this.pieceIterator.hasNext()) {
                  ByteString.LeafByteString var3 = this.pieceIterator.next();
                  this.currentPiece = var3;
                  this.currentPieceSize = var3.size();
               } else {
                  this.currentPiece = null;
                  this.currentPieceSize = 0;
               }
            }
         }
      }

      private int availableInternal() {
         int var1 = this.currentPieceOffsetInRope;
         int var2 = this.currentPieceIndex;
         return this.this$0.size() - (var1 + var2);
      }

      private void initialize() {
         RopeByteString.PieceIterator var1 = new RopeByteString.PieceIterator(this.this$0);
         this.pieceIterator = var1;
         ByteString.LeafByteString var2 = var1.next();
         this.currentPiece = var2;
         this.currentPieceSize = var2.size();
         this.currentPieceIndex = 0;
         this.currentPieceOffsetInRope = 0;
      }

      private int readSkipInternal(byte[] var1, int var2, int var3) {
         int var4 = var3;

         while (var4 > 0) {
            this.advanceIfCurrentPieceFullyRead();
            if (this.currentPiece == null) {
               break;
            }

            int var6 = Math.min(this.currentPieceSize - this.currentPieceIndex, var4);
            int var5 = var2;
            if (var1 != null) {
               this.currentPiece.copyTo(var1, this.currentPieceIndex, var2, var6);
               var5 = var2 + var6;
            }

            this.currentPieceIndex += var6;
            var4 -= var6;
            var2 = var5;
         }

         return var3 - var4;
      }

      @Override
      public int available() throws IOException {
         return this.availableInternal();
      }

      @Override
      public void mark(int var1) {
         this.mark = this.currentPieceOffsetInRope + this.currentPieceIndex;
      }

      @Override
      public boolean markSupported() {
         return true;
      }

      @Override
      public int read() throws IOException {
         this.advanceIfCurrentPieceFullyRead();
         ByteString.LeafByteString var2 = this.currentPiece;
         if (var2 == null) {
            return -1;
         } else {
            int var1 = this.currentPieceIndex++;
            return var2.byteAt(var1) & 0xFF;
         }
      }

      @Override
      public int read(byte[] var1, int var2, int var3) {
         var1.getClass();
         if (var2 >= 0 && var3 >= 0 && var3 <= var1.length - var2) {
            int var4 = this.readSkipInternal(var1, var2, var3);
            var2 = var4;
            if (var4 == 0) {
               if (var3 <= 0 && this.availableInternal() != 0) {
                  return var4;
               }

               var2 = -1;
            }

            return var2;
         } else {
            throw new IndexOutOfBoundsException();
         }
      }

      @Override
      public void reset() {
         // $VF: Couldn't be decompiled
         // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
         // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
         //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
         //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
         //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
         //   at java.base/java.util.Objects.checkIndex(Objects.java:365)
         //   at java.base/java.util.ArrayList.remove(ArrayList.java:552)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1057)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:572)
         //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
         //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:178)
         //
         // Bytecode:
         // 00: aload 0
         // 01: monitorenter
         // 02: aload 0
         // 03: invokespecial com/google/protobuf/RopeByteString$RopeInputStream.initialize ()V
         // 06: aload 0
         // 07: aconst_null
         // 08: bipush 0
         // 09: aload 0
         // 0a: getfield com/google/protobuf/RopeByteString$RopeInputStream.mark I
         // 0d: invokespecial com/google/protobuf/RopeByteString$RopeInputStream.readSkipInternal ([BII)I
         // 10: pop
         // 11: aload 0
         // 12: monitorexit
         // 13: return
         // 14: astore 1
         // 15: aload 0
         // 16: monitorexit
         // 17: aload 1
         // 18: athrow
      }

      @Override
      public long skip(long var1) {
         if (var1 >= 0L) {
            long var3 = var1;
            if (var1 > 2147483647L) {
               var3 = 2147483647L;
            }

            return this.readSkipInternal(null, 0, (int)var3);
         } else {
            throw new IndexOutOfBoundsException();
         }
      }
   }
}
