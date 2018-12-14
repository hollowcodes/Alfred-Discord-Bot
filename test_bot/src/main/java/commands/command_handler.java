package commands;

import jdk.nashorn.internal.objects.annotations.Function;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.AudioManager;
import util.STATICS;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class command_handler extends ListenerAdapter {

    @Function
    public void commanding(JDA jda, Guild guild, Channel channel, TextChannel textChannel, Member member, Message message, MessageHistory history,
                                List<Permission> permissions, User Author, String[] msg_parts, EmbedBuilder builder){
        switch (msg_parts[1]) {

            // help command
            case "help":
                help(guild, channel, builder);
                break;

            // get user information
            case "userinfo":
                user_info(guild, channel, msg_parts, builder);
                break;

            // get server information
            case "serverinfo":
                server_info(guild, channel, permissions, builder);
                break;

            // get bot information
            case "botinfo":
                bot_info(jda, guild, channel, Author, builder);
                break;

            // get rules
            case "rules":
                rules(guild, channel, builder);
                break;

            // get invitation url
            case "invite":
                invitation(guild, channel, permissions, builder);
                break;

            // delete messages
            case "deletemessages":
                delete_messages(guild, channel, textChannel, history, permissions, msg_parts, builder);
                break;

            // create role
            case "createrole":
                create_role(guild, channel, permissions, msg_parts, builder);
                break;

            // give role
            case "giverole":
                give_role(guild, channel, permissions, msg_parts, builder);
                break;

            // create vote
            case "createvote":
                vote(guild, channel, msg_parts, builder);
                break;

            // get bitcoin data
            case "bitcoinstatus":
                btc_data(guild, channel, builder);
                break;

            // stone paper scissors
            case "stonepaperscissors":
                stone_paper_scissors(guild, channel, msg_parts, builder);
                break;

            // write an embeded message
            case "embed":
                embed_text(guild, channel, permissions, message, builder);
                break;

            // report user
            case "report":
                report(guild, channel, textChannel, member, history, msg_parts, builder);
                break;

            // get google results
            case "google":
                google(guild, channel, msg_parts, builder);
                break;

            case "join":
                join_voicechannel(guild, msg_parts);
        }
    }

    @Function
    public static boolean check_permissions(List<Permission> permissions, Permission permission){
        boolean granted;
        if(permissions.contains(permission)){
            granted = true;
        }
        else{
            granted = false;
        }
        return granted;
    }

    // help command
    @Function
    public static void help(Guild guild, Channel channel, EmbedBuilder builder){
        builder.setTitle("help");
        builder.setDescription(
                ":small_blue_diamond: **§ help** to see all commands _(like you are doing right know, lmao)_\n\n" +
                        "**Information**\n" +
                        ":small_blue_diamond: **§ botinfo** to get informations about **Alfred**\n" +
                        ":small_blue_diamond: **§ userinfo _[username]_**  to get information about any user\n" +
                        ":small_blue_diamond: **§ serverinfo** to get information about this server\n" +
                        ":small_blue_diamond: **§ rules** to get the bots rules\n\n" +

                        "**Moderation _(role-dependent)_**\n" +
                        ":small_blue_diamond: **§ invite** to get the invitation url of this server\n" +
                        ":small_blue_diamond: **§ deletemessages _[amount]_** to delete the last x messages of this channel\n" +
                        ":small_blue_diamond: **§ createrole _[name]_  _[administrator/moderator/member]_** to create a new role\n" +
                        ":small_blue_diamond: **§ giverole _[user]_ _[role]_** to delete the last x messages of this channel\n" +
                        ":small_blue_diamond: **§ embed _[message]_** to highlight an important message\n\n" +

                        "**Other useful and unuseful stuff**\n" +
                        ":small_blue_diamond:**§ report _[user]_ _[reason]_** to report a user\n" +
                        ":small_blue_diamond: **§ bitcoinstatus** to get the current status of Bitcoin\n" +
                        ":small_blue_diamond: **§ createvote _[title]_ _[side 1]_ _[side 2]_** to create a vote\n" +
                        ":small_blue_diamond: **§ stonepaperscissors _[player1]_ _[player2]_** to compete and decide"
        );
        builder.setColor(Color.black);

        guild.getTextChannelsByName(channel.getName(), true).get(0).sendMessage(
                builder.build()
        ).queue();
    }

    // get user information
    @Function
    public static void user_info(Guild guild, Channel channel, String[] msg_parts, EmbedBuilder builder){
        try{
            Member member = guild.getMembersByName(msg_parts[2],false).get(0);

            String user_name = member.getUser().getName();
            String join_date = member.getJoinDate().toString().split("T")[0];
            OnlineStatus status = member.getOnlineStatus();
            String discord_join_date = member.getUser().getCreationTime().toString().split("T")[0];

            String servers = "";
            List<Guild> guilds = member.getUser().getMutualGuilds();
            for(Guild g : guilds){
                if(guilds.indexOf(g) == (guilds.size()-1)){
                    servers += g.getName();
                }
                else{
                    servers += g.getName() + " , ";
                }
            }

            List<Role> get_roles = member.getRoles();
            String roles = "";
            if(get_roles.size() == 0){
                roles += "none";
            }
            else {
                for (Role role : get_roles) {
                    if(get_roles.indexOf(role) == (get_roles.size()-1)){
                        roles += role.getName();
                    }
                    else{
                        roles += role.getName() + " , ";
                    }
                }
            }

            Game get_game = member.getGame();
            String game;
            if(get_game == null){
                game = "nothing";
            }
            else{ game = get_game.getName(); }

            builder.setTitle("user information");
            builder.setDescription(
                    ":bust_in_silhouette: user:     **" + user_name + "**" +
                            "\n:speaking_head: status:  **" + status + "**" +
                            "\n:clap: joined discord: **" + discord_join_date + "** " +
                            "\n:desktop: servers:  **" + servers +  "** " +
                            "\n:wave: joined this server:  ** " + join_date +  "** " +
                            "\n:japanese_goblin: roles:       **" + roles + "**" +
                            "\n:space_invader: playing: **" + game + "**");
            builder.setColor(Color.green);

            guild.getTextChannelsByName(channel.getName(), true).get(0).sendMessage(builder.build()).queue();
        }
        catch (Exception e){
            builder.setDescription("No user named **" + msg_parts[2] + "**!");
            builder.setColor(Color.red);
            guild.getTextChannelsByName(channel.getName(), true).get(0).sendMessage(
                    builder.build()
            ).queue();
        }
    }

    // get server information
    @Function
    public static void server_info(Guild guild, Channel channel, List<Permission> permissions, EmbedBuilder builder){

        String name = guild.getName();
        String region = guild.getRegion().getName();
        String owner = guild.getOwner().getUser().getName();
        String creation_time = guild.getCreationTime().toString().split("T")[0];
        int members = guild.getMembers().size();

        String banned = "";
        try {
            if((check_permissions(permissions, Permission.BAN_MEMBERS))){
                if(guild.getBanList().complete().size() == 0){
                    banned += "none";
                }
                else{
                    for(Guild.Ban ban : guild.getBanList().complete()){
                        if(guild.getBanList().complete().indexOf(ban) == (guild.getBanList().complete().size() - 1)){
                            banned += ban.getUser().getName();
                        }
                        else {
                            banned += ban.getUser().getName() + " , ";
                        }
                    }
                }
            }
            else if(!(check_permissions(permissions, Permission.BAN_MEMBERS))){
                banned += "[no permission]";
            }
        }
        catch (Exception e) {banned += "none";}

        builder.setTitle("server information");
        builder.setDescription(
                ":desktop: server:   **" + name + "**" +
                        "\n:crown: owner:   **" + owner +  "**" +
                        "\n:clock: created: **" + creation_time + "**" +
                        "\n:globe_with_meridians: region:   **" + region + "**" +
                        "\n:family_wwbb:  members: **" + members + "** " +
                        "\n:rage: banned: **" + banned + "**");
        builder.setColor(Color.cyan);

        guild.getTextChannelsByName(channel.getName(), true).get(0).sendMessage(
                builder.build()
        ).queue();
    }

    // get bot information
    @Function
    public static void bot_info(JDA jda, Guild guild, Channel channel, User Author, EmbedBuilder builder){
        String made = "made with **J**ava**D**iscord**A**PI";
        String version = STATICS.VERSION;
        String bot = STATICS.BOT_NAME;
        String author = STATICS.AUTHOR;
        String github = STATICS.GITHUB;
        String hosted = Author.getName();

        int joined_servers = jda.getGuilds().size();

        builder.setTitle("bot information");
        builder.setDescription(
                "_" + made + "_" +
                        "\n:small_orange_diamond: name:                **" + bot + "**" +
                        "\n:small_orange_diamond: version:             **" + version +  "**" +
                        "\n:small_orange_diamond: joined servers: **" + joined_servers + "**" +
                        "\n:small_orange_diamond: hosted by:        **" + hosted + "**" +
                        "\n:small_orange_diamond: developed by:  **" + author + "**" +
                        "\n:small_orange_diamond: GitHub:             **" + github + "**");
        builder.setColor(Color.magenta);

        guild.getTextChannelsByName(channel.getName(), true).get(0).sendMessage(
                builder.build()
        ).queue();
    }

    // get rules
    @Function
    public static void rules(Guild guild, Channel channel, EmbedBuilder builder){
        builder.setTitle(":bookmark: Rules:");
        builder.setDescription(
                "\n\nconsiderate behavior, no spamming, no insulting, " +
                        "no spreading of violent or sexual content (well, as long as it`s avoidable :wink:) " +
                        "\n... and of course having **fun**!");
        builder.setColor(Color.green);

        guild.getTextChannelsByName(channel.getName(), true).get(0).sendMessage(
                builder.build()
        ).queue();
    }

    // get invitation link
    @Function
    public static void invitation(Guild guild, Channel channel, List<Permission> permissions, EmbedBuilder builder){
        builder.setTitle("invitation link");

        if(check_permissions(permissions, Permission.BAN_MEMBERS)){
            int expires = 600; // seconds (10 minutes)

            String invite_url = channel.createInvite().setMaxAge(expires).complete().getURL();

            builder.setDescription(
                    "Give your friends the link below, to invite them to **" + guild.getName() + "** !\n");
            builder.setColor(Color.yellow);

            guild.getTextChannelsByName(channel.getName(), true).get(0).sendMessage(
                    builder.build()
            ).queue();
            // url
            guild.getTextChannelsByName(channel.getName(), true).get(0).sendMessage(
                    invite_url
            ).queue();

            builder.setDescription("\n This invitation link expires in " + (expires/60) + " minutes!");
            builder.setColor(Color.yellow);
            guild.getTextChannelsByName(channel.getName(), true).get(0).sendMessage(
                    builder.build()
            ).queue();
        }
        else {
            builder.setDescription(
                    ":red_circle: Sorry, but you don`t have the permissions to do that :(");
            builder.setColor(Color.red);

            guild.getTextChannelsByName(channel.getName(), true).get(0).sendMessage(
                    builder.build()
            ).queue();
        }
    }

    // delete messages
    @Function
    public static void delete_messages(Guild guild, Channel channel, TextChannel textChannel, MessageHistory history, List<Permission> permissions,
                                       String[] msg_parts, EmbedBuilder builder){

        if(check_permissions(permissions, Permission.ADMINISTRATOR)) {
            try{
                int msgs_to_delete = Integer.parseInt(msg_parts[2]);

                List<Message> msgs;
                msgs = history.retrievePast(msgs_to_delete).complete();

                textChannel.deleteMessages(msgs).queue();

                builder.setTitle("I deleted the last **" + msgs_to_delete + "** messages for you _...but you shall not forget..._");
                guild.getTextChannelsByName(channel.getName(), true).get(0).sendMessage(
                        builder.build()
                ).queue();
                builder.setColor(Color.green);
            }
            catch (Exception e){
                builder.setTitle("You have to enter a **number** of messages you want to delete");
                guild.getTextChannelsByName(channel.getName(), true).get(0).sendMessage(
                        builder.build()
                ).queue();
                builder.setColor(Color.red);
            }
        }
        else{
            builder.setTitle(":red_circle: Sorry, but you don`t have the permissions to do that :(");
            guild.getTextChannelsByName(channel.getName(), true).get(0).sendMessage(
                    builder.build()
            ).queue();
            builder.setColor(Color.red);
        }
    }

    // create role
    @Function
    public static void create_role(Guild guild, Channel channel, List<Permission> permissions, String[] msg_parts, EmbedBuilder builder){
        if(check_permissions(permissions, Permission.MANAGE_ROLES)){
            String name = msg_parts[2];
            String role = msg_parts[3];

            List<Permission> setRoles = new ArrayList<>();
            if(role.equals("administrator")) {
                setRoles.add(Permission.ADMINISTRATOR);

                guild.getController().createRole().setPermissions(setRoles).setName(name).setColor(Color.orange).queue();

                builder.setTitle("created role: " + name);
                builder.setColor(Color.green);
                guild.getTextChannelsByName(channel.getName(), true).get(0).sendMessage(
                        builder.build()
                ).queue();
            }
            else if(role.equals("moderator")) {
                setRoles.add(Permission.BAN_MEMBERS);
                setRoles.add(Permission.KICK_MEMBERS);
                setRoles.add(Permission.MESSAGE_TTS);
                setRoles.add(Permission.MESSAGE_HISTORY);
                setRoles.add(Permission.MANAGE_ROLES);

                guild.getController().createRole().setPermissions(setRoles).setName(name).setColor(Color.orange).queue();

                builder.setTitle("created role: " + name);
                builder.setColor(Color.green);
                guild.getTextChannelsByName(channel.getName(), true).get(0).sendMessage(
                        builder.build()
                ).queue();
            }
            else if(role.equals("member")) {
                setRoles.add(Permission.MESSAGE_READ);
                setRoles.add(Permission.MESSAGE_WRITE);
                setRoles.add(Permission.MESSAGE_TTS);

                guild.getController().createRole().setPermissions(setRoles).setName(name).setColor(Color.orange).queue();

                builder.setTitle("created role: " + name);
                builder.setColor(Color.green);
                guild.getTextChannelsByName(channel.getName(), true).get(0).sendMessage(
                        builder.build()
                ).queue();
            }
            else {
                builder.setTitle("role _" + role + "_ is not an option!");
                builder.setDescription("usage: **§ createrole _[name]_ _[administrator/moderator/member]_**");
                builder.setColor(Color.red);
                guild.getTextChannelsByName(channel.getName(), true).get(0).sendMessage(
                        builder.build()
                ).queue();
            }
        }
        else{
            builder.setTitle(":red_circle: Sorry, but you don`t have the permissions to do that :(");
            builder.setColor(Color.red);
            guild.getTextChannelsByName(channel.getName(), true).get(0).sendMessage(
                    builder.build()
            ).queue();
        }
    }

    // give role
    @Function
    public static void give_role(Guild guild, Channel channel, List<Permission> permissions, String[] msg_parts, EmbedBuilder builder){
        if(check_permissions(permissions, Permission.MANAGE_ROLES)){

            try{
                Member member = guild.getMembersByName(msg_parts[2], true).get(0);

                String role_name = "";
                for(int i=4; i<=msg_parts.length; i++){
                    if(i == msg_parts.length){
                        role_name += msg_parts[i-1];
                    }
                    else{
                        role_name += msg_parts[i-1] + " ";
                    }
                }
                System.out.println(role_name);
                Role role = guild.getRolesByName(role_name, true).get(0);

                guild.getController().addSingleRoleToMember(member, role).queue();

                builder.setTitle(":white_check_mark: " + member.getUser().getName() + " has now the role **" + role.getName() + "**");
                builder.setColor(Color.yellow);
                guild.getTextChannelsByName(channel.getName(), true).get(0).sendMessage(
                        builder.build()
                ).queue();
            }
            catch(Exception e){
                System.out.println(e);
                builder.setTitle(":exclamation: Enter an existing role and user!");
                builder.setColor(Color.red);
                guild.getTextChannelsByName(channel.getName(), true).get(0).sendMessage(
                        builder.build()
                ).queue();
            }
        }
        else{
            builder.setTitle(":red_circle: Sorry, but you don`t have the permissions to do that :(");
            builder.setColor(Color.red);
            guild.getTextChannelsByName(channel.getName(), true).get(0).sendMessage(
                    builder.build()
            ).queue();
        }
    }

    @Function
    public static void embed_text(Guild guild, Channel channel, List<Permission> permissions, Message message, EmbedBuilder builder){
        if(check_permissions(permissions, Permission.MANAGE_SERVER)){

            String to_embed = message.getContentRaw().split("embed")[1];
            String user = message.getMember().getUser().getName();

            builder.setTitle(":speaking_head: " + user + " announces:");
            builder.setDescription(to_embed);
            builder.setColor(Color.red);
            guild.getTextChannelsByName(channel.getName(), true).get(0).sendMessage(
                    builder.build()
            ).queue();
        }
        else{
            builder.setTitle(":red_circle: Sorry, but you don`t have the permissions to do that :(");
            builder.setColor(Color.red);
            guild.getTextChannelsByName(channel.getName(), true).get(0).sendMessage(
                    builder.build()
            ).queue();
        }
    }

    @Function
    public static void vote(Guild guild, Channel channel, String[] msg_parts, EmbedBuilder builder){
        String vote_title = msg_parts[2];
        String vote_side1 = msg_parts[3];
        String vote_side2 = msg_parts[4];

        builder.setTitle(":o: **voting: " + vote_title + "**");
        builder.setColor(Color.orange);
        guild.getTextChannelsByName(channel.getName(), true).get(0).sendMessage(
                builder.build()
        ).queue();

        builder.setTitle("give vote to **" + vote_side1 + "** (1)");
        builder.setColor(Color.orange);
        guild.getTextChannelsByName(channel.getName(), true).get(0).sendMessage(
                builder.build()
        ).complete().addReaction("\uD83C\uDDE6").queue();

        builder.setTitle("give vote to **" + vote_side2 + "** (2)");
        builder.setColor(Color.orange);
        guild.getTextChannelsByName(channel.getName(), true).get(0).sendMessage(
                builder.build()
        ).complete().addReaction("\uD83C\uDDE7").queue();
    }

    @Function
    public static void report(Guild guild, Channel channel, TextChannel textChannel, Member member, MessageHistory history,
                              String[] msg_parts, EmbedBuilder builder){
        List<Member> members = new ArrayList<>();

        for(Member m : guild.getMembers()){
            if(!m.getUser().isBot()){
                members.add(m);
            }
        }
        List<Member> admins = new ArrayList<>();
        for(Member m : members){
            if(m.getPermissions().contains(Permission.ADMINISTRATOR)) {
                admins.add(m);
            }
        }

        String reason = "";
        for(int i=3; i<=msg_parts.length - 1; i++){
            reason += msg_parts[i] + " ";
        }
        String r = reason;

        String to_report = msg_parts[2];
        try{
            for(Member admin : admins){
                admin.getUser().openPrivateChannel().queue((channel0) ->
                {
                    builder.setDescription(":radioactive: **" + member.getUser().getName() + "** reported **" + to_report +
                            "** _reason: " + r + "_");
                    builder.setColor(Color.orange);

                    channel0.sendMessage(
                            builder.build()
                    ).queue();
                });
            }
            builder.setDescription(":radioactive: reported successfully");
            builder.setColor(Color.orange);
            guild.getTextChannelsByName(channel.getName(), true).get(0).sendMessage(
                    builder.build()
            ).queue();
        }
        catch (Exception e){
            System.out.println(e);

            builder.setDescription(":red_circle: enter valid member name!");
            builder.setColor(Color.red);
            guild.getTextChannelsByName(channel.getName(), true).get(0).sendMessage(
                    builder.build()
            ).queue();
        }
        try{
        TimeUnit.SECONDS.sleep(1);
        } catch (Exception e){ ; }

        history.getRetrievedHistory();

        List<Message> msgs;
        msgs = history.retrievePast(2).complete();

        textChannel.deleteMessages(msgs).queue();
    }

    @Function
    public static void btc_data(Guild guild, Channel channel, EmbedBuilder builder){
        String data;

        get_crypto_data cd = new get_crypto_data();
        try{
            data = cd.get_json();

            builder.setTitle(":money_with_wings: Bitcoin data");
            builder.setDescription(data);
            builder.setColor(Color.green);

            guild.getTextChannelsByName(channel.getName(), true).get(0).sendMessage(
                    builder.build()
            ).queue();

        }
        catch (Exception e) {
            System.out.println(e);

            builder.setTitle(":red_circle: something went wrong, try it again later!");
            builder.setColor(Color.red);

            guild.getTextChannelsByName(channel.getName(), true).get(0).sendMessage(
                    builder.build()
            ).queue();
        }
    }

    @Function
    public static void stone_paper_scissors(Guild guild, Channel channel, String[] msg_parts, EmbedBuilder builder){
        String player1 = msg_parts[2];
        String player2 = msg_parts[3];

        String[] res = new String[2];

        for(int i=0; i<=1; i++){
            Random random = new Random();
            int randint = random.nextInt(3 - 1 + 1) + 1;

            if(randint == 1){
                res[i] = ":right_facing_fist:";
            }
            else if(randint == 2){
                res[i] = ":raised_hand:";
            }
            else if(randint == 3){
                res[i] = ":v:";
            }
        }

        builder.setTitle(":right_facing_fist: stone paper scissors :left_facing_fist: ");
        builder.setDescription("**" + player1 + "** strikes " + res[0] + " !\n " + "**" + player2 + "** strikes " + res[1] + " !");
        builder.setColor(Color.yellow);

        guild.getTextChannelsByName(channel.getName(), true).get(0).sendMessage(
                builder.build()
        ).queue();
    }

    @Function
    public static void google(Guild guild, Channel channel, String[] msg_parts, EmbedBuilder builder){
        String to_google = "";

        for(int i=2; i<=msg_parts.length - 1; i++){
            to_google += msg_parts[i] + " ";
        }

        try{
            get_google_result g = new get_google_result();

            builder.setTitle(":mag_right: Google search results");
            builder.setDescription("\n" + g.google_result(to_google));
            builder.setColor(Color.green);

            guild.getTextChannelsByName(channel.getName(), true).get(0).sendMessage(
                    builder.build()
            ).queue();
        }
        catch (Exception e){
            builder.setTitle(":red_circle: something went wrong, try it again later!");
            builder.setColor(Color.red);

            guild.getTextChannelsByName(channel.getName(), true).get(0).sendMessage(
                    builder.build()
            ).queue();
        }
    }

    @Function
    public static void join_voicechannel(Guild guild, String[] msg_parts) {

        VoiceChannel voiceChannel = guild.getVoiceChannelsByName(msg_parts[2], true).get(0);

        AudioManager audioManager = guild.getAudioManager();
        audioManager.openAudioConnection(voiceChannel);

        System.out.println("[+] " + STATICS.BOT_NAME + " joined voice channel: \n- server: " + guild.getName() + "\n- voicechannel" + voiceChannel.getName());
    }
}
