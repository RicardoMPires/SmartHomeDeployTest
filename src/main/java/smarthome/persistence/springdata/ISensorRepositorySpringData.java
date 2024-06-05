package smarthome.persistence.springdata;

import org.springframework.data.jpa.repository.JpaRepository;
import smarthome.persistence.jpa.datamodel.SensorDataModel;

public interface ISensorRepositorySpringData extends JpaRepository<SensorDataModel, String> {
}
