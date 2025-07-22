package io.sentry;

import io.sentry.protocol.ViewHierarchy;
import java.io.File;

public final class Attachment {
   private static final String DEFAULT_ATTACHMENT_TYPE = "event.attachment";
   private static final String VIEW_HIERARCHY_ATTACHMENT_TYPE = "event.view_hierarchy";
   private final boolean addToTransactions;
   private String attachmentType;
   private byte[] bytes;
   private final String contentType;
   private final String filename;
   private String pathname;
   private final JsonSerializable serializable;

   public Attachment(JsonSerializable var1, String var2, String var3, String var4, boolean var5) {
      this.bytes = null;
      this.serializable = var1;
      this.filename = var2;
      this.contentType = var3;
      this.attachmentType = var4;
      this.addToTransactions = var5;
   }

   public Attachment(String var1) {
      this(var1, new File(var1).getName());
   }

   public Attachment(String var1, String var2) {
      this(var1, var2, null);
   }

   public Attachment(String var1, String var2, String var3) {
      this(var1, var2, var3, "event.attachment", false);
   }

   public Attachment(String var1, String var2, String var3, String var4, boolean var5) {
      this.pathname = var1;
      this.filename = var2;
      this.serializable = null;
      this.contentType = var3;
      this.attachmentType = var4;
      this.addToTransactions = var5;
   }

   public Attachment(String var1, String var2, String var3, boolean var4) {
      this.attachmentType = "event.attachment";
      this.pathname = var1;
      this.filename = var2;
      this.serializable = null;
      this.contentType = var3;
      this.addToTransactions = var4;
   }

   public Attachment(String var1, String var2, String var3, boolean var4, String var5) {
      this.pathname = var1;
      this.filename = var2;
      this.serializable = null;
      this.contentType = var3;
      this.addToTransactions = var4;
      this.attachmentType = var5;
   }

   public Attachment(byte[] var1, String var2) {
      this(var1, var2, null);
   }

   public Attachment(byte[] var1, String var2, String var3) {
      this(var1, var2, var3, false);
   }

   public Attachment(byte[] var1, String var2, String var3, String var4, boolean var5) {
      this.bytes = var1;
      this.serializable = null;
      this.filename = var2;
      this.contentType = var3;
      this.attachmentType = var4;
      this.addToTransactions = var5;
   }

   public Attachment(byte[] var1, String var2, String var3, boolean var4) {
      this(var1, var2, var3, "event.attachment", var4);
   }

   public static Attachment fromScreenshot(byte[] var0) {
      return new Attachment(var0, "screenshot.png", "image/png", false);
   }

   public static Attachment fromThreadDump(byte[] var0) {
      return new Attachment(var0, "thread-dump.txt", "text/plain", false);
   }

   public static Attachment fromViewHierarchy(ViewHierarchy var0) {
      return new Attachment(var0, "view-hierarchy.json", "application/json", "event.view_hierarchy", false);
   }

   public String getAttachmentType() {
      return this.attachmentType;
   }

   public byte[] getBytes() {
      return this.bytes;
   }

   public String getContentType() {
      return this.contentType;
   }

   public String getFilename() {
      return this.filename;
   }

   public String getPathname() {
      return this.pathname;
   }

   public JsonSerializable getSerializable() {
      return this.serializable;
   }

   boolean isAddToTransactions() {
      return this.addToTransactions;
   }
}
