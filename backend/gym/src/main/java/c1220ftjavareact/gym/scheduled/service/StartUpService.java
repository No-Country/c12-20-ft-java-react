package c1220ftjavareact.gym.scheduled.service;

import c1220ftjavareact.gym.scheduled.entity.ScheduledTaskLog;
import c1220ftjavareact.gym.scheduled.repository.ScheduledTaskLogRepository;
import c1220ftjavareact.gym.subscription.service.ISubscriptionService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Service
public class StartUpService {

    private final ScheduledTaskLogRepository scheduledTaskLogRepository;

    private final ISubscriptionService iSubscriptionService;

    public StartUpService(ScheduledTaskLogRepository scheduledTaskLogRepository, ISubscriptionService iSubscriptionService) {
        this.scheduledTaskLogRepository = scheduledTaskLogRepository;
        this.iSubscriptionService = iSubscriptionService;
    }

    @PostConstruct
    public void executeOncePerDayOnStartup() {
        LocalDateTime now = LocalDateTime.now();
        ScheduledTaskLog lastExecutionLog = scheduledTaskLogRepository.findTopByOrderByLastExecutionDesc();

        if (lastExecutionLog == null || lastExecutionLog.getLastExecution().isBefore(now.minusDays(1))) {
            iSubscriptionService.updateSubscriptionsStatus();

            // Actualizar la tabla ScheduledTaskLog con la nueva fecha y hora de ejecución
            if (lastExecutionLog == null) {
                lastExecutionLog = new ScheduledTaskLog();
            }

            lastExecutionLog.setLastExecution(now);
            scheduledTaskLogRepository.save(lastExecutionLog);
        }
    }

    private void executeFunction() {
        // Aquí colocas la lógica de la función que deseas ejecutar una vez al día
        System.out.println("Función ejecutada una vez al día.");
    }
}
