package ar.com.ada.api.nasa.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "pais")
public class Pais {

    @Id
    @Column(name = "codigo_pais_id")
    private int codigoPaisId;
    private String nombre;
    @OneToMany(mappedBy = "pais", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Temperatura> temperaturas = new ArrayList<>();

    public int getCodigoPaisId() {
        return codigoPaisId;
    }

    public void setCodigoPaisId(int codigoPaisId) {
        this.codigoPaisId = codigoPaisId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Temperatura> getTemperaturas() {
        return temperaturas;
    }

    public void setTemperaturas(List<Temperatura> temperaturas) {
        this.temperaturas = temperaturas;
    }

    public Temperatura getTemperatura(int añoTemperatura){

        for(Temperatura tem: temperaturas){

            if(tem.getAñoTemperatura() == añoTemperatura){

                return tem;
            }
        }
        return null;
    }
    
}