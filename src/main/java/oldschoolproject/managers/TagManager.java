package oldschoolproject.managers;

import oldschoolproject.users.User;
import oldschoolproject.utils.tags.TagBoard;

public class TagManager {

    private static TagBoard tagBoard = new TagBoard();

    public static void setPrefix(User user, String prefix) {
        tagBoard.setPrefix(user, prefix);
    }

    public static void setSuffix(User user, String suffix) {
        tagBoard.setSuffix(user, suffix);
    }
}
