package me.deltaorion.common.test.unit;

import me.deltaorion.common.command.CommandException;
import me.deltaorion.common.command.sent.CommandArg;
import me.deltaorion.common.plugin.ApiPlugin;
import me.deltaorion.common.test.command.FailCommand;
import me.deltaorion.common.test.generic.McTest;
import me.deltaorion.common.test.generic.MinecraftTest;
import me.deltaorion.common.test.mock.TestEnum;
import me.deltaorion.common.test.mock.TestSender;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ArgTest implements MinecraftTest {

    private final ApiPlugin plugin;

    public ArgTest(ApiPlugin plugin) {
        this.plugin = plugin;
    }

    @McTest
    public void basicTest() {
        CommandArg arg = new CommandArg(new TestSender(),plugin,"123",0);
        try {
            arg.asBoolean();
            fail();
        } catch (Exception e) {

        }
        try {
            assertEquals(arg.asInt(),123);
        } catch (CommandException e) {
            fail();
        }

        CommandArg arg2 = new CommandArg(new TestSender(),plugin,"hello",0);
        try {
            assertEquals(arg2.parse(TestEnum.class),TestEnum.HELLO);
        } catch (CommandException e) {
            fail();
        }
    }

    @McTest
    public void registerTest() {
        try {
            plugin.registerCommand(new FailCommand());
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Override
    public String getName() {
        return "Command Argument Test";
    }
}
