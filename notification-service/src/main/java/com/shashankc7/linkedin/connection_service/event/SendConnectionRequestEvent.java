package com.shashankc7.linkedin.connection_service.event;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class SendConnectionRequestEvent
{
    private Long senderId;
    private Long receiverId;
}
