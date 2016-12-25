package xyz.gnarbot.gnar.commands.general;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import xyz.gnarbot.gnar.Bot;
import xyz.gnarbot.gnar.handlers.commands.Command;
import xyz.gnarbot.gnar.handlers.commands.CommandExecutor;
import xyz.gnarbot.gnar.handlers.commands.CommandHandler;
import xyz.gnarbot.gnar.handlers.servers.Shard;
import xyz.gnarbot.gnar.utils.BotData;
import xyz.gnarbot.gnar.utils.Note;

import java.awt.*;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Command(aliases = {"info", "botinfo"}, description = "Show information about GN4R-BOT.")
public class BotInfoCommand extends CommandExecutor
{
    @Override
    public void execute(Note note, String label, String[] args)
    {
        CommandHandler commandHandler = note.getHost().getCommandHandler();
        
        int channels;
        int textChannels = 0;
        int voiceChannels = 0;
        int servers = 0;
        int users = 0;
        int offline = 0;
        int online = 0;
        int inactive = 0;
        int dnd = 0;
        
        for (Shard shard : Bot.INSTANCE.getShards())
        {
            JDA jda = shard.getJDA();
            
            servers += jda.getGuilds().size();
            for (Guild g : jda.getGuilds())
            {
                for (Member u : g.getMembers())
                {
                    switch (u.getOnlineStatus())
                    {
                        case ONLINE:
                            online++;
                            break;
                        case OFFLINE:
                            offline++;
                            break;
                        case IDLE:
                            inactive++;
                            break;
                        case DO_NOT_DISTURB:
                            dnd++;
                            break;
                    }
                }
                users += g.getMembers().size();
                textChannels += g.getTextChannels().size();
                voiceChannels += g.getVoiceChannels().size();
            }
        }
        channels = textChannels + voiceChannels;
        
        
        int commandSize = commandHandler.getUniqueRegistry().values().parallelStream().filter
                (CommandExecutor::isShownInHelp).collect(Collectors.toList()).size();
        
        int requests = Bot.INSTANCE.getShards().stream().flatMap(shard -> shard.getHosts().stream()).mapToInt(guild
                -> guild.getCommandHandler().getRequests()).sum();
        
        StringJoiner joiner = new StringJoiner("\n", "```ini\n", "```\n");
        
        joiner.add("\u258C [Requests] ____ " + requests);
        joiner.add("\u258C [Servers] _____ " + servers);
        joiner.add("\u258C [Shards] ______ " + Bot.INSTANCE.getShards().size());
        joiner.add("\u258C");
        joiner.add("\u258C [Channels] ____ " + channels);
        joiner.add("\u258C  - [Text] _____ " + textChannels);
        joiner.add("\u258C  - [Voice] ____ " + voiceChannels);
        joiner.add("\u258C");
        joiner.add("\u258C [Users] _______ " + users);
        joiner.add("\u258C  - [Online] ___ " + online);
        joiner.add("\u258C  - [Offline] __ " + offline);
        joiner.add("\u258C  - [Inactive] _ " + inactive);
        joiner.add("\u258C  - [DND] ______ " + dnd);
        joiner.add("\u258C");
        joiner.add("\u258C [Creator] _____ Avalon");
        joiner.add("\u258C [Creator] _____ Maeyrl");
        joiner.add("\u258C [Contributor] __ Gatt");
        joiner.add("\u258C [Website] _____ gnarbot.xyz");
        joiner.add("\u258C [Commands] ____ " + commandSize);
        joiner.add("\u258C [Library] _____ JDA 3");
        joiner.add("\u258C [Uptime] ______ " + Bot.INSTANCE.getSimpleUptime() + ".");
        
        note.replyEmbed("**" + BotData.randomQuote() + "** Here is all of my information!", joiner.toString(), new Color(249, 169, 7));
    }
}