package c1220ftjavareact.gym.activity.controller;

import c1220ftjavareact.gym.activity.dto.ActivitySaveDto;
import c1220ftjavareact.gym.activity.dto.ActivityWithIdDto;
import c1220ftjavareact.gym.activity.service.IActivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/activities")
public class ActivityController {
    private final IActivityService iactivityService;

    public ActivityController(IActivityService iactivityService) {
        this.iactivityService = iactivityService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    @PostMapping()
    public ResponseEntity<ActivitySaveDto> createActivity(@RequestBody ActivitySaveDto activitySaveDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.iactivityService.createActivity(activitySaveDto));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    @PutMapping("/{id}")
    public ResponseEntity<ActivityWithIdDto> deleteActivity(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.iactivityService.deleteActivity(id));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ActivitySaveDto> updateActivity(@PathVariable Long id, @RequestBody ActivitySaveDto activity) {
        return ResponseEntity.status(HttpStatus.OK).body(this.iactivityService.updateActivityDto(id, activity));
    }

    @GetMapping
    public ResponseEntity<List<ActivityWithIdDto>> getAllActivities() {
        return ResponseEntity.status(HttpStatus.OK).body(this.iactivityService.getAllActivitiesDto());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityWithIdDto> getActivityById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.iactivityService.getActivityDtoById(id));
    }


}
