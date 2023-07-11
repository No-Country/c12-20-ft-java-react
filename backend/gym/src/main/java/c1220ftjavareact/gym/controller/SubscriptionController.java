package c1220ftjavareact.gym.controller;

import c1220ftjavareact.gym.domain.dto.SubscriptionDTO;
import c1220ftjavareact.gym.service.interfaces.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    public ResponseEntity<SubscriptionDTO> createSubscription(@RequestBody SubscriptionDTO subscriptionDTO) {
        SubscriptionDTO createdSubscription = subscriptionService.createSubscription(subscriptionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubscription);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionDTO> updateSubscription(@PathVariable int id, @RequestBody SubscriptionDTO subscriptionDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(this.subscriptionService.updateSubscription(id, subscriptionDTO));
    }


    //100 | 99  | 101
    //verificar que als suscripciones activas solo cuentan cuando estan activas y segun eso el traingi session lo manejo
    //como 100/100 osea 100 clases y 100 sucripciones activas dar un mensaje que no se puede insertar por que esta lleno la
    //training session osea ya no te puedes suscribir

    //1 trainginf
    //10 | 10  10/10 mensaje que ya esta lleno la traing session no puedes suscribirte
    //capacidad suscripcion id los cuento y de ahi nada valido

    //update canceled
    //cambiar el nombre delete a cancelSuscription
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubscription(@PathVariable int id) {

        boolean success = subscriptionService.deleteSubscription(id);

        if (success) {
            return ResponseEntity.ok("Subscription deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subscription not found.");
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionDTO> getSubscriptionById(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.subscriptionService.getSubscriptionById(id));
    }
}
