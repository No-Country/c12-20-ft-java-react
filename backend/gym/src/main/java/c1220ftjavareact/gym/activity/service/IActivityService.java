package c1220ftjavareact.gym.activity.service;

import c1220ftjavareact.gym.activity.dto.ActivitySaveDto;
import c1220ftjavareact.gym.activity.dto.ActivityWithIdDto;
import c1220ftjavareact.gym.activity.entity.Activity;

import java.util.List;

public interface IActivityService {

    public ActivitySaveDto createActivity(ActivitySaveDto activitySaveDto);

    public ActivityWithIdDto deleteActivity(Long id);

    public ActivitySaveDto updateActivityDto(Long id, ActivitySaveDto activitySaveDto);

    public ActivityWithIdDto getActivityDtoById(Long id);

    public List<ActivityWithIdDto> getAllActivitiesDto();

    public Activity getActivityById(Long id);

    public List<Activity> getAllActivities();
}
