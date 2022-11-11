package com.zephie.house.util;

import com.zephie.house.core.api.IPizza;
import com.zephie.house.core.builders.PizzaBuilder;
import com.zephie.house.core.dto.PizzaDTO;
import com.zephie.house.core.dto.SystemPizzaDTO;
import com.zephie.house.services.entity.PizzaService;
import com.zephie.house.storage.api.IPizzaStorage;
import com.zephie.house.util.exceptions.NotFoundException;
import com.zephie.house.util.exceptions.NotUniqueException;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PizzaServiceTest {
    @Mock
    private IPizzaStorage pizzaStorage;

    @Mock
    private IValidator<PizzaDTO> validator;

    @InjectMocks
    private PizzaService pizzaService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test(expected = ValidationException.class)
    @DisplayName("Null dto creation")
    public void testCreateNull() {
        doThrow(ValidationException.class).when(validator).validate(null);

        pizzaService.create(null);
    }

    @Test(expected = ValidationException.class)
    @DisplayName("Empty name creation")
    public void testCreateEmptyName() {
        PizzaDTO dto = new PizzaDTO();
        dto.setName("");
        doThrow(ValidationException.class).when(validator).validate(dto);

        pizzaService.create(dto);
    }

    @Test(expected = ValidationException.class)
    @DisplayName("Blank name creation")
    public void testCreateBlankName() {
        PizzaDTO dto = new PizzaDTO();
        dto.setName(" ");
        doThrow(ValidationException.class).when(validator).validate(dto);

        pizzaService.create(dto);
    }

    @Test(expected = ValidationException.class)
    @DisplayName("Null name creation")
    public void testCreateNullName() {
        PizzaDTO dto = new PizzaDTO();
        dto.setName(null);
        doThrow(ValidationException.class).when(validator).validate(dto);

        pizzaService.create(dto);
    }

    @Test(expected = ValidationException.class)
    @DisplayName("Empty description creation")
    public void testCreateEmptyDescription() {
        PizzaDTO dto = new PizzaDTO();
        dto.setName("name");
        dto.setDescription("");
        doThrow(ValidationException.class).when(validator).validate(dto);

        pizzaService.create(dto);
    }

    @Test(expected = ValidationException.class)
    @DisplayName("Blank description creation")
    public void testCreateBlankDescription() {
        PizzaDTO dto = new PizzaDTO();
        dto.setName("name");
        dto.setDescription(" ");
        doThrow(ValidationException.class).when(validator).validate(dto);

        pizzaService.create(dto);
    }

    @Test(expected = ValidationException.class)
    @DisplayName("Null description creation")
    public void testCreateNullDescription() {
        PizzaDTO dto = new PizzaDTO();
        dto.setName("name");
        dto.setDescription(null);
        doThrow(ValidationException.class).when(validator).validate(dto);

        pizzaService.create(dto);
    }

    @Test(expected = ValidationException.class)
    @DisplayName("Name too long creation")
    public void testCreateNameTooLong() {
        PizzaDTO dto = new PizzaDTO();
        dto.setName("name");
        dto.setDescription("description");
        dto.setName("name".repeat(100));
        doThrow(ValidationException.class).when(validator).validate(dto);

        pizzaService.create(dto);
    }

    @Test(expected = ValidationException.class)
    @DisplayName("Description too long creation")
    public void testCreateDescriptionTooLong() {
        PizzaDTO dto = new PizzaDTO();
        dto.setName("name");
        dto.setDescription("description");
        dto.setDescription("description".repeat(100));
        doThrow(ValidationException.class).when(validator).validate(dto);

        pizzaService.create(dto);
    }

    @Test(expected = NotUniqueException.class)
    @DisplayName("Not unique name creation")
    public void testCreateNotUnique() {
        IPizza pizza = PizzaBuilder.create().setName("name").setDescription("description").setCreateDate(LocalDateTime.now()).setUpdateDate(LocalDateTime.now()).build();

        given(pizzaStorage.read("name")).willReturn(Optional.of(pizza));

        pizzaService.create(new PizzaDTO("name", "description"));
    }

    @Test
    @DisplayName("Creation")
    public void testCreate() {
        IPizza pizza = PizzaBuilder.create().setId(1L).setName("name").setDescription("description").setCreateDate(LocalDateTime.now()).setUpdateDate(LocalDateTime.now()).build();

        given(pizzaStorage.create(any(SystemPizzaDTO.class))).willReturn(pizza);

        IPizza pizzaFromService = pizzaService.create(new PizzaDTO("name", "description"));

        verify(pizzaStorage).create(any(SystemPizzaDTO.class));

        assertEquals(pizza, pizzaFromService);
    }

    @Test
    @DisplayName("Read all")
    public void testRead() {
        List<IPizza> pizzas = List.of(PizzaBuilder.create().setId(1L).setName("name1").setDescription("description1").setCreateDate(LocalDateTime.now()).setUpdateDate(LocalDateTime.now()).build(), PizzaBuilder.create().setId(2L).setName("name2").setDescription("description2").setCreateDate(LocalDateTime.now()).setUpdateDate(LocalDateTime.now()).build(), PizzaBuilder.create().setId(3L).setName("name3").setDescription("description3").setCreateDate(LocalDateTime.now()).setUpdateDate(LocalDateTime.now()).build());

        given(pizzaStorage.read()).willReturn(pizzas);

        Collection<IPizza> result = pizzaService.read();

        verify(pizzaStorage).read();

        assert result != null;

        assert result.size() == 3;

        assert result.containsAll(pizzas);
    }

    @Test
    @DisplayName("Empty read all")
    public void testReadEmpty() {
        given(pizzaStorage.read()).willReturn(List.of());

        Collection<IPizza> pizzas = pizzaService.read();

        verify(pizzaStorage).read();

        assert pizzas != null;

        assert pizzas.size() == 0;
    }

    @Test(expected = IllegalArgumentException.class)
    @DisplayName("Null id reading")
    public void testReadByNullId() {
        pizzaService.read(null);
    }

    @Test
    @DisplayName("Id not found reading")
    public void testReadByIdNotFound() {
        given(pizzaStorage.read(1L)).willReturn(Optional.empty());

        Optional<IPizza> result = pizzaService.read(1L);

        verify(pizzaStorage).read(1L);

        assert result.isEmpty();
    }

    @Test
    @DisplayName("Read by id")
    public void testReadById() {
        IPizza pizza = PizzaBuilder.create().setId(1L).setName("name").setDescription("description").setCreateDate(LocalDateTime.now()).setUpdateDate(LocalDateTime.now()).build();

        given(pizzaStorage.read(1L)).willReturn(Optional.of(pizza));

        Optional<IPizza> result = pizzaService.read(1L);

        verify(pizzaStorage).read(1L);

        assert result.isPresent();

        assertEquals(pizza, result.get());
    }

    @Test(expected = IllegalArgumentException.class)
    @DisplayName("Null id updating")
    public void testUpdateByNullId() {
        pizzaService.update(null, new PizzaDTO("name", "description"), LocalDateTime.now());
    }

    @Test(expected = ValidationException.class)
    @DisplayName("Null dto updating")
    public void testUpdateByNullDto() {
        doThrow(ValidationException.class).when(validator).validate(null);

        pizzaService.update(1L, null, LocalDateTime.now());
    }

    @Test(expected = IllegalArgumentException.class)
    @DisplayName("Null date updating")
    public void testUpdateByNullDate() {
        pizzaService.update(1L, new PizzaDTO("name", "description"), null);
    }

    @Test(expected = ValidationException.class)
    @DisplayName("Null dto name updating")
    public void testUpdateByDtoWithNullName() {
        PizzaDTO dto = new PizzaDTO();
        dto.setName(null);
        dto.setDescription("description");
        doThrow(ValidationException.class).when(validator).validate(dto);

        pizzaService.update(1L, dto, LocalDateTime.now());
    }

    @Test(expected = ValidationException.class)
    @DisplayName("Empty dto name updating")
    public void testUpdateByDtoWithEmptyName() {
        PizzaDTO dto = new PizzaDTO();
        dto.setName("");
        dto.setDescription("description");
        doThrow(ValidationException.class).when(validator).validate(dto);

        pizzaService.update(1L, dto, LocalDateTime.now());
    }

    @Test(expected = ValidationException.class)
    @DisplayName("Blank dto name updating")
    public void testUpdateByDtoWithBlankName() {
        PizzaDTO dto = new PizzaDTO();
        dto.setName(" ");
        dto.setDescription("description");
        doThrow(ValidationException.class).when(validator).validate(dto);

        pizzaService.update(1L, dto, LocalDateTime.now());
    }

    @Test(expected = ValidationException.class)
    @DisplayName("Null dto description updating")
    public void testUpdateByDtoWithNullDescription() {
        PizzaDTO dto = new PizzaDTO();
        dto.setName("name");
        dto.setDescription(null);
        doThrow(ValidationException.class).when(validator).validate(dto);

        pizzaService.update(1L, dto, LocalDateTime.now());
    }

    @Test(expected = ValidationException.class)
    @DisplayName("Empty dto description updating")
    public void testUpdateByDtoWithEmptyDescription() {
        PizzaDTO dto = new PizzaDTO();
        dto.setName("name");
        dto.setDescription("");
        doThrow(ValidationException.class).when(validator).validate(dto);

        pizzaService.update(1L, dto, LocalDateTime.now());
    }

    @Test(expected = ValidationException.class)
    @DisplayName("Blank dto description updating")
    public void testUpdateByDtoWithBlankDescription() {
        PizzaDTO dto = new PizzaDTO();
        dto.setName("name");
        dto.setDescription(" ");
        doThrow(ValidationException.class).when(validator).validate(dto);

        pizzaService.update(1L, dto, LocalDateTime.now());
    }

    @Test(expected = ValidationException.class)
    @DisplayName("Too long dto name updating")
    public void testUpdateByDtoWithTooLongName() {
        PizzaDTO dto = new PizzaDTO();
        dto.setName("name".repeat(100));
        dto.setDescription("description");
        doThrow(ValidationException.class).when(validator).validate(dto);

        pizzaService.update(1L, dto, LocalDateTime.now());
    }

    @Test(expected = ValidationException.class)
    @DisplayName("Too long dto description updating")
    public void testUpdateByDtoWithTooLongDescription() {
        PizzaDTO dto = new PizzaDTO();
        dto.setName("name");
        dto.setDescription("description".repeat(100));
        doThrow(ValidationException.class).when(validator).validate(dto);

        pizzaService.update(1L, dto, LocalDateTime.now());
    }

    @Test(expected = NotFoundException.class)
    @DisplayName("Id not found updating")
    public void testUpdateNotFound() {
        given(pizzaStorage.read(1L)).willReturn(Optional.empty());

        pizzaService.update(1L, new PizzaDTO("name", "description"), LocalDateTime.now());
    }

    @Test(expected = NotUniqueException.class)
    @DisplayName("Not unique name updating")
    public void testUpdateNotUniqueName() {
        IPizza pizza1 = PizzaBuilder.create().setId(1L).setName("name1").setDescription("description1").setCreateDate(LocalDateTime.now()).setUpdateDate(LocalDateTime.now()).build();

        IPizza pizza2 = PizzaBuilder.create().setId(2L).setName("name2").setDescription("description2").setCreateDate(LocalDateTime.now()).setUpdateDate(LocalDateTime.now()).build();

        given(pizzaStorage.read(1L)).willReturn(Optional.of(pizza1));
        given(pizzaStorage.read("name2")).willReturn(Optional.of(pizza2));

        pizzaService.update(1L, new PizzaDTO("name2", "description1"), LocalDateTime.now());
    }

    @Test(expected = WrongVersionException.class)
    @DisplayName("Wrong version updating")
    public void testUpdateWrongVersion() {
        LocalDateTime updateDate = LocalDateTime.now();

        IPizza pizza = PizzaBuilder.create().setId(1L).setName("name").setDescription("description").setCreateDate(LocalDateTime.now()).setUpdateDate(updateDate).build();

        given(pizzaStorage.read(1L)).willReturn(Optional.of(pizza));

        pizzaService.update(1L, new PizzaDTO("name", "description"), updateDate.minusSeconds(1));
    }

    @Test
    @DisplayName("Updating")
    public void testUpdate() {
        LocalDateTime updateDate = LocalDateTime.now();

        IPizza pizza = PizzaBuilder.create().setId(1L).setName("name").setDescription("description").setCreateDate(LocalDateTime.now()).setUpdateDate(updateDate).build();

        given(pizzaStorage.read(1L)).willReturn(Optional.of(pizza));

        given(pizzaStorage.update(eq(1L), any(SystemPizzaDTO.class), eq(updateDate))).willReturn(pizza);

        IPizza pizzaFromService = pizzaService.update(1L, new PizzaDTO("name", "description"), updateDate);

        verify(pizzaStorage).update(eq(1L), any(SystemPizzaDTO.class), eq(updateDate));

        assertEquals(pizza, pizzaFromService);
    }

    @Test(expected = IllegalArgumentException.class)
    @DisplayName("Null id deletion")
    public void testDeleteByNullId() {
        pizzaService.delete(null, LocalDateTime.now());
    }

    @Test(expected = IllegalArgumentException.class)
    @DisplayName("Null date deletion")
    public void testDeleteByNullDate() {
        pizzaService.delete(1L, null);
    }

    @Test(expected = NotFoundException.class)
    @DisplayName("Id not found deletion")
    public void testDeleteNotFound() {
        given(pizzaStorage.read(1L)).willReturn(Optional.empty());

        pizzaService.delete(1L, LocalDateTime.now());
    }

    @Test(expected = WrongVersionException.class)
    @DisplayName("Wrong version deletion")
    public void testDeleteWrongVersion() {
        LocalDateTime updateDate = LocalDateTime.now();

        IPizza pizza = PizzaBuilder.create().setId(1L).setName("name").setDescription("description").setCreateDate(LocalDateTime.now()).setUpdateDate(updateDate).build();

        given(pizzaStorage.read(1L)).willReturn(Optional.of(pizza));

        pizzaService.delete(1L, updateDate.minusSeconds(1));
    }

    @Test
    @DisplayName("Deletion")
    public void testDelete() {
        LocalDateTime updateDate = LocalDateTime.now();

        IPizza pizza = PizzaBuilder.create().setId(1L).setName("name").setDescription("description").setCreateDate(LocalDateTime.now()).setUpdateDate(updateDate).build();

        given(pizzaStorage.read(1L)).willReturn(Optional.of(pizza));

        pizzaService.delete(1L, updateDate);

        verify(pizzaStorage).delete(1L, updateDate);
    }
}