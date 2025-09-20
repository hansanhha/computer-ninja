package hansanhha.post.dto;


import hansanhha.common.vo.Location;
import hansanhha.post.vo.PostStatus;
import lombok.Builder;


@Builder
public record PostSearchCondition(
        Long categoryId,
        String title,
        PostStatus status,
        int minPrice,
        int maxPrice,
        Location userLocation) {

}
