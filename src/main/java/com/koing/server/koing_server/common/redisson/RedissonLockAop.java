package com.koing.server.koing_server.common.redisson;

import com.koing.server.koing_server.common.visitor.CommandAcceptor;
import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.visitor.LockNameVisitor;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
@DependsOn("embeddedRedisConfig")
public class RedissonLockAop {

    private final RedissonClient redissonClient;
    private final RedissonCallTransaction redissonCallTransaction;
    private final RedissonCallWithNoTransaction redissonCallWithNoTransaction;
    private final Logger LOGGER = LoggerFactory.getLogger(RedissonLockAop.class);

    @Around("@annotation(com.koing.server.koing_server.common.redisson.RedissonLock) && args(acceptor)")
    public Object lock(final ProceedingJoinPoint joinPoint, CommandAcceptor acceptor) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RedissonLock redissonLock = method.getAnnotation(RedissonLock.class);

        LockNameVisitor lockNameVisitor = new LockNameVisitor();
        acceptor.accept(lockNameVisitor);

        String lockName = lockNameVisitor.getLockName();

        /* get rLock 객체 */
        RLock rLock = redissonClient.getLock(lockName);

        try {
            /* get lock */
            System.out.println(Thread.currentThread().getName() + " " + lockName + " " + "tryLock");
            boolean available = rLock.tryLock(redissonLock.waitTime(), redissonLock.leaseTime(), redissonLock.timeUnit());
            System.out.println(Thread.currentThread().getName() + " " + lockName + " " + "getLock");
            if (!available) {
                return ErrorResponse.error(ErrorCode.NOT_ACCEPTABLE_DURING_PAYMENT_EXCEPTION);
            }

            LOGGER.info("Redisson Lock Key : {}", lockName);

            /* service call */
//            return joinPoint.proceed();
            return redissonCallTransaction.proceed(joinPoint);
        } catch (Exception e) {
            throw new InterruptedException();
        } finally {
            rLock.unlock();
            System.out.println(Thread.currentThread().getName() + " " + lockName + " " + "releaseLock");
        }
    }

    @Around("@annotation(com.koing.server.koing_server.common.redisson.RedissonLockWithNoTransaction) && args(acceptor)")
    public Object lockWithNoTransaction(final ProceedingJoinPoint joinPoint, CommandAcceptor acceptor) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RedissonLockWithNoTransaction redissonLockWithNoTransaction = method.getAnnotation(RedissonLockWithNoTransaction.class);

        LockNameVisitor lockNameVisitor = new LockNameVisitor();
        acceptor.accept(lockNameVisitor);

        String lockName = lockNameVisitor.getLockName();

        /* get rLock 객체 */
        RLock rLock = redissonClient.getLock(lockName);

        try {
            /* get lock */
            System.out.println(Thread.currentThread().getName() + " " + lockName + " " + "tryLock");
            boolean available = rLock.tryLock(redissonLockWithNoTransaction.waitTime(), redissonLockWithNoTransaction.leaseTime(), redissonLockWithNoTransaction.timeUnit());
            System.out.println(Thread.currentThread().getName() + " " + lockName + " " + "getLock");
            if (!available) {
                return ErrorResponse.error(ErrorCode.NOT_ACCEPTABLE_DURING_PAYMENT_EXCEPTION);
            }

            LOGGER.info("Redisson Lock Key : {}", lockName);

            /* service call */
//            return joinPoint.proceed();
            return redissonCallWithNoTransaction.proceed(joinPoint);
        } catch (Exception e) {
            throw new InterruptedException();
        } finally {
            rLock.unlock();
            System.out.println(Thread.currentThread().getName() + " " + lockName + " " + "releaseLock");
        }
    }
}
