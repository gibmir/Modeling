import application.Application;
import channels.ServiceChannel;
import modeling.QueuingSystem;
import modeling.types.LimitedQueueApplicationPoster;

import java.util.*;

public class ModelingStarter {

    private final static double MODELING_TIME_IN_HOURS = 10000;

    private final static double INTENSITY_INCOMING_FLOW = 0.5;

    private final static double INTENSITY_SERVICE_FLOW = 0.5;

    private final static int SERVING_CHANNEL_COUNT = 6;

    private final static int QUEUE_LIMIT = 6;

    private final static double INITIAL_MODELING_TIME = 0;

    private final static Random MODEL_RANDOM = new Random();

    public static void main(String[] args) {
        List<Application> incomingFlow = generateIncomingFlow();
        List<ServiceChannel> channels = generateChannels(SERVING_CHANNEL_COUNT);
        QueuingSystem queuingSystem = new QueuingSystem(incomingFlow, channels, new LimitedQueueApplicationPoster(MODELING_TIME_IN_HOURS, QUEUE_LIMIT));
        //QueuingSystem queuingSystem = new QueuingSystem(incomingFlow, channels, new UnlimitedQueueApplicationPoster(MODELING_TIME_IN_HOURS));
        queuingSystem.printResults();


    }

    /**
     * Generates incoming flow of  {@link Application applications}
     *
     * @return incoming flow
     * @see ModelingStarter#getExpRandom(double)
     */
    private static List<Application> generateIncomingFlow() {
        List<Application> incomingFlow = new ArrayList<>();
        double time = INITIAL_MODELING_TIME;
        while ((time += getExpRandom(INTENSITY_INCOMING_FLOW)) < MODELING_TIME_IN_HOURS) {
            incomingFlow.add(new Application(time, getExpRandom(INTENSITY_SERVICE_FLOW)));
        }
        return incomingFlow;
    }

    /**
     * Creates an exponentially distributed random variable. Use parameter lambda
     *
     * @param lambda distribution law parameter
     * @return random variable
     */
    private static double getExpRandom(double lambda) {
        double uniformlyRandomValue;
        uniformlyRandomValue = MODEL_RANDOM.nextDouble();
        return -Math.log(uniformlyRandomValue) / lambda;
    }

    /**
     * Generates list of {@link ServiceChannel service channels}
     *
     * @param channelsCount number of generated channels
     * @return service channel list
     */
    private static List<ServiceChannel> generateChannels(int channelsCount) {
        List<ServiceChannel> channels = new ArrayList<>(channelsCount);
        for (int i = 0; i < channelsCount; i++) {
            channels.add(new ServiceChannel());
        }
        return channels;
    }

}
