package hansanhha.post.dto;


import hansanhha.common.vo.Location;
import hansanhha.post.vo.Money;


public record PostSummaryResponse(
        Long postId,
        String thumbnailImageUrl,
        String title,
        Location tradeLocation,
        String status,
        Money price,
        int viewCount,
        int favoriteCount) {

}
