package ru.gb.lesson4.hw;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Connector {
    final StandardServiceRegistry registry;
    SessionFactory sessionFactory;

    public Connector() {
        registry=new StandardServiceRegistryBuilder()
                .configure("hibernate_hw.cfg.xml")
                .build();
        sessionFactory=new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }
}
