package me.simon.chestgui.API;

import me.simon.chestgui.API.parts.ChestGuiButton;
import net.minecraft.container.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.util.HashMap;

public class ChestGui {
    private NameableContainerFactory SNCF;
    private HashMap<Integer, ChestGuiButton> buttonList;
    private int rowSize;
    private Text containerName;
    private ItemStack background;

    public ChestGui() {
        this.rowSize = 3;
        this.containerName = new LiteralText("Unnamed GUI");
        this.buttonList = new HashMap<>();

        ItemStack backgroundStack = new ItemStack(Items.BRICK);
        backgroundStack.getOrCreateTag().putInt("CustomModelData", 1);
        backgroundStack.setCustomName(new LiteralText(""));
        this.background = backgroundStack;
    }

    public ChestGui addButton(ChestGuiButton button){
        this.buttonList.put(button.slotId, button);
        return this;
    }

    public ChestGui setRowSize(int rowSize){
        if(rowSize > 6 || rowSize < 1) return null;
        else{
            this.rowSize = rowSize;
            return this;
        }
    }

    public ChestGui setName(Text name){
        this.containerName = name;
        return this;
    }

    public ChestGui setBackground(ItemStack item){
        this.background = item;
        return this;
    }

    public NameableContainerFactory build(){

        this.SNCF = new NameableContainerFactory() {
            @Override
            public Text getDisplayName() {
                return containerName;
            }

            @Override
            public Container createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
                return getContainer(syncId, inv, buttonList);
            }
        };
        return SNCF;
    }

    private GUIContainer getContainer(int syncId, PlayerInventory inventory, HashMap<Integer, ChestGuiButton> buttonList){
        GUIContainer output;
        switch (rowSize){
            case 1:
                output = new GUIContainer(ContainerType.GENERIC_9X1, syncId, inventory, new BasicInventory(9 * rowSize), rowSize, buttonList);
                break;
            case 2:
                output = new GUIContainer(ContainerType.GENERIC_9X2, syncId, inventory, new BasicInventory(9 * rowSize), rowSize, buttonList);
                break;
            case 3:
                output = new GUIContainer(ContainerType.GENERIC_9X3, syncId, inventory, new BasicInventory(9 * rowSize), rowSize, buttonList);
                break;
            case 4:
                output = new GUIContainer(ContainerType.GENERIC_9X4, syncId, inventory, new BasicInventory(9 * rowSize), rowSize, buttonList);
                break;
            case 5:
                output = new GUIContainer(ContainerType.GENERIC_9X5, syncId, inventory, new BasicInventory(9 * rowSize), rowSize, buttonList);
                break;
            case 6:
                output = new GUIContainer(ContainerType.GENERIC_9X6, syncId, inventory, new BasicInventory(9 * rowSize), rowSize, buttonList);
                break;
            default:
                output = new GUIContainer(ContainerType.GENERIC_9X3, syncId, inventory, new BasicInventory(9 * rowSize), rowSize, buttonList);
            break;
        }
        buildInventory(output);
        return output;
    }

    private void buildInventory(Container container){
        for(int i = 0; i < container.slots.size() - 36; i++){
            container.setStackInSlot(i, this.background);
        }
        this.buttonList.forEach((integer, button) -> container.setStackInSlot(button.slotId, button.displayItem));
    }
}
