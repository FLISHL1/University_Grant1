package main.logic;


import jakarta.persistence.*;
import main.logic.User.Moderation;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "application")
public class Confirmation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_create")
    private LocalDateTime dateCreate;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_accept")
    private LocalDateTime dateAccept;

    @Column(name = "status")
    private String status;

    @Column()
    private String comment;
    @ManyToOne()
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "id_moderator")
    private Moderation moderator;

    @Column(name = "id_organizator")
    private Integer idOrganizer;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "id_activity")
    private Activity activity;


    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Integer getIdOrganizer() {
        return idOrganizer;
    }

    public void setIdOrganizer(Integer idOrganizer) {
        this.idOrganizer = idOrganizer;
    }

    public Moderation getModerator() {
        return moderator;
    }

    public void setModerator(Moderation idModerator) {
        this.moderator = idModerator;
    }

    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
    }

    public LocalDateTime getDateAccept() {
        return dateAccept;
    }

    public void setDateAccept(LocalDateTime dateAccept) {
        this.dateAccept = dateAccept;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
