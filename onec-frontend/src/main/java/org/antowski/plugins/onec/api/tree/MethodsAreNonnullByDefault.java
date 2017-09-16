package org.antowski.plugins.onec.api.tree;

import javax.annotation.Nonnull;
import javax.annotation.meta.TypeQualifierDefault;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;

@Nonnull
@TypeQualifierDefault({ElementType.METHOD})
@Documented
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface MethodsAreNonnullByDefault {
}