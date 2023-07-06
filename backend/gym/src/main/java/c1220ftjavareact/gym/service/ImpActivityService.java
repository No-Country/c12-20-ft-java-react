package c1220ftjavareact.gym.service;

import c1220ftjavareact.gym.domain.dto.ActivityInDto;
import c1220ftjavareact.gym.domain.mapper.IMapperActivity;
import c1220ftjavareact.gym.repository.ActivityRepository;
import c1220ftjavareact.gym.repository.entity.Activity;
import c1220ftjavareact.gym.service.interfaces.IActivityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public Activity updateActivity(Long id, ActivityInDto activityInDto) {
        return null;
    }

    @Override
    public Activity getActivityById(Long id) {
        return null;
    }

    @Override
    public List<Activity> getAllActivities() {
        return null;
    }
}
