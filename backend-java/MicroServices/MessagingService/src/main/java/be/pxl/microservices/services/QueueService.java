package be.pxl.microservices.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class QueueService {
    @RabbitListener(queues = "messagingQueue")
    public void listen(String in) {
        System.out.println("Message read from messagingQueue : " + in);
    }
}

