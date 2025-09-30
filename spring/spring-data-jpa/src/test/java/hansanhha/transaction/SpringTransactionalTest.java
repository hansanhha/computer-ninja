package hansanhha.transaction;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
@DisplayName("스프링의 @Transactional 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class SpringTransactionalTest {

    @Test
    void 스프링테스트의_Transactional은_테스트종료후_자동롤백한다() {
        assertThat(TransactionSynchronizationManager.isActualTransactionActive()).isTrue();
        assertThat(TransactionSynchronizationManager.isSynchronizationActive()).isTrue();

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCompletion(int status) {
                if (status == TransactionSynchronization.STATUS_ROLLED_BACK) System.out.println("롤백됨");
            }
        });
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void NOT_SUPPORTED_전파는_트랜잭션을_비활성화한다() {
        assertThat(TransactionSynchronizationManager.isActualTransactionActive()).isFalse();
        assertThat(TransactionSynchronizationManager.isSynchronizationActive()).isFalse();
    }

    @Test
    @Rollback(false)
    void rollback_false는_테스트_종료후_커밋된다() {
        assertThat(TransactionSynchronizationManager.isActualTransactionActive()).isTrue();
        assertThat(TransactionSynchronizationManager.isSynchronizationActive()).isTrue();

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                System.out.println("커밋됨");
            }
        });
    }

    @Test
    @Commit
    void commit_어노테이션은_테스트_종료후_커밋된다() {
        assertThat(TransactionSynchronizationManager.isActualTransactionActive()).isTrue();
        assertThat(TransactionSynchronizationManager.isSynchronizationActive()).isTrue();

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                System.out.println("커밋됨");
            }
        });
    }

}
