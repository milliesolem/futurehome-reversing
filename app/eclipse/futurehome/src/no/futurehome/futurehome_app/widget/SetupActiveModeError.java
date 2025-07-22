package no.futurehome.futurehome_app.widget;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

@Metadata(d1 = {"\000\f\n\002\030\002\n\002\020\020\n\002\b\t\b\002\030\0002\b\022\004\022\0020\0000\001B\t\b\002¢\006\004\b\002\020\003j\002\b\004j\002\b\005j\002\b\006j\002\b\007j\002\b\bj\002\b\t¨\006\n"}, d2 = {"Lno/futurehome/futurehome_app/widget/SetupActiveModeError;", "", "<init>", "(Ljava/lang/String;I)V", "None", "NoSiteTokenHash", "NoRuntimeEnv", "NoActiveSiteId", "FetchError", "HubOffline", "app_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
enum SetupActiveModeError {
  FetchError, HubOffline, NoActiveSiteId, NoRuntimeEnv, NoSiteTokenHash, None;
  
  private static final EnumEntries $ENTRIES;
  
  private static final SetupActiveModeError[] $VALUES;
  
  static {
    NoSiteTokenHash = new SetupActiveModeError("NoSiteTokenHash", 1);
    NoRuntimeEnv = new SetupActiveModeError("NoRuntimeEnv", 2);
    NoActiveSiteId = new SetupActiveModeError("NoActiveSiteId", 3);
    FetchError = new SetupActiveModeError("FetchError", 4);
    HubOffline = new SetupActiveModeError("HubOffline", 5);
    SetupActiveModeError[] arrayOfSetupActiveModeError = $values();
    $VALUES = arrayOfSetupActiveModeError;
    $ENTRIES = EnumEntriesKt.enumEntries((Enum[])arrayOfSetupActiveModeError);
  }
  
  public static EnumEntries<SetupActiveModeError> getEntries() {
    return $ENTRIES;
  }
}
