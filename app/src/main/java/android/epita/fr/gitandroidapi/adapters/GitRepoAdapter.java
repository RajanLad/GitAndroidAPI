package android.epita.fr.gitandroidapi.adapters;

import android.content.Context;
import android.epita.fr.gitandroidapi.R;
import android.epita.fr.gitandroidapi.models.Repository;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

public class GitRepoAdapter extends ArrayAdapter<Repository> {

    private Context mContext;
    private int mResource;

    ArrayList<Repository> data;//=new ArrayList<Repository>(); //data = countryList
    private ArrayList<Repository> originalList;
    private NameFilter filter;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView repo;
        TextView description;
        TextView language;
        TextView counts;
    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public GitRepoAdapter(Context context, int resource, ArrayList<Repository> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        this.data =objects ;
        this.originalList = new ArrayList<Repository>() ;
        this.originalList.addAll(data);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //get the persons information
        String repo = getItem(position).getName();
        String description = getItem(position).getDescription();
        String language = getItem(position).getLanguage();
        String counts = getItem(position).getCounts();

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.repo = (TextView) convertView.findViewById(R.id.repos);
            holder.description = (TextView) convertView.findViewById(R.id.description);
            holder.language = (TextView) convertView.findViewById(R.id.language);
            holder.counts = (TextView) convertView.findViewById(R.id.count);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        holder.repo.setText(repo);
        holder.description.setText(description);
        holder.language.setText(language);
        holder.counts.setText(counts);


        return convertView;
    }
    @Override
    public Filter getFilter() {
        if (filter == null)
            filter = new NameFilter();

        return filter;
    }

    private class NameFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            constraint = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();

            if(constraint != null && constraint.toString().length() > 0)
            {
                ArrayList<Repository> filteredItems = new ArrayList<Repository>();

                for(int i = 0, l = originalList.size(); i < l; i++)
                {
                    Repository nameList = originalList.get(i);
                    if(nameList.getName().toLowerCase().contains(constraint))
                        filteredItems.add(nameList);
                }
                results.count = filteredItems.size();
                results.values = filteredItems;
            }
            else
            {
                synchronized(this)
                {
                    results.values = originalList;
                    results.count = originalList.size();
                }
            }
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            data = (ArrayList<Repository>) results.values;
            notifyDataSetChanged();
             clear();
             for(int i = 0, l = data.size(); i < l; i++)
              add(data.get(i));
             notifyDataSetInvalidated();
            }


        }

    }











