package tr.web.minelab.minelabfree.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import tr.web.minelab.minelabfree.MineLABFree;

public class Bot extends ListenerAdapter implements Listener {

    public static JDA jda;
    public Bot() {
        this.startBot();
    }

    private void startBot() {
        String token = MineLABFree.getInstance().getConfig().getString("Discord.Bot.Token");
        try {
            JDABuilder builder = JDABuilder.createDefault(token);
            builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
            builder.setBulkDeleteSplittingEnabled(false);
            builder.setCompression(Compression.NONE);
            builder.enableIntents(GatewayIntent.GUILD_MEMBERS,
                    GatewayIntent.GUILD_BANS,
                    GatewayIntent.GUILD_VOICE_STATES,
                    GatewayIntent.DIRECT_MESSAGE_REACTIONS,
                    GatewayIntent.GUILD_WEBHOOKS,
                    GatewayIntent.DIRECT_MESSAGE_TYPING,
                    GatewayIntent.GUILD_INVITES,
                    GatewayIntent.GUILD_PRESENCES,
                    GatewayIntent.GUILD_MESSAGES);
            builder.addEventListeners(this);
            jda = builder.build();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Message message = event.getMessage();
        String[] args = message.getContentRaw().split("\\s+");
        if (!args[0].startsWith(MineLABFree.getInstance().getConfig().getString("Discord.Bot.Prefix"))) {
            // If the messages does not start with the set prefix, simply do nothing.
            return;
        }
        String command = args[0].replace(MineLABFree.getInstance().getConfig().getString("Discord.Bot.Prefix"), "");

        if(command.equalsIgnoreCase("profil")) {
            try {
                OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
                String embedColor = MineLABFree.getInstance().getConfig().getString("Discord.Embed.Color");
                String embedFooter = MineLABFree.getInstance().getConfig().getString("Discord.Embed.Footer").replaceAll("%player%", player.getName());
                String embedTitle = MineLABFree.getInstance().getConfig().getString("Discord.Embed.Title").replaceAll("%player%", player.getName());
                EmbedBuilder eb = new EmbedBuilder()
                        .setFooter(embedFooter)
                        .setColor(Color.getColor(embedColor))
                        .setTitle(embedTitle);
                if (player == null)
                    message.reply("Lütfen bir kullanıcı adı giriniz.").queue();
                if (player.isBanned()) {
                    eb.setDescription("Bu kullanıcı sunucuda yasaklanmış :/");
                    message.replyEmbeds(eb.build()).queue();
                    return;
                }

                String onlineStatus = "Çevrimdışı";
                if (player.isOnline()) onlineStatus = "Çevrimiçi";

                eb.setDescription(
                        "Kullanıcı: " + player.getName()
                        + "Kredisi: " + (MineLABFree.getFetchData().credits.get(player.getUniqueId()) != null ? MineLABFree.getFetchData().credits.get(player.getUniqueId()) : "0")
                        + "\nInstagram: " + (MineLABFree.getFetchData().instagrams.get(player.getUniqueId()) != null ? MineLABFree.getFetchData().instagrams.get(player.getUniqueId()) : "-")
                        + "\nTwitter: " + (MineLABFree.getFetchData().twitters.get(player.getUniqueId()) != null ? MineLABFree.getFetchData().twitters.get(player.getUniqueId()) : "-")
                        + "\nDiscord: " + (MineLABFree.getFetchData().discords.get(player.getUniqueId()) != null ? MineLABFree.getFetchData().discords.get(player.getUniqueId()) : "-")
                        + "\nSkype: " + (MineLABFree.getFetchData().skypes.get(player.getUniqueId()) != null ? MineLABFree.getFetchData().skypes.get(player.getUniqueId()) : "-")
                        + "\nYouTube: " + (MineLABFree.getFetchData().youtubes.get(player.getUniqueId()) != null ? MineLABFree.getFetchData().youtubes.get(player.getUniqueId()) : "-")
                        + "\n"
                        + "\nAktiflik: " + onlineStatus
                );
                message.replyEmbeds(eb.build()).queue();
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }

        }
    }
}
