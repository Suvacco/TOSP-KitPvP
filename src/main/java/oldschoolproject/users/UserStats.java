package oldschoolproject.users;

public enum UserStats {
    RANK(false),
    KILLS(true),
    DEATHS(true),
    COINS(true),
    KILLSTREAK(true),
    KILLSTREAK_RECORD(true),
    DUELS_COUNT(true),
    DUELS_WINS(true),
    DUELS_LOSSES(true);

    boolean modifiable;

    UserStats(boolean modifiable) {
        this.modifiable = modifiable;
    }

    public boolean isModifiable() {
        return this.modifiable;
    }
}
