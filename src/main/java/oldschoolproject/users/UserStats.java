package oldschoolproject.users;

public enum UserStats {
    RANK(false),
    PERMISSIONS(false),
    KILLSTREAK(true),
    KILLS(true),
    DEATHS(true),
    COINS(true),
    KILLSTREAK_RECORD(true),
    DUELS_COUNT(true),
    DUELS_WINS(true),
    DUELS_LOSSES(true);

    boolean serverControled;

    UserStats(boolean serverControled) {
        this.serverControled = serverControled;
    }

    public boolean isServerControlled() {
        return serverControled;
    }
}
