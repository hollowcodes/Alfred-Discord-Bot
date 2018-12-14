package listeners;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import util.STATICS;

import java.awt.*;

public class readyListener extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event){

        System.out.println("[+] Alfred started - version " + STATICS.VERSION);
        System.out.println("[+] running on " + System.getProperty("os.name") + " , hosted by " + STATICS.AUTHOR);

        // get information for server console
        System.out.println("\njoined servers: \n");
        for (int i = 0; i <= event.getJDA().getGuilds().size() - 1; i++) {
            System.out.println("server " + (i + 1) + " >> name: " + event.getJDA().getGuilds().get(i).getName() + " | id: " +
                    event.getJDA().getGuilds().get(i).getId());

            String text = "- text channels: ";
            String voice = "- voice channels: ";
            for (int j = 0; j <= event.getJDA().getGuilds().get(i).getTextChannels().size() - 1; j++) {
                try{
                    text += event.getJDA().getGuilds().get(i).getTextChannels().get(j).getName() + " | ";
                } catch(Exception e){ System.out.println("- [-] no channels");}
            }
            for (int j = 0; j <= event.getJDA().getGuilds().get(i).getVoiceChannels().size() - 1; j++) {
                try {
                    voice += event.getJDA().getGuilds().get(i).getVoiceChannels().get(j).getName() + " | ";
                } catch (Exception e) {System.out.println("- [-] no channels");}
            }
            System.out.println(text + "\n" + voice + "\n");
        }

        EmbedBuilder builder = new EmbedBuilder();
        for(int i=0; i<=event.getJDA().getGuilds().size()-1; i++){

            builder.setTitle(":wave: _Hello there, I'm online!_");
            builder.setDescription("_type **§ help** to see all the commands_");
            builder.setColor(Color.green);
            event.getJDA().getGuilds().get(i).getTextChannelsByName("general", true).get(0).sendMessage(
                    builder.build()
            ).queue();
        }
    }

}
