package com.vainaweb.schoolsystem.util.mapper;

interface Mapper<E, R, Q> {
  E toEntity(Q request);
  R toResponse(E entity);
}
