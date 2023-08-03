package oldschoolproject.users;

public enum UserStats {
    RANK(false),
    KILLS(true),
    DEATHS(true),
    COINS(true),
    KILLSTREAK(false),
    KILLSTREAK_RECORD(true),
    DUELS_COUNT(true),
    DUELS_WINS(true),
    DUELS_LOSSES(true);

    boolean autoManageable;

    UserStats(boolean autoManageable) {
        this.autoManageable = autoManageable;
    }

    public boolean isAutoManageable() {
        return this.autoManageable;
    }
}
