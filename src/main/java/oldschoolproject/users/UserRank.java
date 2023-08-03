package oldschoolproject.users;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public enum UserRank {

    MEMBER
            (
                    "§7",
                    Arrays.asList("perm.kit.pvp")
            ),
    VIP
            (
                    "§a§lVIP ",
                    Arrays.asList("perm.kit.archer")
            ),
    MVP
            (
                    "§9§lMVP ",
                    Arrays.asList("perm.kit.kangaroo")
            ),
    PRO
            (
                    "§6§lPRO ",
                    Arrays.asList("perm.kit.avatar")
            ),
    MOD
            (
                    "§d§lMOD ",
                    Arrays.asList("perm.cmd.admin")
            ),
    ADMIN
            (
                    "§c§lADMIN ",
                    Arrays.asList("perm.op")
            );

    @Getter
    private String tag;
    @Getter
    private List<String> permissions;

    UserRank(String tag, List<String> permissions) {
        this.tag = tag;
        this.permissions = permissions;
    }
}
