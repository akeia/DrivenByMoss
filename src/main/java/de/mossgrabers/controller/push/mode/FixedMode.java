// Written by Jürgen Moßgraber - mossgrabers.de
// (c) 2017-2019
// Licensed under LGPLv3 - http://www.gnu.org/licenses/lgpl-3.0.txt

package de.mossgrabers.controller.push.mode;

import de.mossgrabers.controller.push.controller.Push1Display;
import de.mossgrabers.controller.push.controller.PushControlSurface;
import de.mossgrabers.framework.command.trigger.clip.TemporaryNewCommand;
import de.mossgrabers.framework.configuration.AbstractConfiguration;
import de.mossgrabers.framework.configuration.Configuration;
import de.mossgrabers.framework.controller.color.ColorManager;
import de.mossgrabers.framework.controller.display.IGraphicDisplay;
import de.mossgrabers.framework.controller.display.ITextDisplay;
import de.mossgrabers.framework.daw.IModel;
import de.mossgrabers.framework.mode.AbstractMode;
import de.mossgrabers.framework.utils.ButtonEvent;


/**
 * Editing the default length of new clips.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public class FixedMode extends BaseMode
{
    /**
     * Constructor.
     *
     * @param surface The control surface
     * @param model The model
     */
    public FixedMode (final PushControlSurface surface, final IModel model)
    {
        super ("Fixed", surface, model);
        this.isTemporary = false;
    }


    /** {@inheritDoc} */
    @Override
    public void onFirstRow (final int index, final ButtonEvent event)
    {
        if (event != ButtonEvent.UP)
            return;
        this.surface.getConfiguration ().setNewClipLength (index);
        this.surface.getModeManager ().restoreMode ();
    }


    /** {@inheritDoc} */
    @Override
    public void onSecondRow (final int index, final ButtonEvent event)
    {
        if (event != ButtonEvent.UP)
            return;
        new TemporaryNewCommand<> (index, this.model, this.surface).execute (ButtonEvent.DOWN);
        this.surface.getModeManager ().restoreMode ();
    }


    /** {@inheritDoc} */
    @Override
    public void updateFirstRow ()
    {
        final ColorManager colorManager = this.model.getColorManager ();
        final Configuration configuration = this.surface.getConfiguration ();
        for (int i = 0; i < 8; i++)
            this.surface.updateTrigger (20 + i, colorManager.getColor (configuration.getNewClipLength () == i ? AbstractMode.BUTTON_COLOR_HI : AbstractMode.BUTTON_COLOR_ON));
    }


    /** {@inheritDoc} */
    @Override
    public void updateSecondRow ()
    {
        final ColorManager colorManager = this.model.getColorManager ();
        for (int i = 0; i < 8; i++)
            this.surface.updateTrigger (102 + i, colorManager.getColor (AbstractMode.BUTTON_COLOR_ON));
    }


    /** {@inheritDoc} */
    @Override
    public void updateDisplay1 (final ITextDisplay display)
    {
        display.setBlock (1, 0, "Create Clip (leng").setBlock (1, 1, "th not stored):");
        final int newClipLength = this.surface.getConfiguration ().getNewClipLength ();
        display.setBlock (2, 0, "New Clip Length:");
        for (int i = 0; i < 8; i++)
        {
            final String newClipLengthValue = AbstractConfiguration.getNewClipLengthValue (i);
            display.setCell (0, i, newClipLengthValue);
            display.setCell (3, i, (newClipLength == i ? Push1Display.SELECT_ARROW : "") + newClipLengthValue);
        }
    }


    /** {@inheritDoc} */
    @Override
    public void updateDisplay2 (final IGraphicDisplay display)
    {
        final int newClipLength = this.surface.getConfiguration ().getNewClipLength ();
        for (int i = 0; i < 8; i++)
        {
            final String newClipLengthValue = AbstractConfiguration.getNewClipLengthValue (i);
            display.addOptionElement (i == 0 ? "Create Clip (length not stored)" : "", newClipLengthValue, false, i == 0 ? "New Clip Length" : "", newClipLengthValue, newClipLength == i, false);
        }
    }
}
