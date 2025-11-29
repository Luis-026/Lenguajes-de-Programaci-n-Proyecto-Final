package com.example.panaderia.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rol_permiso")
public class RolPermiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    @Column(nullable = false, length = 50)
    private String modulo;

    // getters y setters...

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public String getModulo() { return modulo; }
    public void setModulo(String modulo) { this.modulo = modulo; }
}
