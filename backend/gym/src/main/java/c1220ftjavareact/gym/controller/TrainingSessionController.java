package c1220ftjavareact.gym.controller;

import c1220ftjavareact.gym.domain.dto.TrainingSessionDTO;
import c1220ftjavareact.gym.domain.dto.TrainingSessionSaveDTO;
import c1220ftjavareact.gym.service.interfaces.ITrainingSessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Necesito un endpoint para consultar la disponibilidad de
 * Hay que ver el tema de como tratar los horarios, si con un objeto tipo enum o con un date picker por minutos,
 * si se puede hacer con periodos de 30m personalizables seria lo mejor asi se pueden hacer activades de 1h, 1h 30m y 2h
 * si tratara los horarios como minutos no podria seleccionar facilmente un horario adecuado porque no podriamos expresar
 * correctamente los horarios disponibles en la vista
 */
@RestController
@RequestMapping(value = "api/v1/sessions")
public class TrainingSessionController {

    private final ITrainingSessionService iTrainingSessionService;

    public TrainingSessionController(ITrainingSessionService iTrainingSessionService) {
        this.iTrainingSessionService = iTrainingSessionService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<TrainingSessionDTO> saveTrainingSession(TrainingSessionSaveDTO trainingSessionSaveDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.iTrainingSessionService.saveTrainingSession(trainingSessionSaveDTO));
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<TrainingSessionDTO>> getAllTrainingSessions() {
        return ResponseEntity.status(HttpStatus.OK).body(this.iTrainingSessionService.getAllTrainingSession());
    }

    @GetMapping(value = "/find/{sessionId}")
    public ResponseEntity<TrainingSessionDTO> getTrainingSessionsById(@PathVariable Long sessionId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.iTrainingSessionService.getTrainingSessionById(sessionId));
    }

    @GetMapping(value = "/activity/{activityId}")
    public ResponseEntity<List<TrainingSessionDTO>> getAllTrainingSessionsByActivityId(@PathVariable Long activityId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.iTrainingSessionService.getAllByActivityId(activityId));
    }

    @GetMapping(value = "/room/{roomId}")
    public ResponseEntity<List<TrainingSessionDTO>> getAllTrainingSessionsByRoomId(@PathVariable Long roomId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.iTrainingSessionService.getAllByRoomId(roomId));
    }

    @PutMapping(value = "/update/{sessionId}")
    public ResponseEntity<TrainingSessionDTO> updateTrainingSessionById(@RequestBody TrainingSessionDTO updatedTraining, @PathVariable Long sessionId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.iTrainingSessionService.updateTrainingSessionById(updatedTraining, sessionId));
    }


}
