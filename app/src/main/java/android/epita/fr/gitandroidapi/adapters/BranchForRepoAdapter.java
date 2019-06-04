package android.epita.fr.gitandroidapi.adapters;

import android.content.Context;
import android.epita.fr.gitandroidapi.R;
import android.epita.fr.gitandroidapi.models.BranchesForRepo;
import android.epita.fr.gitandroidapi.models.Repository;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BranchForRepoAdapter extends ArrayAdapter<BranchesForRepo>
{
    private Context mContext;
    private int mResource;

    ArrayList<BranchesForRepo> data;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView branchName;
    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public BranchForRepoAdapter(Context context, int resource, ArrayList<BranchesForRepo> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        data =objects ;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //get the persons information
        String branchName = getItem(position).getName();

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        BranchForRepoAdapter.ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new BranchForRepoAdapter.ViewHolder();
            holder.branchName = (TextView) convertView.findViewById(R.id.branch);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (BranchForRepoAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }


        holder.branchName.setText(branchName);

        return convertView;
    }
}
