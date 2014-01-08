package game.protocol;

import junit.framework.Assert;
import junit.framework.TestCase;
import network.ClientMessageHandler;
import network.SpecialMessageID;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WelcomeOnConnectHandlerTest extends TestCase{
    public void testHandle() throws Exception {
        ClientMessageHandler clientMessageHandler = mock(ClientMessageHandler.class);

        WelcomeOnConnectHandler welcomeOnConnectHandler = new WelcomeOnConnectHandler();
        boolean result = welcomeOnConnectHandler.handle(clientMessageHandler, SpecialMessageID.ON_CONNECTED);

        Assert.assertTrue(result);

        result = welcomeOnConnectHandler.handle(clientMessageHandler, "sass");
        Assert.assertFalse(result);
    }
}
