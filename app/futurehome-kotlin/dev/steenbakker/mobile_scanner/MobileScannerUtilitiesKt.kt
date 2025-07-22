package dev.steenbakker.mobile_scanner

import android.graphics.Point
import android.graphics.Rect
import android.graphics.YuvImage
import android.media.Image
import com.google.mlkit.vision.barcode.common.Barcode.Address
import com.google.mlkit.vision.barcode.common.Barcode.CalendarDateTime
import com.google.mlkit.vision.barcode.common.Barcode.CalendarEvent
import com.google.mlkit.vision.barcode.common.Barcode.ContactInfo
import com.google.mlkit.vision.barcode.common.Barcode.DriverLicense
import com.google.mlkit.vision.barcode.common.Barcode.Email
import com.google.mlkit.vision.barcode.common.Barcode.GeoPoint
import com.google.mlkit.vision.barcode.common.Barcode.PersonName
import com.google.mlkit.vision.barcode.common.Barcode.Phone
import com.google.mlkit.vision.barcode.common.Barcode.Sms
import com.google.mlkit.vision.barcode.common.Barcode.UrlBookmark
import com.google.mlkit.vision.barcode.common.Barcode.WiFi
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.util.ArrayList

public final val data: Map<String, Any?>
   public final get() {
      val var3: CalendarEvent = var0.getCalendarEvent();
      val var21: java.util.Map;
      if (var3 != null) {
         var21 = getData(var3);
      } else {
         var21 = null;
      }

      val var5: Pair = TuplesKt.to("calendarEvent", var21);
      var var1: Int = 0;
      val var22: ContactInfo = var0.getContactInfo();
      val var23: java.util.Map;
      if (var22 != null) {
         var23 = getData(var22);
      } else {
         var23 = null;
      }

      val var6: Pair = TuplesKt.to("contactInfo", var23);
      val var7: Array<Point> = var0.getCornerPoints();
      val var25: java.util.List;
      if (var7 != null) {
         val var24: java.util.Collection = new ArrayList(var7.length);

         for (int var2 = var7.length; var1 < var2; var1++) {
            val var8: Point = var7[var1];
            var24.add(getData(var8));
         }

         var25 = var24 as java.util.List;
      } else {
         var25 = null;
      }

      val var41: Pair = TuplesKt.to("corners", var25);
      val var42: Pair = TuplesKt.to("displayValue", var0.getDisplayValue());
      val var26: DriverLicense = var0.getDriverLicense();
      val var27: java.util.Map;
      if (var26 != null) {
         var27 = getData(var26);
      } else {
         var27 = null;
      }

      val var9: Pair = TuplesKt.to("driverLicense", var27);
      val var28: Email = var0.getEmail();
      val var29: java.util.Map;
      if (var28 != null) {
         var29 = getData(var28);
      } else {
         var29 = null;
      }

      val var11: Pair = TuplesKt.to("email", var29);
      val var10: Pair = TuplesKt.to("format", var0.getFormat());
      val var30: GeoPoint = var0.getGeoPoint();
      val var31: java.util.Map;
      if (var30 != null) {
         var31 = getData(var30);
      } else {
         var31 = null;
      }

      val var12: Pair = TuplesKt.to("geoPoint", var31);
      val var32: Phone = var0.getPhone();
      val var33: java.util.Map;
      if (var32 != null) {
         var33 = getData(var32);
      } else {
         var33 = null;
      }

      val var13: Pair = TuplesKt.to("phone", var33);
      val var15: Pair = TuplesKt.to("rawBytes", var0.getRawBytes());
      val var14: Pair = TuplesKt.to("rawValue", var0.getRawValue());
      val var34: Rect = var0.getBoundingBox();
      val var35: java.util.Map;
      if (var34 != null) {
         var35 = getSize(var34);
      } else {
         var35 = null;
      }

      val var16: Pair = TuplesKt.to("size", var35);
      val var36: Sms = var0.getSms();
      val var37: java.util.Map;
      if (var36 != null) {
         var37 = getData(var36);
      } else {
         var37 = null;
      }

      val var17: Pair = TuplesKt.to("sms", var37);
      val var18: Pair = TuplesKt.to("type", var0.getValueType());
      val var38: UrlBookmark = var0.getUrl();
      val var39: java.util.Map;
      if (var38 != null) {
         var39 = getData(var38);
      } else {
         var39 = null;
      }

      val var40: Pair = TuplesKt.to("url", var39);
      val var19: WiFi = var0.getWifi();
      var var20: java.util.Map = null;
      if (var19 != null) {
         var20 = getData(var19);
      }

      return MapsKt.mapOf(
         new Pair[]{var5, var6, var41, var42, var9, var11, var10, var12, var13, var15, var14, var16, var17, var18, var40, TuplesKt.to("wifi", var20)}
      );
   }


private final val data: Map<String, Double>
   private final get() {
      return MapsKt.mapOf(new Pair[]{TuplesKt.to("x", (double)var0.x), TuplesKt.to("y", (double)var0.y)});
   }


private final val data: Map<String, Any?>
   private final get() {
      val var3: Pair = TuplesKt.to("description", var0.getDescription());
      val var1: CalendarDateTime = var0.getEnd();
      val var8: java.lang.String;
      if (var1 != null) {
         var8 = var1.getRawValue();
      } else {
         var8 = null;
      }

      val var5: Pair = TuplesKt.to("end", var8);
      val var6: Pair = TuplesKt.to("location", var0.getLocation());
      val var4: Pair = TuplesKt.to("organizer", var0.getOrganizer());
      val var7: CalendarDateTime = var0.getStart();
      var var9: java.lang.String = null;
      if (var7 != null) {
         var9 = var7.getRawValue();
      }

      return MapsKt.mapOf(
         new Pair[]{var3, var5, var6, var4, TuplesKt.to("start", var9), TuplesKt.to("status", var0.getStatus()), TuplesKt.to("summary", var0.getSummary())}
      );
   }


private final val data: Map<String, Any?>
   private final get() {
      var var1: java.util.List = var0.getAddresses();
      val var2: java.lang.Iterable = var1;
      val var8: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var1, 10));

      for (Address var3 : var2) {
         var8.add(getData(var3));
      }

      val var15: Pair = TuplesKt.to("addresses", var8 as java.util.List);
      var1 = var0.getEmails();
      val var16: java.lang.Iterable = var1;
      val var10: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var1, 10));

      for (Email var4 : var16) {
         var10.add(getData(var4));
      }

      val var18: Pair = TuplesKt.to("emails", var10 as java.util.List);
      val var11: PersonName = var0.getName();
      val var12: java.util.Map;
      if (var11 != null) {
         var12 = getData(var11);
      } else {
         var12 = null;
      }

      val var13: Pair = TuplesKt.to("name", var12);
      val var19: Pair = TuplesKt.to("organization", var0.getOrganization());
      val var5: java.util.List = var0.getPhones();
      val var6: java.lang.Iterable = var5;
      val var20: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var5, 10));

      for (Phone var21 : var6) {
         var20.add(getData(var21));
      }

      return MapsKt.mapOf(
         new Pair[]{
            var15,
            var18,
            var13,
            var19,
            TuplesKt.to("phones", var20 as java.util.List),
            TuplesKt.to("title", var0.getTitle()),
            TuplesKt.to("urls", var0.getUrls())
         }
      );
   }


private final val data: Map<String, Any?>
   private final get() {
      val var3: Array<java.lang.String> = var0.getAddressLines();
      val var5: Array<Any> = var3;
      val var4: java.util.Collection = new ArrayList(var3.length);
      val var2: Int = var3.length;

      for (int var1 = 0; var1 < var2; var1++) {
         var4.add((var5[var1] as java.lang.String).toString());
      }

      return MapsKt.mapOf(new Pair[]{TuplesKt.to("addressLines", var4 as java.util.List), TuplesKt.to("type", var0.getType())});
   }


private final val data: Map<String, Any?>
   private final get() {
      return MapsKt.mapOf(
         new Pair[]{
            TuplesKt.to("first", var0.getFirst()),
            TuplesKt.to("formattedName", var0.getFormattedName()),
            TuplesKt.to("last", var0.getLast()),
            TuplesKt.to("middle", var0.getMiddle()),
            TuplesKt.to("prefix", var0.getPrefix()),
            TuplesKt.to("pronunciation", var0.getPronunciation()),
            TuplesKt.to("suffix", var0.getSuffix())
         }
      );
   }


private final val data: Map<String, Any?>
   private final get() {
      return MapsKt.mapOf(
         new Pair[]{
            TuplesKt.to("addressCity", var0.getAddressCity()),
            TuplesKt.to("addressState", var0.getAddressState()),
            TuplesKt.to("addressStreet", var0.getAddressStreet()),
            TuplesKt.to("addressZip", var0.getAddressZip()),
            TuplesKt.to("birthDate", var0.getBirthDate()),
            TuplesKt.to("documentType", var0.getDocumentType()),
            TuplesKt.to("expiryDate", var0.getExpiryDate()),
            TuplesKt.to("firstName", var0.getFirstName()),
            TuplesKt.to("gender", var0.getGender()),
            TuplesKt.to("issueDate", var0.getIssueDate()),
            TuplesKt.to("issuingCountry", var0.getIssuingCountry()),
            TuplesKt.to("lastName", var0.getLastName()),
            TuplesKt.to("licenseNumber", var0.getLicenseNumber()),
            TuplesKt.to("middleName", var0.getMiddleName())
         }
      );
   }


private final val data: Map<String, Any?>
   private final get() {
      return MapsKt.mapOf(
         new Pair[]{
            TuplesKt.to("address", var0.getAddress()),
            TuplesKt.to("body", var0.getBody()),
            TuplesKt.to("subject", var0.getSubject()),
            TuplesKt.to("type", var0.getType())
         }
      );
   }


private final val data: Map<String, Any?>
   private final get() {
      return MapsKt.mapOf(new Pair[]{TuplesKt.to("latitude", var0.getLat()), TuplesKt.to("longitude", var0.getLng())});
   }


private final val data: Map<String, Any?>
   private final get() {
      return MapsKt.mapOf(new Pair[]{TuplesKt.to("number", var0.getNumber()), TuplesKt.to("type", var0.getType())});
   }


private final val data: Map<String, Any?>
   private final get() {
      return MapsKt.mapOf(new Pair[]{TuplesKt.to("message", var0.getMessage()), TuplesKt.to("phoneNumber", var0.getPhoneNumber())});
   }


private final val data: Map<String, Any?>
   private final get() {
      return MapsKt.mapOf(new Pair[]{TuplesKt.to("title", var0.getTitle()), TuplesKt.to("url", var0.getUrl())});
   }


private final val data: Map<String, Any?>
   private final get() {
      return MapsKt.mapOf(
         new Pair[]{TuplesKt.to("encryptionType", var0.getEncryptionType()), TuplesKt.to("password", var0.getPassword()), TuplesKt.to("ssid", var0.getSsid())}
      );
   }


private final val size: Map<String, Any?>
   private final get() {
      return if (var0.left <= var0.right && var0.top <= var0.bottom)
         MapsKt.mapOf(new Pair[]{TuplesKt.to("width", (double)var0.width()), TuplesKt.to("height", (double)var0.height())})
         else
         MapsKt.emptyMap();
   }


public fun Image.toByteArray(): ByteArray {
   val var5: ByteBuffer = var0.getPlanes()[0].getBuffer();
   val var3: ByteBuffer = var0.getPlanes()[2].getBuffer();
   val var1: Int = var5.remaining();
   val var2: Int = var3.remaining();
   val var4: ByteArray = new byte[var1 + var2];
   var5.get(var4, 0, var1);
   var3.get(var4, var1, var2);
   val var8: YuvImage = new YuvImage(var4, 17, var0.getWidth(), var0.getHeight(), null);
   val var6: ByteArrayOutputStream = new ByteArrayOutputStream();
   var8.compressToJpeg(new Rect(0, 0, var8.getWidth(), var8.getHeight()), 50, var6);
   val var7: ByteArray = var6.toByteArray();
   return var7;
}
