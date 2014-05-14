package net.vrallev.android.base;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ralf Wondratschek
 */
public class SaveInstanceHelper {

    private final BaseActivitySupport mActivity;
    private final Object mTarget;

    public SaveInstanceHelper(BaseActivitySupport activity) {
        this(activity, activity);
    }

    public SaveInstanceHelper(BaseActivitySupport activity, Object target) {
        mActivity = activity;
        mTarget = target;
    }

    public void onPreOnCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Map<String, Field> annotatedFields = getAnnotatedFields(mTarget.getClass(), RetainPrimitive.class, null);
            for (String key : annotatedFields.keySet()) {
                loadSavedStatePrimitive(savedInstanceState, key, annotatedFields.get(key));
            }
        }
    }

    public void onPostOnCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Map<String, Field> annotatedFields = getAnnotatedFields(mTarget.getClass(), RetainObject.class, null);
            for (String key : annotatedFields.keySet()) {
                loadSavedStateObject(mActivity.load(key), annotatedFields.get(key));
            }
        }
    }

    public void onPreSaveInstanceState(Bundle outState) {
        Map<String, Field> annotatedFields = getAnnotatedFields(mTarget.getClass(), RetainObject.class, null);
        for (String key : annotatedFields.keySet()) {
            Field field = annotatedFields.get(key);
            field.setAccessible(true);

            Object value;
            try {
                value = field.get(mTarget);
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException(e);
            }

            mActivity.put(key, value);
        }
    }

    public void onPostSaveInstanceState(Bundle outState) {
        Map<String, Field> annotatedFields = getAnnotatedFields(mTarget.getClass(), RetainPrimitive.class, null);
        for (String key : annotatedFields.keySet()) {
            fillOutState(outState, key, annotatedFields.get(key));
        }
    }

    protected void loadSavedStatePrimitive(Bundle bundle, String key, Field field) {
        field.setAccessible(true);

        try {
            Class<?> type = field.getType();

            if (type.equals(String.class)) {
                field.set(mTarget, bundle.getString(key, null));
            } else if (type.equals(Integer.TYPE)) {
                field.setInt(mTarget, bundle.getInt(key, 0));
            } else if (type.equals(Long.TYPE)) {
                field.setLong(mTarget, bundle.getLong(key, 0L));
            } else if (type.equals(Boolean.TYPE)) {
                field.setBoolean(mTarget, bundle.getBoolean(key, false));
            } else if (type.equals(byte[].class)) {
                field.set(mTarget, bundle.getByteArray(key));
            } else if (type.isAssignableFrom(Parcelable.class)) {
                field.set(mTarget, bundle.getParcelable(key));
            } else {
                throw new IllegalArgumentException();
            }

        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    protected void loadSavedStateObject(Object value, Field field) {
        field.setAccessible(true);
        try {
            field.set(mTarget, value);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    protected void fillOutState(Bundle bundle, String key, Field field) {
        field.setAccessible(true);

        Object value;
        try {
            value = field.get(mTarget);
            if (value == null) {
                return;
            }

        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }

        if (value instanceof String) {
            bundle.putString(key, (String) value);
        } else if (value instanceof Integer) {
            bundle.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            bundle.putLong(key, (Long) value);
        } else if (value instanceof Boolean) {
            bundle.putBoolean(key, (Boolean) value);
        } else if (value instanceof byte[]) {
            bundle.putByteArray(key, (byte[]) value);
        } else if (field.getType().isAssignableFrom(Parcelable.class)) {
            bundle.putParcelable(key, (Parcelable) value);
        } else {
            throw new IllegalArgumentException();
        }
    }

    protected Map<String, Field> getAnnotatedFields(Class<?> clazz, Class<? extends Annotation> annotation, Map<String, Field> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        if (clazz.equals(FragmentActivity.class) || clazz.equals(Fragment.class) || clazz.equals(DialogFragment.class)) {
            return map;
        }

        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(annotation)) {
                map.put(clazz.getName() + "_" + declaredField.getName(), declaredField);
            }
        }

        return getAnnotatedFields(clazz.getSuperclass(), annotation, map);
    }
}
