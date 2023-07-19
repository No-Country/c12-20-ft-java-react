package c1220ftjavareact.gym.activity.service;

import c1220ftjavareact.gym.activity.dto.ActivitySaveDto;
import c1220ftjavareact.gym.activity.dto.ActivityWithIdDto;
import c1220ftjavareact.gym.activity.repository.ActivityRepository;
import c1220ftjavareact.gym.activity.entity.Activity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImpActivityService implements IActivityService {

    private final ActivityRepository activityRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public ActivitySaveDto createActivity(ActivitySaveDto activitySaveDto) {
        Activity activity = modelMapper.map(activitySaveDto, Activity.class);
        activity.setImg("img");
        activity.setCreateDate(LocalDate.now());
        this.activityRepository.save(activity);
        return this.modelMapper.map(activity, ActivitySaveDto.class);
    }

    @Transactional
    @Override
    public void deleteActivity(Long id) {
        this.activityRepository.deleteById(id);
    }

    @Transactional
    @Override
    public ActivitySaveDto updateActivityDto(Long id, ActivitySaveDto activitySaveDto) {
        Activity activity = this.activityRepository.findById(id).orElseThrow(null);
        if (activity != null) {
            this.modelMapper.map(activitySaveDto, activity);
            Activity updateActivity = this.activityRepository.save(activity);
            return this.modelMapper.map(updateActivity, ActivitySaveDto.class);
        }
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public ActivityWithIdDto getActivityDtoById(Long id) {
        Activity activity = activityRepository.findById(id).orElseThrow(() -> new RuntimeException("Activities null"));
        ActivityWithIdDto activityWithIdDto = new ActivityWithIdDto();
        return this.modelMapper.map(activity, ActivityWithIdDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ActivityWithIdDto> getAllActivitiesDto() {
        List<Activity> activities = this.activityRepository.findAll();
        List<ActivityWithIdDto> activityWithIdDtos = new ArrayList();

        for(Activity activity : activities) {
            ActivityWithIdDto activityWithIdDto = this.modelMapper.map(activity, ActivityWithIdDto.class);
            activityWithIdDtos.add(activityWithIdDto);
        }
        return activityWithIdDtos;
    }

    @Transactional(readOnly = true)
    @Override
    public Activity getActivityById(Long id) {
        return activityRepository.findById(id).orElseThrow(() -> new RuntimeException("Activities null"));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }


}
