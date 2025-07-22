package kotlinx.android.extensions

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@Retention(RetentionPolicy.RUNTIME)
annotation class ContainerOptions(
   val cache: CacheImplementation = CacheImplementation.HASH_MAP
)
