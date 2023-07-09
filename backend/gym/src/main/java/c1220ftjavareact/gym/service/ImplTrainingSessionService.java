package c1220ftjavareact.gym.service;

import c1220ftjavareact.gym.domain.dto.TrainingSessionDTO;
import c1220ftjavareact.gym.domain.dto.TrainingSessionSaveDTO;
import c1220ftjavareact.gym.repository.entity.Activity;
import c1220ftjavareact.gym.repository.entity.Room;
import c1220ftjavareact.gym.repository.entity.TrainingSession;
import c1220ftjavareact.gym.repository.TrainingSessionRepository;
import c1220ftjavareact.gym.service.interfaces.IActivityService;
import c1220ftjavareact.gym.service.interfaces.IRoomService;
import c1220ftjavareact.gym.service.interfaces.ITrainingSessionService;
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
        if(trainingSession.isEmpty()) {
            /// exception
        }

        TrainingSessionDTO dto = mapper.map(trainingSession,TrainingSessionDTO.class);

        return dto;
    }

    /// Obtener entidad de training session
    @Override
    public TrainingSession getTrainingEntity(Long id) {
        Optional<TrainingSession> trainingSession = trainingSessionRepository.findById(id);
        if(trainingSession.isEmpty()) {
            /// throw exception
        }

        return trainingSession.get();
    }


    @Override
    @Transactional
    public TrainingSessionDTO updateTrainingSessionById(TrainingSessionDTO updateSession, Long id) {
        return null;
    }

    @Override
    @Transactional
    public void removeTrainingSessionById(Long id) {

    }

    private List<TrainingSessionDTO> convertEntityList(List<TrainingSession> entityList) {
        List<TrainingSessionDTO> listDTO = new ArrayList<>();

        for(TrainingSession item : entityList) {
            TrainingSessionDTO aux = mapper.map(item,TrainingSessionDTO.class);
            listDTO.add(aux);
        }

        return listDTO;
    }

}
