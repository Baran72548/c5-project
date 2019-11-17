package com.barmej.guesstheanswer;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LocaleHelper {
    public static Context setLocale(Context context, String language){
        return updateResourcesLegacy(context, language);
    }

    @SuppressWarnings("deprecation")
    private static Context updateResourcesLegacy(Context context, String language){
        Locale locale = new Locale(language);
        locale.setDefault(locale);

        Resources resouces = context.getResources();

        Configuration configuration = resouces.getConfiguration();
        configuration.locale = locale;

        resouces.updateConfiguration(configuration, resouces.getDisplayMetrics());

        return context;
    }
}
