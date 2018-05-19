package modeling.types;

import application.Application;
import channels.ServiceChannel;

import java.util.List;

public class UnlimitedQueueApplicationPoster extends AbstractApplicationPoster {

    public UnlimitedQueueApplicationPoster(double modelingTime) {
        super(modelingTime);
    }
    @Override
    public void postingApplications(List<Application> applications, List<ServiceChannel> channels) {
        ServiceChannel earliestReleaseChannel;
        for (Application application : applications) {
            earliestReleaseChannel = getEarliestReleaseChannel(channels);
            applicationPost(application, earliestReleaseChannel);
        }
    }
}
