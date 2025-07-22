package io.sentry.clientreport;

import io.sentry.util.Objects;

final class ClientReportKey {
   private final String category;
   private final String reason;

   ClientReportKey(String var1, String var2) {
      this.reason = var1;
      this.category = var2;
   }

   @Override
   public boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof ClientReportKey)) {
         return false;
      } else {
         var1 = var1;
         if (!Objects.equals(this.getReason(), var1.getReason()) || !Objects.equals(this.getCategory(), var1.getCategory())) {
            var2 = false;
         }

         return var2;
      }
   }

   public String getCategory() {
      return this.category;
   }

   public String getReason() {
      return this.reason;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getReason(), this.getCategory());
   }
}
