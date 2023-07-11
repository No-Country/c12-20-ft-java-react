package c1220ftjavareact.gym.controller;

import c1220ftjavareact.gym.domain.dto.ActivityDto;
import c1220ftjavareact.gym.service.interfaces.IActivityService;
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
    public ResponseEntity<ActivityDto> createActivity(@RequestBody ActivityDto activityDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.iactivityService.createActivity(activityDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable("id") Long id) {
        this.iactivityService.deleteActivity(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityDto> updateActivity(@PathVariable Long id, @RequestBody ActivityDto activity) {
        return ResponseEntity.status(HttpStatus.OK).body(this.iactivityService.updateActivity(id, activity));
    }

    @GetMapping
    public ResponseEntity<List<ActivityDto>> getAllActivities() {
        return ResponseEntity.status(HttpStatus.OK).body(this.iactivityService.getAllActivities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityDto> getActivityById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.iactivityService.getActivitDtoyById(id));
    }


}
