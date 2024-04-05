package com.vainaweb.schoolsystem.component.mapper;

interface Mapper<E, R, Q> {
  E toEntity(Q request);
  R toResponse(E entity);
}
