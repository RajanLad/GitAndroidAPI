package android.epita.fr.gitandroidapi.models;

import io.realm.RealmObject;

public class BranchesForRepo extends RealmObject
{
    public BranchesForRepo()
    {

    }

    private String name;

    public BranchesForRepo(String branchName) {
        this.name = branchName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
