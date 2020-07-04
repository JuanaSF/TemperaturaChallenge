package ar.com.ada.api.nasa.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.ada.api.nasa.entities.Temperatura;

public interface TemperaturaRepository extends JpaRepository<Temperatura, Integer>{
    
}