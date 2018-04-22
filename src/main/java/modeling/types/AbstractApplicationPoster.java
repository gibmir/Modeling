package modeling.types;

import application.Application;
import channels.ServiceChannel;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractApplicationPoster {
    private final double modelingTime;

    AbstractApplicationPoster(double modelingTime) {
        this.modelingTime = modelingTime;
    }

    public abstract void postingApplications(List<Application> applications, List<ServiceChannel> channels);

    protected static ServiceChannel getEarliestReleaseChannel(List<ServiceChannel> channels) {
        return channels.stream()
                .min(Comparator.comparing(ServiceChannel::getReleaseTime))
                .orElseThrow(NullPointerException::new);
    }

    protected void applicationPost(Application application, ServiceChannel earliestReleaseChannel) {
        if (managesToServe(earliestReleaseChannel.getReleaseTime())) {
            if (arrivedOnTime(application,earliestReleaseChannel.getReleaseTime())) {
                serviceApp(application, earliestReleaseChannel);
            } else {
                queueUp(application, earliestReleaseChannel);
            }
        }
    }

    private boolean managesToServe(double channelReleaseTime) {
        return channelReleaseTime < modelingTime;
    }

    private boolean arrivedOnTime(Application application, double channelReleaseTime) {
        return application.getArrivalTime() >= channelReleaseTime;
    }

    private static void serviceApp(Application application, ServiceChannel channel) {
        channel.setReleaseTime(application.getArrivalTime() + application.getServiceTime());
        application.setServiceEndTime(channel.getReleaseTime());
        application.setProcessed(true);
    }

    private static void queueUp(Application application, ServiceChannel channel) {
        double channelReleaseTime = channel.getReleaseTime();
        application.setServiceStartTime(channelReleaseTime);
        application.setQueueWaitTime(application.getServiceStartTime() - application.getArrivalTime());
        channel.setReleaseTime(channelReleaseTime+application.getServiceTime());
        application.setServiceEndTime(channel.getReleaseTime());
        application.setProcessed(true);
    }
}
