package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

// AOP 적용, 스프링 빈 등록
@Aspect
@Component
public class TimeTraceAop {
    // 공통관심사항을 적용할 곳(hello.hellospring 패키지 하위 모두) 타겟팅
    @Around("execution(* hello.hellospring..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();    // 시작 시각

        System.out.println("START: " + joinPoint.toString());

        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();   // 종료 시각
            long timeMs = finish - start;   // 호출 시간

            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}
