package c1220ftjavareact.gym.service;

import c1220ftjavareact.gym.dto.TrainingSessionDTO;
import c1220ftjavareact.gym.dto.TrainingSessionSaveDTO;
import c1220ftjavareact.gym.entity.TrainingSession;
import c1220ftjavareact.gym.repository.TrainingSessionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImplTrainingSessionService implements ITrainingSessionService{

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
    public void saveTrainingSession(TrainingSessionSaveDTO trainingSession) {

    }

    @Override
    public List<TrainingSessionDTO> getAllTrainingSession() {
        return null;
    }

    @Override
    public TrainingSessionDTO getTrainingSessionById(Long id) {
    return null;
    }

    @Override
    public void updateTrainingSessionById(TrainingSessionDTO trainingSession, Long id) {

    }

    @Override
    public void removeTrainingSessionById(Long id) {

    }
}
