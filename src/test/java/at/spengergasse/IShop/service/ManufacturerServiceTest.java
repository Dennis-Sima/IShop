package at.spengergasse.IShop.service;

import at.spengergasse.IShop.domain.Customer;
import at.spengergasse.IShop.domain.Manufacturer;
import at.spengergasse.IShop.persistence.ManufacturerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;

import static at.spengergasse.IShop.domain.DomainFixtures.defaultManufacturer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ManufacturerServiceTest {

    @Mock
    private ManufacturerRepository manufacturerRepository;

    private ManufacturerService manufacturerService;

    @BeforeEach
    void setup(){
        assumeThat(manufacturerRepository).isNotNull();
        manufacturerService = new ManufacturerService(manufacturerRepository);
    }
    @Test
    void ensureCreateManufacturerCallsRightCollaborators()
    {
        //given
        Manufacturer manufacturer = defaultManufacturer();
        when(manufacturerRepository.save(manufacturer)).thenReturn(manufacturer);

        //when
        Manufacturer createdManufacturer = manufacturerService.createManufacturer(manufacturer);

        //then
        assertThat(createdManufacturer).isNotNull();
        assertThat(createdManufacturer).isSameAs(manufacturer);

        verify(manufacturerRepository, times(1)).save(manufacturer);
        verifyNoMoreInteractions(manufacturerRepository);
    }
}