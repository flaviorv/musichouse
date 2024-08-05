package com.musichouse.model.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor@AllArgsConstructor@Data@Builder
@Entity
public class Customer {
    private String name;
    @Id
    @Email
    private String email;
    @Size(min = 3, max = 6)
    private String password;
}
