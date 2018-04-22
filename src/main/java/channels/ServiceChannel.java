package channels;

import java.util.ArrayList;
import java.util.List;
/**
 * Represents service channel
 * */
public class ServiceChannel {

    private double releaseTime;
    private List<Double> queue;

    public ServiceChannel() {
        queue = new ArrayList<>();
    }

    public ServiceChannel(double releaseTime, List<Double> queue) {
        this.releaseTime = releaseTime;
        this.queue = queue;
    }

    public double getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(double releaseTime) {
        this.releaseTime = releaseTime;
    }

    public List<Double> getQueue() {
        return queue;
    }

    @Override
    public String toString() {
        return "releaseTime=" + releaseTime +
                '\n';
    }
}
