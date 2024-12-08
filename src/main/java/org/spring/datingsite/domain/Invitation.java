package org.spring.datingsite.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "invitations")
public class Invitation extends Base {
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
    private Boolean isAccepted = false;

    public void removeSender(User sender) {
        sender.removeSentInvitation(this);
    }

    public void removeReceiver(User receiver) {
        receiver.removeReceivedInvitation(this);
    }
}
