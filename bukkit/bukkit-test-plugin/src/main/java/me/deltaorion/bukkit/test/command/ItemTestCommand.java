package me.deltaorion.bukkit.test.command;

import me.deltaorion.bukkit.item.EMaterial;
import me.deltaorion.bukkit.item.ItemBuilder;
import me.deltaorion.bukkit.item.potion.PotionBuilder;
import me.deltaorion.bukkit.plugin.UnsupportedVersionException;
import me.deltaorion.bukkit.plugin.plugin.BukkitAPIDepends;
import me.deltaorion.bukkit.plugin.plugin.BukkitPlugin;
import me.deltaorion.common.APIPermissions;
import me.deltaorion.common.command.CommandException;
import me.deltaorion.common.command.FunctionalCommand;
import me.deltaorion.common.command.sent.SentCommand;
import me.deltaorion.common.locale.message.Message;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.Locale;

public class ItemTestCommand extends FunctionalCommand {

    private final BukkitPlugin plugin;

    public ItemTestCommand(BukkitPlugin plugin) {
        super(APIPermissions.COMMAND, NO_USAGE);
        this.plugin = plugin;
    }

    @Override
    public void commandLogic(SentCommand command) throws CommandException {
        if(command.getSender().isConsole())
            throw new CommandException("Test as player");

        if(!plugin.getDependency(BukkitAPIDepends.NBTAPI).isActive())
            throw new CommandException("Please enable the NBT API on the test server to use this command!");

        Player player = plugin.getServer().getPlayer(command.getSender().getUniqueId());

        if(command.getArgOrBlank(0).asString().equalsIgnoreCase("read")) {
            ItemStack itemStack = player.getItemInHand();
            if(itemStack==null)
                return;

            String tag = command.getArgOrDefault(1,"Gamer").asString();
            command.getSender().sendMessage("Tag "+tag+": " + new ItemBuilder(itemStack).getTagValue(tag));
            return;
        }

        ItemStack testAdd = new ItemBuilder(EMaterial.DIAMOND_CHESTPLATE)
                .setAmount(1)
                .setAmount(1)
                .setDurability(200)
                .setDurability(300)
                .setType(Material.DIAMOND_LEGGINGS)
                .setType(Material.DIAMOND_LEGGINGS)
                //.setUnbreakable(true)
                .addFlags(ItemFlag.HIDE_ATTRIBUTES)
                .addFlags(ItemFlag.HIDE_ATTRIBUTES)
                .addEnchantment(Enchantment.DEPTH_STRIDER,7)
                .addEnchantment(Enchantment.DEPTH_STRIDER,7)
                .setLore("Hello","World")
                .setLore("Hello","World")
                .addLoreLine("Line 3")
                .addLoreLine(Message.valueOfTranslatable("hello-arg"), Locale.ENGLISH,"Hello")
                .addTag("Gamer","DeltaOrion")
                .setUnstackable()
                .setDisplayName("Title")
                .build();

        ItemStack potionSimple = new ItemBuilder(EMaterial.POTION)
                .potion(potionBuilder -> {
                    potionBuilder.setColor(PotionType.FIRE_RESISTANCE)
                            .setType(PotionBuilder.Type.SPLASH);
                })
                .setDisplayName("Simple Potion").build();

        ItemStack potion = new ItemBuilder(EMaterial.POTION)
                .addLoreLine("Glass Cannon")
                .potion(potionBuilder -> {
                    potionBuilder.setColor(PotionType.FIRE_RESISTANCE)
                            .addEffect(new PotionEffect(PotionEffectType.POISON,100,5))
                            .addEffect(new PotionEffect(PotionEffectType.SPEED,100,10));
                }).build();

        try {
            ItemStack lingering = new ItemBuilder(EMaterial.SUGAR_CANE)
                    .setDisplayName("Lingering")
                    .potion(potionBuilder -> {
                        potionBuilder.setMainEffect(PotionEffectType.SATURATION)
                                .setType(PotionBuilder.Type.LINGERING)
                                .setColor(Color.BLACK)
                                .addEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE,1000,2));
                    }).build();
            player.getInventory().addItem(lingering);
        } catch(UnsupportedVersionException ignored) {

        }

        try {
            ItemStack lingering = new ItemBuilder(EMaterial.ACACIA_DOOR)
                    .setDisplayName("Lingering2")
                    .potion(potionBuilder -> {
                        potionBuilder.addEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,100,2));
                        potionBuilder.addEffect(new PotionEffect(PotionEffectType.JUMP,100,4));
                        potionBuilder.setColor(PotionType.AWKWARD);
                        potionBuilder.setMainEffect(PotionEffectType.JUMP);
                        potionBuilder.setType(PotionBuilder.Type.LINGERING);
                    }).build();
            player.getInventory().addItem(lingering);
        } catch (UnsupportedVersionException | NoSuchFieldError ignored) {

        }

        ItemStack skull = new ItemBuilder(EMaterial.CREEPER_HEAD)
                .setAmount(3)
                .setAmount(5)
                .setUnstackable()
                .addTag("Gamer","DeltaOrion")
                .skull(skullBuilder -> {
                    skullBuilder.setType(SkullType.CREEPER);
                }).build();

        ItemStack skullNothing = new ItemBuilder(EMaterial.WITCH_SPAWN_EGG)
                .skull( skullBuilder -> {})
                .build();

        ItemStack skull2 = new ItemBuilder(EMaterial.PLAYER_HEAD)
                .skull(skullBuilder -> {
                    skullBuilder.setTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjhkNDA4ODQyZTc2YTVhNDU0ZGMxYzdlOWFjNWMxYThhYzNmNGFkMzRkNjk3M2I1Mjc1NDkxZGZmOGM1YzI1MSJ9fX0=");
                }).build();

        ItemStack potionClear = new ItemBuilder(EMaterial.POTION)
                .potion(potionBuilder -> {
                    potionBuilder.setType(PotionBuilder.Type.SPLASH)
                            .setColor(PotionType.INSTANT_DAMAGE)
                            .addEffect(new PotionEffect(PotionEffectType.BLINDNESS,5,3))
                            .addEffect(new PotionEffect(PotionEffectType.POISON,10,6))
                            .clearEffects()
                            .addEffect(new PotionEffect(PotionEffectType.CONFUSION,5,1));
                }).build();

        ItemStack instantHealing = new ItemBuilder(PotionType.INSTANT_HEAL, PotionBuilder.Type.SPLASH,true,false).build();

        player.getInventory().addItem(instantHealing);


        potionClear = new ItemBuilder(potionClear)
                .potion(potionBuilder -> {
                    potionBuilder.clearEffects()
                            .addEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,100,1));
                }).build();

        ItemStack potionNothing = new ItemBuilder(EMaterial.NETHER_WART)
                .potion(potionBuilder -> {})
                .build();

        ItemStack hiddenEnchant = new ItemBuilder(EMaterial.WITCH_SPAWN_EGG)
                .addHiddenEnchant().build();

        ItemStack stripTest = new ItemBuilder(EMaterial.RED_WOOL)
                .addFlags(ItemFlag.HIDE_POTION_EFFECTS)
                .removeFlags(ItemFlag.HIDE_POTION_EFFECTS)
                .removeFlags(ItemFlag.HIDE_ENCHANTS)
                .addHiddenEnchant()
                .removeHiddenEnchant()
                .addLoreLine("Gamer")
                .setLore("Hallo")
                .setDisplayName("Gamer")
                .clearLore()
                .clearLore()
                .addEnchantment(Enchantment.DEPTH_STRIDER,3)
                .removeEnchantment(Enchantment.ARROW_DAMAGE)
                .clearEnchantments()
                .hideAll()
                .clearFlags()
                .removeDisplayName()
                .build();

        ItemStack NBTTest = new ItemBuilder(EMaterial.SUGAR_CANE)
                .addTag("Gamer", "This should remain after restart!")
                .addTag("This","This should also remain")
                .build();


        player.getInventory().addItem(testAdd);
        player.getInventory().addItem(skull);
        player.getInventory().addItem(skull2);
        player.getInventory().addItem(potion);
        player.getInventory().addItem(potionSimple);
        player.getInventory().addItem(potionClear);
        player.getInventory().addItem(hiddenEnchant);
        player.getInventory().addItem(skullNothing);
        player.getInventory().addItem(potionNothing);
        player.getInventory().addItem(stripTest);
        player.getInventory().addItem(NBTTest);

    }
}
