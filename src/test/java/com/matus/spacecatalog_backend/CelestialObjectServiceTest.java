package com.matus.spacecatalog_backend;


import com.matus.spacecatalog_backend.DTO.CelestialObjectRequestDTO;
import com.matus.spacecatalog_backend.DTO.CelestialObjectResponseDTO;
import com.matus.spacecatalog_backend.entity.CelestialObject;
import com.matus.spacecatalog_backend.repository.CelestialObjectRepository;
import com.matus.spacecatalog_backend.service.CelestialObjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CelestialObjectServiceTest {

    @Mock
    private CelestialObjectRepository repository;

    @InjectMocks
    private CelestialObjectService service;

    @Captor
    private ArgumentCaptor<CelestialObject> celestialCaptor;




                            // ======= CREATE =======

    @Test
    void create_whenDtoIsSaved_checkAllValuesStoredCorrectly() {

        CelestialObjectRequestDTO request = new CelestialObjectRequestDTO();
        request.setObjectName("C");
        request.setObjectType("P");
        request.setObjectDesignation("O");
        request.setDiscoveryYear(1600);
        request.setDistanceFromSunAu(2.1);
        request.setObjectMassToEarth(6.4);
        request.setObjectSpeedKmS(154.4);
        request.setShortDescription("OO");
        request.setImageMainUrl("ll");


        when(repository.existsByObjectName("C")).thenReturn(false);
        when(repository.save(any(CelestialObject.class)))
                .thenAnswer(inv -> {
                    CelestialObject c = inv.getArgument(0);
                    c.setId(1L);
                    return c;
                });


        CelestialObjectResponseDTO dto = service.create(request);
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getObjectName()).isEqualTo("C");
        assertThat(dto.getObjectType()).isEqualTo("P");
        assertThat(dto.getObjectDesignation()).isEqualTo("O");
        assertThat(dto.getDiscoveryYear()).isEqualTo(1600);
        assertThat(dto.getDistanceFromSunAu()).isCloseTo(2.1, within(1e-9));
        assertThat(dto.getObjectSpeedKmS()).isCloseTo(6.4, within(1e-9));
        assertThat(dto.getObjectMassToEarth()).isCloseTo(154.4, within(1e-9));
        assertThat(dto.getShortDescription()).isEqualTo("OO");
        assertThat(dto.getImageMainUrl()).isEqualTo("ll");


        verify(repository).existsByObjectName("C");
        verify(repository).save(celestialCaptor.capture());
        CelestialObject saved = celestialCaptor.getValue();

        assertThat(dto.getObjectName()).isEqualTo("C");
        assertThat(dto.getObjectType()).isEqualTo("P");
        assertThat(dto.getObjectDesignation()).isEqualTo("O");
        assertThat(dto.getDiscoveryYear()).isEqualTo(1600);
        assertThat(dto.getDistanceFromSunAu()).isCloseTo(2.1, within(1e-9));
        assertThat(dto.getObjectMassToEarth()).isCloseTo(6.4, within(1e-9));
        assertThat(dto.getObjectSpeedKmS()).isCloseTo(154.4, within(1e-9));
        assertThat(dto.getShortDescription()).isEqualTo("OO");
        assertThat(dto.getImageMainUrl()).isEqualTo("ll");
    }


    @Test
    void create_whenObjectNameDuplicity_throwConflict() {

        CelestialObjectRequestDTO request = new CelestialObjectRequestDTO();
        request.setObjectName("C");
        request.setObjectType("P");
        request.setObjectDesignation("O");
        request.setDiscoveryYear(1600);
        request.setDistanceFromSunAu(2.1);
        request.setObjectMassToEarth(6.4);
        request.setObjectSpeedKmS(154.4);
        request.setShortDescription("OO");
        request.setImageMainUrl("ll");

        when(repository.existsByObjectName("C")).thenReturn(true);

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOfSatisfying(ResponseStatusException.class, ex ->
                assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.CONFLICT)
                )
                .hasMessageContaining("Object name already exists");

        verify(repository, never()).save(any());
       verify(repository).existsByObjectName("C");
    }

    @Test
    void create_whenObjectDesignationDuplicity_throwConflict() {

        CelestialObjectRequestDTO request = new CelestialObjectRequestDTO();
        request.setObjectName("C");
        request.setObjectType("P");
        request.setObjectDesignation("O");
        request.setDiscoveryYear(1600);
        request.setDistanceFromSunAu(2.1);
        request.setObjectMassToEarth(6.4);
        request.setObjectSpeedKmS(154.4);
        request.setShortDescription("OO");
        request.setImageMainUrl("ll");

        when(repository.existsByObjectDesignation("O")).thenReturn(true);

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOfSatisfying(ResponseStatusException.class, ex ->
                assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.CONFLICT)
                )
                .hasMessageContaining("Object designation already exists");

        verify(repository, never()).save(any());
        verify(repository).existsByObjectDesignation("O");
    }


    @Test
    void create_whenObjectNameNull_thenThrowBadRequest() {

        CelestialObjectRequestDTO request = new CelestialObjectRequestDTO();
        request.setObjectName(null);
        request.setObjectType("P");
        request.setObjectDesignation("O");
        request.setDiscoveryYear(1600);
        request.setDistanceFromSunAu(2.1);
        request.setObjectMassToEarth(6.4);
        request.setObjectSpeedKmS(154.4);
        request.setShortDescription("OO");
        request.setImageMainUrl("ll");

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOfSatisfying(ResponseStatusException.class, ex ->
                assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST)
                )
                .hasMessageContaining("Name cant be null or empty");

        verify(repository, never()).save(any(CelestialObject.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    void create_whenObjectNameEmpty_thenThrowBadRequest() {

        CelestialObjectRequestDTO request = new CelestialObjectRequestDTO();
        request.setObjectName("  ");
        request.setObjectType("P");
        request.setObjectDesignation("O");
        request.setDiscoveryYear(1600);
        request.setDistanceFromSunAu(2.1);
        request.setObjectMassToEarth(6.4);
        request.setObjectSpeedKmS(154.4);
        request.setShortDescription("OO");
        request.setImageMainUrl("ll");

        assertThatThrownBy(() -> service.create(request))
                .isInstanceOfSatisfying(ResponseStatusException.class, ex ->
                        assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST)
                )
                .hasMessageContaining("Name cant be null or empty");

        verify(repository, never()).save(any());
        verifyNoMoreInteractions(repository);
    }


                                // ======= FIND ALL =======

    @Test
    void findAll_showListOfObject(){

        CelestialObject c1= CelestialObject.builder()
                .objectName("Mars")
                .objectType("Planet")
                .objectDesignation("Idk")
                .discoveryYear(1600)
                .distanceFromSunAu(1.4)
                .objectSpeedKmS(154.2)
                .objectMassToEarth(0.6)
                .shortDescription("Red planet")
                .imageMainUrl("kkk")
                .build();

        CelestialObject c2= CelestialObject.builder()
                .objectName("Venus")
                .objectType("Planet")
                .objectDesignation("Idk")
                .discoveryYear(1500)
                .distanceFromSunAu(0.8)
                .objectSpeedKmS(110.2)
                .objectMassToEarth(1.1)
                .shortDescription("Hot planet")
                .imageMainUrl("pp")
                .build();

      List<CelestialObject> list = List.of(c1,c2);
      Page<CelestialObject> page = new PageImpl<>(list, PageRequest.of(0,2),2);
      Pageable pageable = PageRequest.of(0, 2, Sort.by("objectName").ascending());



        when(repository.findAll(pageable)).thenReturn(page);

        Page<CelestialObjectResponseDTO> result = service.findAll(pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent())
                .extracting(CelestialObjectResponseDTO::getObjectName)
                .containsExactly("Mars", "Venus");

        verify(repository).findAll(pageable);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void showAll_returnEmptyList(){

        Pageable pageable = PageRequest.of(0, 5);
        Page<CelestialObject> emptyPage = Page.empty(pageable);

        when(repository.findAll(pageable)).thenReturn(emptyPage);

        Page<CelestialObjectResponseDTO> result = service.findAll(pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEmpty();
        assertThat(result.getTotalElements()).isZero();

        verify(repository).findAll(pageable);
        verifyNoMoreInteractions(repository);

    }


                                // ====== FIND BY ID ======

    @Test
    void findById_whenExistsReturn(){

        CelestialObject c1= CelestialObject.builder()
                .id(4L)
                .objectName("Mars")
                .objectType("Planet")

                .build();

        when(repository.findById(4L)).thenReturn(Optional.of(c1));

        CelestialObjectResponseDTO dto = service.findById(4L);

        assertThat(dto.getObjectName()).isEqualTo("Mars");
        assertThat(dto.getObjectType()).isEqualTo("Planet");
        assertThat(dto.getId()).isEqualTo(4L);

        verify(repository).findById(4L);
    }

    @Test
    void findById_whenIdIsEmpty_thenThrowNotFound(){

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(()-> service.findById(1L))
                .isInstanceOfSatisfying(ResponseStatusException.class, ex ->
                assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND)
                )
                .hasMessageContaining("Object not found");

        verify(repository).findById(1L);
    }


                                        // ====== DELETE =====
    @Test
    void delete_whenIdExists_thenDelete(){

        when(repository.existsById(4L)).thenReturn(true);

        service.delete(4L);

        verify(repository).existsById(4L);
        verify(repository).deleteById(4L);
    }


    @Test
    void delete_whenIdNotExists_thenThrowNotFound(){

        when(repository.existsById(4L)).thenReturn(false);

        assertThatThrownBy(() -> service.delete(4L))
                .isInstanceOfSatisfying(ResponseStatusException.class, ex ->
                assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND)
                )
                .hasMessageContaining("Not found for delete");

        verify(repository).existsById(4L);
        verify(repository, never()).deleteById(anyLong());
    }


                                     // ====== UPDATE =======

    @Test
    void update_whenEntityIsStored_checkTheValuesAreCorrect() {

        CelestialObjectRequestDTO request = new CelestialObjectRequestDTO();
        request.setObjectName("C");
        request.setObjectType("P");
        request.setObjectDesignation("O");
        request.setDiscoveryYear(1600);
        request.setDistanceFromSunAu(2.1);
        request.setObjectMassToEarth(6.4);
        request.setObjectSpeedKmS(154.4);
        request.setShortDescription("OO");
        request.setImageMainUrl("ll");

        CelestialObject oldObject = CelestialObject.builder()
                .id(1L)
                .objectName("Mars")
                .objectType("OldType")
                .objectDesignation("OldDes")
                .discoveryYear(1500)
                .distanceFromSunAu(1.0)
                .objectSpeedKmS(100.0)
                .objectMassToEarth(0.5)
                .shortDescription("Old description")
                .imageMainUrl("old-url")
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(oldObject));
        when(repository.findByObjectName("C")).thenReturn(Optional.empty());
        when(repository.findByObjectDesignation("O")).thenReturn(Optional.empty());

        when(repository.save(any(CelestialObject.class))).thenAnswer(inv -> inv.getArgument(0));

        ArgumentCaptor<CelestialObject> captor = ArgumentCaptor.forClass(CelestialObject.class);

        CelestialObjectResponseDTO dto = service.updateObject(1L, request);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getObjectName()).isEqualTo("C");
        assertThat(dto.getObjectType()).isEqualTo("P");
        assertThat(dto.getObjectDesignation()).isEqualTo("O");

        verify(repository).findById(1L);
        verify(repository).findByObjectName("C");
        verify(repository).findByObjectDesignation("O");
        verify(repository).save(captor.capture());

        CelestialObject saved = captor.getValue();
        assertThat(saved.getId()).isEqualTo(1L);
        assertThat(saved.getObjectName()).isEqualTo("C");
        assertThat(saved.getObjectType()).isEqualTo("P");
        assertThat(saved.getObjectDesignation()).isEqualTo("O");
        assertThat(saved.getDiscoveryYear()).isEqualTo(1600);
        assertThat(saved.getDistanceFromSunAu()).isCloseTo(2.1, within(1e-9));
        assertThat(saved.getObjectSpeedKmS()).isCloseTo(154.4, within(1e-9));
        assertThat(saved.getObjectMassToEarth()).isCloseTo(6.4, within(1e-9));
        assertThat(saved.getShortDescription()).isEqualTo("OO");
        assertThat(saved.getImageMainUrl()).isEqualTo("ll");
    }


    @Test
    void update_whenObjectNameAlreadyExists_thenThrowConflict(){

        CelestialObjectRequestDTO request = new CelestialObjectRequestDTO();
        request.setObjectName("C");
        request.setObjectType("P");
        request.setObjectDesignation("O");
        request.setDiscoveryYear(1600);
        request.setDistanceFromSunAu(2.1);
        request.setObjectMassToEarth(6.4);
        request.setObjectSpeedKmS(154.4);
        request.setShortDescription("OO");
        request.setImageMainUrl("ll");

        CelestialObject existing = CelestialObject.builder()
                .id(1L)
                .objectName("Mars")
                .objectType("Planet")
                .build();

        CelestialObject other = CelestialObject.builder()
                .id(2L)
                .objectName("C")
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.findByObjectName("C")).thenReturn(Optional.of(other));

        assertThatThrownBy(() -> service.updateObject(1L, request))
                .isInstanceOfSatisfying(ResponseStatusException.class, ex ->
                        assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.CONFLICT)
                )
                .hasMessageContaining("Name already exists");

        verify(repository).findById(1L);
        verify(repository).findByObjectName("C");
        verify(repository, never()).save(any());
    }

    @Test
    void update_whenObjectDesignationAlreadyExists_thenThrowConflict() {

        CelestialObjectRequestDTO request = new CelestialObjectRequestDTO();
        request.setObjectName("C");
        request.setObjectType("P");
        request.setObjectDesignation("O");
        request.setDiscoveryYear(1600);
        request.setDistanceFromSunAu(2.1);
        request.setObjectMassToEarth(6.4);
        request.setObjectSpeedKmS(154.4);
        request.setShortDescription("OO");
        request.setImageMainUrl("ll");

        CelestialObject existing = CelestialObject.builder()
                .id(1L)
                .objectName("Mars")
                .objectDesignation("OLD_DES")
                .build();

        CelestialObject other = CelestialObject.builder()
                .id(2L)
                .objectDesignation("O")
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.findByObjectDesignation("O")).thenReturn(Optional.of(other));

        assertThatThrownBy(() -> service.updateObject(1L, request))
                .isInstanceOfSatisfying(ResponseStatusException.class, ex ->
                        assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.CONFLICT)
                )
                .hasMessageContaining("Designation already exists");

        verify(repository).findById(1L);
        verify(repository).findByObjectDesignation("O");
        verify(repository, never()).save(any());
    }

    @Test
    void update_whenIdNotFound_thenThrowNotFound() {

        CelestialObjectRequestDTO request = new CelestialObjectRequestDTO();
        request.setObjectName("C");
        request.setObjectType("P");
        request.setObjectDesignation("O");
        request.setDiscoveryYear(1600);
        request.setDistanceFromSunAu(2.1);
        request.setObjectMassToEarth(6.4);
        request.setObjectSpeedKmS(154.4);
        request.setShortDescription("OO");
        request.setImageMainUrl("ll");


        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.updateObject(1L, request))
                .isInstanceOfSatisfying(ResponseStatusException.class, ex ->
                        assertThat(ex.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND)
                )
                .hasMessageContaining("Object not found");

        verify(repository).findById(1L);
        verifyNoMoreInteractions(repository);
    }

}
