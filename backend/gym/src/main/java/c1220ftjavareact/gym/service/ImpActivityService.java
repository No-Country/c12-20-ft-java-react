package c1220ftjavareact.gym.service;

import c1220ftjavareact.gym.domain.dto.ActivityInDto;
import c1220ftjavareact.gym.domain.mapper.IMapperActivity;
import c1220ftjavareact.gym.repository.entity.Activity;
import c1220ftjavareact.gym.repository.ActivityRepository;
import c1220ftjavareact.gym.service.interfaces.IActivityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ImpActivityService implements IActivityService {

    private final ActivityRepository activityRepository;
    private final IMapperActivity<ActivityInDto, Activity> iMapperActivity;


    public ImpActivityService(ActivityRepository activityRepository, IMapperActivity iMapperActivity) {
        this.activityRepository = activityRepository;
        this.iMapperActivity = iMapperActivity;
    }

    @Transactional
    @Override
    public Activity createActivity(ActivityInDto activityInDto) {
        return this.activityRepository.save(iMapperActivity.map(activityInDto));
    }

    @Transactional
    @Override
    public void deleteActivity(Long id) {
        this.activityRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Activity updateActivity(Long id, ActivityInDto activityInDto) {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Activity> getActivityById(Long id) {
        return activityRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Activity> getAllActivities() {
       return activityRepository.findAll();
    }
}
