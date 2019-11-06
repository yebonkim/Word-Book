package example.com.englishnote;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.balysv.materialmenu.MaterialMenuDrawable;

/**
 * Created by yebonkim on 2017. 3. 20..
 */

public class ActionBarManager {

    public static void initBackArrowActionbar(AppCompatActivity activity, Toolbar toolbar, String title) {
        initDefault(activity, toolbar, title);

        MaterialMenuDrawable menuDrawable = new MaterialMenuDrawable(activity, Color.WHITE, MaterialMenuDrawable.Stroke.THIN);
        menuDrawable.setIconState(MaterialMenuDrawable.IconState.ARROW);

        toolbar.setNavigationIcon(menuDrawable);
    }

    protected static void initDefault(AppCompatActivity activity, Toolbar toolbar, String title) {
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle(title);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
