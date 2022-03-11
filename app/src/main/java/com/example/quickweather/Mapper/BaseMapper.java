package com.example.quickweather.Mapper;

public interface BaseMapper <Entity, DomainModel> {

    public DomainModel mapFromEntity(Entity entity);
}
