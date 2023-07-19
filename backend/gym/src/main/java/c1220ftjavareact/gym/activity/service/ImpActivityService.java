package c1220ftjavareact.gym.activity.service;

<<<<<<< HEAD
import c1220ftjavareact.gym.activity.dto.ActivitySaveDto;
import c1220ftjavareact.gym.activity.dto.ActivityWithIdDto;
import c1220ftjavareact.gym.activity.repository.ActivityRepository;
import c1220ftjavareact.gym.activity.entity.Activity;
=======
import c1220ftjavareact.gym.activity.dto.ActivityDto;
import c1220ftjavareact.gym.activity.repository.ActivityRepository;
import c1220ftjavareact.gym.activity.entity.Activity;
import c1220ftjavareact.gym.activity.service.IActivityService;
>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34
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
<<<<<<< HEAD
    public ActivitySaveDto createActivity(ActivitySaveDto activitySaveDto) {
        Activity activity = modelMapper.map(activitySaveDto, Activity.class);
        activity.setImg("img");
        activity.setCreateDate(LocalDate.now());
        this.activityRepository.save(activity);
        return this.modelMapper.map(activity, ActivitySaveDto.class);
=======
    public ActivityDto createActivity(ActivityDto activityDto) {
        Activity activity = modelMapper.map(activityDto, Activity.class);
        activity.setImg("img");
        activity.setCreateDate(LocalDate.now());
        this.activityRepository.save(activity);
        return this.modelMapper.map(activity, ActivityDto.class);
>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34
    }

    @Transactional
    @Override
    public void deleteActivity(Long id) {
        this.activityRepository.deleteById(id);
    }

    @Transactional
    @Override
<<<<<<< HEAD
    public ActivitySaveDto updateActivityDto(Long id, ActivitySaveDto activitySaveDto) {
        Activity activity = this.activityRepository.findById(id).orElseThrow(null);
        if (activity != null) {
            this.modelMapper.map(activitySaveDto, activity);
            Activity updateActivity = this.activityRepository.save(activity);
            return this.modelMapper.map(updateActivity, ActivitySaveDto.class);
=======
    public ActivityDto updateActivityDto(Long id, ActivityDto activityDto) {
        Activity activity = this.activityRepository.findById(id).orElseThrow(null);
        if (activity != null) {
            this.modelMapper.map(activityDto, activity);
            Activity updateActivite = this.activityRepository.save(activity);
            return this.modelMapper.map(updateActivite, ActivityDto.class);
>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34
        }
        return null;
    }

    @Transactional(readOnly = true)
    @Override
<<<<<<< HEAD
    public ActivityWithIdDto getActivityDtoById(Long id) {
        Activity activity = activityRepository.findById(id).orElseThrow(() -> new RuntimeException("Activities null"));
        ActivityWithIdDto activityWithIdDto = new ActivityWithIdDto();
        return this.modelMapper.map(activity, ActivityWithIdDto.class);
=======
    public ActivityDto getActivityDtoById(Long id) {
        Activity activity = activityRepository.findById(id).orElseThrow(() -> new RuntimeException("Activities null"));
        ActivityDto activityDto = new ActivityDto();
        return this.modelMapper.map(activity, ActivityDto.class);
>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34
    }

    @Transactional(readOnly = true)
    @Override
<<<<<<< HEAD
    public List<ActivityWithIdDto> getAllActivitiesDto() {
        List<Activity> activities = this.activityRepository.findAll();
        List<ActivityWithIdDto> activityWithIdDtos = new ArrayList();

        for(Activity activity : activities) {
            ActivityWithIdDto activityWithIdDto = this.modelMapper.map(activity, ActivityWithIdDto.class);
            activityWithIdDtos.add(activityWithIdDto);
        }
        return activityWithIdDtos;
=======
    public List<ActivityDto> getAllActivitiesDto() {
        List<Activity> activities = this.activityRepository.findAll();
        List<ActivityDto> activityDtos = new ArrayList();

        for(Activity activity : activities) {
            ActivityDto activityDto = this.modelMapper.map(activity, ActivityDto.class);
            activityDtos.add(activityDto);
        }
        return activityDtos;
>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34
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
