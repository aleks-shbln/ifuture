package ru.ashabalin.account.service.configuration;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.ashabalin.account.service.services.StatisticService;
import ru.ashabalin.account.service.utils.AopOrder;
import ru.ashabalin.account.service.utils.Counter;

@Aspect
@Order(AopOrder.STATISTIC_ADVICE)
@Component
public class StatisticAspect {

    private final StatisticService statisticService;

    @Autowired
    public StatisticAspect(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @Pointcut("execution(public Long ru.ashabalin.account.service.services.impl.AccountServiceImpl.getAmount(Integer))")
    public void callAtGetAmount() { }

    @Before("callAtGetAmount()")
    public void beforeGetAmount(JoinPoint jp) {
        statisticService.addInvocation(Counter.GET_AMOUNT);
    }

    @Pointcut("execution(public void ru.ashabalin.account.service.services.impl.AccountServiceImpl.addAmount(Integer, Long))")
    public void callAtAddAmount() { }

    @Before("callAtAddAmount()")
    public void beforeAddAmount(JoinPoint jp) {
        statisticService.addInvocation(Counter.ADD_AMOUNT);
    }

}
