package core;

import listeners.*;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import util.PRIVATE;
import util.STATICS;

import javax.security.auth.login.LoginException;


public class Main {

    public static void main(String[] Args){

        JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken(PRIVATE.TOKEN);

        builder.setAutoReconnect(true);

        // set online
        builder.setStatus(OnlineStatus.ONLINE);

        // listeners
        builder.addEventListener(new message_receivedListener());
        builder.addEventListener(new readyListener());
        builder.addEventListener(new voiceListener());
        builder.addEventListener(new guild_joinListener());
        builder.addEventListener(new guild_leaveListener());
        builder.addEventListener(new bot_joinListener());

        // show version
        builder.setGame(Game.playing("version " + STATICS.VERSION));

        try {
            JDA jda = builder.buildBlocking();
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
