package io.sentry.util.thread;

import io.sentry.protocol.SentryThread;

public interface IMainThreadChecker {
   boolean isMainThread();

   boolean isMainThread(long var1);

   boolean isMainThread(SentryThread var1);

   boolean isMainThread(Thread var1);
}
