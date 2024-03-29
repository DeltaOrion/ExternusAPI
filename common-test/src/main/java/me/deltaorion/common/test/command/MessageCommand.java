package me.deltaorion.common.test.command;

import me.deltaorion.common.APIPermissions;
import me.deltaorion.common.command.CommandException;
import me.deltaorion.common.command.FunctionalCommand;
import me.deltaorion.common.command.sent.SentCommand;
import me.deltaorion.common.command.tabcompletion.Completers;
import me.deltaorion.common.locale.message.Message;
import me.deltaorion.common.plugin.EPlugin;
import me.deltaorion.common.plugin.sender.Sender;

public class MessageCommand extends FunctionalCommand {

    private final EPlugin plugin;

    public MessageCommand(EPlugin plugin) {
        super(APIPermissions.COMMAND, "/msg <player> <msg>", Message.valueOf("Sends a direct message privately to the specified player"));
        this.plugin = plugin;
        registerCompleter(1, Completers.ONLINE_SENDERS(plugin.getEServer()));
    }

    @Override
    public void commandLogic(SentCommand command) throws CommandException {
        Sender recipient = command.getArgOrFail(0).parse(Sender.class);
        StringBuilder prefixReceiver = new StringBuilder("[")
                .append(command.getSender().getName())
                .append("]")
                .append(" --> ");

        StringBuilder prefixSender = new StringBuilder("[me] --> ");

        StringBuilder message = new StringBuilder();
        for(int i=1;i<command.argCount();i++) {
            message.append(command.getArgOrFail(i))
                    .append(" ");
        }

        prefixReceiver.append(message);
        prefixSender.append(message);

        recipient.sendMessage(prefixReceiver);
        command.getSender().sendMessage(prefixSender);

    }
}
