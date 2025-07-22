package kotlin.enums

import kotlin.jvm.internal.markers.KMappedMarker

public sealed interface EnumEntries<E extends java.lang.Enum<E>> : java.util.List<E>, KMappedMarker
