import application.Application;
import modeling.systems.QueuingSystemCharacteristicsCollector;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class QueuingSystemCharacteristicsCollectorTest {
    List<Application> servedApps;
    @Before
    public void init(){
        servedApps = new ArrayList<>();
        servedApps.add(new Application(1,1));
        servedApps.add(new Application(2, 1));
        servedApps.add(new Application(3, 1));
        servedApps.forEach(application -> application.setProcessed(true));
    }

    @Test
    public void getServedAppTest() {
        assertThat(servedApps,equalTo(QueuingSystemCharacteristicsCollector.getServedApp(servedApps)));
//        assertEquals(servedApps, QueuingSystemCharacteristicsCollector.getServedApp(servedApps));
    }
}
