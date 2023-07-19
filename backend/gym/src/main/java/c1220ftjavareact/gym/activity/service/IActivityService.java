package c1220ftjavareact.gym.activity.service;

<<<<<<< HEAD
import c1220ftjavareact.gym.activity.dto.ActivitySaveDto;
import c1220ftjavareact.gym.activity.dto.ActivityWithIdDto;
=======
import c1220ftjavareact.gym.activity.dto.ActivityDto;
>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34
import c1220ftjavareact.gym.activity.entity.Activity;

import java.util.List;

public interface IActivityService {

<<<<<<< HEAD
    public ActivitySaveDto createActivity(ActivitySaveDto activitySaveDto);

    public void deleteActivity(Long id);

    public ActivitySaveDto updateActivityDto(Long id, ActivitySaveDto activitySaveDto);

    public ActivityWithIdDto getActivityDtoById(Long id);

    public List<ActivityWithIdDto> getAllActivitiesDto();
=======
    public ActivityDto createActivity(ActivityDto activityDto);

    public void deleteActivity(Long id);

    public ActivityDto updateActivityDto(Long id, ActivityDto activityDto);

    public ActivityDto getActivityDtoById(Long id);

    public List<ActivityDto> getAllActivitiesDto();
>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34

    public Activity getActivityById(Long id);

    public List<Activity> getAllActivities();
}
