package c1220ftjavareact.gym.service;

import c1220ftjavareact.gym.activity.entity.Activity;
import c1220ftjavareact.gym.activity.service.IActivityService;
import c1220ftjavareact.gym.repository.TrainingSessionRepository;
import c1220ftjavareact.gym.room.entity.Room;
import c1220ftjavareact.gym.room.service.IRoomService;
import c1220ftjavareact.gym.training.dto.AvailableTimesDTO;
import c1220ftjavareact.gym.training.dto.TrainingSessionDTO;
import c1220ftjavareact.gym.training.dto.TrainingSessionSaveDTO;
import c1220ftjavareact.gym.training.entity.TrainingSession;
import c1220ftjavareact.gym.training.service.ITrainingSessionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    }


    @Override
    public List<TrainingSessionDTO> getAllTrainingSession() {
        List<TrainingSession> listSessions = trainingSessionRepository.findAll();
        return this.convertEntityList(listSessions);
    }

    @Override
    public List<TrainingSessionDTO> getAllByActivityId(Long activityId) {
        List<TrainingSession> listSessions = iActivityService.getActivityById(activityId).getTrainingSession();
        return this.convertEntityList(listSessions);
    }

    @Override
    public List<TrainingSessionDTO> getAllByRoomId(Long roomId) {
        List<TrainingSession> listSessions = iRoomService.getRoomById(roomId).getTrainingSession();
        return this.convertEntityList(listSessions);
    }


    @Override
    public TrainingSessionDTO getTrainingSessionById(Long id) {
        Optional<TrainingSession> trainingSession = trainingSessionRepository.findById(id);
        if (trainingSession.isEmpty()) {
        }

        TrainingSessionDTO dto = mapper.map(trainingSession.get(), TrainingSessionDTO.class);
        return dto;
    }

    @Override
    public TrainingSession getTrainingEntity(Long id) {
        Optional<TrainingSession> trainingSession = trainingSessionRepository.findById(id);
        if (trainingSession.isEmpty()) {
        }

        return trainingSession.get();
    }



    @Override
    @Transactional
    public TrainingSessionDTO updateTrainingSessionById(TrainingSessionDTO updateSession, Long id) {

        Optional<TrainingSession> optionalEntity = trainingSessionRepository.findById(id);
        if (optionalEntity.isEmpty()) {
            /// throw exception
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
        trainingEntity.setRoom(room);

        trainingEntity = trainingSessionRepository.save(trainingEntity);
        TrainingSessionDTO dto = mapper.map(trainingEntity, TrainingSessionDTO.class);

        return dto;
    }

    @Override
    @Transactional
    public TrainingSessionDTO removeTrainingSessionById(Long id) {

        return null;
    }

    @Override
    public AvailableTimesDTO getAvailableTimes() {
        return null;
    }

    @Override
    public AvailableTimesDTO getUnavailableTimes() {
        return null;
    }

    @Override
    public Integer getCapacity(Long id) {
        TrainingSessionDTO trainingSessionDTO = getTrainingSessionById(id);
        if (trainingSessionDTO == null) {
            return null;
        }
        return trainingSessionDTO.getCapacity();
    }

    private List<TrainingSessionDTO> convertEntityList(List<TrainingSession> entityList) {
        List<TrainingSessionDTO> listDTO = new ArrayList<>();

        for (TrainingSession item : entityList) {
            TrainingSessionDTO aux = mapper.map(item, TrainingSessionDTO.class);
            listDTO.add(aux);
        }

        return listDTO;
    }

}
