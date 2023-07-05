package c1220ftjavareact.gym.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Necesito un endpoint para consultar la disponibilidad de
 * Hay que ver el tema de como tratar los horarios, si con un objeto tipo enum o con un date picker por minutos,
 * si se puede hacer con periodos de 30m personalizables seria lo mejor asi se pueden hacer activades de 1h, 1h 30m y 2h
 * si tratara los horarios como minutos no podria seleccionar facilmente un horario adecuado porque no podriamos expresar
 * correctamente los horarios disponibles en la vista
 */
@RestController
@RequestMapping(value = "/sessions")
public class TrainingSessionController {

}
