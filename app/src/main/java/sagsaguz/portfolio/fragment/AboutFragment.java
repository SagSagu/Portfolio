package sagsaguz.portfolio.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sagsaguz.portfolio.R;
import sagsaguz.portfolio.adapter.ProjectsAdapter;
import sagsaguz.portfolio.adapter.SkillsAdapter;
import sagsaguz.portfolio.utils.TextViewFactory;

public class AboutFragment extends Fragment{

    ImageView ivSkills, ivProjects, ivInterests, ivEducation;

    TextSwitcher degreeName, degreeDuration, degreePlace;
    ImageView moveLeft, moveRight;

    RecyclerView rvSkills;
    List<String> languageNames = new ArrayList<>();
    List<String> statisticsValues = new ArrayList<>();
    RecyclerView rvProjects;
    List<String> appNames = new ArrayList<>();
    List<String> appImages = new ArrayList<>();
    List<String> proAppNames = new ArrayList<>();
    List<String> proAppImages = new ArrayList<>();
    List<String> perAppNames = new ArrayList<>();
    List<String> perAppImages = new ArrayList<>();

    ProjectsAdapter projectsAdapter;

    int currentPosition = 0;

    private final String[] names = {"The Louvre", "Gwanghwamun", "The Louvre", "Gwanghwamun", "The Louvre", "Gwanghwamun"};
    private final String[] duration = {"2009-2011", "2011-2015", "2009-2011", "2011-2015", "2009-2011", "2011-2015"};
    private final String[] place = {"Seshadripuram composite pu college", "Sambhram Institute of Technology", "Seshadripuram composite pu college", "Sambhram Institute of Technology", "Seshadripuram composite pu college", "Sambhram Institute of Technology"};

    public AboutFragment(){
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        skills(view);
        projects(view);
        interests(view);
        education(view);

        return view;
    }

    private void skills(View view){
        ivSkills = view.findViewById(R.id.ivSkills);
        ivSkills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Skills", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayoutManager lmSkills = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        rvSkills = view.findViewById(R.id.rvSkills);
        rvSkills.setLayoutManager(lmSkills);

        languageNames.clear();
        statisticsValues.clear();

        languageNames.add("Java");
        languageNames.add("Kotlin");
        languageNames.add("C");
        //languageNames.add("Others");

        statisticsValues.add("210");
        statisticsValues.add("200");
        statisticsValues.add("180");
        //statisticsValues.add("200");

        SkillsAdapter skillsAdapter = new SkillsAdapter(getContext(),languageNames, statisticsValues);
        rvSkills.setAdapter(skillsAdapter);
    }

    private void projects(View view){
        ivProjects = view.findViewById(R.id.ivProjects);
        ivProjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Projects", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayoutManager lmProjects = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        rvProjects = view.findViewById(R.id.rvProjects);
        rvProjects.setLayoutManager(lmProjects);

        proAppNames.clear();
        proAppImages.clear();
        perAppNames.clear();
        perAppImages.clear();

        proAppNames.add("Brilla");
        proAppNames.add("Brilla : Vedic Maths and Maths Tricks");
        perAppNames.add("Appograph");
        //perAppNames.add("Others");

        proAppImages.add("https://firebasestorage.googleapis.com/v0/b/college-7a896.appspot.com/o/portfolio%2Fbrilla_launcher_icon.png?alt=media&token=19a4fa7f-f713-4b11-b504-721a6fbb8726");
        proAppImages.add("https://firebasestorage.googleapis.com/v0/b/college-7a896.appspot.com/o/portfolio%2Fbrilla_vm_icon.png?alt=media&token=cbc41c88-11e2-473f-b375-669b90bace50");
        perAppImages.add("https://firebasestorage.googleapis.com/v0/b/college-7a896.appspot.com/o/portfolio%2Fappograph_launcher_icon.png?alt=media&token=2e9cd67e-8b68-452b-8a55-299cba78be69");
        //perAppImages.add("https://s3.amazonaws.com/brightkidmont/profilePics/23456.jpg");

        projectsAdapter = new ProjectsAdapter(getContext(),appNames, appImages);
        rvProjects.setAdapter(projectsAdapter);

        proApps();

        SwitchCompat projectsSwitch = view.findViewById(R.id.projectsSwitch);
        projectsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked){
                    perApps();
                } else {
                    proApps();
                }
            }
        });
    }

    private void proApps(){
        appNames.clear();
        appImages.clear();
        appNames.addAll(proAppNames);
        appImages.addAll(proAppImages);
        projectsAdapter.notifyDataSetChanged();
    }

    private void perApps(){
        appNames.clear();
        appImages.clear();
        appNames.addAll(perAppNames);
        appImages.addAll(perAppImages);
        projectsAdapter.notifyDataSetChanged();
    }

    private void interests(View view){
        ivInterests = view.findViewById(R.id.ivInterests);
        ivInterests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Interests", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void education(View view){
        ivEducation = view.findViewById(R.id.ivEducation);
        ivEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Education", Toast.LENGTH_SHORT).show();
            }
        });

        degreeName = view.findViewById(R.id.degreeName);
        degreeName.setFactory(new TextViewFactory(getContext(), R.style.DegreeNameTextView, false));
        degreeName.setCurrentText(names[0]);

        degreeDuration = view.findViewById(R.id.degreeDuration);
        degreeDuration.setFactory(new TextViewFactory(getContext(), R.style.DegreeDurationTextView, true));
        degreeDuration.setCurrentText(duration[0]);

        degreePlace = view.findViewById(R.id.degreePlace);
        degreePlace.setInAnimation(getContext(), R.anim.fade_in);
        degreePlace.setOutAnimation(getContext(), R.anim.fade_out);
        degreePlace.setFactory(new TextViewFactory(getContext(), R.style.DegreePlaceTextView, false));
        degreePlace.setCurrentText(place[0]);

        moveLeft = view.findViewById(R.id.moveLeft);
        moveLeft.setVisibility(View.INVISIBLE);
        moveRight = view.findViewById(R.id.moveRight);

        moveRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = currentPosition+1;
                onEducationChanged(position);
                if (position == names.length-1){
                    moveRight.setVisibility(View.INVISIBLE);
                }
                if (position == 1){
                    moveLeft.setVisibility(View.VISIBLE);
                }
            }
        });

        moveLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = currentPosition-1;
                onEducationChanged(position);
                if (position == 0){
                    moveLeft.setVisibility(View.INVISIBLE);
                    moveRight.setVisibility(View.VISIBLE);
                }
                if (position == names.length-2){
                    moveRight.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void onEducationChanged(int position){
        int animH[] = new int[] {R.anim.slide_in_right, R.anim.slide_out_left};
        //int animV[] = new int[] {R.anim.slide_in_top, R.anim.slide_out_bottom};

        final boolean left2right = position < currentPosition;
        if (left2right) {
            animH[0] = R.anim.slide_in_left;
            animH[1] = R.anim.slide_out_right;

            //animV[0] = R.anim.slide_in_bottom;
            //animV[1] = R.anim.slide_out_top;
        }

        degreeName.setInAnimation(getContext(), animH[0]);
        degreeName.setOutAnimation(getContext(), animH[1]);
        degreeName.setText(names[position % names.length]);

        degreeDuration.setInAnimation(getContext(), animH[0]);
        degreeDuration.setOutAnimation(getContext(), animH[1]);
        degreeDuration.setText(duration[position % duration.length]);

        degreePlace.setText(place[position % place.length]);

        currentPosition = position;

    }

}
