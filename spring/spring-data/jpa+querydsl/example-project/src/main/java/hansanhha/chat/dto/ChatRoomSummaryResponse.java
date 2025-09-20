package hansanhha.chat.dto;


import hansanhha.common.vo.Location;
import hansanhha.post.vo.PostStatus;


public record ChatRoomSummaryResponse(
        Long chatRoomId,
        String postThumbnailUrl,
        Location postLocation,
        String postStatus,
        String chatRoomTitle,
        String lastSenderNickname,
        String lastMessageContent) {

    public ChatRoomSummaryResponse {
        postStatus = PostStatus.valueOf(postStatus).getDisplayName();
    }

}
