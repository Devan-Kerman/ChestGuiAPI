package me.simon.chestgui.tet;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.simon.chestgui.API.ChestGui;
import me.simon.chestgui.API.parts.ChestGuiButton;
import net.minecraft.container.NameableContainerFactory;
import net.minecraft.container.SlotActionType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.MessageType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

public class TestCommand {
        public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
            dispatcher.register(CommandManager.literal("testGui")
                    .executes(TestCommand::testGui)
            );
        }

        private static int testGui(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
            ServerPlayerEntity player = ctx.getSource().getPlayer();
            player.openContainer(openGui(player));
            return 1;
        }
        private static NameableContainerFactory openGui(ServerPlayerEntity playerEntity) {
            NameableContainerFactory factory =
                    new ChestGui()
                            .setName(new LiteralText("Cool GUI yee haw").formatted(Formatting.RED))
                            .setRowSize(2)
                            .addButton(new ChestGuiButton(8, new ItemStack(Items.GREEN_WOOL))
                                    .setName(new LiteralText("Cool nice green wool").formatted(Formatting.GREEN))
                                    .setAction(SlotActionType.PICKUP,
                                            ()-> playerEntity.sendChatMessage(
                                                    new LiteralText("testing this thing again")
                                                    , MessageType.CHAT)))
                            .addButton(new ChestGuiButton( 0, new ItemStack(Items.RED_WOOL))
                                    .setAction(SlotActionType.QUICK_MOVE, playerEntity::kill)
                                    .setName(new LiteralText("RED MEANS DEATH").formatted(Formatting.RED)))
                            .build();
            return factory;
        }
}
