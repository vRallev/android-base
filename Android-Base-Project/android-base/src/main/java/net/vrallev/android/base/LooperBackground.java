package net.vrallev.android.base;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * @author Ralf Wondratschek
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface LooperBackground {
}
