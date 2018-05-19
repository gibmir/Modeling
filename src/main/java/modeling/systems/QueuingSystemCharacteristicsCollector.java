package modeling.systems;

import application.Application;
import channels.ServiceChannel;
import modeling.types.DigitalInformationTransmissionPoster;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class QueuingSystemCharacteristicsCollector {
    public static List<Application> getServedApp(List<Application> incomingFlow) {
        return incomingFlow.stream()
                .filter(Application::isProcessed)
                .collect(Collectors.toList());
    }

    public static double getServiceEndTime(List<ServiceChannel> channels) {
        return channels.stream().
                max(Comparator.comparing(ServiceChannel::getReleaseTime))
                .orElseThrow(NullPointerException::new)
                .getReleaseTime();
    }

    public static double getAverageWaitingTime(List<Application> servedApp) {
        return servedApp.stream()
                .map(Application::getQueueWaitTime)
                .reduce(0.0, (appWaitTime, nextAppWaitTime) -> appWaitTime += nextAppWaitTime) / servedApp.size();
    }

    public static double getSystemSpentTime(List<Application> servedApp) {
        double result = 0;
        for (Application application : servedApp) {
            result += application.getServiceEndTime() - application.getArrivalTime();
        }
        return result / servedApp.size();
    }

    public static double getWorkTime(List<Application> servedApp) {
        return servedApp.stream()
                .map(Application::getServiceTime)
                .reduce(0.0, (appServiceTime, nextAppServiceTime) -> appServiceTime += nextAppServiceTime);
    }

    public static double getUseRate(double workTime, double serviceEndTime, int channelsCount) {
        return workTime / serviceEndTime / channelsCount;
    }
    public static int getDiscardedApplicationsCount(DigitalInformationTransmissionPoster digitalInformationTransmissionPoster){
        return digitalInformationTransmissionPoster.getDiscardedApplicationsCount();
    }
    public static int getResourcesConnectionCount(DigitalInformationTransmissionPoster digitalInformationTransmissionPoster){
        return  digitalInformationTransmissionPoster.getResourceConnectionsCount();
    }
}
