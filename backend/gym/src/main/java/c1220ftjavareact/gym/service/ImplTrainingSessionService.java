package c1220ftjavareact.gym.service;

import c1220ftjavareact.gym.domain.dto.TrainingSessionDTO;
import c1220ftjavareact.gym.domain.dto.TrainingSessionSaveDTO;
import c1220ftjavareact.gym.repository.entity.TrainingSession;
import c1220ftjavareact.gym.repository.TrainingSessionRepository;
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
    public void saveTrainingSession(TrainingSessionSaveDTO trainingSession) {
        /// VERIFICAR EXISTE ACTIVITY
        iActivityService.finbyId(trainingSession.getActivityId()); /// tira una excepcion si no encuentra
        /// VERIFICAR EXISTE ROOM
        iRoomService.findById(trainingSession.getRoomId());
        /// VERIFICAR DISPONIBILIDAD (POR HACER)

        /// LOGICA (POR HACER)

        /// PERSISTIR
        TrainingSession savedTraining = mapper.map(trainingSession, TrainingSession.class);
        trainingSessionRepository.save(savedTraining);

    }


    @Override
    public List<TrainingSessionDTO> getAllTrainingSession() {
        List<TrainingSession> listSessions = trainingSessionRepository.findAll();
        List<TrainingSessionDTO> listSessionsDto = new ArrayList<>();

        for(TrainingSession item : listSessions) {
            TrainingSessionDTO aux = mapper.map(item,TrainingSessionDTO.class);
            aux.setRoom_id(item.getRoom().getId());
            aux.setRoom_name(item.getRoom().getName());
            listSessionsDto.add(aux);
        }

        return listSessionsDto;
    }

    @Override
    public List<TrainingSessionDTO> getAllByActivityId() {
        return null;
    }


    @Override
    public TrainingSessionDTO getTrainingSessionById(Long id) {
        Optional<TrainingSession> trainingSession = trainingSessionRepository.findById(id);
        if(trainingSession.isEmpty()) {
            /// exception
        }

        TrainingSessionDTO dto = mapper.map(trainingSession,TrainingSessionDTO.class);
        dto.setRoom_id(trainingSession.getRoom().getId());
        dto.setRoom_name(trainingSession.getRoom().getName());

        return dto;
    }


    @Override
    @Transactional
    public void updateTrainingSessionById(TrainingSessionDTO trainingSession, Long id) {

    }

    @Override
    @Transactional
    public void removeTrainingSessionById(Long id) {

    }
}
