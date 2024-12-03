package org.spring.datingsite.domain;

import java.io.Serializable;
import java.util.UUID;

public class InvitationId implements Serializable {
    private UUID senderId;
    private UUID receiverId;
}
