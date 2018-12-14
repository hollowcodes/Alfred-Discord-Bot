package listeners;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;

import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import util.STATICS;

import java.awt.*;

public class bot_joinListener extends ListenerAdapter {

    @Override
    public void onGuildJoin(GuildJoinEvent event){

        EmbedBuilder builder = new EmbedBuilder();

        Guild guild = event.getGuild();

        builder.setTitle(":wave:Hello, my name is **" + STATICS.BOT_NAME + "**!");
        builder.setDescription(
                "I am glad to join your server!" +
                "\nType **§ help** to see all the commands I can run for you!");
        builder.setColor(Color.green);

        guild.getTextChannels().get(0).sendMessage(
                builder.build()
        ).queue();


        builder.setTitle("important");
        builder.setDescription(
                "You have to give me the role of an administrator, otherwise I am useless!");
        builder.setColor(Color.green);


        guild.getTextChannels().get(0).sendMessage(
                builder.build()
        ).queue();

    }
}
