package android.epita.fr.gitandroidapi.adapters;

import android.content.Context;
import android.epita.fr.gitandroidapi.R;
import android.epita.fr.gitandroidapi.models.ContributorsForRepo;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ContributorsForRepoAdapter extends ArrayAdapter<ContributorsForRepo>
{
    private Context mContext;
    private int mResource;

    ArrayList<ContributorsForRepo> data;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView contributor_name;
        ImageView avatar;
    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public ContributorsForRepoAdapter(Context context, int resource, ArrayList<ContributorsForRepo> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        data =objects ;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //get the persons information
        String contriName = getItem(position).getLogin();
        String avatar = getItem(position).getAvatar_url();

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ContributorsForRepoAdapter.ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ContributorsForRepoAdapter.ViewHolder();
            holder.contributor_name = (TextView) convertView.findViewById(R.id.contributors);
            holder.avatar = (ImageView) convertView.findViewById(R.id.avatar);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ContributorsForRepoAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }


        holder.contributor_name.setText(contriName);
        Glide.with(mContext).load(avatar).into(holder.avatar);

        return convertView;
    }
}
