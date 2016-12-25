package xyz.gnarbot.gnar.commands.general;

import xyz.gnarbot.gnar.handlers.commands.Command;
import xyz.gnarbot.gnar.handlers.commands.CommandExecutor;
import xyz.gnarbot.gnar.utils.BotData;
import xyz.gnarbot.gnar.utils.Note;

import java.awt.*;

@Command(aliases = {"invite", "invitebot"}, description = "Get a link to invite GN4R to your server.")
public class InviteBotCommand extends CommandExecutor
{
    @Override
    public void execute(Note note, String label, String[] args)
    {
        String tempLink = "https://discordapp.com/oauth2/authorize?client_id=201492375653056512&scope=bot&permissions" +
                "=8";
        
        note.replyEmbed("**" + BotData.randomQuote() + "** Want some Gnar on your server?!", "**[Click here to invite Gnar to your server](" + tempLink + ")", Color.BLUE);
    }
}