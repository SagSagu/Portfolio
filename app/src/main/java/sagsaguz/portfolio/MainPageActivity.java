package sagsaguz.portfolio;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import sagsaguz.portfolio.adapter.ThemeAdapter;
import sagsaguz.portfolio.adapter.ViewPagerAdapter;
import sagsaguz.portfolio.fragment.AboutFragment;
import sagsaguz.portfolio.fragment.CardFragment;
import sagsaguz.portfolio.fragment.ExperienceFragment;
import sagsaguz.portfolio.utils.BaseActivity;
import sagsaguz.portfolio.utils.DrawableToBitmap;
import sagsaguz.portfolio.utils.RecyclerViewClickListener;
import sagsaguz.portfolio.utils.Theme;
import sagsaguz.portfolio.utils.ThemeUtil;
import sagsaguz.portfolio.utils.ThemeView;

import static sagsaguz.portfolio.utils.ThemeUtil.THEME_RED;

public class MainPageActivity extends BaseActivity implements View.OnClickListener, TextToSpeech.OnInitListener{

    private BottomSheetBehavior mBottomSheetBehavior;

    public static List<Theme> mThemeList = new ArrayList<>();
    public static int selectedTheme = 0;
    private ThemeAdapter mAdapter;

    private FloatingActionButton fabLogin;
    private RelativeLayout rlLogin;
    private ImageView ivClose, speechLogin, textLogin;
    private EditText etPassword;

    private final int REQ_CODE_SPEECH_INPUT = 100;

    private TextToSpeech tts;
    @SuppressLint("StaticFieldLeak")
    public static MainPageActivity mainPageActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainPageActivity = this;

        initBottomSheet();

        prepareThemeData();

        tts = new TextToSpeech(this, this);

        fabLogin = findViewById(R.id.fabLogin);
        rlLogin = findViewById(R.id.rlLogin);
        etPassword = findViewById(R.id.etPassword);

        ivClose = findViewById(R.id.ivClose);
        speechLogin = findViewById(R.id.speechLogin);
        textLogin = findViewById(R.id.textLogin);

        changeIconColor();

        ivClose.setOnClickListener(this);
        speechLogin.setOnClickListener(this);
        textLogin.setOnClickListener(this);

        ThemeView themeView = findViewById(R.id.theme_selected);
        themeView.setTheme(mThemeList.get(selectedTheme));

        ViewPager container = findViewById(R.id.container);

        TabLayout tabLayout = findViewById(R.id.tabs);

        setupViewPager(container);
        tabLayout.setupWithViewPager(container);

        TextView tvLogin = findViewById(R.id.tvLogin);
        Typeface tf = Typeface.createFromAsset(getAssets(),"login_font.ttf");
        tvLogin.setTypeface(tf,Typeface.BOLD);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new CardFragment(), "Portfolio");
        adapter.addFrag(new ExperienceFragment(), "Experience");
        adapter.addFrag(new AboutFragment(), "About me");
        viewPager.setAdapter(adapter);
    }

    private void initBottomSheet(){

        LinearLayout llBottomSheet = findViewById(R.id.bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);

        SwitchCompat switchCompat = findViewById(R.id.switch_dark_mode);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isNightMode = b;
                if(mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED){
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                compoundButton.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(isNightMode){
                            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        }else{
                            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        }
                    }
                },200);
            }
        });

        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);

        mAdapter = new ThemeAdapter(mThemeList, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MainPageActivity.this.recreate();
                    }
                },400);
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),4);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void prepareThemeData() {
        mThemeList.clear();
        mThemeList.addAll(ThemeUtil.getThemeList());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.theme_selected :
                switch (mBottomSheetBehavior.getState()){
                    case BottomSheetBehavior.STATE_HIDDEN :
                        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        break;

                    case BottomSheetBehavior.STATE_COLLAPSED :
                        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        break;

                    case BottomSheetBehavior.STATE_EXPANDED :
                        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        break;
                }
                break;
            case R.id.fabLogin:
                fabAnim();
                break;
            case R.id.ivClose:
                reverseFabAnim();
                break;
            case R.id.speechLogin:
                promptSpeechInput();
                break;
            case R.id.textLogin:
                textPasswordLogin();
                break;
        }
    }

    private void fabAnim(){

        Rect rectangle = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarHeight = rectangle.top;
        int contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        int titleBarHeight= contentViewTop - statusBarHeight;

        final float pixelDensity;
        pixelDensity = getResources().getDisplayMetrics().density;

        final Interpolator interpolator = AnimationUtils.loadInterpolator(getBaseContext(), android.R.interpolator.fast_out_slow_in);

        final float finalRadius = Math.max(rlLogin.getWidth(), rlLogin.getHeight());
        final float radius = fabLogin.getHeight()/2;

        int x, y;
        final int width, height;

        width = rlLogin.getWidth();
        height = rlLogin.getHeight();

        x = rlLogin.getWidth() / 2;
        x = (int) (x - ((16 * pixelDensity) + (28 * pixelDensity)));
        y = (rlLogin.getHeight() / 2) + titleBarHeight;
        //final int hypotenuse = (int) Math.hypot(x, y);

        fabLogin.animate()
                .translationX(-x)
                .translationY(-y)
                .setInterpolator(interpolator)
                .setDuration(200)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                    }
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        Animator anim = ViewAnimationUtils.createCircularReveal(rlLogin, width / 2, height / 2, radius, finalRadius);
                        anim.setDuration(200);
                        rlLogin.setVisibility(View.VISIBLE);
                        fabLogin.setVisibility(View.GONE);
                        anim.start();
                    }
                    @Override
                    public void onAnimationCancel(Animator animator) {
                    }
                    @Override
                    public void onAnimationRepeat(Animator animator) {
                    }
                });
    }

    private void reverseFabAnim(){

        final int width, height;

        final Interpolator interpolator = AnimationUtils.loadInterpolator(getBaseContext(), android.R.interpolator.linear_out_slow_in);

        float finalRadius = Math.max(rlLogin.getWidth(), rlLogin.getHeight());
        float radius = fabLogin.getHeight()/2;

        width = rlLogin.getWidth();
        height = rlLogin.getHeight();

        Animator anim = ViewAnimationUtils.createCircularReveal(rlLogin, width / 2, height / 2, finalRadius, radius);
        anim.setDuration(200);
        anim.start();
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }
            @Override
            public void onAnimationEnd(Animator animator) {
                rlLogin.setVisibility(View.GONE);
                fabLogin.setVisibility(View.VISIBLE);
                fabLogin.animate()
                        .translationX(0f)
                        .translationY(0f)
                        .setInterpolator(interpolator)
                        .setDuration(200)
                    .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                    }
                    @Override
                    public void onAnimationEnd(Animator animator) {
                    }
                    @Override
                    public void onAnimationCancel(Animator animator) {
                    }
                    @Override
                    public void onAnimationRepeat(Animator animator) {
                    }
                });
            }
            @Override
            public void onAnimationCancel(Animator animator) {
            }
            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
    }

    private void changeIconColor(){
        customIconColor(ivClose, getResources().getDrawable(R.drawable.icon_close));
        customIconColor(speechLogin, getResources().getDrawable(R.drawable.icon_speech));
        customIconColor(textLogin, getResources().getDrawable(R.drawable.icon_login));
    }

    public void customIconColor(ImageView imageView, Drawable sourceDrawable){
        Bitmap sourceBitmap = DrawableToBitmap.convertDrawableToBitmap(sourceDrawable);
        int[] attrs = {R.attr.customTextColor};
        TypedArray ta = obtainStyledAttributes(attrs);
        int color = ta.getResourceId(0, R.color.blackText);
        ta.recycle();
        Bitmap mFinalBitmap = DrawableToBitmap.changeImageColor(sourceBitmap, Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(MainPageActivity.this, color) & 0x00ffffff)));
        imageView.setImageBitmap(mFinalBitmap);
    }

    private void textPasswordLogin(){
        if (etPassword.getText().toString().equals("you are my friend")){
            Toast.makeText(this, "Working", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Wrong password", Toast.LENGTH_SHORT).show();
        }
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (result.get(0).equals("you are my friend")){
                        Toast.makeText(this, "Working", Toast.LENGTH_SHORT).show();
                    } else {
                        speakOut();
                    }
                }
                break;
            }
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            }
        } else {
            Log.e("TTS", "Initialization Failed!");
        }
    }

    private void speakOut() {
        tts.speak("Wrong password!!!", TextToSpeech.QUEUE_FLUSH, null);
    }

}
