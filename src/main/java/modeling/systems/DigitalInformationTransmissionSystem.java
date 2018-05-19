package modeling.systems;

import application.Application;
import channels.ServiceChannel;
import modeling.types.DigitalInformationTransmissionPoster;

import static modeling.systems.QueuingSystemCharacteristicsCollector.*;

import java.util.List;

public class DigitalInformationTransmissionSystem extends QueuingSystem {

    public DigitalInformationTransmissionSystem(List<Application> applications, List<ServiceChannel> channels, DigitalInformationTransmissionPoster digitalInformationTransmissionPoster) {
        super(applications, channels, digitalInformationTransmissionPoster);
    }

    @Override
    public void printResults() {
        super.printResults();
        System.out.println("Resources connection count: " + getResourcesConnectionCount((DigitalInformationTransmissionPoster) super.modelingType));
        System.out.println("Discarded application count: " + getDiscardedApplicationsCount((DigitalInformationTransmissionPoster) super.modelingType));
    }
}
