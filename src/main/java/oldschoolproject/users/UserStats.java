package oldschoolproject.users;

public enum UserStats {
    RANK(true),
    PERMISSIONS(true),
    KILLSTREAK(false),
    KILLS(false),
    DEATHS(false),
    COINS(false),
    KILLSTREAK_RECORD(false),
    DUELS_COUNT(false),
    DUELS_WINS(false),
    DUELS_LOSSES(false);

    boolean controllable;

    UserStats(boolean controllable) {
        this.controllable = controllable;
    }

    public boolean isNotControllable() {
        return !this.controllable;
    }
}
