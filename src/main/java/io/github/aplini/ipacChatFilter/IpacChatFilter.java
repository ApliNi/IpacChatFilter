package io.github.aplini.ipacChatFilter;

import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.sensitive.word.api.context.InnerSensitiveWordContext;
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import com.github.houbb.sensitive.word.support.check.WordChecks;
import com.github.houbb.sensitive.word.support.ignore.SpecialCharSensitiveWordCharIgnore;
import com.github.houbb.sensitive.word.support.resultcondition.WordResultConditions;
import com.github.houbb.sensitive.word.support.tag.WordTags;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public final class IpacChatFilter extends JavaPlugin implements Listener {

    public static IpacChatFilter plugin;
    public static SensitiveWordBs wordBs = null;
    public static List<String> wordDenys;
    public static List<String> wordAllows;
    public static List<Map<?, ?>> preRegexList;

    @Override
    public void onEnable() {
        plugin = this;

        Objects.requireNonNull(getCommand("icf")).setExecutor(this);
        Bukkit.getPluginManager().registerEvents(this, this);

        try {
            reload();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void reload() throws IOException {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();

        File wordDenyFile = new File(plugin.getDataFolder(), "word_deny.txt");
        plugin.getLogger().info(wordDenyFile.toString());
        if (!wordDenyFile.exists()) {
            wordDenyFile.createNewFile();
        }
        File wordAllowFile = new File(plugin.getDataFolder(), "word_allow.txt");
        if (!wordAllowFile.exists()) {
            wordAllowFile.createNewFile();
        }

        wordDenys = Files.readAllLines(wordDenyFile.toPath(), StandardCharsets.UTF_8);
        wordAllows = Files.readAllLines(wordAllowFile.toPath(), StandardCharsets.UTF_8);

        // 去重
        wordDenys = new ArrayList<>(new LinkedHashSet<>(wordDenys));
        wordAllows = new ArrayList<>(new LinkedHashSet<>(wordAllows));

        if(wordBs != null){
            wordBs.destroy();
        }
        wordBs = SensitiveWordBs.newInstance()
                .wordDeny(() -> wordDenys)
                .wordAllow(() -> wordAllows)
                .wordReplace((stringBuilder, rawText, wordResult, wordContext) -> {
                    int wordLength = wordResult.endIndex() - wordResult.startIndex();
                    String str = plugin.getConfig().getString("wordReplaceTo", "*");
                    stringBuilder.append(str.repeat(Math.max(0, wordLength)));
                })
                .ignoreCase(plugin.getConfig().getBoolean("bsConfig.ignoreCase", true))
                .ignoreWidth(plugin.getConfig().getBoolean("bsConfig.ignoreWidth", true))
                .ignoreNumStyle(plugin.getConfig().getBoolean("bsConfig.ignoreNumStyle", true))
                .ignoreChineseStyle(plugin.getConfig().getBoolean("bsConfig.ignoreChineseStyle", true))
                .ignoreEnglishStyle(plugin.getConfig().getBoolean("bsConfig.ignoreEnglishStyle", true))
                .ignoreRepeat(plugin.getConfig().getBoolean("bsConfig.ignoreRepeat", false))
                .enableNumCheck(plugin.getConfig().getBoolean("bsConfig.enableNumCheck", false))
                .enableEmailCheck(plugin.getConfig().getBoolean("bsConfig.enableEmailCheck", false))
                .enableUrlCheck(plugin.getConfig().getBoolean("bsConfig.enableUrlCheck", false))
                .enableIpv4Check(plugin.getConfig().getBoolean("bsConfig.enableIpv4Check", false))
                .enableWordCheck(plugin.getConfig().getBoolean("bsConfig.enableWordCheck", true))
                .wordFailFast(plugin.getConfig().getBoolean("bsConfig.wordFailFast", true))
                .wordCheckNum(WordChecks.num())
                .wordCheckEmail(WordChecks.email())
                .wordCheckUrl(WordChecks.url())
                .wordCheckIpv4(WordChecks.ipv4())
                .wordCheckWord(WordChecks.word())
                .numCheckLen(plugin.getConfig().getInt("bsConfig.numCheckLen", 8))
                .wordTag(WordTags.none())
                .charIgnore(new SpecialCharSensitiveWordCharIgnore(){
                    private static final String SPECIAL = String.join("", plugin.getConfig().getStringList("ignoreChars"));
                    private static final Set<Character> SET;
                    static {
                        SET = StringUtil.toCharSet(SPECIAL);
                    }
                    @Override
                    protected boolean doIgnore(int ix, String text, InnerSensitiveWordContext innerContext) {
                        char c = text.charAt(ix);
                        return SET.contains(c);
                    }
                })
                .wordResultCondition(WordResultConditions.alwaysTrue())
                .init();

        preRegexList.clear();
        preRegexList = plugin.getConfig().getMapList("preRegex");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event){

        if(!event.getPlayer().hasPermission("IpacChatFilter.filter")){
            return;
        }
        if(event.getPlayer().hasPermission("IpacChatFilter.bypass")){
            return;
        }

        String message = event.getMessage();
        String result = message;

        // 前置替换
        for (Map<?, ?> item : preRegexList) {
            String regex = (String) item.get("regex");
            String replace = (String) item.get("to");
            result = result.replaceAll(regex, replace);
        }

        result = wordBs.replace(result);

        if(!message.equals(result)){
            event.setMessage(result);
            if(plugin.getConfig().getBoolean("log", true)){
                plugin.getLogger().info("Original[" + event.getPlayer().getName() + "]: " + message);
            }
        }
    }

    @Override // 执行指令
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){

        // 默认输出插件信息
        if(args.length == 0){
            sender.sendMessage(
                    "\n"+
                            "IpacEL > IpacChatFilter: 聊天过滤器\n"+
                            "  指令:\n"+
                            "    - /utn reload          - 重载配置\n"
            );
            return true;
        }

        // 重载配置
        if(args[0].equals("reload")){
            try {
                reload();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sender.sendMessage("[ICF] 已完成重载");
            return true;
        }

        // 返回 false 时, 玩家将收到命令不存在的错误
        return false;
    }
}
