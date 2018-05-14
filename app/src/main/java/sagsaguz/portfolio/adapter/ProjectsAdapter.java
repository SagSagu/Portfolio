package sagsaguz.portfolio.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import sagsaguz.portfolio.R;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ViewHolder>{

    private List<String> appNames = new ArrayList<>();
    private List<String> appImages = new ArrayList<>();
    private Context context;

    public ProjectsAdapter(Context context,List<String> appNames, List<String> appImages){
        this.context = context;
        this.appNames =appNames;
        this.appImages = appImages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.projects_item, parent, false);
        return new ProjectsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.appName.setText(appNames.get(position));
        Picasso.with(context).load(appImages.get(position)).into(holder.appImage);
    }

    @Override
    public int getItemCount() {
        return appNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView appImage;
        TextView appName;

        ViewHolder(View itemView) {
            super(itemView);
            appImage = itemView.findViewById(R.id.appImage);
            appName = itemView.findViewById(R.id.appName);
        }

    }
}
