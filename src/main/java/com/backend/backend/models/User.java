package com.backend.backend.models;

import com.backend.backend.enuns.TypesEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;


@Entity(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = AUTO)
    private Long id;
    private String name;
    private String username;
    private String password;
    private String email;
    //public TypesEmail typesEmail = TypesEmail.WELCOME;
    @ManyToMany(fetch = EAGER)
    private Collection<Role> roles = new ArrayList<>();

    // Mapeamento com entidade email
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Email> emails;
}
