package com.example.panaderia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.panaderia.entity.Rol;
import com.example.panaderia.entity.RolPermiso;

public interface RolPermisoRepository extends JpaRepository<RolPermiso, Long> {

    List<RolPermiso> findByRol(Rol rol);

    void deleteByRol(Rol rol);
}