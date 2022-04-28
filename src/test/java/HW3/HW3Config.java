package HW3;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

@org.aeonbits.owner.Config.Sources({"file:src/test/resources/spoonconfig.properties"})
public interface HW3Config extends Config {
    HW3.HW3Config spoonConfig = ConfigFactory.create(HW3.HW3Config.class);

    String baseURI();
    String apiKey();
}