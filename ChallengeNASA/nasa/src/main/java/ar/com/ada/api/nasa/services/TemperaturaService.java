package ar.com.ada.api.nasa.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.nasa.entities.Pais;
import ar.com.ada.api.nasa.entities.Temperatura;
import ar.com.ada.api.nasa.repos.TemperaturaRepository;

@Service
public class TemperaturaService {

    @Autowired
    TemperaturaRepository repo;
    @Autowired
    PaisService paisService;

    public int crearTemperatura(int codigoPais, int anio, double grados) {

        Temperatura temperatura = new Temperatura();
        temperatura.setAñoTemperatura(anio);
        temperatura.setGrados(grados);

        boolean resultado = existe(temperatura, codigoPais);

        if (resultado) {
            return 0;

        } else {
            temperatura.setPais(paisService.buscarPaisPor(codigoPais));
            grabar(temperatura);
            return temperatura.getTemperaturaId();
        }
    }

    public void grabar(Temperatura temperatura) {

        repo.save(temperatura);
    }

    public Temperatura buscarTemperaturaPor(int codigoPais, int añoTemperatura) {

        Pais paisDondeSeRegistro = paisService.buscarPaisPor(codigoPais);
        Temperatura temperatura = paisDondeSeRegistro.getTemperatura(añoTemperatura);

        return temperatura;
    }

    public boolean existe(Temperatura temperatura, int codigoPais) {

        Temperatura tExistente = this.buscarTemperaturaPor(codigoPais, temperatura.getAñoTemperatura());

        if (tExistente != null) {
            return true;
        }
        return false;
    }

    public Temperatura buscarTemperaturaPor(int id) {

        Optional<Temperatura> tempOp = repo.findById(id);

        if (tempOp.isPresent()) {
            return tempOp.get();
        }
        return null;
    }

    public void actualizarAnio(Temperatura temperatura, int anio){

        temperatura.setAñoTemperatura(anio);
        repo.save(temperatura);
    }

    public boolean eliminarTemperatura(int id) {

        Temperatura temperaturaABorrar = buscarTemperaturaPor(id);
        actualizarAnio(temperaturaABorrar, 0);

        return true;
    }

    public List<Temperatura> listarTemperaturasPor(int anio){

        List<Pais> paises = new ArrayList<>();
        paises = paisService.buscarPais();

        List<Temperatura> tem = new ArrayList<>();

        for (Pais pais : paises){

            tem.add(pais.getTemperatura(anio));
        }

        return tem;
        
    }

    public Temperatura traerTemperaturaMaxima(Pais pais){

        List<Temperatura> temperaturas = new ArrayList<>();

        temperaturas = pais.getTemperaturas();

        Temperatura max = new Temperatura();

        max = temperaturas.get(0);

        for (Temperatura temperatura : temperaturas) {
            
            if(temperatura.getGrados() > max.getGrados()){

                max = temperatura;
            }
        }

        return max;
    }


}