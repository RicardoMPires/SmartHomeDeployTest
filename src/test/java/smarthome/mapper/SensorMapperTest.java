package smarthome.mapper;

import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensor.TemperatureSensor;
import smarthome.domain.vo.devicevo.DeviceIDVO;
import smarthome.domain.vo.sensortype.SensorTypeIDVO;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.domain.vo.sensorvo.SensorNameVO;
import smarthome.mapper.dto.SensorDTO;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class SensorMapperTest {

    /**
     * Verifies when a valid DTO is received (not null), SensorNameVO is created, and when getValue() is invoked,
     * the expected sensor name in a string format is retrieved.
     * SensorMapper Class has two collaborators in this scenario, SensorDTO and SensorNameVO classes. To properly isolate
     * the class under test (SensorMapper), a double of the SensorDTO class is made as well as a MockedConstruction of the
     * SensorNameVO Class, in order to condition its behaviour when a SensorNameVO is created. It is also verified that
     * one exact Value Object is created.
     */
    @Test
    void givenAValidDto_SensorNameVOIsCreated_WhenGetValueIsInvoked_ThenShouldReturnTheExpectedSensorName() {
        //Arrange
        SensorDTO doubleDto = mock(SensorDTO.class);
        when(doubleDto.getSensorName()).thenReturn("Swimming pool temperature sensor");
        String expected = "Swimming pool temperature sensor";
        int valuesListExpectedSize = 1;

        try (MockedConstruction<SensorNameVO> mockedVO = mockConstruction(SensorNameVO.class, (mock, context) ->
                when(mock.getValue()).thenReturn(expected))) {
            SensorNameVO valueObject = SensorMapper.createSensorNameVO(doubleDto);
            List<SensorNameVO> valuesList = mockedVO.constructed();

            //Act
            String result = valueObject.getValue();

            //Assert
            assertEquals(expected, result);
            assertEquals(valuesListExpectedSize, valuesList.size());
        }

    }

    /**
     * Verifies when an invalid DTO is received (null), SensorNameVO is not created and an IllegalArgumentException is thrown,
     * by the Mapper.
     * SensorMapper Class has only one collaborator in this scenario, which is the SensorDTO class. To properly isolate the
     * class under test (SensorMapper), a double of the SensorDTO class is made. There is no need to have a MockedConstruction
     * of the SensorNameVO Class since the code breaks before that interaction.
     */
    @Test
    void givenANullDto_WhenCreateSensorNameVOIsInvoked_ThenShouldThrowIllegalArgumentException() {
        //Arrange
        String expected = "Invalid DTO, Value Object cannot be created";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> SensorMapper.createSensorNameVO(null));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Verifies when a DTO containing invalid information is received (null name), SensorNameVO is not created, and an
     * IllegalArgumentException is thrown, by the SensorNameVO Class.
     * SensorMapper Class has two collaborators in this scenario, SensorDTO and SensorNameVO classes. To properly isolate
     * the class under test (SensorMapper), a double of the SensorDTO class is made. A MockedConstruction of the SensorNameVO Class
     * should be made as well, however, in this particular situation, the constructor of the SensorNameVO object throws
     * an exception, because it seems Mockito does not allow throwing exceptions through mock objects. A partial isolation
     * test scenario is considered.
     */
    @Test
    void givenADtoWithNullSensorName_WhenCreateSensorNameVOIsInvoked_ThenShouldThrowIllegalArgumentException() {
        //Arrange
        SensorDTO doubleDto = mock(SensorDTO.class);
        when(doubleDto.getSensorName()).thenReturn(null);
        String expected = "Invalid parameters.";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> SensorMapper.createSensorNameVO(doubleDto));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }


    /**
     * Verifies when a DTO containing invalid information is received (blank name), SensorNameVO is not created, and an
     * IllegalArgumentException is thrown, by the SensorNameVO Class.
     * The same scenario is verified as in the test to create a SensorNameVO object containing a null string name.
     * A partial isolation test scenario is considered.
     */
    @Test
    void givenADtoWithBlankSensorName_WhenCreateSensorNameVOIsInvoked_ThenShouldThrowIllegalArgumentException() {
        //Arrange
        SensorDTO doubleDto = mock(SensorDTO.class);
        when(doubleDto.getSensorName()).thenReturn("   ");
        String expected = "Invalid parameters.";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> SensorMapper.createSensorNameVO(doubleDto));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test case for the domainToDTO method of the SensorMapper class when the input is null.
     * This test verifies that the method throws an IllegalArgumentException when the input is null.
     */
    @Test
    void givenNullSensor_WhenDomainToDTOIsInvoked_ThenShouldThrowIllegalArgumentException() {
        //Arrange
        String expected = "Invalid Sensor, DTO cannot be created";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> SensorMapper.domainToDTO(null));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test case for the domainToDTO method of the SensorMapper class when the input is a valid Sensor.
     * This test verifies that the method returns a SensorDTO with the same attributes as the input Sensor.
     */
    @Test
    void givenValidSensor_WhenDomainToDTOIsInvoked_ThenShouldReturnSensorDTO() {
        //Arrange
        Sensor sensor = mock(TemperatureSensor.class);
        when(sensor.getId()).thenReturn(new SensorIDVO(UUID.randomUUID()));
        when(sensor.getSensorName()).thenReturn(new SensorNameVO("Sensor1"));
        when(sensor.getDeviceID()).thenReturn(new DeviceIDVO(UUID.randomUUID()));
        when(sensor.getSensorTypeID()).thenReturn(new SensorTypeIDVO("TemperatureSensor"));

        //Act
        SensorDTO result = SensorMapper.domainToDTO(sensor);

        //Assert
        assertEquals(sensor.getId().getID(), result.getSensorID());
        assertEquals(sensor.getSensorName().getValue(), result.getSensorName());
        assertEquals(sensor.getDeviceID().getID(), result.getDeviceID());
        assertEquals(sensor.getSensorTypeID().getID(), result.getSensorTypeID());
    }

    /**
     * Test case for the createDeviceID method of the SensorMapper class when the input DTO has a null device ID.
     * This test verifies that the method throws an IllegalArgumentException when the device ID of the input DTO is null.
     */
    @Test
    void givenDTOWithNullDeviceID_WhenCreateDeviceIDIsInvoked_ThenShouldThrowIllegalArgumentException() {
        //Arrange
        SensorDTO doubleDto = mock(SensorDTO.class);
        when(doubleDto.getDeviceID()).thenReturn(null);
        String expected = "Invalid device ID";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> SensorMapper.createDeviceID(doubleDto));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test case for the createDeviceID method of the SensorMapper class when the input DTO has an invalid format device ID.
     * This test verifies that the method throws an IllegalArgumentException when the device ID of the input DTO is not a valid UUID.
     */
    @Test
    void givenDTOWithInvalidFormatDeviceID_WhenCreateDeviceIDIsInvoked_ThenShouldThrowIllegalArgumentException() {
        //Arrange
        SensorDTO doubleDto = mock(SensorDTO.class);
        when(doubleDto.getDeviceID()).thenReturn("zzz");
        String expected = "Invalid device ID";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> SensorMapper.createDeviceID(doubleDto));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test case for the createDeviceID method of the SensorMapper class when the input DTO has an empty device ID.
     * This test verifies that the method throws an IllegalArgumentException when the device ID of the input DTO is empty.
     */
    @Test
    void givenDTOWIthEmptyDeviceID_WhenCreateDeviceIDIsInvoked_ThenShouldThrowIllegalArgumentException() {
        //Arrange
        SensorDTO doubleDto = mock(SensorDTO.class);
        when(doubleDto.getDeviceID()).thenReturn(" ");
        String expected = "Invalid device ID";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> SensorMapper.createDeviceID(doubleDto));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test case for the createDeviceID method of the SensorMapper class when the input DTO has a valid device ID.
     * This test verifies that the method returns a DeviceIDVO with the same ID as the device ID of the input DTO.
     */
    @Test
    void givenADtoWithValidDeviceID_WhenCreateDeviceIDIsInvoked_ThenShouldReturnDeviceIDVO() {
        //Arrange
        SensorDTO doubleDto = mock(SensorDTO.class);
        when(doubleDto.getDeviceID()).thenReturn("123e4567-e89b-12d3-a456-75664244000");

        //Act
        DeviceIDVO result = SensorMapper.createDeviceID(doubleDto);

        //Assert
        assertEquals(UUID.fromString(doubleDto.getDeviceID()).toString(), result.getID());
    }

    /**
     * Test case for the createDeviceID method of the SensorMapper class when the input DTO is null.
     * This test verifies that the method throws an IllegalArgumentException when the input DTO is null.
     */
    @Test
    void givenNullDTO_WhenCreateDeviceIDIsInvoked_ThenShouldThrowIllegalArgumentException() {
        //Arrange
        String expected = "Invalid DTO, Value Object cannot be created";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> SensorMapper.createDeviceID(null));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test case for the createSensorTypeIDVO method of the SensorMapper class when the input DTO is null.
     * This test verifies that the method throws an IllegalArgumentException when the input DTO is null.
     */
    @Test
    void givenNullDTO_WhenCreateSensorTypeIDVOIsInvoked_ThenShouldThrowIllegalArgumentException() {
        //Arrange
        String expected = "Invalid DTO, Value Object cannot be created";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> SensorMapper.createSensorTypeIDVO(null));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test case for the createSensorTypeIDVO method of the SensorMapper class when the input DTO has a valid sensor type ID.
     * This test verifies that the method returns a SensorTypeIDVO with the same ID as the sensor type ID of the input DTO.
     */
    @Test
    void givenDTOWithValidSensorTypeID_WhenCreateSensorTypeIDVOIsInvoked_ThenShouldReturnSensorTypeIDVO() {
        //Arrange
        SensorDTO doubleDto = mock(SensorDTO.class);
        when(doubleDto.getSensorTypeID()).thenReturn("TemperatureSensor");

        //Act
        SensorTypeIDVO result = SensorMapper.createSensorTypeIDVO(doubleDto);

        //Assert
        assertEquals(doubleDto.getSensorTypeID(), result.getID());
    }

    /**
     * Test case for the createSensorTypeIDVO method of the SensorMapper class when the input DTO has a null sensor type ID.
     * This test verifies that the method throws an IllegalArgumentException when the sensor type ID of the input DTO is null.
     */
    @Test
    void givenDTOWithNullSensorTypeID_WhenCreateSensorTypeIDVOIsInvoked_ThenShouldThrowIllegalArgumentException() {
        //Arrange
        SensorDTO doubleDto = mock(SensorDTO.class);
        when(doubleDto.getSensorTypeID()).thenReturn(null);
        String expected = "SensorTypeID cannot be null";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> SensorMapper.createSensorTypeIDVO(doubleDto));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test case for the createSensorTypeIDVO method of the SensorMapper class when the input DTO has an invalid sensor type ID.
     * This test verifies that the method throws an IllegalArgumentException when the sensor type ID of the input DTO is empty.
     */
    @Test
    void givenDTOWithEmptySensorTypeID_WhenCreateSensorTypeIDVOIsInvoked_ThenShouldThrowIllegalArgumentException() {
        //Arrange
        SensorDTO doubleDto = mock(SensorDTO.class);
        when(doubleDto.getSensorTypeID()).thenReturn(" ");
        String expected = "SensorTypeID cannot be null";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> SensorMapper.createSensorTypeIDVO(doubleDto));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test case for the createSensorIDVO method of the SensorMapper class when the input sensor ID is null.
     * This test verifies that the method throws an IllegalArgumentException when the input sensor ID is null.
     * The expected behavior is that the method throws an IllegalArgumentException with the message "Invalid sensor ID."
     */
    @Test
    void givenNullSensorID_WhenCreateSensorIDVOIsInvoked_ThenShouldThrowIllegalArgumentException() {
        //Arrange
        String sensorID = null;
        String expected = "Invalid sensor ID";

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> SensorMapper.createSensorIDVO(sensorID));
        String result = exception.getMessage();

        //Assert
        assertEquals(expected, result);
    }

    /**
     * Test case for the createSensorIDVO method of the SensorMapper class when the input sensor ID is null.
     * This test verifies that the method throws an IllegalArgumentException when the input sensor ID is null.
     * The expected behavior is that the method throws an IllegalArgumentException with the message "Invalid sensor ID."
     */
    @Test
    void givenValidSensorID_WhenCreateSensorIDVOIsInvoked_ThenShouldReturnSensorIDVO() {
        //Arrange
        String sensorID = "1fa85f64-5717-4562-b3fc-2c963f66afa6";
        SensorIDVO expected = new SensorIDVO(UUID.fromString(sensorID));

        //Act
        SensorIDVO result = SensorMapper.createSensorIDVO(sensorID);

        //Assert
        assertEquals(expected.getID(), result.getID());
    }
}