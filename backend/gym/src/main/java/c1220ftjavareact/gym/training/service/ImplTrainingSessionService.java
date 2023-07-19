package c1220ftjavareact.gym.training.service;

import c1220ftjavareact.gym.activity.entity.Activity;
import c1220ftjavareact.gym.room.entity.Room;
import c1220ftjavareact.gym.activity.service.IActivityService;
import c1220ftjavareact.gym.room.service.IRoomService;
<<<<<<< HEAD
import c1220ftjavareact.gym.training.dto.AvailableTimesDTO;
import c1220ftjavareact.gym.training.dto.TrainingSessionDTO;
import c1220ftjavareact.gym.training.dto.TrainingSessionSaveDTO;
import c1220ftjavareact.gym.training.entity.TrainingSession;
import c1220ftjavareact.gym.training.exception.TrainingException;
import c1220ftjavareact.gym.training.repository.TrainingSessionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
=======
import c1220ftjavareact.gym.training.dto.TrainingSessionDTO;
import c1220ftjavareact.gym.training.dto.TrainingSessionSaveDTO;
import c1220ftjavareact.gym.training.entity.TrainingSession;
import c1220ftjavareact.gym.training.repository.TrainingSessionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34

@Service
public class ImplTrainingSessionService implements ITrainingSessionService {

    private final TrainingSessionRepository trainingSessionRepository;
    private final IActivityService iActivityService;
    private final IRoomService iRoomService;
    private final ModelMapper mapper;

    public ImplTrainingSessionService(TrainingSessionRepository trainingSessionRepository, IActivityService iActivityService, IRoomService iRoomService, ModelMapper mapper) {
        this.trainingSessionRepository = trainingSessionRepository;
        this.iActivityService = iActivityService;
        this.iRoomService = iRoomService;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public TrainingSessionDTO saveTrainingSession(TrainingSessionSaveDTO trainingSession) {
<<<<<<< HEAD
        /// Verificar si existe Activity
        Activity activity = iActivityService.getActivityById(trainingSession.getActivityId());

        /// verificar si existe Room
        Room room = iRoomService.getRoomById(trainingSession.getRoomId());

        /// Verificar capacidad maxima de room
        if (room.getMaxCapacity() < trainingSession.getCapacity()) {
            throw new TrainingException("Capacity out of room range", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        /// obtener sesiones en un room y conservar no borradas
        List<TrainingSession> sessionsAtRoom = room.getTrainingSession();
        sessionsAtRoom = this.filterSessions(sessionsAtRoom);

        /// verificar por dias
        if (this.verifyRoomAvailable(trainingSession)) {
            System.out.println("Objeto en servicio (ENTRANDO A IF): " + trainingSession);
            TrainingSession savedTraining = mapper.map(trainingSession, TrainingSession.class);

            /// Relacion Activity
            savedTraining.setActivity(activity);

            /// Relacion Room
            savedTraining.setRoom(room);

            /// Persistence
            savedTraining = trainingSessionRepository.save(savedTraining);
            TrainingSessionDTO dto = mapper.map(savedTraining, TrainingSessionDTO.class);

            return dto;
        } else {
            throw new TrainingException("Room not available at the selected time", HttpStatus.UNPROCESSABLE_ENTITY);
        }
=======
        /// Verificar si existe Activity, verificar si existe Room, verificar disponibilidad (pendiente)

        TrainingSession savedTraining = mapper.map(trainingSession, TrainingSession.class);

        /// Activity
        Activity activity = iActivityService.getActivityById(trainingSession.getActivityId());
        savedTraining.setActivity(activity);

        /// Room
        Room room = iRoomService.getRoomById(trainingSession.getRoomId());
        savedTraining.setRoom(room);

        /// Persistence
        savedTraining = trainingSessionRepository.save(savedTraining);
        TrainingSessionDTO dto = mapper.map(savedTraining, TrainingSessionDTO.class);

        return dto;
>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34
    }


    @Override
    public List<TrainingSessionDTO> getAllTrainingSession() {
<<<<<<< HEAD
        List<TrainingSession> listSessions = trainingSessionRepository.findByDeletedFalse();
=======
        List<TrainingSession> listSessions = trainingSessionRepository.findAll();
>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34
        return this.convertEntityList(listSessions);
    }

    @Override
    public List<TrainingSessionDTO> getAllByActivityId(Long activityId) {
        List<TrainingSession> listSessions = iActivityService.getActivityById(activityId).getTrainingSession();
<<<<<<< HEAD
        listSessions = this.filterSessions(listSessions);

=======
>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34
        return this.convertEntityList(listSessions);
    }

    @Override
    public List<TrainingSessionDTO> getAllByRoomId(Long roomId) {
        List<TrainingSession> listSessions = iRoomService.getRoomById(roomId).getTrainingSession();
<<<<<<< HEAD
        listSessions = this.filterSessions(listSessions);

=======
>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34
        return this.convertEntityList(listSessions);
    }


    @Override
    public TrainingSessionDTO getTrainingSessionById(Long id) {
        Optional<TrainingSession> trainingSession = trainingSessionRepository.findById(id);
<<<<<<< HEAD
        if (trainingSession.isEmpty() || trainingSession.get().isDeleted()) {
            throw new TrainingException("Training session not found", HttpStatus.NOT_FOUND);
        }

        TrainingSessionDTO dto = mapper.map(trainingSession, TrainingSessionDTO.class);
=======
        if (trainingSession.isEmpty()) {
            /// exception
        }

        TrainingSessionDTO dto = mapper.map(trainingSession, TrainingSessionDTO.class);

>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34
        return dto;
    }

    /// Obtener entidad de training session
    @Override
    public TrainingSession getTrainingEntity(Long id) {
        Optional<TrainingSession> trainingSession = trainingSessionRepository.findById(id);
        if (trainingSession.isEmpty()) {
<<<<<<< HEAD
            throw new TrainingException("Training session not found", HttpStatus.NOT_FOUND);
=======
            /// throw exception
>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34
        }

        return trainingSession.get();
    }

<<<<<<< HEAD
=======

>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34
    @Override
    @Transactional
    public TrainingSessionDTO updateTrainingSessionById(TrainingSessionDTO updateSession, Long id) {

        Optional<TrainingSession> optionalEntity = trainingSessionRepository.findById(id);
<<<<<<< HEAD
        if (optionalEntity.isEmpty() || optionalEntity.get().isDeleted()) {
            throw new TrainingException("Training session not found", HttpStatus.NOT_FOUND);
=======
        if (optionalEntity.isEmpty()) {
            /// throw exception
>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34
        }

        /// actualizar atributos
        TrainingSession aux = mapper.map(updateSession, TrainingSession.class);
        TrainingSession trainingEntity = optionalEntity.get();
        trainingEntity.setCapacity(aux.getCapacity());
        trainingEntity.setTimeEnd(aux.getTimeEnd());
        trainingEntity.setTimeStart(aux.getTimeStart());
        trainingEntity.setMonday(aux.isMonday());
        trainingEntity.setFriday(aux.isFriday());
        trainingEntity.setSunday(aux.isSunday());
        trainingEntity.setSaturday(aux.isSaturday());
        trainingEntity.setThursday(aux.isThursday());
        trainingEntity.setTuesday(aux.isTuesday());
        trainingEntity.setWednesday(aux.isWednesday());

        /// Activity
        Activity activity = iActivityService.getActivityById(updateSession.getActivityId());
        trainingEntity.setActivity(activity);

        /// Room
        Room room = iRoomService.getRoomById(updateSession.getRoomId());
<<<<<<< HEAD
        if (this.verifyRoomAvailable(updateSession)) {
            trainingEntity.setRoom(room);

            trainingEntity = trainingSessionRepository.save(trainingEntity);
            TrainingSessionDTO dto = mapper.map(trainingEntity, TrainingSessionDTO.class);

            return dto;
        } else {
            throw new TrainingException("Room not available at the selected time", HttpStatus.UNPROCESSABLE_ENTITY);
        }

=======
        trainingEntity.setRoom(room);

        trainingEntity = trainingSessionRepository.save(trainingEntity);
        TrainingSessionDTO dto = mapper.map(trainingEntity, TrainingSessionDTO.class);

        return dto;
>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34
    }

    @Override
    @Transactional
<<<<<<< HEAD
    public TrainingSessionDTO removeTrainingSessionById(Long id) {
        Optional<TrainingSession> optionalEntity = trainingSessionRepository.findById(id);
        if (optionalEntity.isEmpty() || optionalEntity.get().isDeleted()) {
            throw new TrainingException("Training session not found", HttpStatus.NOT_FOUND);
        }

        /// Creacion del dto de la training_session antes de eliminarla
        TrainingSessionDTO dto = mapper.map(optionalEntity.get(), TrainingSessionDTO.class);

        trainingSessionRepository.deleteSession(id);

        return dto;

    }

    @Override
    public AvailableTimesDTO getAvailableTimes() {
        return null;
    }

    @Override
    public AvailableTimesDTO getUnavailableTimes() {
        Map<Long, HashMap<String,ArrayList<String[]>>> unAvailableTimes = new HashMap<>();

        return null;
=======
    public void removeTrainingSessionById(Long id) {

>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34
    }

    private List<TrainingSessionDTO> convertEntityList(List<TrainingSession> entityList) {
        List<TrainingSessionDTO> listDTO = new ArrayList<>();

        for (TrainingSession item : entityList) {
            TrainingSessionDTO aux = mapper.map(item, TrainingSessionDTO.class);
            listDTO.add(aux);
        }

        return listDTO;
    }

<<<<<<< HEAD
    private boolean verifyRoomAvailable(TrainingSessionSaveDTO trainingSession) {
        /// verificar si existe Room
        Room room = iRoomService.getRoomById(trainingSession.getRoomId());

        /// obtener training sessions de ese room
        List<TrainingSession> sessionsAtRoom = room.getTrainingSession();

        /// filtrar por los dias que nos interesan
        List<TrainingSession> filteredSessionsRoom = sessionsAtRoom.stream()
                .filter(session -> session.isMonday() && !session.isDeleted() && trainingSession.isMonday()
                        || session.isTuesday() && !session.isDeleted() && trainingSession.isTuesday()
                        || session.isWednesday() && !session.isDeleted() && trainingSession.isWednesday()
                        || session.isThursday() && !session.isDeleted() && trainingSession.isThursday()
                        || session.isFriday() && !session.isDeleted() && trainingSession.isFriday()
                        || session.isSaturday() && !session.isDeleted() && trainingSession.isSaturday()
                        || session.isSunday() && !session.isDeleted() && trainingSession.isSunday())
                .collect(Collectors.toList());

        /// Evaluar horarios para determinar disponibilidad
        LocalTime newSessionStartTime = LocalTime.parse(trainingSession.getTimeStart());
        LocalTime newSessionEndTime = LocalTime.parse(trainingSession.getTimeEnd());

        for (TrainingSession session : filteredSessionsRoom) {
            LocalTime existingSessionStartTime = session.getTimeStart();
            LocalTime existingSessionEndTime = session.getTimeEnd();

            if (newSessionStartTime.isBefore(existingSessionEndTime) && newSessionEndTime.isAfter(existingSessionStartTime)) {
                // Se solapa con una sesión existente, no está disponible
                return false;
            }
        }

        return true;
    }

    private boolean verifyRoomAvailable(TrainingSessionDTO trainingSession) {
        TrainingSessionSaveDTO aux = mapper.map(trainingSession, TrainingSessionSaveDTO.class);
        return verifyRoomAvailable(aux);
    }

    private List<TrainingSession> filterSessions(List<TrainingSession> listSessions) {
        List<TrainingSession> filteredList = new ArrayList<>();

        /// Se seleccionan unicamente las sessiones no borradas
        for (TrainingSession item : listSessions) {
            if (!item.isDeleted()) {
                filteredList.add(item);
            }
        }

        return filteredList;
    }

=======
>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34
}
