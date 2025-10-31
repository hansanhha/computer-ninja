package hansanhha;

import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.stereotype.Component;

@Component
public class FailureJobRetryListener implements RetryListener {
    
    @Override
    public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
        System.out.println(
            """
            start retry
            - name: %s
            - count: %s
            """.formatted(context.getAttribute(RetryContext.NAME), context.getRetryCount())
        );
        return true;
    }

    @Override
    public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback,
            Throwable throwable) {
        System.out.println(
            """
            closed retry
            - name: %s
            - count: %s
            - error: %s
            """.formatted(context.getAttribute(RetryContext.NAME), context.getRetryCount(), context.getLastThrowable())
        );
    }

    @Override
    public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback,
            Throwable throwable) {
        System.out.println(
            """
            error occured during retry
            - name: %s
            - count: %s
            - error: %s
            """.formatted(context.getAttribute(RetryContext.NAME), context.getRetryCount(), context.getLastThrowable())
        );
    }
    
}
