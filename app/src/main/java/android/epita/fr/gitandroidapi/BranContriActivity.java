package android.epita.fr.gitandroidapi;

import android.content.Intent;
import android.epita.fr.gitandroidapi.adapters.BranchForRepoAdapter;
import android.epita.fr.gitandroidapi.adapters.ContributorsForRepoAdapter;
import android.epita.fr.gitandroidapi.interfaces.GitHubClient;
import android.epita.fr.gitandroidapi.models.BranchesForRepo;
import android.epita.fr.gitandroidapi.models.ContributorsForRepo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

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
    ArrayList<BranchesForRepo> branches;

    private BranchForRepoAdapter adapter;
    ListView branchLv;

    private ContributorsForRepoAdapter contriAdapter;
    ListView contriLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bran_contri);

        branchLv = (ListView) findViewById(R.id.branchlist);
        contriLv = (ListView) findViewById(R.id.contributorslist);

        fromMainActivity=getIntent();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        client = retrofit.create(GitHubClient.class);

        branchUrl=fromMainActivity.getStringExtra(BRANCH_URL_FOR_INTENT);

        contributorUrl=fromMainActivity.getStringExtra(CONTRIBUTOR_URL_FOR_INTENT);

        getBranches();

        getContributors();
    }

    private void getBranches()
    {
        Call<List<BranchesForRepo>> call = client.getBranchesForTheRepo(branchUrl);

        call.enqueue(new Callback<List<BranchesForRepo>>() {
            @Override
            public void onResponse(Call<List<BranchesForRepo>> call, Response<List<BranchesForRepo>> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        ArrayList<BranchesForRepo> jsonresponse = (ArrayList<BranchesForRepo>) response.body();
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

                        ArrayList<ContributorsForRepo> jsonresponse = (ArrayList<ContributorsForRepo>) response.body();
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
}
