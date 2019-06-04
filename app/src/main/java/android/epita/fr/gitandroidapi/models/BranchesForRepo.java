package android.epita.fr.gitandroidapi.models;

public class BranchesForRepo
{
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
