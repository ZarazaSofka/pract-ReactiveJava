package com.example.pract7;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public Flux<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Mono<Hotel> getHotelById(Long id) {
        return hotelRepository.findById(id).filter(hotel -> Objects.equals(hotel.getId(), id))
                .switchIfEmpty(Mono.error(new RuntimeException("Отель не найден")));
    }

    public Mono<Hotel> createHotel(Hotel hotel) {

        System.out.println(hotel);
        return hotelRepository.save(hotel);
    }

    public Mono<Void> deleteHotel(Long id) {
        return hotelRepository.deleteById(id);
    }

    public Flux<Hotel> getHotelsByCity(String city) {
        return hotelRepository.findAllByCity(city)
                .filter(hotel -> Objects.equals(hotel.getCity(), city));
    }
}
