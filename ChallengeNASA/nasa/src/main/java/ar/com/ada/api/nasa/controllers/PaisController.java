package ar.com.ada.api.nasa.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.nasa.entities.Pais;
import ar.com.ada.api.nasa.models.request.*;
import ar.com.ada.api.nasa.models.response.GenericResponse;
import ar.com.ada.api.nasa.services.PaisService;

@Controller
public class PaisController {

    @Autowired
    PaisService paisService;

    /*
     * POST /paises : que permita la creación de un país RequestBody: {
     * “codigoPais”: 32, “nombre”: “Argentina” } 
     * GET /paises : que devuelva la lista de países SIN las temperaturas. 
     * GET /paises/{id} : que devuelva la info de un pais en particular(SIN las temperaturas) 
     * PUT /paises/{id} : que actualice solo el nombre del país. Usar un requestBody que 
     * solo tenga el nombre del país.
     */

    @PostMapping("/paises")
    public ResponseEntity<?> crearPais(@RequestBody PaisRequest pais) {

        GenericResponse r = new GenericResponse();

        boolean resultado = paisService.crearPais(pais.codigoPais, pais.nombre);

        if (resultado) {
            r.isOk = true;
            r.message = "Se creo un pais exitosamente!.";
            return ResponseEntity.ok(r);
        } else {

            r.isOk = false;
            r.message = "No se pudo crear el pais! ya existe uno con ese codigo.";

            return ResponseEntity.badRequest().body(r);
        }

    }

    @GetMapping("/paises")
    public ResponseEntity<?> listarPaises() {

        return ResponseEntity.ok(paisService.buscarPais());
    }

    @GetMapping("/paises/{id}")
    public ResponseEntity<?> mostrarPais(@PathVariable int id) {

        Pais pais = paisService.buscarPaisPor(id);

        if (pais != null) {
            return ResponseEntity.ok(pais);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/paises/{id}")
    public ResponseEntity<?> actualizarPais(@PathVariable int id, @RequestBody NombrePaisRequest info) {

        GenericResponse r = new GenericResponse();

        Pais pais = paisService.buscarPaisPor(id);

        if (pais == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        boolean actualizacionExitosa = paisService.actualizarNombre(pais, info.nombre);

        if (actualizacionExitosa) {

            r.isOk = true;
            r.message = "Actualización de nombre exitosa";
            return ResponseEntity.ok(r);

        } else {

            r.isOk = false;
            r.message = "Algo fallo en la actualizacion";
            return ResponseEntity.ok(r);
        }

    }

}