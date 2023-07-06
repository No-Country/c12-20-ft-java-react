package c1220ftjavareact.gym.controller;

import c1220ftjavareact.gym.domain.dto.ActivityInDto;
import c1220ftjavareact.gym.repository.entity.Activity;
import c1220ftjavareact.gym.service.interfaces.IActivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/activities")
public class ActivityController {
    private final IActivityService iactivityService;
    private final ActivityInDto activityInDto;

    public ActivityController(IActivityService iactivityService, ActivityInDto activityInDto) {
        this.iactivityService = iactivityService;
        this.activityInDto = activityInDto;
    }

    @PostMapping()
    public ResponseEntity<Activity> createActivity(@RequestBody ActivityInDto activityInDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.iactivityService.createActivity(activityInDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable("id") Long id) {
        this.iactivityService.deleteActivity(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Activity> updateActivity(@PathVariable Long id, Activity activity) {
        return ResponseEntity.status(HttpStatus.OK).body(this.iactivityService.updateActivity(id, activityInDto));
    }

    @GetMapping
    public ResponseEntity<List<Activity>> getAllActivities() {
        return ResponseEntity.status(HttpStatus.OK).body(this.iactivityService.getAllActivities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Activity>> getActivityById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.iactivityService.getActivityById(id));
    }


}
