package application;

/**
 * Represents application for service.
 */
public class Application {

    private final double arrivalTime;
    private final double serviceTime;
    private double serviceStartTime;
    private double serviceEndTime;
    private double queueWaitTime;
    private boolean processed;

    public Application(double arrivalTime, double serviceTime) {
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public double getServiceTime() {
        return serviceTime;
    }

    public double getServiceStartTime() {
        return serviceStartTime;
    }

    public void setServiceStartTime(double serviceStartTime) {
        this.serviceStartTime = serviceStartTime;
    }

    public double getServiceEndTime() {
        return serviceEndTime;
    }

    public void setServiceEndTime(double serviceEndTime) {
        this.serviceEndTime = serviceEndTime;
    }

    public double getQueueWaitTime() {
        return queueWaitTime;
    }

    public void setQueueWaitTime(double queueWaitTime) {
        this.queueWaitTime = queueWaitTime;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    @Override
    public String toString() {
        return "arrivalTime=" + arrivalTime +
                ", serviceTime=" + serviceTime +
                '\n';
    }
}
