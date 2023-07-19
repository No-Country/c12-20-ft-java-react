package c1220ftjavareact.gym.subscription.controller;


import c1220ftjavareact.gym.training.service.ITrainingSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import c1220ftjavareact.gym.subscription.dto.SubscriptionDTO;
import c1220ftjavareact.gym.subscription.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscriptions")
@Slf4j
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    private final ITrainingSessionService trainingSessionRepository;

    @PostMapping
    public ResponseEntity<SubscriptionDTO> createSubscription(@RequestBody SubscriptionDTO subscriptionDTO) {
        Long trainingSessionId = subscriptionDTO.idTrainingSession();

        // Obtener la cantidad de training sessions con el mismo ID
        Integer countTrainingSessions = subscriptionService.getCountTrainingSession(trainingSessionId);

        // Obtener la capacidad de la training session
        Integer capacity = trainingSessionRepository.getCapacity(trainingSessionId);

        // Verificar si la capacidad está agotada
        if (countTrainingSessions >= capacity) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // Verificar si customer_id es nulo
        if (subscriptionDTO.customerId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // Lógica para crear la suscripción
        SubscriptionDTO createdSubscription = subscriptionService.createSubscription(subscriptionDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubscription);
    }



    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionDTO> updateSubscription(@PathVariable Long id, @RequestBody SubscriptionDTO subscriptionDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(this.subscriptionService.updateSubscription(id, subscriptionDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubscription(@PathVariable Long id) {

        boolean success = subscriptionService.deleteSubscription(id);

        if (success) {
            return ResponseEntity.ok("Subscription deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subscription not found.");
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionDTO> getSubscriptionById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.subscriptionService.getSubscriptionById(id));
    }
}
