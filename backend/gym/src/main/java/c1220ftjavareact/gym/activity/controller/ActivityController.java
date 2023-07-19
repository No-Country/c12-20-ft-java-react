package c1220ftjavareact.gym.activity.controller;

<<<<<<< HEAD
import c1220ftjavareact.gym.activity.dto.ActivitySaveDto;
import c1220ftjavareact.gym.activity.dto.ActivityWithIdDto;
=======
import c1220ftjavareact.gym.activity.dto.ActivityDto;
>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34
import c1220ftjavareact.gym.activity.service.IActivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/activities")
public class ActivityController {
    private final IActivityService iactivityService;

    public ActivityController(IActivityService iactivityService) {
        this.iactivityService = iactivityService;
    }

    @PostMapping()
<<<<<<< HEAD
    public ResponseEntity<ActivitySaveDto> createActivity(@RequestBody ActivitySaveDto activitySaveDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.iactivityService.createActivity(activitySaveDto));
=======
    public ResponseEntity<ActivityDto> createActivity(@RequestBody ActivityDto activityDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.iactivityService.createActivity(activityDto));
>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable("id") Long id) {
        this.iactivityService.deleteActivity(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
<<<<<<< HEAD
    public ResponseEntity<ActivitySaveDto> updateActivity(@PathVariable Long id, @RequestBody ActivitySaveDto activity) {
=======
    public ResponseEntity<ActivityDto> updateActivity(@PathVariable Long id, @RequestBody ActivityDto activity) {
>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34
        return ResponseEntity.status(HttpStatus.OK).body(this.iactivityService.updateActivityDto(id, activity));
    }

    @GetMapping
<<<<<<< HEAD
    public ResponseEntity<List<ActivityWithIdDto>> getAllActivities() {
=======
    public ResponseEntity<List<ActivityDto>> getAllActivities() {
>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34
        return ResponseEntity.status(HttpStatus.OK).body(this.iactivityService.getAllActivitiesDto());
    }

    @GetMapping("/{id}")
<<<<<<< HEAD
    public ResponseEntity<ActivityWithIdDto> getActivityById(@PathVariable("id") Long id) {
=======
    public ResponseEntity<ActivityDto> getActivityById(@PathVariable("id") Long id) {
>>>>>>> c54f09b24c3bd3e341803af2083ddfc8cfa6cb34
        return ResponseEntity.status(HttpStatus.OK).body(this.iactivityService.getActivityDtoById(id));
    }


}
