package oldschoolproject.events.custom;

import lombok.Getter;
import lombok.Setter;
import oldschoolproject.users.User;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter @Setter
public class PlayerKillstreakEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    User user;
    StreakAction streakAction;

    public PlayerKillstreakEvent(User user, StreakAction streakAction) {
        this.user = user;
        this.streakAction = streakAction;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public enum StreakAction {
        LOSE, GAIN;
    }
}
