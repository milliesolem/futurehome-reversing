package no.futurehome.futurehome_app.widget.models

import android.widget.RemoteViews

public interface Row {
   public abstract fun getRemoteViews(rowIndex: Int): RemoteViews {
   }
}
