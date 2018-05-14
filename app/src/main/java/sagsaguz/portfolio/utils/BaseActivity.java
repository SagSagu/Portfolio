package sagsaguz.portfolio.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import static sagsaguz.portfolio.utils.ThemeUtil.THEME_RED;

public class BaseActivity extends AppCompatActivity {
    public static int mTheme = THEME_RED;
    public static boolean isNightMode = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(ThemeUtil.getThemeId(mTheme));
    }


}
