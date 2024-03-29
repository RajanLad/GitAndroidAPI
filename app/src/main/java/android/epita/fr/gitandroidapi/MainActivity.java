package android.epita.fr.gitandroidapi;

import android.content.Context;
import android.content.Intent;
import android.epita.fr.gitandroidapi.adapters.GitRepoAdapter;
import android.epita.fr.gitandroidapi.interfaces.GitHubClient;
import android.epita.fr.gitandroidapi.models.Repository;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";
    private static String BASE_URL="https://api.github.com";
    private static String BRANCH_URL_FOR_INTENT="branch_url";
    private static String CONTRIBUTOR_URL_FOR_INTENT="contributor_url";

    ArrayList<Repository> repos;
    ArrayList<Repository> realmBasedRepo;
    GitHubClient client;
    Intent brancontri;
    ListView lv;
    EditText ed;
    private GitRepoAdapter adapter;

    Realm realm;
    RealmConfiguration config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView)findViewById(R.id.listOfGit);
        ed = (EditText)findViewById(R.id.searchThem);

        repos = new ArrayList<>();

        //Retro init
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create());
        Retrofit retrofit = builder.build();
        client = retrofit.create(GitHubClient.class);

        //Realm
        //Init Realm
        // Initialize Realm (just once per application)
        Realm.init(getApplicationContext());



        config = new RealmConfiguration.Builder()
                .name("default2")
                .schemaVersion(3)
                .deleteRealmIfMigrationNeeded()
                .build();

        realm = Realm.getInstance(config);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(!repos.isEmpty()) {
                    brancontri = new Intent(MainActivity.this, BranContriActivity.class);
                    brancontri.putExtra(BRANCH_URL_FOR_INTENT, repos.get(i).getBranches_url().replace("{/branch}", ""));
                    brancontri.putExtra(CONTRIBUTOR_URL_FOR_INTENT, repos.get(i).getContributors_url());
                    startActivity(brancontri);
                }
                else if(!realmBasedRepo.isEmpty())
                {
                    brancontri = new Intent(MainActivity.this, BranContriActivity.class);
                    brancontri.putExtra(BRANCH_URL_FOR_INTENT, realmBasedRepo.get(i).getBranches_url().replace("{/branch}", ""));
                    brancontri.putExtra(CONTRIBUTOR_URL_FOR_INTENT, realmBasedRepo.get(i).getContributors_url());
                    startActivity(brancontri);
                }
            }
        });

        ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(isInternetAvailable()) {
                    onSearchOfThis(charSequence.toString());
                }
                else
                {
                    onSearchOfThisOffline(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void onSearchOfThis(String searchString)
    {
        Call<String> call = client.reposBySearch(searchString);

        final String search=searchString;

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        String jsonresponse = response.body().toString();
                        decodeToAdapter(jsonresponse);


                        (MainActivity.this).adapter.getFilter().filter(search);
                        adapter.notifyDataSetChanged();
                    }
                    else
                    {
                        //if null
                    }
                }
                else
                {
                    //ifNotSuccessful
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("DLD",t.getMessage());
            }
        });
    }


    private void decodeToAdapter(String response){

        try {
            //getting the whole json object from the response
            JSONObject obj = new JSONObject(response);


            if(true){

                realm.beginTransaction();

                ArrayList<Repository> retroModelArrayList = new ArrayList<>();
                JSONArray dataArray  = obj.getJSONArray("items");

                for (int i = 0; i < dataArray.length(); i++) {

                    Repository retroModel = new Repository("","","","","","");
                    JSONObject dataobj = dataArray.getJSONObject(i);

                    retroModel.setName(dataobj.getString("name"));
                    retroModel.setDescription(dataobj.getString("description"));
                    retroModel.setLanguage(dataobj.getString("language"));
                    retroModel.setCounts(dataobj.getString("stargazers_count"));
                    retroModel.setBranches_url(dataobj.getString("branches_url"));
                    retroModel.setContributors_url(dataobj.getString("contributors_url"));
                    retroModelArrayList.add(retroModel);
                }


                repos = retroModelArrayList;

                realm.insertOrUpdate(repos);

                adapter = new GitRepoAdapter(MainActivity.this,  R.layout.list_view, repos);
                lv.setAdapter(adapter);

                realm.commitTransaction();

            }else {
                Toast.makeText(MainActivity.this, obj.optString("message")+" ", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        finally
        {
            //realm.close();
        }

    }

    public boolean isInternetAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    private void onSearchOfThisOffline(String search)
    {
        realmBasedRepo = (ArrayList<Repository>) realm.copyFromRealm(realm.where(Repository.class).findAll());

//        if(!realmBasedRepo.isEmpty()) {
//            for (int j=0;j<realmBasedRepo.size();j++)
//                Log.d("CRYMAN", realmBasedRepo.get(j).getName() + "");
//        }

        adapter = new GitRepoAdapter(MainActivity.this,  R.layout.list_view, realmBasedRepo);
        lv.setAdapter(adapter);

        (MainActivity.this).adapter.getFilter().filter(search);
        adapter.notifyDataSetChanged();
    }
}
