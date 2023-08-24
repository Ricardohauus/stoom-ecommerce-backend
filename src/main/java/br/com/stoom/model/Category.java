package br.com.stoom.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category")
    private Long id;

    @NotEmpty(message = "Name {field.not.blank}")
    @Size(min = 2, max = 150, message = "{field.size}")
    @Column(name = "name", length = 150, nullable = false, unique = true)
    private String name;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

}
