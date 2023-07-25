package c1220ftjavareact.gym.subscription.controller;


import c1220ftjavareact.gym.subscription.dto.SubscribedSessionDTO;
import c1220ftjavareact.gym.subscription.dto.SubscriptionInfoDTO;
import c1220ftjavareact.gym.subscription.dto.SubscriptionSaveDTO;
import c1220ftjavareact.gym.subscription.dto.SubscriptionUpdateDTO;
import c1220ftjavareact.gym.subscription.service.ISubscriptionService;
import c1220ftjavareact.gym.training.dto.TrainingSessionDTO;
import c1220ftjavareact.gym.training.dto.TrainingSessionSaveDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/v1/subscriptions")
public class SubscriptionController {
    private final ISubscriptionService subscriptionService;

    public SubscriptionController(ISubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<String> saveSubscription(@RequestBody SubscriptionSaveDTO subscriptionSaveDTO) {
        this.subscriptionService.saveSubscription(subscriptionSaveDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Subscription created.");
    }

    @PutMapping(value = "/update/{subscriptionId}")
    public ResponseEntity<String> updateSubscriptionById(@RequestBody SubscriptionUpdateDTO subscriptionUpdateDTO) {
        this.subscriptionService.updateSubscription(subscriptionUpdateDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Subscription updated.");
    }

    /// MARCOS
    @GetMapping()
    public HttpEntity<Set<SubscriptionInfoDTO>> findAllSubscriptionByFormat() {
        return ResponseEntity.ok(this.subscriptionService.findAllSubscription());
    }

    /// MARCOS
    @GetMapping("/users/{id}")
    public HttpEntity<Set<SubscribedSessionDTO>> findSubscribedSession(@PathVariable Long id) {
        return ResponseEntity.ok(this.subscriptionService.findSubscribedSession(id));
    }
}
