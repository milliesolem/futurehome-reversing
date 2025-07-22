package no.futurehome.futurehome_app.widget.models;

import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

@Metadata(d1 = {"\000\f\n\002\030\002\n\002\020\020\n\002\b\b\b\002\030\0002\b\022\004\022\0020\0000\001B\t\b\002¢\006\004\b\002\020\003j\002\b\004j\002\b\005j\002\b\006j\002\b\007j\002\b\b¨\006\t"}, d2 = {"Lno/futurehome/futurehome_app/widget/models/Language;", "", "<init>", "(Ljava/lang/String;I)V", "EN", "FI", "NB", "DA", "SV", "app_release"}, k = 1, mv = {2, 1, 0}, xi = 48)
public enum Language {
  DA, EN, FI, NB, SV;
  
  private static final EnumEntries $ENTRIES;
  
  private static final Language[] $VALUES;
  
  static {
    DA = new Language("DA", 3);
    SV = new Language("SV", 4);
    Language[] arrayOfLanguage = $values();
    $VALUES = arrayOfLanguage;
    $ENTRIES = EnumEntriesKt.enumEntries((Enum[])arrayOfLanguage);
  }
  
  public static EnumEntries<Language> getEntries() {
    return $ENTRIES;
  }
}
