package uk.co.bubobubo.security;

import java.util.HashSet;
import java.util.Set;

public class RepositoryNameHolder {

    private Set<String> roleNames;

    public RepositoryNameHolder() {
        this(new HashSet<String>());
    }

    public RepositoryNameHolder(Set<String> roleNames) {
        this.roleNames = roleNames;
    }

    public Set<String> getRoleNames() {
        return roleNames;
    }

    public void addRoleName(String roleName) {
        roleNames.add(roleName);
    }

    public boolean containsRepository(String repositoryId) {
        return roleNames.contains(repositoryId);
    }
}
