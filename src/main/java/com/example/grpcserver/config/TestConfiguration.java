package com.example.grpcserver.config;

import com.example.grpcserver.event.TestEvent;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventPublicationInterceptor;

import java.lang.reflect.Method;

@Slf4j
@Configuration
public class TestConfiguration {
    @Bean
    public EventPublicationInterceptor eventPublicationInterceptor() {
        EventPublicationInterceptor interceptor = new EventPublicationInterceptor();
        interceptor.setApplicationEventClass(TestEvent.class);
        return interceptor;
    }

    @Bean
    public BeanFactoryPostProcessor beanFactoryPostProcessor(){
        return new BeanFactoryPostProcessor() {
            @Override
            public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//                    beanFactory.registerScope();
            }
        };
    }

    @Bean
    public FactoryBean<TestBean> hhh() {
        return new AbstractFactoryBean<TestBean>() {
            @Override
            public boolean isSingleton() {
                return super.isSingleton();
            }

            @Override
            public Class<?> getObjectType() {
                return TestBean.class;
            }

            @NotNull
            @Override
            protected TestBean createInstance() {
//                return new TestSubBean();//静态代理子类对象
                ProxyFactory proxyFactory = new ProxyFactory();
                proxyFactory.setTargetClass(TestBean.class);
                proxyFactory.addAdvisor(new PointcutAdvisor() {
                    @Override
                    public Pointcut getPointcut() {
                        return new StaticMethodMatcherPointcut() {
                            @Override
                            public boolean matches(Method method, Class<?> targetClass) {
                                return method.getName().equals("test");
                            }
                        };
                    }

                    @Override
                    public Advice getAdvice() {
                        return new MethodInterceptor() {
                            @Nullable
                            @Override
                            public Object invoke(@NotNull MethodInvocation invocation) throws Throwable {
                                return "proxyTest";
                            }
                        };
                    }

                    @Override
                    public boolean isPerInstance() {
                        return false;
                    }
                });
                return (TestBean)proxyFactory.getProxy();
            };
        };
    }

    @Bean
    public BeanPostProcessor beanPostProcessor(){
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                if(bean.getClass().equals(TestBean.class)){
                    log.info("发现你了");
                }
                return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if(bean.getClass().equals(TestBean.class)){
                    log.info("发现你了");
                }
                return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
            }
        };
    }


    @Bean
    public static Pointcut pointcut() {
        return new StaticMethodMatcherPointcut() {
            @Override
            public boolean matches(Method method, Class<?> targetClass) {
                return "execute".equals(method.getName()) && TestEvent.class.equals(targetClass);
            }
        };
    }


    @Bean
    public static PointcutAdvisor pointcutAdvisor(Pointcut pointcut,
                                                  EventPublicationInterceptor eventPublicationInterceptor) {
        // EventPulicationInterceptor is MethodInterceptor is Advice
        return new DefaultPointcutAdvisor(pointcut, eventPublicationInterceptor);
    }
}
