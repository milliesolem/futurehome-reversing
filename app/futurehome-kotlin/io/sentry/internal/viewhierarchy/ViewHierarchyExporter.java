package io.sentry.internal.viewhierarchy;

import io.sentry.protocol.ViewHierarchyNode;

public interface ViewHierarchyExporter {
   boolean export(ViewHierarchyNode var1, Object var2);
}
