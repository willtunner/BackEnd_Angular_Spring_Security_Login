package com.backend.backend.models;

import com.backend.backend.enuns.StatusEmail;
import com.backend.backend.enuns.TypesEmail;
import javax.persistence.*;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_EMAIL_SCHEMA", schema = "emails")
public class Email {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String subject;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean emailSend = false;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean deleted = false;
    private Integer count = 0;
    private LocalDateTime sendDateEmail;
    private StatusEmail statusEmail;
    private TypesEmail typesEmail;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

}
