package org.spring.datingsite.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@IdClass(InvitationId.class)
@Table(name = "invitations")
public class Invitation {
    @EmbeddedId
    private InvitationId id;
    
    @ManyToOne
    @MapsId("senderId")
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @MapsId("receiverId")
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;
    
    @Column(name = "is_accepted", nullable = false)
    private boolean isAccepted = false;
}
