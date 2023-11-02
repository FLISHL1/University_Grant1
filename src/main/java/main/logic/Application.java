package main.logic;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "application")
public class Application {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "date_create")
    private Date dateCreate;

    @Column(name = "date_accept")
    private Date dateAccept;

    @Column(name = "status")
    private String status;

    @Column()
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_activity")
    private Activity activity;
}
