package com.vainaweb.schoolsystem.model.mapper;

interface Mapper<E, R, Q> {
  E toEntity(Q request);
  R toResponse(E entity);
}
