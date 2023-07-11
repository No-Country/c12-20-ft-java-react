package c1220ftjavareact.gym.service.interfaces;

import c1220ftjavareact.gym.domain.dto.ActivityDto;
import c1220ftjavareact.gym.repository.entity.Activity;

import java.util.List;

public interface IActivityService {

    public ActivityDto createActivity(ActivityDto activityDto);

    public void deleteActivity(Long id);

    public ActivityDto updateActivityDto(Long id, ActivityDto activityDto);

    public ActivityDto getActivityDtoById(Long id);

    public List<ActivityDto> getAllActivitiesDto();

    public Activity getActivityById(Long id);

    public List<Activity> getAllActivities();
}
