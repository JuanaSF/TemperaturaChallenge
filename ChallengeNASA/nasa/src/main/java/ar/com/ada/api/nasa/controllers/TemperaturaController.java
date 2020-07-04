package ar.com.ada.api.nasa.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.nasa.entities.*;
import ar.com.ada.api.nasa.models.request.*;
import ar.com.ada.api.nasa.models.response.*;
import ar.com.ada.api.nasa.services.*;

@Controller
public class TemperaturaController {

    @Autowired
    TemperaturaService temperaturaService;

    @Autowired
    PaisService paisService;
    /*
     * POST /temperaturas : que registre una temperatura de un país en un año
     * específico RequestBody { “codigoPais”: 32, “anio”: 2010, “grados”: 38.6 }
     * Respuesta Esperada(JSON): { “id”: 25 //O cualquier número de temperatura que
     * devuelva. } Nota: que no permita cargar una temperatura ya existente para el
     * año especificado. Es decir, si hay una temperatura en el país correspondiente
     * al año ingresado, debe generar un error. 
     * GET /temperaturas/paises/{codigoPais} : que devuelva la lista de temperaturas con
     * sus años de un país especifico, indicado por “codigoPais”. 
     * DELETE /temperaturas/{id}: no se borrará la temperatura id, deberá cambiar el año a
     * 0.
     */

    @PostMapping("/temperaturas")
    public ResponseEntity<IdTemperaturaResponse> crearTemperatura(@RequestBody InfoTempRequest infoTem) {

        IdTemperaturaResponse res = new IdTemperaturaResponse();

        int resultado = temperaturaService.crearTemperatura(infoTem.codigoPais, infoTem.anio, infoTem.grados);

        if (resultado != 0) {

            res.id = resultado;
            return ResponseEntity.ok(res);

        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/temperaturas/paises/{codigoPais}")
    public ResponseEntity<List<InfoTempResponse>> listarTemperaturas(@PathVariable int codigoPais) {

        Pais pais = new Pais();
        pais = paisService.buscarPaisPor(codigoPais);
        List<Temperatura> temperaturas = pais.getTemperaturas();
        List<InfoTempResponse> res = new ArrayList<>();

        for (Temperatura temperatura : temperaturas) {

            InfoTempResponse tem = new InfoTempResponse();
            tem.añoTemperatura = temperatura.getAñoTemperatura();
            tem.grados = temperatura.getGrados();

            res.add(tem);
        }

        return ResponseEntity.ok(res);

    }

    @DeleteMapping("/temperaturas/{id}")
    public ResponseEntity<?> eliminarTemperatura(@PathVariable int id) {

        GenericResponse r = new GenericResponse();
        boolean borrada = temperaturaService.eliminarTemperatura(id);

        if (borrada) {

            r.isOk = true;
            r.message = "Eliminada exitosamente";

            return ResponseEntity.ok(r);
        }

        r.isOk = false;
        r.message = "algo salió mal";
        return ResponseEntity.badRequest().body(r);

    }

    /*-----------------------  BONUS   -----------------------
     * GET /temperaturas/anios/{anio} : que devuelva la lista de temperaturas de un
     * año en particular en el siguiente formato JSON Array: [{ “nombrePais”:
     * “Argentina”, “grados”: 29 }, { “nombrePais”: “Venezuela”, “grados”: 45 }] 
     * GET /temperaturas/maximas/{codigoPais} : que devuelva la temperatura máxima para
     * un país en particular en este formato JSON(informar el año en que ocurrió) {
     * “nombrePais”: “Venezuela”, “temperaturaMaxima”: 45, “anio”: 2011 }
     */

    @GetMapping("/temperaturas/anios/{anio}")
    public ResponseEntity<List<TemperaturaResponse>> listarTemperatura(@PathVariable int anio) {

        List<Temperatura> temperaturas = temperaturaService.listarTemperaturasPor(anio);

        List<TemperaturaResponse> temperaturasRes = new ArrayList<>();

        for (Temperatura tem : temperaturas) {

            TemperaturaResponse temRes = new TemperaturaResponse();

            if (tem != null) {
                temRes.nombrePais = tem.getPais().getNombre();
                temRes.grados = tem.getGrados();
                temperaturasRes.add(temRes);
            }
        }

        return ResponseEntity.ok(temperaturasRes);
    }

    @GetMapping("/temperaturas/maximas/{codigoPais}")
    public ResponseEntity<TempMaximaResponse> devolverTemperaturaMax(@PathVariable int codigoPais){

        TempMaximaResponse maxima = new TempMaximaResponse();
        Pais pais = paisService.buscarPaisPor(codigoPais);
        Temperatura temperaturaMax = temperaturaService.traerTemperaturaMaxima(pais);

        maxima.nombrePais = pais.getNombre();
        maxima.anio = temperaturaMax.getAñoTemperatura();
        maxima.temperaturaMaxima = temperaturaMax.getGrados();

        return ResponseEntity.ok(maxima);
    }

}