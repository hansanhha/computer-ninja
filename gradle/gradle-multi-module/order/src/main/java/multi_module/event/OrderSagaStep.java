package multi_module.event;

public enum OrderSagaStep {
    
    ORDER_CREATED,
    PAYMENT_COMPLETED,
    STOCK_RESERVED,
    COMPLETED,
    COMPENSATION_STARTED
}
