package listeners;
import commands.command_handler;

import jdk.nashorn.internal.objects.annotations.Function;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import util.STATICS;

import java.util.List;

public class message_receivedListener extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        event.getChannel();
        EmbedBuilder builder = new EmbedBuilder();

        // get jda, guild, channel, member, message, chat history, user and bot author
        JDA jda = event.getJDA();
        Guild guild = event.getGuild();
        Channel channel = event.getChannel();
        TextChannel textChannel = event.getChannel();
        Member member = event.getMember();
        User Author = event.getAuthor();
        Message message = event.getMessage();
        String msg = message.getContentRaw();
        String by = message.getMember().getUser().getName();
        MessageHistory history = new MessageHistory(event.getChannel());

        // get permissions
        List<Permission> permissions = event.getMessage().getMember().getPermissions();

        // log messages and creation times
        List<String> msg_logger = STATICS.MSG_LOGGER;
        List<Integer> msg_sec_logger = STATICS.MSG_SEC_LOGGER;
        if(!by.equals(STATICS.BOT_NAME)){
            msg_logger.add(msg + by + channel);
            msg_sec_logger.add(event.getMessage().getCreationTime().getSecond());
        }

        /* MESSAGE RECEIVE EVENTS */

        String[] msg_parts = msg.split(" ");

        // commands
            // all the commands can be found in the commands/command_handler class
        if (msg_parts[0].equals("§")) {
            command_handler c = new command_handler();
            c.commanding(jda, guild, channel, textChannel, member, message, history, permissions, Author, msg_parts, builder);
        }

        // handle spam
        if(msg_sec_logger.size() >= 3){
            spam_handler(guild, channel, msg_logger, msg_sec_logger, message, by);
        }

        // Hello Hungry I`m Dad - joke
        if(msg_parts[0].equals("Im") || msg_parts[0].equals("I`m") || msg_parts[0].equals("I'm") ||
                msg_parts[0].equals("im") || msg_parts[0].equals("i`m")|| msg_parts[0].equals("i'm")){
            dad_joke(guild, channel, msg_parts);
        }
    }

    // handle spam
    @Function
    public void spam_handler(Guild guild, Channel channel, List<String> msg_logger, List<Integer> msg_sec_logger, Message message, String by){
        int s_size = msg_sec_logger.size();
        int m_size = msg_logger.size();
        if((msg_sec_logger.get(s_size -1).equals(msg_sec_logger.get(s_size -2))) || (msg_logger.get(m_size -1).equals(msg_logger.get(m_size -2)) &&
                msg_logger.get(m_size -2).equals(msg_logger.get(m_size -3)))){

            if(!by.equals(STATICS.BOT_NAME)){
                message.delete().queue();

                guild.getTextChannelsByName(channel.getName(), true).get(0).sendMessage(
                        "**Stop spamming!**"
                ).queue();
            }
            if(by.equals(STATICS.BOT_NAME)){
                message.delete().queue();
            }
        }
    }

    // hello hungry I`m dad - joke
    @Function
    public void dad_joke(Guild guild, Channel channel, String[] msg_parts){
        String introduction = "";
        for(int i=1; i<=msg_parts.length-1; i++){
            introduction += " " + msg_parts[i] + " ";
        }
        guild.getTextChannelsByName(channel.getName(), true).get(0).sendMessage(
                "Hello **" + introduction + "**, I am " + STATICS.BOT_NAME
        ).queue();
    }
}







