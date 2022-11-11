package br.com.apirest.apirestjunit.domain;

import lombok.*;

import javax.persistence.*;

@Entity(name = "Usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @Column(unique = true)
    private String email;
    private String password;

}
