package com.zephie.house.util;

import com.zephie.house.core.api.IPizza;
import com.zephie.house.core.api.IPizzaInfo;
import com.zephie.house.core.builders.PizzaBuilder;
import com.zephie.house.core.builders.PizzaInfoBuilder;
import com.zephie.house.core.dto.PizzaInfoDTO;
import com.zephie.house.core.dto.SystemPizzaInfoDTO;
import com.zephie.house.services.entity.PizzaInfoService;
import com.zephie.house.storage.api.IPizzaInfoStorage;
import com.zephie.house.storage.api.IPizzaStorage;
import com.zephie.house.util.exceptions.FKNotFound;
import com.zephie.house.util.exceptions.NotFoundException;
import com.zephie.house.util.exceptions.ValidationException;
import com.zephie.house.util.exceptions.WrongVersionException;
import com.zephie.house.util.validators.api.IValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PizzaInfoServiceTest {

    @Mock
    private IPizzaInfoStorage storage;

    @Mock
    private IPizzaStorage pizzaStorage;

    @Mock
    private IValidator<PizzaInfoDTO> validator;

    @InjectMocks
    private PizzaInfoService service;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test(expected = ValidationException.class)
    @DisplayName("Null dto creation")
    public void testNullDtoCreation() {
        doThrow(ValidationException.class).when(validator).validate(null);

        service.create(null);
    }

    @Test(expected = ValidationException.class)
    @DisplayName("Null pizza id creation")
    public void testNullPizzaIdCreation() {
        PizzaInfoDTO dto = new PizzaInfoDTO();
        dto.setPizzaId(null);

        doThrow(ValidationException.class).when(validator).validate(dto);

        service.create(dto);
    }

    @Test(expected = ValidationException.class)
    @DisplayName("Negative size creation")
    public void testNegativeSizeCreation() {
        PizzaInfoDTO dto = new PizzaInfoDTO();
        dto.setPizzaId(1L);
        dto.setSize(-1);

        doThrow(ValidationException.class).when(validator).validate(dto);

        service.create(dto);
    }

    @Test(expected = ValidationException.class)
    @DisplayName("Zero size creation")
    public void testZeroSizeCreation() {
        PizzaInfoDTO dto = new PizzaInfoDTO();
        dto.setPizzaId(1L);
        dto.setSize(0);

        doThrow(ValidationException.class).when(validator).validate(dto);

        service.create(dto);
    }

    @Test(expected = FKNotFound.class)
    @DisplayName("Non existing pizza id creation")
    public void testNonExistingPizzaIdCreation() {
        given(pizzaStorage.isPresent(1L)).willReturn(false);
        service.create(new PizzaInfoDTO(1L, 1));
    }

    @Test
    @DisplayName("Valid creation")
    public void testValidCreation() {
        IPizza pizza = PizzaBuilder
                .create()
                .setId(1L)
                .setName("name")
                .setDescription("description")
                .setCreateDate(LocalDateTime.now())
                .setUpdateDate(LocalDateTime.now())
                .build();

        IPizzaInfo pizzaInfo = PizzaInfoBuilder
                .create()
                .setId(1L)
                .setPizza(pizza)
                .setSize(1)
                .setCreateDate(LocalDateTime.now())
                .setUpdateDate(LocalDateTime.now())
                .build();

        given(pizzaStorage.isPresent(1L)).willReturn(true);
        given(storage.create(any(SystemPizzaInfoDTO.class))).willReturn(pizzaInfo);

        IPizzaInfo pizzaInfoFromStorage = service.create(new PizzaInfoDTO(1L, 1));

        verify(storage).create(any(SystemPizzaInfoDTO.class));

        assertEquals(pizzaInfo, pizzaInfoFromStorage);
    }

    @Test
    @DisplayName("Read all")
    public void testReadAll() {
        List<IPizzaInfo> pizzaInfos = Stream.iterate(0, i -> i + 1)
                .limit(3)
                .map(i -> PizzaInfoBuilder
                        .create()
                        .setId((long) i)
                        .setPizza(PizzaBuilder
                                .create()
                                .setId((long) i)
                                .setName("name" + i)
                                .setDescription("description" + i)
                                .setCreateDate(LocalDateTime.now())
                                .setUpdateDate(LocalDateTime.now())
                                .build())
                        .setSize(i)
                        .setCreateDate(LocalDateTime.now())
                        .setUpdateDate(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());

        given(storage.read()).willReturn(pizzaInfos);

        Collection<IPizzaInfo> pizzaInfosFromStorage = service.read();

        verify(storage).read();

        assert pizzaInfosFromStorage != null;

        assert pizzaInfosFromStorage.size() == pizzaInfos.size();

        assert pizzaInfosFromStorage.containsAll(pizzaInfos);
    }

    @Test
    @DisplayName("Empty read all")
    public void testEmptyReadAll() {
        given(storage.read()).willReturn(List.of());

        Collection<IPizzaInfo> pizzaInfosFromStorage = service.read();

        verify(storage).read();

        assert pizzaInfosFromStorage != null;

        assert pizzaInfosFromStorage.size() == 0;
    }

    @Test(expected = IllegalArgumentException.class)
    @DisplayName("Null id reading")
    public void testNullIdReading() {
        service.read(null);
    }

    @Test
    @DisplayName("Id not found reading")
    public void testIdNotFoundReading() {
        given(storage.isPresent(1L)).willReturn(false);

        Optional<IPizzaInfo> pizzaInfoFromStorage = service.read(1L);

        verify(storage).read(1L);

        assert pizzaInfoFromStorage.isEmpty();
    }

    @Test
    @DisplayName("Read by id")
    public void testReadById() {
        IPizzaInfo pizzaInfo = PizzaInfoBuilder
                .create()
                .setId(1L)
                .setPizza(PizzaBuilder
                        .create()
                        .setId(1L)
                        .setName("name")
                        .setDescription("description")
                        .setCreateDate(LocalDateTime.now())
                        .setUpdateDate(LocalDateTime.now())
                        .build())
                .setSize(1)
                .setCreateDate(LocalDateTime.now())
                .setUpdateDate(LocalDateTime.now())
                .build();

        given(storage.read(1L)).willReturn(Optional.of(pizzaInfo));

        Optional<IPizzaInfo> pizzaInfoFromStorage = service.read(1L);

        verify(storage).read(1L);

        assert pizzaInfoFromStorage.isPresent();

        assertEquals(pizzaInfo, pizzaInfoFromStorage.get());
    }

    @Test(expected = IllegalArgumentException.class)
    @DisplayName("Null id updating")
    public void testNullIdUpdating() {
        service.update(null, new PizzaInfoDTO(1L, 1), LocalDateTime.now());
    }

    @Test(expected = ValidationException.class)
    @DisplayName("Null dto updating")
    public void testNullDtoUpdating() {
        doThrow(ValidationException.class).when(validator).validate(null);

        service.update(1L, null, LocalDateTime.now());
    }

    @Test(expected = IllegalArgumentException.class)
    @DisplayName("Null update date updating")
    public void testNullUpdateDateUpdating() {
        service.update(1L, new PizzaInfoDTO(1L, 1), null);
    }

    @Test(expected = ValidationException.class)
    @DisplayName("Null pizza id updating")
    public void testNullPizzaIdUpdating() {
        PizzaInfoDTO dto = new PizzaInfoDTO();
        dto.setPizzaId(null);
        dto.setSize(1);

        doThrow(ValidationException.class).when(validator).validate(dto);

        service.update(1L, dto, LocalDateTime.now());
    }

    @Test(expected = ValidationException.class)
    @DisplayName("Negative size updating")
    public void testNegativeSizeUpdating() {
        PizzaInfoDTO dto = new PizzaInfoDTO();
        dto.setPizzaId(1L);
        dto.setSize(-1);

        doThrow(ValidationException.class).when(validator).validate(dto);

        service.update(1L, dto, LocalDateTime.now());
    }

    @Test(expected = ValidationException.class)
    @DisplayName("Zero size updating")
    public void testZeroSizeUpdating() {
        PizzaInfoDTO dto = new PizzaInfoDTO();
        dto.setPizzaId(1L);
        dto.setSize(0);

        doThrow(ValidationException.class).when(validator).validate(dto);

        service.update(1L, dto, LocalDateTime.now());
    }

    @Test(expected = NotFoundException.class)
    @DisplayName("Non existing id updating")
    public void testNonExistingIdUpdating() {
        given(storage.isPresent(1L)).willReturn(false);
        service.update(1L, new PizzaInfoDTO(1L, 1), LocalDateTime.now());
    }

    @Test(expected = WrongVersionException.class)
    @DisplayName("Wrong version updating")
    public void testWrongVersionUpdating() {
        LocalDateTime updateDate = LocalDateTime.now();

        IPizzaInfo pizzaInfo = PizzaInfoBuilder
                .create()
                .setId(1L)
                .setPizza(PizzaBuilder
                        .create()
                        .setId(1L)
                        .setName("name")
                        .setDescription("description")
                        .setCreateDate(LocalDateTime.now())
                        .setUpdateDate(LocalDateTime.now())
                        .build())
                .setSize(1)
                .setCreateDate(LocalDateTime.now())
                .setUpdateDate(updateDate)
                .build();

        given(storage.isPresent(1L)).willReturn(true);
        given(storage.read(1L)).willReturn(Optional.of(pizzaInfo));

        service.update(1L, new PizzaInfoDTO(1L, 1), updateDate.minusSeconds(1));
    }

    @Test(expected = FKNotFound.class)
    @DisplayName("Non existing pizza id updating")
    public void testNonExistingPizzaIdUpdating() {
        LocalDateTime updateDate = LocalDateTime.now();

        IPizzaInfo pizzaInfo = PizzaInfoBuilder
                .create()
                .setId(1L)
                .setPizza(PizzaBuilder
                        .create()
                        .setId(1L)
                        .setName("name")
                        .setDescription("description")
                        .setCreateDate(LocalDateTime.now())
                        .setUpdateDate(LocalDateTime.now())
                        .build())
                .setSize(1)
                .setCreateDate(LocalDateTime.now())
                .setUpdateDate(updateDate)
                .build();

        given(storage.isPresent(1L)).willReturn(true);
        given(storage.read(1L)).willReturn(Optional.of(pizzaInfo));
        given(pizzaStorage.isPresent(1L)).willReturn(false);

        service.update(1L, new PizzaInfoDTO(1L, 1), updateDate);
    }
}