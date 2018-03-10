package com.number.client.number.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.number.client.exception.NumberException;
import com.number.client.model.NumberModel;
import com.number.client.number.NumberService;

@Aspect
@Component
public class NumberResourceAspect {

	private Environment environment;

	private NumberService numberService;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public NumberResourceAspect(Environment environment, NumberService numberService) {
		this.environment = environment;
		this.numberService = numberService;
	}

	@Around("execution(* com.number.client.number.NumberResource.save(..)) && args(numberModel,..)")
	public Object addNumber(ProceedingJoinPoint joinPoint, NumberModel numberModel) throws NumberException, Throwable {
		Object object = null;

		if (log.isDebugEnabled()) {
			enterLog(joinPoint);
		}

		try {
			boolean isExist = numberService.isNumberExist(numberModel.getNumber());

			if (isExist) {
				throw new NumberException(HttpStatus.CONFLICT, environment.getProperty("number.is.exist"));
			} else {
				object = joinPoint.proceed();

				if (log.isDebugEnabled()) {
					exitLog(joinPoint, object);
				}
			}
		} catch (Exception e) {
			throw e;
		}

		return object;
	}

	@Around("findByNumber(number) || deleteByNumber(number)")
	public Object checkGivenNumber(ProceedingJoinPoint joinPoint, long number) throws NumberException, Throwable {
		Object object = null;

		if (log.isDebugEnabled()) {
			enterLog(joinPoint);
		}

		try {
			boolean isExist = numberService.isNumberExist(number);

			if (isExist) {
				object = joinPoint.proceed();

				if (log.isDebugEnabled()) {
					exitLog(joinPoint, object);
				}
			} else {
				throw new NumberException(HttpStatus.NOT_FOUND, environment.getProperty("number.not.found"));
			}
		} catch (Exception e) {
			throw e;
		}

		return object;
	}

	@Around("maxNumber() || minNumber() || findAll()")
	public Object checkAddedNumber(ProceedingJoinPoint joinPoint) throws NumberException, Throwable {
		Object object = null;

		if (log.isDebugEnabled()) {
			enterLog(joinPoint);
		}

		try {
			boolean isNumberExist = numberService.isNumberExist();

			if (isNumberExist) {
				object = joinPoint.proceed();

				if (log.isDebugEnabled()) {
					exitLog(joinPoint, object);
				}
			} else {
				throw new NumberException(HttpStatus.NO_CONTENT, environment.getProperty("number.not.added"));
			}

		} catch (Exception e) {
			throw e;
		}

		return object;
	}

	@AfterThrowing(pointcut = "loggingPointcut()", throwing = "e")
	public void logAfterThrow(JoinPoint joinPoint, Throwable e) {
		if (e instanceof NumberException) {
			log.error("Number Exception in {}.{}() with cause = \'{}\' and exception = \'{}\'",
					joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
					e.getCause() != null ? e.getCause() : "NULL", e.getMessage(), e);
		} else {
			log.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
					joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL");
		}
	}

	@Pointcut("execution(* com.number.client.number.NumberResource.*(..))")
	public void loggingPointcut() {
		// logging Pointcut
	}

	@Pointcut("execution(* com.number.client.number.NumberResource.findByNumber(..)) && args(number,..)")
	public void findByNumber(long number) {
		// findByNumber Pointcut
	}

	@Pointcut("execution(* com.number.client.number.NumberResource.deleteByNumber(..)) && args(number,..)")
	public void deleteByNumber(long number) {
		// deleteByNumber Pointcut
	}

	@Pointcut("execution(* com.number.client.number.NumberResource.maxNumber(..))")
	public void maxNumber() {
		// maxNumber Pointcut
	}

	@Pointcut("execution(* com.number.client.number.NumberResource.minNumber(..))")
	public void minNumber() {
		// minNumber Pointcut
	}

	@Pointcut("execution(* com.number.client.number.NumberResource.findAll(..))")
	public void findAll() {
		// findAll Pointcut
	}

	private void enterLog(ProceedingJoinPoint joinPoint) {
		log.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
	}

	private void exitLog(ProceedingJoinPoint joinPoint, Object object) {
		log.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(), object);
	}

}
