package uk.lazycat.shop.util.log.time;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LogExecuteTimeAspect {

	@Around("@annotation(LogExecuteTime)")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis(); // 開始時間

		Object proceed = joinPoint.proceed(); // 執行目標方法

		long executionTime = System.currentTimeMillis() - start; // 執行完後計算時間

		log.info(joinPoint.getSignature() + " 執行時間：" + executionTime + "ms");

		return proceed;
	}

}
