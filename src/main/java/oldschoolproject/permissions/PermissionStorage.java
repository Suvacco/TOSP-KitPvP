package oldschoolproject.permissions;

import java.util.ArrayList;
import java.util.List;

public class PermissionStorage {

    private List<String> permissions = new ArrayList<>();

    public PermissionStorage() {
        this.permissions = new ArrayList<>();
    }

    public void addPermissions(List<String> permissions) {
        this.permissions.addAll(permissions);
    }

    public void addPermission(String permission) {
        this.permissions.add(permission);
    }

    public void removePermission(String permission) {
        this.permissions.remove(permission);
    }

    public void removePermissions(List<String> permissions) {
        this.permissions.removeAll(permissions);
    }

    public boolean hasPermission(String permission) {
        return this.permissions.contains(permission);
    }

    public List<String> getPermissions() {
        return this.permissions;
    }

}
