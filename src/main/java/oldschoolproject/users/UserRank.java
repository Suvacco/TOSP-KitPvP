package oldschoolproject.users;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public enum UserRank {

    MEMBER
            (
                    "§7",
                    Arrays.asList("rank.kit.pvp", "rank.kit.archer")
            ),
    VIP
            (
                    "§a§lVIP ",
                    Arrays.asList("rank.kit.avatar", "rank.kit.kangaroo"),
                    MEMBER
            ),
    MVP
            (
                    "§9§lMVP ",
                    Arrays.asList("rank.kit.kangaroo"),
                    VIP
            ),
    PRO
            (
                    "§6§lPRO ",
                    Arrays.asList("rank.kit.avatar"),
                    MVP
            ),
    MOD
            (
                    "§d§lMOD ",
                    Arrays.asList("rank.cmd.admin"),
                    PRO
            ),
    ADMIN
            (
                    "§c§lADMIN ",
                    Arrays.asList("rank.op"),
                    MOD
            );

    private String tag;
    private List<String> permissions;

    UserRank(String tag, List<String> permissions) {
        this.tag = tag;
        this.permissions = permissions;
    }

    UserRank(String tag, List<String> permissions, UserRank... ranks) {
        this.tag = tag;
        this.permissions = combinePermissions(permissions, ranks);
    }

    private static List<String> combinePermissions(List<String> additionalPermissions, UserRank... ranks) {
        Set<String> combinedPermissions = new HashSet<>();

        for (UserRank rank : ranks) {
            combinedPermissions.addAll(rank.getPermissions());
        }

        combinedPermissions.addAll(additionalPermissions);

        return new ArrayList<>(combinedPermissions);
    }
}
