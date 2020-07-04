package ar.com.ada.api.nasa.entities;

import javax.persistence.*;

@Entity
@Table(name = "temperatura")
public class Temperatura {

    @Id
    @Column(name = "temperatura_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int temperaturaId;
    @ManyToOne
    @JoinColumn(name = "pais", referencedColumnName = "codigo_pais_id")
    private Pais pais;
    @Column(name = "año_temperatura")
    private int añoTemperatura;
    private double grados;

    public int getTemperaturaId() {
        return temperaturaId;
    }

    public void setTemperaturaId(int temperaturaId) {
        this.temperaturaId = temperaturaId;
    }

    public int getAñoTemperatura() {
        return añoTemperatura;
    }

    public void setAñoTemperatura(int añoTemperatura) {
        this.añoTemperatura = añoTemperatura;
    }

    public double getGrados() {
        return grados;
    }

    public void setGrados(double grados) {
        this.grados = grados;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
        this.pais.getTemperaturas().add(this);
    }
}