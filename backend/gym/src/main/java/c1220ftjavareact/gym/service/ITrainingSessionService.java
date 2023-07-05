package c1220ftjavareact.gym.service;

import c1220ftjavareact.gym.dto.TrainingSessionDTO;
import c1220ftjavareact.gym.dto.TrainingSessionSaveDTO;

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
    void saveTrainingSession(TrainingSessionSaveDTO trainingSession);

    /// obtener todas las sesiones disponibles
    List<TrainingSessionDTO> getAllTrainingSession();

    /// obtener una sesion en particular
    TrainingSessionDTO getTrainingSessionById(Long id);

    /// actualizar la informacion de una sesion
    void updateTrainingSessionById(TrainingSessionDTO trainingSession, Long id);

    /// eliminar una sesion disponible
    void removeTrainingSessionById(Long id);
}
