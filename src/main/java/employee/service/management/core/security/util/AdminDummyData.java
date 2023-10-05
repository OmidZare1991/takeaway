package employee.service.management.core.security.util;

public enum AdminDummyData {
    FIRST_NAME("admin_firstname"),
    LASTNAME("admin_lastname"),
    EMAIL("o.zare@test.com"),
    PASSWORD("admin_password");
    private final String value;

     AdminDummyData(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
