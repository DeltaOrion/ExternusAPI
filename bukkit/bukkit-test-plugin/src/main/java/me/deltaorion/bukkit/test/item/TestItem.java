package me.deltaorion.bukkit.test.item;

import com.google.common.collect.ImmutableList;
import me.deltaorion.bukkit.item.custom.CustomItem;
import me.deltaorion.bukkit.item.custom.CustomItemEvent;
import me.deltaorion.bukkit.item.custom.ItemEventHandler;
import me.deltaorion.bukkit.item.position.InventoryItem;
import me.deltaorion.bukkit.item.position.SlotType;
import me.deltaorion.bukkit.item.predicate.EventCondition;
import me.deltaorion.bukkit.item.wrapper.CustomEventWrapper;
import me.deltaorion.common.locale.message.Message;
import me.deltaorion.bukkit.test.bukkit.TestEvent;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.junit.Assert;

import java.util.List;

import static org.junit.Assert.*;

public class TestItem extends CustomItem {

    private final List<String> helper;

    public TestItem(List<String> helper) {
        super("Test_Item", new ItemStack(Material.DIAMOND_CHESTPLATE));
        this.helper = helper;
        setDefaultDisplayName(Message.valueOfTranslatable("hello"));
        setDefaultLore(ImmutableList.of(Message.valueOfTranslatable("hello")));
    }

    @ItemEventHandler(condition = EventCondition.HOTBAR)
    private void onEvent(CustomItemEvent<TestEvent> event) {
        if(event.getEvent().getMessage().equals("count"))
            return;

        if(event.getEvent().getMessage().equals("armor")) {
            return;
        }
        helper.add(event.getEvent().getMessage());
    }

    @ItemEventHandler(condition = EventCondition.MAIN_HAND, wrapper = CustomEventWrapper.PLAYER_EVENT)
    public void onEvent2(CustomItemEvent<TestEvent> event) {

        Assert.assertTrue(event.getItemStacks().size()<=1);
        InventoryItem item = event.getItemStacks().get(0);
        Assert.assertEquals(SlotType.MAIN_HAND,item.getSlotType());
        assertEquals(event.getEntity().getEquipment().getItemInHand(),item.getItemStack());

        if(event.getEntity() instanceof HumanEntity) {
            assertEquals(((HumanEntity) event.getEntity()).getInventory().getHeldItemSlot(),item.getRawSlot());
        }

        if(event.getEvent().getMessage().equals("count"))
            return;

        if(event.getEvent().getMessage().equals("armor")) {
            return;
        }
        helper.add(event.getEvent().getMessage());
    }

    @ItemEventHandler(condition = EventCondition.INVENTORY)
    public void onEvent3(CustomItemEvent<TestEvent> event) {
        if(event.getEvent().getMessage().equals("count")) {
            for(int i=0;i<event.getItemStacks().size();i++) {
                helper.add("g");
            }
            return;
        }

        if(event.getEvent().getMessage().equals("armor")) {
            return;
        }
        helper.add(event.getEvent().getMessage());
    }

    @ItemEventHandler(condition = EventCondition.ARMOR)
    public void onEvent4(CustomItemEvent<TestEvent> event) {
        if(event.getEvent().getMessage().equals("count"))
            return;

        assertTrue(event.getItemStacks().size()<=4);
        for(InventoryItem item : event.getItemStacks()) {
            if(item.getSlotType().equals(SlotType.MAIN_HAND))
                fail();
            if(item.getSlotType().equals(SlotType.OTHER))
                fail();
            if(item.getSlotType().equals(SlotType.CHESTPLATE)) {
                assertEquals(item.getItemStack(),event.getEntity().getEquipment().getChestplate());
            }
            if(item.getSlotType().equals(SlotType.LEGGINGS)) {
                assertEquals(item.getItemStack(),event.getEntity().getEquipment().getLeggings());
            }
            if(item.getSlotType().equals(SlotType.BOOTS)) {
                assertEquals(item.getItemStack(),event.getEntity().getEquipment().getBoots());
            }
            if(item.getSlotType().equals(SlotType.HELMET)) {
                assertEquals(item.getItemStack(),event.getEntity().getEquipment().getHelmet());
            }
        }

        if(event.getEvent().getMessage().equals("armor")) {
            PlayerInventory inventory = ((HumanEntity) event.getEntity()).getInventory();
            if(isCustomItem(inventory.getChestplate())) {
                helper.add("g");
            }
            if(isCustomItem(inventory.getLeggings()))
                helper.add("g");
            if(isCustomItem(inventory.getBoots()))
                helper.add("g");
            if(isCustomItem(inventory.getHelmet()))
                helper.add("g");

            return;
        }
        helper.add(event.getEvent().getMessage());
    }




}
