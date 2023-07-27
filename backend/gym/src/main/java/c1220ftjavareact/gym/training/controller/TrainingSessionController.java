package c1220ftjavareact.gym.training.controller;

import c1220ftjavareact.gym.training.dto.TrainingSessionDTO;
import c1220ftjavareact.gym.training.dto.TrainingSessionSaveDTO;
import c1220ftjavareact.gym.training.model.UnAvailableTimes;
import c1220ftjavareact.gym.training.service.ITrainingSessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/sessions")
public class TrainingSessionController {

    private final ITrainingSessionService iTrainingSessionService;

    public TrainingSessionController(ITrainingSessionService iTrainingSessionService) {
        this.iTrainingSessionService = iTrainingSessionService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    @PostMapping(value = "/create")
    public ResponseEntity<TrainingSessionDTO> saveTrainingSession(@RequestBody TrainingSessionSaveDTO trainingSessionSaveDTO) {
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

    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    @PutMapping(value = "/update/{sessionId}")
    public ResponseEntity<TrainingSessionDTO> updateTrainingSessionById(@RequestBody TrainingSessionDTO updatedTraining, @PathVariable Long sessionId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.iTrainingSessionService.updateTrainingSessionById(updatedTraining, sessionId));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    @DeleteMapping(value = "/delete/{sessionId}")
    public ResponseEntity<TrainingSessionDTO> deleteTrainingSessionsById(@PathVariable Long sessionId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.iTrainingSessionService.removeTrainingSessionById(sessionId));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    @GetMapping(value = "/unavailabletimes")
    public ResponseEntity<UnAvailableTimes> getUnAvailableTimes() {
        return ResponseEntity.status(HttpStatus.OK).body(this.iTrainingSessionService.getUnavailableTimes());
    }

}
