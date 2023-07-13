package c1220ftjavareact.gym.activity.service;

import c1220ftjavareact.gym.activity.dto.ActivityDto;
import c1220ftjavareact.gym.activity.entity.Activity;

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
