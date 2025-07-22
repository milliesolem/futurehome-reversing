package no.futurehome.futurehome_app.widget.models;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

@Metadata(d1 = {"\000\f\n\002\030\002\n\002\020\020\n\002\b\r\b\002\030\0002\b\022\004\022\0020\0000\001B\t\b\002¢\006\004\b\002\020\003j\002\b\004j\002\b\005j\002\b\006j\002\b\007j\002\b\bj\002\b\tj\002\b\nj\002\b\013j\002\b\fj\002\b\r¨\006\016"}, d2 = {"Lno/futurehome/futurehome_app/widget/models/WidgetState;", "", "<init>", "(Ljava/lang/String;I)V", "NoConnection", "OpenAppError", "NoUser", "GeneralError", "PresentingSites", "NoSites", "NoHub", "HubOffline", "PresentingModesAndShortcuts", "Loading", "app_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
public enum WidgetState {
  GeneralError, HubOffline, Loading, NoConnection, NoHub, NoSites, NoUser, OpenAppError, PresentingModesAndShortcuts, PresentingSites;
  
  private static final EnumEntries $ENTRIES;
  
  private static final WidgetState[] $VALUES;
  
  static {
    NoUser = new WidgetState("NoUser", 2);
    GeneralError = new WidgetState("GeneralError", 3);
    PresentingSites = new WidgetState("PresentingSites", 4);
    NoSites = new WidgetState("NoSites", 5);
    NoHub = new WidgetState("NoHub", 6);
    HubOffline = new WidgetState("HubOffline", 7);
    PresentingModesAndShortcuts = new WidgetState("PresentingModesAndShortcuts", 8);
    Loading = new WidgetState("Loading", 9);
    WidgetState[] arrayOfWidgetState = $values();
    $VALUES = arrayOfWidgetState;
    $ENTRIES = EnumEntriesKt.enumEntries((Enum[])arrayOfWidgetState);
  }
  
  public static EnumEntries<WidgetState> getEntries() {
    return $ENTRIES;
  }
}
