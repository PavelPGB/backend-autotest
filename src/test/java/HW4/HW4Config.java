package HW4;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

@org.aeonbits.owner.Config.Sources({"file:src/test/resources/spoonconfig.properties"})
public interface HW4Config extends Config {
    HW4.HW4Config spoonConfig = ConfigFactory.create(HW4.HW4Config.class);

    String baseURI();
    String apiKey();
}