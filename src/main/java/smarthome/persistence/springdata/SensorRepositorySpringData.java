package smarthome.persistence.springdata;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import smarthome.domain.sensor.Sensor;
import smarthome.domain.sensor.SensorFactory;
import smarthome.domain.vo.sensorvo.SensorIDVO;
import smarthome.mapper.assembler.SensorAssembler;
import smarthome.persistence.SensorRepository;
import smarthome.persistence.jpa.datamodel.SensorDataModel;

import java.util.Optional;

@Repository
public class SensorRepositorySpringData implements SensorRepository {

    private final ISensorRepositorySpringData iSensorRepositorySpringData;
    private final SensorFactory sensorFactory;

    public SensorRepositorySpringData(SensorFactory sensorFactory, ISensorRepositorySpringData iSensorRepositorySpringData) {
        this.iSensorRepositorySpringData = iSensorRepositorySpringData;
        this.sensorFactory = sensorFactory;
    }

    @Override
    public boolean save(Sensor entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Sensor is null");
        }
        SensorDataModel sensorDataModel = new SensorDataModel(entity);
        try {
            this.iSensorRepositorySpringData.save(sensorDataModel);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public Iterable<Sensor> findAll() {
        try {
            Iterable<SensorDataModel> sensorDataModelIterable = this.iSensorRepositorySpringData.findAll();
            return SensorAssembler.toDomain(this.sensorFactory, sensorDataModelIterable);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Sensor findById(SensorIDVO id) {
        if (id == null) {
            return null;
        }
        String sensorId = id.getID();
        try {
            Optional<SensorDataModel> sensorDataModelOptional = this.iSensorRepositorySpringData.findById(sensorId);
            if (sensorDataModelOptional.isPresent()) {
                SensorDataModel sensorDataModel = sensorDataModelOptional.get();
                return SensorAssembler.toDomain(this.sensorFactory, sensorDataModel);
            }
            return null;
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public boolean isPresent(SensorIDVO id) {
        if (id == null) {
            return false;
        }
        return findById(id) != null;
    }
}
