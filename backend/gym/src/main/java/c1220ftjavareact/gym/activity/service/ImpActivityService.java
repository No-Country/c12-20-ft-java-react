package c1220ftjavareact.gym.activity.service;

import c1220ftjavareact.gym.activity.dto.ActivitySaveDto;
import c1220ftjavareact.gym.activity.dto.ActivityWithIdDto;
import c1220ftjavareact.gym.activity.entity.Activity;
import c1220ftjavareact.gym.activity.exception.ActivityException;
import c1220ftjavareact.gym.activity.repository.ActivityRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
public class ImpActivityService implements IActivityService {

    private final ActivityRepository activityRepository;
    private final ModelMapper modelMapper;


    @Transactional
    @Override
    public ActivitySaveDto createActivity(ActivitySaveDto activitySaveDto) {
        Activity activity = modelMapper.map(activitySaveDto, Activity.class);
        activity.setCreateDate(LocalDate.now());
        activity.setDeleted(false);
        if (!StringUtils.hasText(activitySaveDto.getName())) {
            throw new ActivityException("The name cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if (!StringUtils.hasText(activitySaveDto.getDescription())) {
            throw new ActivityException("The description cannot be empty", HttpStatus.BAD_REQUEST);
        }
        if (!StringUtils.hasText(activitySaveDto.getImg())) {
            throw new ActivityException("The picture cannot be empty", HttpStatus.BAD_REQUEST);
        }
        this.activityRepository.save(activity);
        return this.modelMapper.map(activity, ActivitySaveDto.class);
    }

    @Transactional
    @Override
    public ActivityWithIdDto deleteActivity(Long id) {
        Optional<Activity> activity = this.activityRepository.findById(id);
        if (activity.isEmpty() || activity.get().isDeleted()) {
            throw new ActivityException("Activity not found", HttpStatus.NOT_FOUND);
        }

        this.activityRepository.deleteActivity(id);

        ActivityWithIdDto activityWithIdDto = modelMapper.map(activity.get(), ActivityWithIdDto.class);
        return activityWithIdDto;
    }


    @Transactional
    @Override
    public ActivitySaveDto updateActivityDto(Long id, ActivitySaveDto activitySaveDto) {
        Activity activity = this.activityRepository.findById(id).orElseThrow(null);


        if (!StringUtils.hasText(activitySaveDto.getName())) {
            throw new ActivityException("The name is impty", HttpStatus.BAD_REQUEST);
        }

        if (!StringUtils.hasText(activitySaveDto.getDescription())) {
            throw new ActivityException("The description is impty", HttpStatus.BAD_REQUEST);
        }

        if (!StringUtils.hasText(activitySaveDto.getImg())) {
            throw new ActivityException("The picture is impty", HttpStatus.BAD_REQUEST);
        }

        if (activity == null || activity.isDeleted()) {
            throw new ActivityException("The activity is not found ", HttpStatus.BAD_REQUEST);
        } else {
            this.modelMapper.map(activitySaveDto, activity);
            Activity updateActivity = this.activityRepository.save(activity);
            return this.modelMapper.map(updateActivity, ActivitySaveDto.class);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public ActivityWithIdDto getActivityDtoById(Long id) {
        Activity activity = activityRepository.findActivityFalse(id);
        if (activity == null) {
            throw new ActivityException("The activity is not found", HttpStatus.BAD_REQUEST);
        }
        ActivityWithIdDto activityWithIdDto = new ActivityWithIdDto();
        return this.modelMapper.map(activity, ActivityWithIdDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ActivityWithIdDto> getAllActivitiesDto() {
        List<Activity> activities = this.activityRepository.findAllActivityByDeletedFalse();
        List<ActivityWithIdDto> activityWithIdDtos = new ArrayList();

        for (Activity activity : activities) {
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
