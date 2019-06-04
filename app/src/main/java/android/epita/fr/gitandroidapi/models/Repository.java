package android.epita.fr.gitandroidapi.models;

public class Repository
{
        private String name;
        private String description;
        private String language;
        private String stargazers_count;

        private String branches_url;
        private String contributors_url;

        public Repository(String name, String description, String language, String counts,String branches_url,String contributors_url) {
            this.name = name;
            this.description = description;
            this.language = language;
            this.stargazers_count = counts;
            this.branches_url = branches_url;
            this.contributors_url = contributors_url;
        }

    public String getBranches_url() {
        return branches_url;
    }

    public void setBranches_url(String branches_url) {
        this.branches_url = branches_url;
    }

    public String getContributors_url() {
        return contributors_url;
    }

    public void setContributors_url(String contributors) {
        this.contributors_url = contributors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCounts() {
        return stargazers_count;
    }

    public void setCounts(String counts) {
        this.stargazers_count = counts;
    }
}
