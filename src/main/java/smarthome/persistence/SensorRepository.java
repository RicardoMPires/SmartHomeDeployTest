package smarthome.persistence;

import smarthome.domain.sensor.Sensor;
import smarthome.domain.vo.sensorvo.SensorIDVO;

public interface SensorRepository extends Repository<SensorIDVO, Sensor>{
}
