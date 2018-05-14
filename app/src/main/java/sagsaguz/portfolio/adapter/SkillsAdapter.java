package sagsaguz.portfolio.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sagsaguz.portfolio.R;
import sagsaguz.portfolio.utils.Circle;
import sagsaguz.portfolio.utils.CircleAnimation;

public class SkillsAdapter extends RecyclerView.Adapter<SkillsAdapter.ViewHolder>{

    List<String> languageNames = new ArrayList<>();
    List<String> statisticsValues = new ArrayList<>();
    Context context;

    public SkillsAdapter(Context context,List<String> languageNames, List<String> statisticsValues){
        this.context = context;
        this.languageNames = languageNames;
        this.statisticsValues = statisticsValues;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.skills_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int angle = Integer.parseInt(statisticsValues.get(position));
        CircleAnimation animation1 = new CircleAnimation(holder.statisticsCircle, angle);
        animation1.setDuration(1000);
        holder.statisticsCircle.startAnimation(animation1);
        holder.languageName.setText(languageNames.get(position));
    }

    @Override
    public int getItemCount() {
        return statisticsValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        Circle defaultCircle;
        Circle statisticsCircle;
        TextView languageName;

        ViewHolder(View itemView) {
            super(itemView);
            defaultCircle = itemView.findViewById(R.id.defaultCircle);
            defaultCircle.setAngle(360);
            statisticsCircle = itemView.findViewById(R.id.statisticsCircle);
            statisticsCircle.setColor(R.color.primaryColorEmerald);
            languageName = itemView.findViewById(R.id.languageName);
        }

    }
}
