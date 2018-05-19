package modeling.systems;

import application.Application;
import channels.ServiceChannel;
import modeling.types.AbstractApplicationPoster;

import static modeling.systems.QueuingSystemCharacteristicsCollector.*;

import java.util.List;

public class QueuingSystem {
    private List<Application> applications;
    private List<ServiceChannel> channels;
    protected AbstractApplicationPoster modelingType;

    public QueuingSystem(List<Application> applications, List<ServiceChannel> channels, AbstractApplicationPoster abstractApplicationPoster) {
        this.applications = applications;
        this.channels = channels;
        this.modelingType = abstractApplicationPoster;
    }

    public void printResults() {
        modelingType.postingApplications(applications, channels);
        List<Application> servedApplications = getServedApp(applications);
        double serviceEndTime = getServiceEndTime(channels);
        double averageWaitingTime = getAverageWaitingTime(servedApplications);
        double spentTime = getSystemSpentTime(servedApplications);
        double workTime = getWorkTime(servedApplications);
        System.out.println("Received applications:" + applications.size());
        System.out.println("Served applications:" + servedApplications.size());
        System.out.println("Service end time: " + serviceEndTime);
        System.out.println("Average waiting time: " + averageWaitingTime);
        System.out.println("Spent time: " + spentTime);
        System.out.println("Work time: " + workTime);
        System.out.println("Use rate: " + getUseRate(workTime, serviceEndTime, channels.size()));
    }
}
