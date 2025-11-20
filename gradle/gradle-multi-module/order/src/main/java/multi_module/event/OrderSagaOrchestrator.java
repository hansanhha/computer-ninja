package multi_module.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import multi_module.service.InventoryCommandService;
import multi_module.service.OrderCommandService;
import multi_module.service.PaymentCommandService;

@Component
public class OrderSagaOrchestrator {

    private final ApplicationEventPublisher publisher;
    private final PaymentCommandService paymentCommandService;
    private final InventoryCommandService inventoryCommandService;
    private final OrderCommandService orderCommandService;

    public OrderSagaOrchestrator(ApplicationEventPublisher publisher,
                                 PaymentCommandService paymentCommandService,
                                 InventoryCommandService inventoryCommandService,
                                 OrderCommandService orderCommandService) {
        this.publisher = publisher;
        this.paymentCommandService = paymentCommandService;
        this.inventoryCommandService = inventoryCommandService;
        this.orderCommandService = orderCommandService;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(OrderCreatedEvent event) {
        paymentCommandService.requestPayment(event.orderId());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(PaymentCompletedEvent event) {
        inventoryCommandService.reserveStock(event.orderId());
    }

    // 결제 실패에 대한 order 모듈의 보상 트랜잭션 수행
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(PaymentFailedEvent event) {
        orderCommandService.cancelOrder(event.orderId());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(StockReservedEvent event) {
        orderCommandService.completeOrder(event.orderId());
    }
    
    // 재고 확보 실패에 대한 order 모듈의 보상 트랜잭션 수행
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(StockReservationFailedEvent event) {
        paymentCommandService.refund(event.orderId());
        orderCommandService.cancelOrder(event.orderId());
    }
    
}
