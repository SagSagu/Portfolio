package sagsaguz.portfolio.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ramotion.cardslider.CardSliderLayoutManager;
import com.ramotion.cardslider.CardSnapHelper;

import java.util.ArrayList;
import java.util.List;

import sagsaguz.portfolio.MainPageActivity;
import sagsaguz.portfolio.R;
import sagsaguz.portfolio.adapter.SliderAdapter;
import sagsaguz.portfolio.utils.DrawableToBitmap;
import sagsaguz.portfolio.utils.TextViewFactory;

import static java.lang.Integer.parseInt;
import static sagsaguz.portfolio.MainPageActivity.mainPageActivity;

public class ExperienceFragment extends Fragment implements OnMapReadyCallback {

    private final int[] pics = {R.drawable.goku_ui, R.drawable.goku_ui, R.drawable.goku_ui, R.drawable.goku_ui, R.drawable.goku_ui};

    private final SliderAdapter sliderAdapter = new SliderAdapter(pics, 5, new OnCardClickListener());

    private RecyclerView rvExperience;
    private ImageView ivDuration;
    private TextSwitcher tsDuration;
    private ImageView ivCompany;
    private TextView tvCompany1, tvCompany2;

    private CardSliderLayoutManager layoutManager;
    private int currentPosition;
    private long countryAnimDuration;

    private final String[] countries = {"PARIS", "SEOUL", "LONDON", "BEIJING", "THIRA"};
    private final String[] names = {"The Louvre", "Gwanghwamun", "The Louvre", "Gwanghwamun", "The Louvre", "Gwanghwamun"};

    private GoogleMap gMap;
    Marker marker;
    private LatLng pLatLng;
    private final Double[][] latLanCo = new Double[5][2];
    private List<String> placeList = new ArrayList<>();

    public ExperienceFragment(){
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_experience, container, false);

        rvExperience = view.findViewById(R.id.rvExperience);
        rvExperience.setLayoutManager(new CardSliderLayoutManager(mainPageActivity));
        new CardSnapHelper().attachToRecyclerView(rvExperience);

        rvExperience.setAdapter(sliderAdapter);
        rvExperience.setHasFixedSize(true);

        layoutManager = (CardSliderLayoutManager) rvExperience.getLayoutManager();

        rvExperience.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    onActiveCardChange();
                }
            }
        });

        initCountryText(view);

        tsDuration= view.findViewById(R.id.tsDuration);
        tsDuration.setFactory(new TextViewFactory(getContext(), R.style.DegreePlaceTextView, false));
        tsDuration.setCurrentText(names[0]);

        ivCompany = view.findViewById(R.id.ivCompany);
        ivCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Company", Toast.LENGTH_SHORT).show();
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    private void initCountryText(View view) {
        countryAnimDuration = 350;
        tvCompany1 = view.findViewById(R.id.tvCompany1);
        tvCompany2 = view.findViewById(R.id.tvCompany2);

        tvCompany1.setText(countries[0]);
        tvCompany2.setAlpha(0f);

        tvCompany1.setTypeface(Typeface.createFromAsset(mainPageActivity.getAssets(), "login_font.ttf"));
        tvCompany2.setTypeface(Typeface.createFromAsset(mainPageActivity.getAssets(), "login_font.ttf"));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        placeList.add("Brigade");
        placeList.add("Yeshwanthpur");
        placeList.add("Brigade");
        placeList.add("Yeshwanthpur");
        placeList.add("Brigade");

        latLanCo[0][0] = 13.0374;
        latLanCo[0][1] = 77.5358;
        latLanCo[1][0] = 13.0280;
        latLanCo[1][1] = 77.5409;
        latLanCo[2][0] = 12.8407;
        latLanCo[2][1] = 77.6763;
        latLanCo[3][0] = 13.0280;
        latLanCo[3][1] = 77.5409;
        latLanCo[4][0] = -33.852;
        latLanCo[4][1] = 151.211;

        int pos = layoutManager.getActiveCardPosition();

        LatLng placePos = new LatLng(latLanCo[pos][0], latLanCo[pos][1]);
        pLatLng = placePos;
        marker = googleMap.addMarker(new MarkerOptions().position(placePos)
                .title(placeList.get(0)));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(placePos)
                .zoom(14)
                .bearing(0)
                .tilt(0)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        gMap = googleMap;
    }

    private void onActiveCardChange() {
        final int pos = layoutManager.getActiveCardPosition();
        if (pos == RecyclerView.NO_POSITION || pos == currentPosition) {
            return;
        }
        onActiveCardChange(pos);
    }

    private void onActiveCardChange(int pos) {
        int animH[] = new int[] {R.anim.slide_in_right, R.anim.slide_out_left};

        final boolean left2right = pos < currentPosition;
        if (left2right) {
            animH[0] = R.anim.slide_in_left;
            animH[1] = R.anim.slide_out_right;
        }

        setCountryText(countries[pos % countries.length]);

        tsDuration.setInAnimation(getContext(), animH[0]);
        tsDuration.setOutAnimation(getContext(), animH[1]);
        tsDuration.setText(names[pos % names.length]);

        currentPosition = pos;

        marker.hideInfoWindow();
        LatLng toPosition = new LatLng(latLanCo[pos][0], latLanCo[pos][1]);
        animateMarker(marker, pLatLng, toPosition, false, pos);
    }

    public void animateMarker(final Marker marker,final LatLng fromPosition, final LatLng toPosition,
                              final boolean hideMarker, int pos) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final long duration = 1200;
        final Interpolator interpolator = new LinearInterpolator();
        marker.setTitle(placeList.get(pos));
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * fromPosition.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * fromPosition.latitude;
                marker.setPosition(new LatLng(lat, lng));
                if (t < 1.0) {
                    // Post again 5ms later.
                    handler.postDelayed(this, 5);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                marker.setPosition(toPosition);
            }
        }, 1210);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(toPosition)
                .zoom(14)
                .bearing(0)
                .tilt(0)
                .build();
        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        pLatLng = toPosition;
    }

    private void setCountryText(String text) {
        final TextView invisibleText;
        final TextView visibleText;
        if (tvCompany1.getAlpha() > tvCompany2.getAlpha()) {
            visibleText = tvCompany1;
            invisibleText = tvCompany2;
        } else {
            visibleText = tvCompany2;
            invisibleText = tvCompany1;
        }
        invisibleText.setText(text);

        final ObjectAnimator iAlpha = ObjectAnimator.ofFloat(invisibleText, "alpha", 1f);
        final ObjectAnimator vAlpha = ObjectAnimator.ofFloat(visibleText, "alpha", 0f);

        final AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(iAlpha, vAlpha);
        animSet.setDuration(countryAnimDuration);
        animSet.start();
    }

    private class OnCardClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            /*final CardSliderLayoutManager lm =  (CardSliderLayoutManager) rvExperience.getLayoutManager();

            if (lm.isSmoothScrolling()) {
                return;
            }

            final int activeCardPosition = lm.getActiveCardPosition();
            if (activeCardPosition == RecyclerView.NO_POSITION) {
                return;
            }

            final int clickedPosition = rvExperience.getChildAdapterPosition(view);
            if (clickedPosition == activeCardPosition) {
                final Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra(DetailsActivity.BUNDLE_IMAGE_ID, pics[activeCardPosition % pics.length]);

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent);
                } else {
                    final CardView cardView = (CardView) view;
                    final View sharedView = cardView.getChildAt(cardView.getChildCount() - 1);
                    final ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation(getActivity(), sharedView, "shared");
                    startActivity(intent, options.toBundle());
                }
            } else if (clickedPosition > activeCardPosition) {
                rvExperience.smoothScrollToPosition(clickedPosition);
                //onActiveCardChange(clickedPosition);
            }*/
        }
    }

}
