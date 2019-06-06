package android.epita.fr.gitandroidapi;

import android.content.Intent;
import android.epita.fr.gitandroidapi.adapters.BranchForRepoAdapter;
import android.epita.fr.gitandroidapi.adapters.ContributorsForRepoAdapter;
import android.epita.fr.gitandroidapi.interfaces.GitHubClient;
import android.epita.fr.gitandroidapi.models.BranchesForRepo;
import android.epita.fr.gitandroidapi.models.ContributorsForRepo;
import android.epita.fr.gitandroidapi.models.Repository;
import android.epita.fr.gitandroidapi.sessionsfunctions.Functions;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BranContriActivity extends AppCompatActivity {

    Intent fromMainActivity;
    private static String BASE_URL="https://api.github.com";
    private static String BRANCH_URL_FOR_INTENT="branch_url";
    private static String CONTRIBUTOR_URL_FOR_INTENT="contributor_url";
    private String branchUrl;
    private String contributorUrl;
    GitHubClient client;

    ArrayList<BranchesForRepo> realmBasedRepoBranch;
    ArrayList<ContributorsForRepo> realmBasedRepoContri;
    private BranchForRepoAdapter adapter;
    ListView branchLv;

    private ContributorsForRepoAdapter contriAdapter;
    ListView contriLv;

    Realm realm;
    RealmConfiguration config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bran_contri);

        branchLv = (ListView) findViewById(R.id.branchlist);
        contriLv = (ListView) findViewById(R.id.contributorslist);

        fromMainActivity=getIntent();

        //Retro init
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        client = retrofit.create(GitHubClient.class);

        //Realm
        //Init Realm

        config = new RealmConfiguration.Builder()
                .name("default2")
                .schemaVersion(3)
                .deleteRealmIfMigrationNeeded()
                .build();

        realm = Realm.getInstance(config);

        branchUrl=fromMainActivity.getStringExtra(BRANCH_URL_FOR_INTENT);

        contributorUrl=fromMainActivity.getStringExtra(CONTRIBUTOR_URL_FOR_INTENT);

        if(Functions.isInternetAvailable(getApplicationContext())) {
            getBranches();
            getContributors();
        }
        else {
            onSearchOfThisOfflineBranch();
            onSearchOfThisOfflineContributors();
        }
    }

    private void getBranches()
    {
        Call<List<BranchesForRepo>> call = client.getBranchesForTheRepo(branchUrl);

        call.enqueue(new Callback<List<BranchesForRepo>>() {
            @Override
            public void onResponse(Call<List<BranchesForRepo>> call, Response<List<BranchesForRepo>> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        realm.beginTransaction();

                        ArrayList<BranchesForRepo> jsonresponse = (ArrayList<BranchesForRepo>) response.body();

                        realm.insertOrUpdate(jsonresponse);
                        realm.commitTransaction();

                        adapter = new BranchForRepoAdapter(BranContriActivity.this,  R.layout.list_view_branches, jsonresponse);
                        branchLv.setAdapter(adapter);


                    }
                    else
                    {
                        //to-do
                        Log.d("DLD","NULL");
                    }
                }
                else
                {
                    //to-do
                    Log.d("DLD","NULL and fuucking wrong");
                }
            }

            @Override
            public void onFailure(Call<List<BranchesForRepo>> call, Throwable t) {
            }
        });
    }

    private void getContributors()
    {
        Call<List<ContributorsForRepo>> call = client.getContributorsForTheRepo(contributorUrl);

        call.enqueue(new Callback<List<ContributorsForRepo>>() {
            @Override
            public void onResponse(Call<List<ContributorsForRepo>> call, Response<List<ContributorsForRepo>> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        realm.beginTransaction();

                        ArrayList<ContributorsForRepo> jsonresponse = (ArrayList<ContributorsForRepo>) response.body();

                        realm.insertOrUpdate(jsonresponse);
                        realm.commitTransaction();

                        contriAdapter = new ContributorsForRepoAdapter(BranContriActivity.this,  R.layout.list_view_contributors, jsonresponse);
                        contriLv.setAdapter(contriAdapter);
                    }
                    else
                    {
                        //to-do
                        Log.d("DLD","NULL");
                    }
                }
                else
                {
                    //to-do
                    Log.d("DLD","NULL and fuucking wrong");
                }
            }

            @Override
            public void onFailure(Call<List<ContributorsForRepo>> call, Throwable t) {
            }
        });
    }

    private void onSearchOfThisOfflineBranch()
    {
        realmBasedRepoBranch = (ArrayList<BranchesForRepo>) realm.copyFromRealm(realm.where(BranchesForRepo.class).findAll());

        if(!realmBasedRepoBranch.isEmpty()) {
            for (int j=0;j<realmBasedRepoBranch.size();j++)
                Log.d("CRYMAN", realmBasedRepoBranch.get(j).getName() + "");
        }
        else
        {
            Log.d("CRYMAN", "is empty , literally" + "");
        }

        adapter = new BranchForRepoAdapter(BranContriActivity.this,  R.layout.list_view_branches, realmBasedRepoBranch);
        branchLv.setAdapter(adapter);

    }

    private void onSearchOfThisOfflineContributors()
    {
        realmBasedRepoContri = (ArrayList<ContributorsForRepo>) realm.copyFromRealm(realm.where(ContributorsForRepo.class).findAll());

        if(!realmBasedRepoContri.isEmpty()) {
            for (int j=0;j<realmBasedRepoContri.size();j++)
                Log.d("CRYMAN", realmBasedRepoContri.get(j).getLogin() + "");
        }
        else
        {
            Log.d("CRYMAN", "is empty , literally" + "");
        }

        contriAdapter = new ContributorsForRepoAdapter(BranContriActivity.this,  R.layout.list_view_contributors, realmBasedRepoContri);
        contriLv.setAdapter(contriAdapter);

    }
}
