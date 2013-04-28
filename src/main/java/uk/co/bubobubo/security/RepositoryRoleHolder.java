package uk.co.bubobubo.security;

import java.util.HashSet;
import java.util.Set;

public class RepositoryRoleHolder {

    private Set<String> roleNames;

    public RepositoryRoleHolder() {
        this(new HashSet<String>());
    }

    public RepositoryRoleHolder(Set<String> roleNames) {
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
