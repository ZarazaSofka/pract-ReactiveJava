package com.example.pract7;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/all")
    public Flux<Hotel> getAllHotels() {
        return hotelService.getAllHotels()
                .onErrorResume(e -> Flux.error(new RuntimeException("Ошибка получения отелей")));
    }

    @GetMapping("/id/{id}")
    public Mono<Hotel> getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id)
                .onErrorResume(e -> Mono.error(new RuntimeException("Отель не найден")));
    }

    @PostMapping("/add")
    public Mono<Hotel> createHotel(@RequestBody Hotel hotel) {
        return hotelService.createHotel(hotel)
                .onErrorResume(e -> Mono.error(new RuntimeException("Ошибка создания отеля")));
    }

    @DeleteMapping("/id/{id}")
    public Mono<Void> deleteHotel(@PathVariable Long id) {
        return hotelService.deleteHotel(id)
                .onErrorResume(e -> Mono.error(new RuntimeException("Ошибка удаления отеля")));
    }

    @GetMapping("/city/{city}")
    public Flux<Hotel> getHotelsByCity(@PathVariable String city) {
        return hotelService.getHotelsByCity(city)
                .onErrorResume(e -> Flux.error(new RuntimeException("Ошибка получения отелей города")));
    }
}
