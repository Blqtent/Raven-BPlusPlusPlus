package keystrokesmod.client.command.commands;

import keystrokesmod.client.command.Command;

import static keystrokesmod.client.clickgui.raven.Terminal.print;

public class Shoutout extends Command {
    public Shoutout() {
        super("shoutout", "Everyone who helped make b+++", 0, 0, new String[] {}, new String[] { "love", "thanks" });
    }

    @Override
    public void onCall(String[] args) {
        print("Everyone who made b+++ possible:");
        print("- sigmaclientwastaken (raven b++ dev)");
        print("- k-ov (raven b++ dev)");
        print("- Comsic-SC (most bypasses)");
        print("- kopamed (raven b+ dev)");
        print("- hevex/blowsy (weeaboo, b3 dev) (disapproves to b+ as he earned less money because less ppl clicked on his adfly link)");
        print("- StephenIsTaken (main dev (still learning lmao))");
        print("- jmraichdev (client dev)");
        print("- nighttab (website dev)");
        print("- mood (java help)");
    }
}
