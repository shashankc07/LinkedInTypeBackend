package com.shashankc7.linkedin.posts_service.event;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class PostLikedEvent
{
    Long postId;
    Long creatorId;
    Long likedByUserId;
}
