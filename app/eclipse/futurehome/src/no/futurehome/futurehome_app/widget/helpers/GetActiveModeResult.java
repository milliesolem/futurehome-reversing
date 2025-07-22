package no.futurehome.futurehome_app.widget.helpers;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

@Metadata(d1 = {"\000\f\n\002\030\002\n\002\020\020\n\002\b\t\b\002\030\0002\b\022\004\022\0020\0000\001B\t\b\002¢\006\004\b\002\020\003j\002\b\004j\002\b\005j\002\b\006j\002\b\007j\002\b\bj\002\b\t¨\006\n"}, d2 = {"Lno/futurehome/futurehome_app/widget/helpers/GetActiveModeResult;", "", "<init>", "(Ljava/lang/String;I)V", "FetchError", "HubTimeout", "HomeMode", "AwayMode", "SleepMode", "VacationMode", "app_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
public enum GetActiveModeResult {
  AwayMode, FetchError, HomeMode, HubTimeout, SleepMode, VacationMode;
  
  private static final EnumEntries $ENTRIES;
  
  private static final GetActiveModeResult[] $VALUES;
  
  static {
    HomeMode = new GetActiveModeResult("HomeMode", 2);
    AwayMode = new GetActiveModeResult("AwayMode", 3);
    SleepMode = new GetActiveModeResult("SleepMode", 4);
    VacationMode = new GetActiveModeResult("VacationMode", 5);
    GetActiveModeResult[] arrayOfGetActiveModeResult = $values();
    $VALUES = arrayOfGetActiveModeResult;
    $ENTRIES = EnumEntriesKt.enumEntries((Enum[])arrayOfGetActiveModeResult);
  }
  
  public static EnumEntries<GetActiveModeResult> getEntries() {
    return $ENTRIES;
  }
}
