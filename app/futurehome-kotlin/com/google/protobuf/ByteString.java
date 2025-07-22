package com.google.protobuf;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

@CheckReturnValue
public abstract class ByteString implements Iterable<Byte>, Serializable {
   static final int CONCATENATE_BY_COPY_SIZE = 128;
   public static final ByteString EMPTY = new ByteString.LiteralByteString(Internal.EMPTY_BYTE_ARRAY);
   static final int MAX_READ_FROM_CHUNK_SIZE = 8192;
   static final int MIN_READ_FROM_CHUNK_SIZE = 256;
   private static final int UNSIGNED_BYTE_MASK = 255;
   private static final Comparator<ByteString> UNSIGNED_LEXICOGRAPHICAL_COMPARATOR;
   private static final ByteString.ByteArrayCopier byteArrayCopier;
   private static final long serialVersionUID = 1L;
   private int hash = 0;

   static {
      Object var0;
      if (Android.isOnAndroidDevice()) {
         var0 = new ByteString.SystemByteArrayCopier();
      } else {
         var0 = new ByteString.ArraysByteArrayCopier();
      }

      byteArrayCopier = (ByteString.ByteArrayCopier)var0;
      UNSIGNED_LEXICOGRAPHICAL_COMPARATOR = new Comparator<ByteString>() {
         public int compare(ByteString var1, ByteString var2) {
            ByteString.ByteIterator var4 = var1.iterator();
            ByteString.ByteIterator var5 = var2.iterator();

            while (var4.hasNext() && var5.hasNext()) {
               int var3 = Integer.valueOf(ByteString.toInt(var4.nextByte())).compareTo(ByteString.toInt(var5.nextByte()));
               if (var3 != 0) {
                  return var3;
               }
            }

            return Integer.valueOf(var1.size()).compareTo(var2.size());
         }
      };
   }

   ByteString() {
   }

   private static ByteString balancedConcat(Iterator<ByteString> var0, int var1) {
      if (var1 >= 1) {
         ByteString var3;
         if (var1 == 1) {
            var3 = (ByteString)var0.next();
         } else {
            int var2 = var1 >>> 1;
            var3 = balancedConcat(var0, var2).concat(balancedConcat(var0, var1 - var2));
         }

         return var3;
      } else {
         throw new IllegalArgumentException(String.format("length (%s) must be >= 1", var1));
      }
   }

   static void checkIndex(int var0, int var1) {
      if ((var1 - (var0 + 1) | var0) < 0) {
         if (var0 < 0) {
            StringBuilder var3 = new StringBuilder("Index < 0: ");
            var3.append(var0);
            throw new ArrayIndexOutOfBoundsException(var3.toString());
         } else {
            StringBuilder var2 = new StringBuilder("Index > length: ");
            var2.append(var0);
            var2.append(", ");
            var2.append(var1);
            throw new ArrayIndexOutOfBoundsException(var2.toString());
         }
      }
   }

   static int checkRange(int var0, int var1, int var2) {
      int var3 = var1 - var0;
      if ((var0 | var1 | var3 | var2 - var1) < 0) {
         if (var0 >= 0) {
            if (var1 < var0) {
               StringBuilder var6 = new StringBuilder("Beginning index larger than ending index: ");
               var6.append(var0);
               var6.append(", ");
               var6.append(var1);
               throw new IndexOutOfBoundsException(var6.toString());
            } else {
               StringBuilder var5 = new StringBuilder("End index: ");
               var5.append(var1);
               var5.append(" >= ");
               var5.append(var2);
               throw new IndexOutOfBoundsException(var5.toString());
            }
         } else {
            StringBuilder var4 = new StringBuilder("Beginning index: ");
            var4.append(var0);
            var4.append(" < 0");
            throw new IndexOutOfBoundsException(var4.toString());
         }
      } else {
         return var3;
      }
   }

   public static ByteString copyFrom(Iterable<ByteString> var0) {
      int var2;
      if (!(var0 instanceof Collection)) {
         Iterator var3 = var0.iterator();
         int var1 = 0;

         while (true) {
            var2 = var1;
            if (!var3.hasNext()) {
               break;
            }

            var3.next();
            var1++;
         }
      } else {
         var2 = ((Collection)var0).size();
      }

      return var2 == 0 ? EMPTY : balancedConcat(var0.iterator(), var2);
   }

   public static ByteString copyFrom(String var0, String var1) throws UnsupportedEncodingException {
      return new ByteString.LiteralByteString(var0.getBytes(var1));
   }

   public static ByteString copyFrom(String var0, Charset var1) {
      return new ByteString.LiteralByteString(var0.getBytes(var1));
   }

   public static ByteString copyFrom(ByteBuffer var0) {
      return copyFrom(var0, var0.remaining());
   }

   public static ByteString copyFrom(ByteBuffer var0, int var1) {
      checkRange(0, var1, var0.remaining());
      byte[] var2 = new byte[var1];
      var0.get(var2);
      return new ByteString.LiteralByteString(var2);
   }

   public static ByteString copyFrom(byte[] var0) {
      return copyFrom(var0, 0, var0.length);
   }

   public static ByteString copyFrom(byte[] var0, int var1, int var2) {
      checkRange(var1, var1 + var2, var0.length);
      return new ByteString.LiteralByteString(byteArrayCopier.copyFrom(var0, var1, var2));
   }

   public static ByteString copyFromUtf8(String var0) {
      return new ByteString.LiteralByteString(var0.getBytes(Internal.UTF_8));
   }

   public static final ByteString empty() {
      return EMPTY;
   }

   private static int extractHexDigit(String var0, int var1) {
      int var2 = hexDigit(var0.charAt(var1));
      if (var2 != -1) {
         return var2;
      } else {
         StringBuilder var3 = new StringBuilder("Invalid hexString ");
         var3.append(var0);
         var3.append(" must only contain [0-9a-fA-F] but contained ");
         var3.append(var0.charAt(var1));
         var3.append(" at index ");
         var3.append(var1);
         throw new NumberFormatException(var3.toString());
      }
   }

   public static ByteString fromHex(String var0) {
      if (var0.length() % 2 != 0) {
         StringBuilder var6 = new StringBuilder("Invalid hexString ");
         var6.append(var0);
         var6.append(" of length ");
         var6.append(var0.length());
         var6.append(" must be even.");
         throw new NumberFormatException(var6.toString());
      } else {
         int var2 = var0.length() / 2;
         byte[] var5 = new byte[var2];

         for (int var1 = 0; var1 < var2; var1++) {
            int var3 = var1 * 2;
            int var4 = extractHexDigit(var0, var3);
            var5[var1] = (byte)(extractHexDigit(var0, var3 + 1) | var4 << 4);
         }

         return new ByteString.LiteralByteString(var5);
      }
   }

   private static int hexDigit(char var0) {
      if (var0 >= '0' && var0 <= '9') {
         return var0 - 48;
      } else if (var0 >= 'A' && var0 <= 'F') {
         return var0 - 55;
      } else {
         return var0 >= 97 && var0 <= 102 ? var0 - 87 : -1;
      }
   }

   static ByteString.CodedBuilder newCodedBuilder(int var0) {
      return new ByteString.CodedBuilder(var0);
   }

   public static ByteString.Output newOutput() {
      return new ByteString.Output(128);
   }

   public static ByteString.Output newOutput(int var0) {
      return new ByteString.Output(var0);
   }

   private static ByteString readChunk(InputStream var0, int var1) throws IOException {
      byte[] var4 = new byte[var1];
      int var2 = 0;

      while (var2 < var1) {
         int var3 = var0.read(var4, var2, var1 - var2);
         if (var3 == -1) {
            break;
         }

         var2 += var3;
      }

      return var2 == 0 ? null : copyFrom(var4, 0, var2);
   }

   public static ByteString readFrom(InputStream var0) throws IOException {
      return readFrom(var0, 256, 8192);
   }

   public static ByteString readFrom(InputStream var0, int var1) throws IOException {
      return readFrom(var0, var1, var1);
   }

   public static ByteString readFrom(InputStream var0, int var1, int var2) throws IOException {
      ArrayList var4 = new ArrayList();

      while (true) {
         ByteString var3 = readChunk(var0, var1);
         if (var3 == null) {
            return copyFrom(var4);
         }

         var4.add(var3);
         var1 = Math.min(var1 * 2, var2);
      }
   }

   private static int toInt(byte var0) {
      return var0 & 0xFF;
   }

   private String truncateAndEscapeForDisplay() {
      String var1;
      if (this.size() <= 50) {
         var1 = TextFormatEscaper.escapeBytes(this);
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append(TextFormatEscaper.escapeBytes(this.substring(0, 47)));
         var2.append("...");
         var1 = var2.toString();
      }

      return var1;
   }

   public static Comparator<ByteString> unsignedLexicographicalComparator() {
      return UNSIGNED_LEXICOGRAPHICAL_COMPARATOR;
   }

   static ByteString wrap(ByteBuffer var0) {
      if (var0.hasArray()) {
         int var1 = var0.arrayOffset();
         return wrap(var0.array(), var1 + var0.position(), var0.remaining());
      } else {
         return new NioByteString(var0);
      }
   }

   static ByteString wrap(byte[] var0) {
      return new ByteString.LiteralByteString(var0);
   }

   static ByteString wrap(byte[] var0, int var1, int var2) {
      return new ByteString.BoundedByteString(var0, var1, var2);
   }

   public abstract ByteBuffer asReadOnlyByteBuffer();

   public abstract List<ByteBuffer> asReadOnlyByteBufferList();

   public abstract byte byteAt(int var1);

   public final ByteString concat(ByteString var1) {
      if (Integer.MAX_VALUE - this.size() >= var1.size()) {
         return RopeByteString.concatenate(this, var1);
      } else {
         StringBuilder var2 = new StringBuilder("ByteString would be too long: ");
         var2.append(this.size());
         var2.append("+");
         var2.append(var1.size());
         throw new IllegalArgumentException(var2.toString());
      }
   }

   public abstract void copyTo(ByteBuffer var1);

   public void copyTo(byte[] var1, int var2) {
      this.copyTo(var1, 0, var2, this.size());
   }

   @Deprecated
   public final void copyTo(byte[] var1, int var2, int var3, int var4) {
      checkRange(var2, var2 + var4, this.size());
      checkRange(var3, var3 + var4, var1.length);
      if (var4 > 0) {
         this.copyToInternal(var1, var2, var3, var4);
      }
   }

   protected abstract void copyToInternal(byte[] var1, int var2, int var3, int var4);

   public final boolean endsWith(ByteString var1) {
      boolean var2;
      if (this.size() >= var1.size() && this.substring(this.size() - var1.size()).equals(var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   @Override
   public abstract boolean equals(Object var1);

   protected abstract int getTreeDepth();

   @Override
   public final int hashCode() {
      int var2 = this.hash;
      int var1 = var2;
      if (var2 == 0) {
         var1 = this.size();
         var2 = this.partialHash(var1, 0, var1);
         var1 = var2;
         if (var2 == 0) {
            var1 = 1;
         }

         this.hash = var1;
      }

      return var1;
   }

   abstract byte internalByteAt(int var1);

   protected abstract boolean isBalanced();

   public final boolean isEmpty() {
      boolean var1;
      if (this.size() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public abstract boolean isValidUtf8();

   public ByteString.ByteIterator iterator() {
      return new ByteString.AbstractByteIterator(this) {
         private final int limit;
         private int position;
         final ByteString this$0;

         {
            this.this$0 = var1;
            this.position = 0;
            this.limit = var1.size();
         }

         @Override
         public boolean hasNext() {
            boolean var1;
            if (this.position < this.limit) {
               var1 = true;
            } else {
               var1 = false;
            }

            return var1;
         }

         @Override
         public byte nextByte() {
            int var1 = this.position;
            if (var1 < this.limit) {
               this.position = var1 + 1;
               return this.this$0.internalByteAt(var1);
            } else {
               throw new NoSuchElementException();
            }
         }
      };
   }

   public abstract CodedInputStream newCodedInput();

   public abstract InputStream newInput();

   protected abstract int partialHash(int var1, int var2, int var3);

   protected abstract int partialIsValidUtf8(int var1, int var2, int var3);

   protected final int peekCachedHashCode() {
      return this.hash;
   }

   public abstract int size();

   public final boolean startsWith(ByteString var1) {
      int var2 = this.size();
      int var3 = var1.size();
      boolean var5 = false;
      boolean var4 = var5;
      if (var2 >= var3) {
         var4 = var5;
         if (this.substring(0, var1.size()).equals(var1)) {
            var4 = true;
         }
      }

      return var4;
   }

   public final ByteString substring(int var1) {
      return this.substring(var1, this.size());
   }

   public abstract ByteString substring(int var1, int var2);

   public final byte[] toByteArray() {
      int var1 = this.size();
      if (var1 == 0) {
         return Internal.EMPTY_BYTE_ARRAY;
      } else {
         byte[] var2 = new byte[var1];
         this.copyToInternal(var2, 0, 0, var1);
         return var2;
      }
   }

   @Override
   public final String toString() {
      return String.format(
         Locale.ROOT,
         "<ByteString@%s size=%d contents=\"%s\">",
         Integer.toHexString(System.identityHashCode(this)),
         this.size(),
         this.truncateAndEscapeForDisplay()
      );
   }

   public final String toString(String var1) throws UnsupportedEncodingException {
      try {
         return this.toString(Charset.forName(var1));
      } catch (UnsupportedCharsetException var3) {
         UnsupportedEncodingException var4 = new UnsupportedEncodingException(var1);
         var4.initCause(var3);
         throw var4;
      }
   }

   public final String toString(Charset var1) {
      String var2;
      if (this.size() == 0) {
         var2 = "";
      } else {
         var2 = this.toStringInternal(var1);
      }

      return var2;
   }

   protected abstract String toStringInternal(Charset var1);

   public final String toStringUtf8() {
      return this.toString(Internal.UTF_8);
   }

   abstract void writeTo(ByteOutput var1) throws IOException;

   public abstract void writeTo(OutputStream var1) throws IOException;

   final void writeTo(OutputStream var1, int var2, int var3) throws IOException {
      checkRange(var2, var2 + var3, this.size());
      if (var3 > 0) {
         this.writeToInternal(var1, var2, var3);
      }
   }

   abstract void writeToInternal(OutputStream var1, int var2, int var3) throws IOException;

   abstract void writeToReverse(ByteOutput var1) throws IOException;

   abstract static class AbstractByteIterator implements ByteString.ByteIterator {
      public final Byte next() {
         return this.nextByte();
      }

      @Override
      public final void remove() {
         throw new UnsupportedOperationException();
      }
   }

   private static final class ArraysByteArrayCopier implements ByteString.ByteArrayCopier {
      private ArraysByteArrayCopier() {
      }

      @Override
      public byte[] copyFrom(byte[] var1, int var2, int var3) {
         return Arrays.copyOfRange(var1, var2, var3 + var2);
      }
   }

   private static final class BoundedByteString extends ByteString.LiteralByteString {
      private static final long serialVersionUID = 1L;
      private final int bytesLength;
      private final int bytesOffset;

      BoundedByteString(byte[] var1, int var2, int var3) {
         super(var1);
         checkRange(var2, var2 + var3, var1.length);
         this.bytesOffset = var2;
         this.bytesLength = var3;
      }

      private void readObject(ObjectInputStream var1) throws IOException {
         throw new InvalidObjectException("BoundedByteStream instances are not to be serialized directly");
      }

      @Override
      public byte byteAt(int var1) {
         checkIndex(var1, this.size());
         return this.bytes[this.bytesOffset + var1];
      }

      @Override
      protected void copyToInternal(byte[] var1, int var2, int var3, int var4) {
         System.arraycopy(this.bytes, this.getOffsetIntoBytes() + var2, var1, var3, var4);
      }

      @Override
      protected int getOffsetIntoBytes() {
         return this.bytesOffset;
      }

      @Override
      byte internalByteAt(int var1) {
         return this.bytes[this.bytesOffset + var1];
      }

      @Override
      public int size() {
         return this.bytesLength;
      }

      Object writeReplace() {
         return ByteString.wrap(this.toByteArray());
      }
   }

   private interface ByteArrayCopier {
      byte[] copyFrom(byte[] var1, int var2, int var3);
   }

   public interface ByteIterator extends Iterator<Byte> {
      byte nextByte();
   }

   static final class CodedBuilder {
      private final byte[] buffer;
      private final CodedOutputStream output;

      private CodedBuilder(int var1) {
         byte[] var2 = new byte[var1];
         this.buffer = var2;
         this.output = CodedOutputStream.newInstance(var2);
      }

      public ByteString build() {
         this.output.checkNoSpaceLeft();
         return new ByteString.LiteralByteString(this.buffer);
      }

      public CodedOutputStream getCodedOutput() {
         return this.output;
      }
   }

   abstract static class LeafByteString extends ByteString {
      private static final long serialVersionUID = 1L;

      abstract boolean equalsRange(ByteString var1, int var2, int var3);

      @Override
      protected final int getTreeDepth() {
         return 0;
      }

      @Override
      protected final boolean isBalanced() {
         return true;
      }

      @Override
      void writeToReverse(ByteOutput var1) throws IOException {
         this.writeTo(var1);
      }
   }

   private static class LiteralByteString extends ByteString.LeafByteString {
      private static final long serialVersionUID = 1L;
      protected final byte[] bytes;

      LiteralByteString(byte[] var1) {
         var1.getClass();
         this.bytes = var1;
      }

      @Override
      public final ByteBuffer asReadOnlyByteBuffer() {
         return ByteBuffer.wrap(this.bytes, this.getOffsetIntoBytes(), this.size()).asReadOnlyBuffer();
      }

      @Override
      public final List<ByteBuffer> asReadOnlyByteBufferList() {
         return Collections.singletonList(this.asReadOnlyByteBuffer());
      }

      @Override
      public byte byteAt(int var1) {
         return this.bytes[var1];
      }

      @Override
      public final void copyTo(ByteBuffer var1) {
         var1.put(this.bytes, this.getOffsetIntoBytes(), this.size());
      }

      @Override
      protected void copyToInternal(byte[] var1, int var2, int var3, int var4) {
         System.arraycopy(this.bytes, var2, var1, var3, var4);
      }

      @Override
      public final boolean equals(Object var1) {
         if (var1 == this) {
            return true;
         } else if (!(var1 instanceof ByteString)) {
            return false;
         } else if (this.size() != var1.size()) {
            return false;
         } else if (this.size() == 0) {
            return true;
         } else if (var1 instanceof ByteString.LiteralByteString) {
            var1 = var1;
            int var2 = this.peekCachedHashCode();
            int var3 = var1.peekCachedHashCode();
            return var2 != 0 && var3 != 0 && var2 != var3 ? false : this.equalsRange(var1, 0, this.size());
         } else {
            return var1.equals(this);
         }
      }

      @Override
      final boolean equalsRange(ByteString var1, int var2, int var3) {
         if (var3 <= var1.size()) {
            int var4 = var2 + var3;
            if (var4 <= var1.size()) {
               if (var1 instanceof ByteString.LiteralByteString) {
                  ByteString.LiteralByteString var7 = (ByteString.LiteralByteString)var1;
                  byte[] var12 = this.bytes;
                  byte[] var9 = var7.bytes;
                  int var5 = this.getOffsetIntoBytes();
                  var4 = this.getOffsetIntoBytes();

                  for (int var10 = var7.getOffsetIntoBytes() + var2; var4 < var5 + var3; var10++) {
                     if (var12[var4] != var9[var10]) {
                        return false;
                     }

                     var4++;
                  }

                  return true;
               } else {
                  return var1.substring(var2, var4).equals(this.substring(0, var3));
               }
            } else {
               StringBuilder var6 = new StringBuilder("Ran off end of other: ");
               var6.append(var2);
               var6.append(", ");
               var6.append(var3);
               var6.append(", ");
               var6.append(var1.size());
               throw new IllegalArgumentException(var6.toString());
            }
         } else {
            StringBuilder var8 = new StringBuilder("Length too large: ");
            var8.append(var3);
            var8.append(this.size());
            throw new IllegalArgumentException(var8.toString());
         }
      }

      protected int getOffsetIntoBytes() {
         return 0;
      }

      @Override
      byte internalByteAt(int var1) {
         return this.bytes[var1];
      }

      @Override
      public final boolean isValidUtf8() {
         int var1 = this.getOffsetIntoBytes();
         return Utf8.isValidUtf8(this.bytes, var1, this.size() + var1);
      }

      @Override
      public final CodedInputStream newCodedInput() {
         return CodedInputStream.newInstance(this.bytes, this.getOffsetIntoBytes(), this.size(), true);
      }

      @Override
      public final InputStream newInput() {
         return new ByteArrayInputStream(this.bytes, this.getOffsetIntoBytes(), this.size());
      }

      @Override
      protected final int partialHash(int var1, int var2, int var3) {
         return Internal.partialHash(var1, this.bytes, this.getOffsetIntoBytes() + var2, var3);
      }

      @Override
      protected final int partialIsValidUtf8(int var1, int var2, int var3) {
         var2 = this.getOffsetIntoBytes() + var2;
         return Utf8.partialIsValidUtf8(var1, this.bytes, var2, var3 + var2);
      }

      @Override
      public int size() {
         return this.bytes.length;
      }

      @Override
      public final ByteString substring(int var1, int var2) {
         var2 = checkRange(var1, var2, this.size());
         return (ByteString)(var2 == 0 ? ByteString.EMPTY : new ByteString.BoundedByteString(this.bytes, this.getOffsetIntoBytes() + var1, var2));
      }

      @Override
      protected final String toStringInternal(Charset var1) {
         return new String(this.bytes, this.getOffsetIntoBytes(), this.size(), var1);
      }

      @Override
      final void writeTo(ByteOutput var1) throws IOException {
         var1.writeLazy(this.bytes, this.getOffsetIntoBytes(), this.size());
      }

      @Override
      public final void writeTo(OutputStream var1) throws IOException {
         var1.write(this.toByteArray());
      }

      @Override
      final void writeToInternal(OutputStream var1, int var2, int var3) throws IOException {
         var1.write(this.bytes, this.getOffsetIntoBytes() + var2, var3);
      }
   }

   public static final class Output extends OutputStream {
      private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
      private byte[] buffer;
      private int bufferPos;
      private final ArrayList<ByteString> flushedBuffers;
      private int flushedBuffersTotalBytes;
      private final int initialCapacity;

      Output(int var1) {
         if (var1 >= 0) {
            this.initialCapacity = var1;
            this.flushedBuffers = new ArrayList<>();
            this.buffer = new byte[var1];
         } else {
            throw new IllegalArgumentException("Buffer size < 0");
         }
      }

      private void flushFullBuffer(int var1) {
         this.flushedBuffers.add(new ByteString.LiteralByteString(this.buffer));
         int var2 = this.flushedBuffersTotalBytes + this.buffer.length;
         this.flushedBuffersTotalBytes = var2;
         this.buffer = new byte[Math.max(this.initialCapacity, Math.max(var1, var2 >>> 1))];
         this.bufferPos = 0;
      }

      private void flushLastBuffer() {
         int var1 = this.bufferPos;
         byte[] var2 = this.buffer;
         if (var1 < var2.length) {
            if (var1 > 0) {
               var2 = Arrays.copyOf(var2, var1);
               this.flushedBuffers.add(new ByteString.LiteralByteString(var2));
            }
         } else {
            this.flushedBuffers.add(new ByteString.LiteralByteString(this.buffer));
            this.buffer = EMPTY_BYTE_ARRAY;
         }

         this.flushedBuffersTotalBytes = this.flushedBuffersTotalBytes + this.bufferPos;
         this.bufferPos = 0;
      }

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
         // 03: getfield com/google/protobuf/ByteString$Output.flushedBuffers Ljava/util/ArrayList;
         // 06: invokevirtual java/util/ArrayList.clear ()V
         // 09: aload 0
         // 0a: bipush 0
         // 0b: putfield com/google/protobuf/ByteString$Output.flushedBuffersTotalBytes I
         // 0e: aload 0
         // 0f: bipush 0
         // 10: putfield com/google/protobuf/ByteString$Output.bufferPos I
         // 13: aload 0
         // 14: monitorexit
         // 15: return
         // 16: astore 1
         // 17: aload 0
         // 18: monitorexit
         // 19: aload 1
         // 1a: athrow
      }

      public int size() {
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
         // 03: getfield com/google/protobuf/ByteString$Output.flushedBuffersTotalBytes I
         // 06: istore 1
         // 07: aload 0
         // 08: getfield com/google/protobuf/ByteString$Output.bufferPos I
         // 0b: istore 2
         // 0c: aload 0
         // 0d: monitorexit
         // 0e: iload 1
         // 0f: iload 2
         // 10: iadd
         // 11: ireturn
         // 12: astore 3
         // 13: aload 0
         // 14: monitorexit
         // 15: aload 3
         // 16: athrow
      }

      public ByteString toByteString() {
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
         // 03: invokespecial com/google/protobuf/ByteString$Output.flushLastBuffer ()V
         // 06: aload 0
         // 07: getfield com/google/protobuf/ByteString$Output.flushedBuffers Ljava/util/ArrayList;
         // 0a: invokestatic com/google/protobuf/ByteString.copyFrom (Ljava/lang/Iterable;)Lcom/google/protobuf/ByteString;
         // 0d: astore 1
         // 0e: aload 0
         // 0f: monitorexit
         // 10: aload 1
         // 11: areturn
         // 12: astore 1
         // 13: aload 0
         // 14: monitorexit
         // 15: aload 1
         // 16: athrow
      }

      @Override
      public String toString() {
         return String.format("<ByteString.Output@%s size=%d>", Integer.toHexString(System.identityHashCode(this)), this.size());
      }

      @Override
      public void write(int param1) {
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
         // 03: getfield com/google/protobuf/ByteString$Output.bufferPos I
         // 06: aload 0
         // 07: getfield com/google/protobuf/ByteString$Output.buffer [B
         // 0a: arraylength
         // 0b: if_icmpne 13
         // 0e: aload 0
         // 0f: bipush 1
         // 10: invokespecial com/google/protobuf/ByteString$Output.flushFullBuffer (I)V
         // 13: aload 0
         // 14: getfield com/google/protobuf/ByteString$Output.buffer [B
         // 17: astore 3
         // 18: aload 0
         // 19: getfield com/google/protobuf/ByteString$Output.bufferPos I
         // 1c: istore 2
         // 1d: aload 0
         // 1e: iload 2
         // 1f: bipush 1
         // 20: iadd
         // 21: putfield com/google/protobuf/ByteString$Output.bufferPos I
         // 24: aload 3
         // 25: iload 2
         // 26: iload 1
         // 27: i2b
         // 28: bastore
         // 29: aload 0
         // 2a: monitorexit
         // 2b: return
         // 2c: astore 3
         // 2d: aload 0
         // 2e: monitorexit
         // 2f: aload 3
         // 30: athrow
      }

      @Override
      public void write(byte[] param1, int param2, int param3) {
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
         // 03: getfield com/google/protobuf/ByteString$Output.buffer [B
         // 06: astore 6
         // 08: aload 6
         // 0a: arraylength
         // 0b: istore 5
         // 0d: aload 0
         // 0e: getfield com/google/protobuf/ByteString$Output.bufferPos I
         // 11: istore 4
         // 13: iload 3
         // 14: iload 5
         // 16: iload 4
         // 18: isub
         // 19: if_icmpgt 33
         // 1c: aload 1
         // 1d: iload 2
         // 1e: aload 6
         // 20: iload 4
         // 22: iload 3
         // 23: invokestatic java/lang/System.arraycopy (Ljava/lang/Object;ILjava/lang/Object;II)V
         // 26: aload 0
         // 27: aload 0
         // 28: getfield com/google/protobuf/ByteString$Output.bufferPos I
         // 2b: iload 3
         // 2c: iadd
         // 2d: putfield com/google/protobuf/ByteString$Output.bufferPos I
         // 30: goto 63
         // 33: aload 6
         // 35: arraylength
         // 36: iload 4
         // 38: isub
         // 39: istore 5
         // 3b: aload 1
         // 3c: iload 2
         // 3d: aload 6
         // 3f: iload 4
         // 41: iload 5
         // 43: invokestatic java/lang/System.arraycopy (Ljava/lang/Object;ILjava/lang/Object;II)V
         // 46: iload 3
         // 47: iload 5
         // 49: isub
         // 4a: istore 3
         // 4b: aload 0
         // 4c: iload 3
         // 4d: invokespecial com/google/protobuf/ByteString$Output.flushFullBuffer (I)V
         // 50: aload 1
         // 51: iload 2
         // 52: iload 5
         // 54: iadd
         // 55: aload 0
         // 56: getfield com/google/protobuf/ByteString$Output.buffer [B
         // 59: bipush 0
         // 5a: iload 3
         // 5b: invokestatic java/lang/System.arraycopy (Ljava/lang/Object;ILjava/lang/Object;II)V
         // 5e: aload 0
         // 5f: iload 3
         // 60: putfield com/google/protobuf/ByteString$Output.bufferPos I
         // 63: aload 0
         // 64: monitorexit
         // 65: return
         // 66: astore 1
         // 67: aload 0
         // 68: monitorexit
         // 69: aload 1
         // 6a: athrow
      }

      public void writeTo(OutputStream param1) throws IOException {
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
         // 03: getfield com/google/protobuf/ByteString$Output.flushedBuffers Ljava/util/ArrayList;
         // 06: astore 5
         // 08: bipush 0
         // 09: istore 2
         // 0a: aload 5
         // 0c: bipush 0
         // 0d: anewarray 6
         // 10: invokevirtual java/util/ArrayList.toArray ([Ljava/lang/Object;)[Ljava/lang/Object;
         // 13: checkcast [Lcom/google/protobuf/ByteString;
         // 16: astore 5
         // 18: aload 0
         // 19: getfield com/google/protobuf/ByteString$Output.buffer [B
         // 1c: astore 6
         // 1e: aload 0
         // 1f: getfield com/google/protobuf/ByteString$Output.bufferPos I
         // 22: istore 4
         // 24: aload 0
         // 25: monitorexit
         // 26: aload 5
         // 28: arraylength
         // 29: istore 3
         // 2a: iload 2
         // 2b: iload 3
         // 2c: if_icmpge 3d
         // 2f: aload 5
         // 31: iload 2
         // 32: aaload
         // 33: aload 1
         // 34: invokevirtual com/google/protobuf/ByteString.writeTo (Ljava/io/OutputStream;)V
         // 37: iinc 2 1
         // 3a: goto 2a
         // 3d: aload 1
         // 3e: aload 6
         // 40: iload 4
         // 42: invokestatic java/util/Arrays.copyOf ([BI)[B
         // 45: invokevirtual java/io/OutputStream.write ([B)V
         // 48: return
         // 49: astore 1
         // 4a: aload 0
         // 4b: monitorexit
         // 4c: aload 1
         // 4d: athrow
      }
   }

   private static final class SystemByteArrayCopier implements ByteString.ByteArrayCopier {
      private SystemByteArrayCopier() {
      }

      @Override
      public byte[] copyFrom(byte[] var1, int var2, int var3) {
         byte[] var4 = new byte[var3];
         System.arraycopy(var1, var2, var4, 0, var3);
         return var4;
      }
   }
}
