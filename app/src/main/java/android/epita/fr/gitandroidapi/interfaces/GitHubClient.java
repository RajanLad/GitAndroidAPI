package android.epita.fr.gitandroidapi.interfaces;

import android.epita.fr.gitandroidapi.models.BranchesForRepo;
import android.epita.fr.gitandroidapi.models.ContributorsForRepo;
import android.epita.fr.gitandroidapi.models.Repository;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface GitHubClient {

    @GET("/orgs/{organization}/repos")
    Call<List<Repository>> reposForOrganization(@Path("organization") String organiation);

    @GET("/search/repositories")
    Call<String> reposBySearch(@Query("q") String name);

    @GET
    Call<List<BranchesForRepo>> getBranchesForTheRepo(@Url String url);

    @GET
    Call<List<ContributorsForRepo>> getContributorsForTheRepo(@Url String url);

    //https://api.github.com/repositories?since=364

    @GET("/repositories")
    Call<List<Repository>> getReposForOffline(@Query("since") int since);

}
