package game;

import junit.framework.Assert;
import junit.framework.TestCase;

public class CommandLineParserTest extends TestCase {
    public void testParseCommandLine() throws Exception {
        String[] command = "abc -p1 1001 -p2 1002 -p3 1003 -p4 1004 -p5 1005 -t 1000".split(" ");
        GameRoleConfig gameRoleConfig = GameCommandLineParser.parseCommandLine(command);

        Assert.assertEquals(gameRoleConfig.getPolices().get(0),  "1001");
        Assert.assertEquals(gameRoleConfig.getPolices().get(4),  "1005");
        Assert.assertEquals(gameRoleConfig.getThief(), "1000");
    }
}
