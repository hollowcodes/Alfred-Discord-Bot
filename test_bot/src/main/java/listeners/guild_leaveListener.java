package listeners;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class guild_leaveListener extends ListenerAdapter {

    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent event){

        EmbedBuilder builder = new EmbedBuilder();

        Guild guild = event.getGuild();
        String name = event.getUser().getName();

        String join_date = event.getMember().getJoinDate().toString().split("T")[0];
        DateFormat df = new SimpleDateFormat("yy-MM-dd");
        Date date = new Date();

        builder.setTitle(join_date + "  -  " + "20" + df.format(date));

        builder.setDescription(
                ":pensive: This day, we got left by a well known and appropriate member of our little community..." +
                "\nThank you for your serves, you will remain forever in our hearts, **" + name + "**");
        builder.setColor(Color.black);

        guild.getTextChannelsByName("general", true).get(0).sendMessage(
                builder.build()
        ).queue();

    }
}
