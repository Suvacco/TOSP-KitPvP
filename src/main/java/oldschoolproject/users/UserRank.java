package oldschoolproject.users;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
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
                    Arrays.asList(MEMBER)
            ),
    MVP
            (
                    "§9§lMVP ",
                    Arrays.asList("rank.kit.kangaroo"),
                    Arrays.asList(MEMBER, VIP)
            ),
    PRO
            (
                    "§6§lPRO ",
                    Arrays.asList("rank.kit.avatar"),
                    Arrays.asList(MEMBER, VIP, MVP)
            ),
    MOD
            (
                    "§d§lMOD ",
                    Arrays.asList("rank.cmd.admin"),
                    Arrays.asList(MEMBER, VIP, MVP, PRO)
            ),
    ADMIN
            (
                    "§c§lADMIN ",
                    Arrays.asList("rank.op"),
                    Arrays.asList(MEMBER, VIP, MVP, PRO, MOD)
            );

    private String tag;
    private List<String> permissions;
    private List<UserRank> inheritedRanks;

    UserRank(String tag, List<String> permissions) {
        this.tag = tag;
        this.permissions = permissions;
    }

    UserRank(String tag, List<String> permissions, List<UserRank> inheritedRanks) {
        this.tag = tag;
        this.inheritedRanks = inheritedRanks;
        this.permissions = combinePermissions(inheritedRanks, permissions);
    }

    private static List<String> combinePermissions(List<UserRank> ranks, List<String> additionalPermissions) {
        List<String> combinedPermissions = ranks.stream()
                .flatMap(rank -> rank.getPermissions().stream())
                .distinct()
                .collect(Collectors.toList());

        combinedPermissions.addAll(additionalPermissions);
        return combinedPermissions;
    }
}
