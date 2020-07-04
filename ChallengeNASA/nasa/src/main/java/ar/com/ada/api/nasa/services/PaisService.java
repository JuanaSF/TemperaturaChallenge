package ar.com.ada.api.nasa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.nasa.entities.Pais;
import ar.com.ada.api.nasa.repos.PaisRepository;

@Service
public class PaisService {

    @Autowired
    PaisRepository repo;

    public boolean crearPais(int codigoPais, String nombre) {

        Pais pais = new Pais();
        pais.setCodigoPaisId(codigoPais);
        pais.setNombre(nombre);
        repo.save(pais);

        return true;
    }

    public List<Pais> buscarPais() {

        return repo.findAll();
    }

    public Pais buscarPaisPor(int id) {

        Optional<Pais> paisO = repo.findById(id);

        if(paisO.isPresent()){
            return paisO.get();
        }
        return null;
    }

    public boolean actualizarNombre(Pais pais, String nombre){

        pais.setNombre(nombre);
        repo.save(pais);

        return true;
    }


}