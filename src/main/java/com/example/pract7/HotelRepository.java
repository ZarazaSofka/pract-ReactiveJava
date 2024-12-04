package com.example.pract7;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface HotelRepository extends ReactiveCrudRepository<Hotel, Long> {
    Flux<Hotel> findAllByCity(String city);
}
