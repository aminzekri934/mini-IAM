public class Permission {
    private int permissionId;
    private String permissionName;

    public Permission(int permissionId, String permissionName) {
        this.permissionId = permissionId;
        this.permissionName = permissionName;
    }

    // Getter und Setter
    public int getPermissionId() { return permissionId; }
    public void setPermissionId(int permissionId) { this.permissionId = permissionId; }

    public String getPermissionName() { return permissionName; }
    public void setPermissionName(String permissionName) { this.permissionName = permissionName; }

    @Override
    public String toString() {
        return "Permission{id=" + permissionId + ", name='" + permissionName + "'}";
    }
}