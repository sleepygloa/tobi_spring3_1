package com.pojo.interfaces;

import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;

public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, MessageSource, ApplicationEventPublisher {

}
