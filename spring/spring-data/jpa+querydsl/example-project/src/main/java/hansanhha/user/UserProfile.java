package hansanhha.user;


import hansanhha.common.vo.Location;
import java.time.LocalDateTime;


public record UserProfile(
        String username,
        String nickname,
        Location location,
        LocalDateTime createdAt) {

}
