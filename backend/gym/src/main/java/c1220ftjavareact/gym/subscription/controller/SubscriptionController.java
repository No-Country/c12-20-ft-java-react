package c1220ftjavareact.gym.subscription.controller;


import c1220ftjavareact.gym.subscription.other.SubscribedSessionDTO;
import c1220ftjavareact.gym.subscription.other.SubscriptionInfoDTO;
import c1220ftjavareact.gym.subscription.dto.SubscriptionSaveDTO;
import c1220ftjavareact.gym.subscription.dto.SubscriptionUpdateDTO;
import c1220ftjavareact.gym.subscription.service.ISubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
    private final ISubscriptionService subscriptionService;

    @PostMapping(value = "/create")
    public HttpEntity<String> saveSubscription(@RequestBody SubscriptionSaveDTO subscriptionSaveDTO) {
        this.subscriptionService.saveSubscription(subscriptionSaveDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Subscription created.");
    }

    @PutMapping(value = "/update")
    public HttpEntity<String> updateSubscriptionById(@RequestBody SubscriptionUpdateDTO subscriptionUpdateDTO) {
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