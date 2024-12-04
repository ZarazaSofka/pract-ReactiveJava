package com.example.pract7;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.when;

@SpringBootTest
public class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;
    @InjectMocks
    private HotelService hotelService;


    @Test
    public void testGetAllHotels() {
        List<Hotel> hotelList = Arrays.asList(
                new Hotel(1L, "Four Seasons", "Москва", 5),
                new Hotel(2L, "Karl House", "Казань", 3),
                new Hotel(3L, "Националь", "Москва", 5)
        );

        when(hotelRepository.findAll()).thenReturn(Flux.fromIterable(hotelList));

        StepVerifier.create(hotelService.getAllHotels())
                .thenRequest(1)
                .expectNext(hotelList.get(0))
                .thenRequest(2)
                .expectNext(hotelList.get(1), hotelList.get(2))
                .verifyComplete();
    }

    @Test
    public void testGetHotelById_success() {
        Hotel hotel = new Hotel(1L, "Four Seasons", "Москва", 5);

        when(hotelRepository.findById(1L)).thenReturn(Mono.just(hotel));

        StepVerifier.create(hotelService.getHotelById(1L))
                .expectNext(hotel)
                .verifyComplete();
    }

    @Test
    public void testGetHotelById_notFound() {
        when(hotelRepository.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(hotelService.getHotelById(1L))
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    public void testCreateHotel() {
        Hotel hotel = new Hotel(null, "Metropol", "Москва", 5);
        Hotel savedHotel = new Hotel(1L, "Metropol", "Москва", 5);

        when(hotelRepository.save(hotel)).thenReturn(Mono.just(savedHotel));

        StepVerifier.create(hotelService.createHotel(hotel))
                .expectNext(savedHotel)
                .verifyComplete();
    }

    @Test
    public void testDeleteHotel() {
        when(hotelRepository.deleteById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(hotelService.deleteHotel(1L))
                .verifyComplete();
    }

    @Test
    public void testGetHotelsByCity() {
        List<Hotel> hotelList = Arrays.asList(
                new Hotel(1L, "Four Seasons", "Москва", 5),
                new Hotel(2L, "Karl House", "Казань", 3),
                new Hotel(3L, "Националь", "Москва", 5)
        );

        String city = "Москва";

        when(hotelRepository.findAllByCity(city))
                .thenReturn(Flux.fromIterable(hotelList).filter(hotel -> Objects.equals(hotel.getCity(), city)));

        StepVerifier.create(hotelService.getHotelsByCity(city))
                .thenRequest(1)
                .expectNext(hotelList.get(0))
                .thenRequest(1)
                .expectNext(hotelList.get(2))
                .verifyComplete();
    }
}
