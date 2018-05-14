package sagsaguz.portfolio.utils;

import android.content.Context;
import android.os.Build;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class TextViewFactory implements  ViewSwitcher.ViewFactory {

    @StyleRes
    final int styleId;
    final boolean center;
    private Context context;

    public TextViewFactory(Context context, @StyleRes int styleId, boolean center) {
        this.context = context;
        this.styleId = styleId;
        this.center = center;
    }

    @SuppressWarnings("deprecation")
    @Override
    public View makeView() {
        final TextView textView = new TextView(context);

        if (center) {
            textView.setGravity(Gravity.CENTER);
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            textView.setTextAppearance(context, styleId);
        } else {
            textView.setTextAppearance(styleId);
        }

        return textView;
    }

}
