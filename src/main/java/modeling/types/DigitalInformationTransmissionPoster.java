package modeling.types;

import application.Application;
import channels.ServiceChannel;

import java.util.List;

public class DigitalInformationTransmissionPoster extends AbstractApplicationPoster {
    private final static int SERVICE_TIME_LIMIT = 5;
    private final static double APPLICATION_SERVICE_TIME_WITH_RESOURCES = 4;
    private final static double APPLICATION_SERVICE_TIME_WITHOUT_RESOURCES = 10;
    private final static int MAXIMUM_PERCENT_OF_DESTROYED_APPLICATIONS = 30;
    private int discardedApplications = 0;
    private boolean currentResourceConnectionStatus = false;
    private int resourceConnectionsCount = 0;

    public DigitalInformationTransmissionPoster(double modelingTime) {
        super(modelingTime);
    }

    @Override
    public void postingApplications(List<Application> applications, List<ServiceChannel> channels) {
        ServiceChannel earliestReleaseChannel;
        for (int i = 0; i < applications.size(); i++) {
            earliestReleaseChannel = getEarliestReleaseChannel(channels);
            if (!manageToService(earliestReleaseChannel)) {
                break;
            }
            manageResources(i + 1, applications.get(i));
            applicationPost(applications.get(i), earliestReleaseChannel);
//            System.out.println(applications.get(i).getServiceTime() + " " + applications.get(i).getArrivalTime() + " " + applications.get(i).getServiceStartTime() + " " + applications.get(i).getServiceEndTime() + " " + applications.get(i).isProcessed());
        }
    }

    private void manageResources(int pastApplicationsCount, Application currentApplication) {
        if ((currentResourceConnectionStatus != isResourcesNeedToBeConnected(pastApplicationsCount))) {
            if (!currentResourceConnectionStatus) {
                connectResources(currentApplication);
                resourceConnectionsCount++;
                currentResourceConnectionStatus = true;
            } else {
                disconnectResources(currentApplication);
                currentResourceConnectionStatus = false;
            }
        } else {
            if (currentResourceConnectionStatus) {
                connectResources(currentApplication);
            } else {
                disconnectResources(currentApplication);
            }
        }
    }

    private boolean isResourcesNeedToBeConnected(int pastApplicationsCount) {
        return getThirtyPercent(pastApplicationsCount) <= discardedApplications;
    }

    private double getThirtyPercent(int number) {
        return ((double) number / 100) * MAXIMUM_PERCENT_OF_DESTROYED_APPLICATIONS;
    }

    private void connectResources(Application application) {
        application.setServiceTime(APPLICATION_SERVICE_TIME_WITH_RESOURCES);
    }

    private void disconnectResources(Application application) {
        application.setServiceTime(APPLICATION_SERVICE_TIME_WITHOUT_RESOURCES);
    }

    @Override
    protected void serviceApp(Application application, ServiceChannel channel) {
        super.serviceApp(application, channel);
        discardApplicationIfNeeded(application, channel);
    }

    @Override
    protected void queueUp(Application application, ServiceChannel channel) {
        super.queueUp(application, channel);
        discardApplicationIfNeeded(application, channel);
    }

    private void discardApplicationIfNeeded(Application application, ServiceChannel channel) {
        if (isBeyondServiceTimeLimit(application)) {
            discardAfterExceedingServiceTimeLimit(application, channel);
            application.setProcessed(false);
        } else {
            application.setProcessed(true);
        }
    }

    private boolean isBeyondServiceTimeLimit(Application application) {
        double applicationServiceTime = application.getServiceEndTime() - application.getArrivalTime();
        return applicationServiceTime > SERVICE_TIME_LIMIT;
    }

    private void discardAfterExceedingServiceTimeLimit(Application application, ServiceChannel channel) {
        application.setServiceEndTime(application.getServiceStartTime() + SERVICE_TIME_LIMIT);
        channel.setReleaseTime(application.getServiceEndTime());
        discardedApplications++;
    }

    public int getDiscardedApplicationsCount() {
        return discardedApplications;
    }

    public int getResourceConnectionsCount() {
        return resourceConnectionsCount;
    }
}
