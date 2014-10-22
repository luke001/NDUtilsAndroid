package com.nelepovds.ndutils.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Created by dmitrynelepov on 26.09.14.
 */
public class NDUIWidgetHelper {

    public static void initWidgets(Object target) {
        initWidgets(target, null);
    }

    public static void initWidgets(Object target, Object findViewTarget) {
        Class[] types = {int.class};
        Field[] fields = target.getClass().getDeclaredFields();
        for (Field oneField : fields) {
            if (oneField.isAnnotationPresent(NDUIWidget.class)) {
                Boolean isAccessible = oneField.isAccessible();
                oneField.setAccessible(true);
                NDUIWidget uiWidget = oneField.getAnnotation(NDUIWidget.class);
                int valueId = uiWidget.value();
                try {
                    Method method = (findViewTarget != null ? findViewTarget.getClass() : target.getClass()).getMethod("findViewById", types);
                    if (method != null) {
                        try {
                            View widgetValue = (View) method.invoke((findViewTarget != null ? findViewTarget : target), Integer.valueOf(valueId));
                            oneField.set(target, widgetValue);
                            initElseNDWidgetParams(uiWidget, widgetValue);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }

                oneField.setAccessible(isAccessible);
            }
        }
    }

    private static void initElseNDWidgetParams(NDUIWidget uiWidget, View widgetValue) {
        if (uiWidget.textTypeFace().length() > 0) {
            //Try set font
            try {
                Typeface getFont = Typeface.createFromAsset(widgetValue.getContext().getAssets(), uiWidget.textTypeFace());
                setTypeFace(getFont, widgetValue, uiWidget.textTypeFaceStyle());
            } catch (Exception ex) {

            }
        }
    }

    private static void setTypeFace(Typeface typeFace, View widgetValue, int typeFaceStyle) {
        try {
            Class[] types = {Typeface.class, int.class};
            Method method = (widgetValue.getClass()).getMethod("setTypeface", types);
            if (method != null) {
                try {
                    method.invoke(widgetValue, typeFace, Integer.valueOf(typeFaceStyle));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
