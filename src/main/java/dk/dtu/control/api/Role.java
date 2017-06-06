package dk.dtu.control.api;

public enum Role {
    None(0),
    Operator(1),
    Foreman(2),
    Pharmacist(3);

    int level;

    Role(int level){
        this.level = level;
    }

    public boolean hasPermissions(String roleToCheck){
        return this.level > Role.valueOf(roleToCheck).level;
    }
}