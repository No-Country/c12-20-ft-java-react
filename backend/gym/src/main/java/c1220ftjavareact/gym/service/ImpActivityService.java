package c1220ftjavareact.gym.service;

import c1220ftjavareact.gym.domain.dto.ActivityDto;
import c1220ftjavareact.gym.repository.ActivityRepository;
import c1220ftjavareact.gym.repository.entity.Activity;
import c1220ftjavareact.gym.service.interfaces.IActivityService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImpActivityService implements IActivityService {

    private final ActivityRepository activityRepository;
    private final ModelMapper modelMapper;


    public ImpActivityService(ActivityRepository activityRepository, ModelMapper modelMapper) {
        this.activityRepository = activityRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public ActivityDto createActivity(ActivityDto activityDto) {
        Activity activity = modelMapper.map(activityDto, Activity.class);
        activity.setImg("img");
        activity.setCreateDate(LocalDate.now());
        this.activityRepository.save(activity);
        return this.modelMapper.map(activity, ActivityDto.class);
    }

    @Transactional
    @Override
    public void deleteActivity(Long id) {
        this.activityRepository.deleteById(id);
    }

    @Transactional
    @Override
    public ActivityDto updateActivity(Long id, ActivityDto activityDto) {
        Activity activity = this.activityRepository.findById(id).orElseThrow(null);
        if (activity != null) {
            this.modelMapper.map(activityDto, activity);
            Activity updateActivite = this.activityRepository.save(activity);
            return this.modelMapper.map(updateActivite, ActivityDto.class);
        }
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public ActivityDto getActivitDtoyById(Long id) {
        Activity activity = activityRepository.findById(id).orElseThrow(()-> new RuntimeException("Activities null"));
        ActivityDto activityDto = new ActivityDto();
        return this.modelMapper.map(activity, ActivityDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ActivityDto> getAllActivities() {
        List<Activity> activities = this.activityRepository.findAll();
        List<ActivityDto> activityDtos = new ArrayList();

        for(Activity activity : activities) {
            ActivityDto activityDto = this.modelMapper.map(activity, ActivityDto.class);
            activityDtos.add(activityDto);
        }
        return activityDtos;
    }

    @Transactional(readOnly = true)
    @Override
    public Activity getActivityById(Long id) {
        return activityRepository.findById(id).orElseThrow(null);
    }
}
