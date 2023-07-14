package c1220ftjavareact.gym.training.service;

import c1220ftjavareact.gym.activity.entity.Activity;
import c1220ftjavareact.gym.room.entity.Room;
import c1220ftjavareact.gym.activity.service.IActivityService;
import c1220ftjavareact.gym.room.service.IRoomService;
import c1220ftjavareact.gym.training.dto.AvailableTimesDTO;
import c1220ftjavareact.gym.training.dto.TrainingSessionDTO;
import c1220ftjavareact.gym.training.dto.TrainingSessionSaveDTO;
import c1220ftjavareact.gym.training.entity.TrainingSession;
import c1220ftjavareact.gym.training.exception.TrainingException;
import c1220ftjavareact.gym.training.repository.TrainingSessionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

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
    }


    @Override
    public List<TrainingSessionDTO> getAllTrainingSession() {
        List<TrainingSession> listSessions = trainingSessionRepository.findByDeletedFalse();
        return this.convertEntityList(listSessions);
    }

    @Override
    public List<TrainingSessionDTO> getAllByActivityId(Long activityId) {
        List<TrainingSession> listSessions = iActivityService.getActivityById(activityId).getTrainingSession();
        listSessions = this.filterSessions(listSessions);

        return this.convertEntityList(listSessions);
    }

    @Override
    public List<TrainingSessionDTO> getAllByRoomId(Long roomId) {
        List<TrainingSession> listSessions = iRoomService.getRoomById(roomId).getTrainingSession();
        listSessions = this.filterSessions(listSessions);

        return this.convertEntityList(listSessions);
    }


    @Override
    public TrainingSessionDTO getTrainingSessionById(Long id) {
        Optional<TrainingSession> trainingSession = trainingSessionRepository.findById(id);
        TrainingSession aux = trainingSession.get();
        if (trainingSession.isEmpty() || trainingSession.get().isDeleted()) {
            throw new TrainingException("Training session not found", HttpStatus.NOT_FOUND);
        }

        TrainingSessionDTO dto = mapper.map(aux, TrainingSessionDTO.class);
        return dto;
    }

    /// Obtener entidad de training session
    @Override
    public TrainingSession getTrainingEntity(Long id) {
        Optional<TrainingSession> trainingSession = trainingSessionRepository.findById(id);
        if (trainingSession.isEmpty()) {
            throw new TrainingException("Training session not found", HttpStatus.NOT_FOUND);
        }

        return trainingSession.get();
    }

    @Override
    @Transactional
    public TrainingSessionDTO updateTrainingSessionById(TrainingSessionDTO updateSession, Long id) {

        Optional<TrainingSession> optionalEntity = trainingSessionRepository.findById(id);
        if (optionalEntity.isEmpty() || optionalEntity.get().isDeleted()) {
            throw new TrainingException("Training session not found", HttpStatus.NOT_FOUND);
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
        if (this.verifyRoomAvailable(updateSession)) {
            trainingEntity.setRoom(room);

            trainingEntity = trainingSessionRepository.save(trainingEntity);
            TrainingSessionDTO dto = mapper.map(trainingEntity, TrainingSessionDTO.class);

            return dto;
        } else {
            throw new TrainingException("Room not available at the selected time", HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }

    @Override
    @Transactional(readOnly = true)
    public Integer getCapacity(Long id) {
        return trainingSessionRepository.getReferenceById(id).getCapacity();
    }

    @Override
    @Transactional
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
    }

    private List<TrainingSessionDTO> convertEntityList(List<TrainingSession> entityList) {
        List<TrainingSessionDTO> listDTO = new ArrayList<>();

        for (TrainingSession item : entityList) {
            System.out.println("PRUEBA:" + item);
            TrainingSessionDTO aux = mapper.map(item, TrainingSessionDTO.class);
            listDTO.add(aux);
        }

        return listDTO;
    }

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

}
