package listeners;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import java.time.LocalTime;

public class voiceListener extends ListenerAdapter {

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event){

        Member member = event.getVoiceState().getMember();

        // get time
        String[] time_parts = LocalTime.now().toString().split(":");
        String time = time_parts[0] + ":" + time_parts[1] + " o'clock";

        // voice-channel join logging
        if(event.getVoiceState().getChannel() == event.getGuild().getAfkChannel()){
            event.getGuild().getTextChannelsByName("voicelog", true).get(0).sendMessage(
                    ":sleeping:  **" + member.getUser().getName() + "** is now AFK [_" + time + "_] \n").queue();
        }
        else{
            event.getGuild().getTextChannelsByName("voicelog", true).get(0).sendMessage(
                    ":wave: **" + member.getUser().getName() + "** joined the voice channel **" +
                            event.getChannelJoined().getName() + "** [_" + time + "_] \n").queue();
        }
    }
}
