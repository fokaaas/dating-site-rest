package org.spring.datingsite.domain;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
public class InvitationId implements Serializable {

    private UUID senderId;

    private UUID receiverId;
}
