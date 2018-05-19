package modeling.types;

import application.Application;
import channels.ServiceChannel;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractApplicationPoster {
    protected final double modelingTime;

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
        if (manageToService(earliestReleaseChannel)) {
            if (arrivedOnTime(application,earliestReleaseChannel.getReleaseTime())) {
                serviceApp(application, earliestReleaseChannel);
            } else {
                queueUp(application, earliestReleaseChannel);
            }
        }
    }

    protected boolean manageToService(ServiceChannel serviceChannel) {
        return serviceChannel.getReleaseTime() < modelingTime;
    }

    protected boolean arrivedOnTime(Application application, double channelReleaseTime) {
        return application.getArrivalTime() >= channelReleaseTime;
    }

    protected void serviceApp(Application application, ServiceChannel channel) {
        channel.setReleaseTime(application.getArrivalTime() + application.getServiceTime());
        application.setServiceStartTime(application.getArrivalTime());
        application.setServiceEndTime(channel.getReleaseTime());
        application.setProcessed(true);
    }

    protected void queueUp(Application application, ServiceChannel channel) {
        double channelReleaseTime = channel.getReleaseTime();
        application.setServiceStartTime(channelReleaseTime);
        application.setQueueWaitTime(application.getServiceStartTime() - application.getArrivalTime());
        channel.setReleaseTime(channelReleaseTime+application.getServiceTime());
        application.setServiceEndTime(channel.getReleaseTime());
        application.setProcessed(true);
    }
}
