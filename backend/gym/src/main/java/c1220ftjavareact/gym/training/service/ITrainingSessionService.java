package c1220ftjavareact.gym.training.service;

import c1220ftjavareact.gym.training.dto.TrainingSessionDTO;
import c1220ftjavareact.gym.training.dto.TrainingSessionSaveDTO;
import c1220ftjavareact.gym.training.entity.TrainingSession;
import c1220ftjavareact.gym.training.model.UnAvailableTimes;

import java.util.List;

/**
 * /// usar el servicio de salas, para obtener todas las salas
 * /// Obtener los tiempos en las sesiones ocupan las salas
 * use gym;
 * SELECT r.id, r.name, ts.time_start, ts.time_end
 * FROM room r
 * JOIN training_session ts ON r.id = ts.id_room
 * /// Obtengo horarios disponibles de cada sala por dia (utilizando por los valores de dia de las sesiones)
 */

public interface ITrainingSessionService {

    /// crear una sesion nueva
    TrainingSessionDTO saveTrainingSession(TrainingSessionSaveDTO trainingSession);

    /// obtener todas las sesiones disponibles
    List<TrainingSessionDTO> getAllTrainingSession();

    /// obtener sesiones por activity
    List<TrainingSessionDTO> getAllByActivityId(Long activityId);

    /// obtener sesiones por room
    List<TrainingSessionDTO> getAllByRoomId(Long roomId);

    /// obtener una sesion en particular
    TrainingSessionDTO getTrainingSessionById(Long id);

    /// obtener entidad de sesion
    TrainingSession getTrainingEntity(Long id);

    /// actualizar la informacion de una sesion
    TrainingSessionDTO updateTrainingSessionById(TrainingSessionDTO trainingSession, Long id);

    /// eliminar una sesion disponible
    Integer getCapacity(Long id);

    /// eliminar una sesion
    TrainingSessionDTO removeTrainingSessionById(Long id);

    /// obtener turnos ocupados
    UnAvailableTimes getUnavailableTimes();
}