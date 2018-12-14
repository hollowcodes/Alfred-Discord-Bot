package listeners;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;

public class guild_joinListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event){

        Guild guild = event.getGuild();

        String name = event.getUser().getName();

        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle(":vulcan:Welcome to _" + guild.getName() + "_ , **" + name + "**!");
        builder.setDescription(
                "\nType **§ serverinfo** to get more information about **" + guild.getName() + "**" +
                "\nand **§ help** to see all the other commands");
        builder.setColor(Color.green);

        guild.getTextChannelsByName("general", true).get(0).sendMessage(
                builder.build()
        ).queue();

        builder.setTitle(":bookmark:Rules:");
        builder.setDescription(
                "\n\nconsiderate behavior, no spamming, no insulting, " +
                "no spreading of violent or sexual content (well, as long as it`s avoidable :wink:) " +
                "\n\n_one of us, one of us, one of us..._");
        builder.setColor(Color.green);

        guild.getTextChannelsByName("general", true).get(0).sendMessage(
                builder.build()
        ).queue();
    }
}
