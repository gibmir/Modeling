package modeling.types;

import application.Application;
import channels.ServiceChannel;

import java.util.List;
import java.util.stream.Collectors;

public class LimitedQueueApplicationPoster extends AbstractApplicationPoster {
    private final double queueLimit;

    public LimitedQueueApplicationPoster(double modelingTime, double queueLimit) {
        super(modelingTime);
        this.queueLimit = queueLimit;
    }

    @Override
    public void postingApplications(List<Application> applications, List<ServiceChannel> channels) {
        ServiceChannel earliestReleaseChannel;
        List<ServiceChannel> availableChannels;
        for (Application application : applications) {
            releaseChannelsQueue(application, channels);
            availableChannels = getAvailableChannels(channels);
            if (!availableChannels.isEmpty()) {
                earliestReleaseChannel = getEarliestReleaseChannel(availableChannels);
                applicationPost(application, earliestReleaseChannel);
            }
        }
    }

    private void releaseChannelsQueue(Application application, List<ServiceChannel> channels) {
        channels.forEach(channel -> channel.getQueue().
                removeIf(applicationEndServiceTime -> applicationEndServiceTime <= application.getArrivalTime()));
    }

    private List<ServiceChannel> getAvailableChannels(List<ServiceChannel> channels) {
        return channels.stream().
                filter(channel -> channel.getQueue().size() < queueLimit).
                collect(Collectors.toList());
    }
}
