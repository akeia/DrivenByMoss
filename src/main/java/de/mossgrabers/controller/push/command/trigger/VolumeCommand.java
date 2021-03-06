// Written by Jürgen Moßgraber - mossgrabers.de
// (c) 2017-2019
// Licensed under LGPLv3 - http://www.gnu.org/licenses/lgpl-3.0.txt

package de.mossgrabers.controller.push.command.trigger;

import de.mossgrabers.controller.push.PushConfiguration;
import de.mossgrabers.controller.push.controller.PushControlSurface;
import de.mossgrabers.framework.command.core.AbstractTriggerCommand;
import de.mossgrabers.framework.daw.IModel;
import de.mossgrabers.framework.mode.ModeManager;
import de.mossgrabers.framework.mode.Modes;
import de.mossgrabers.framework.utils.ButtonEvent;


/**
 * Command to edit the Volume of tracks.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public class VolumeCommand extends AbstractTriggerCommand<PushControlSurface, PushConfiguration>
{
    /**
     * Constructor.
     *
     * @param model The model
     * @param surface The surface
     */
    public VolumeCommand (final IModel model, final PushControlSurface surface)
    {
        super (model, surface);
    }


    /** {@inheritDoc} */
    @Override
    public void execute (final ButtonEvent event)
    {
        if (event != ButtonEvent.DOWN)
            return;

        final ModeManager modeManager = this.surface.getModeManager ();
        final Modes currentMode = modeManager.getActiveOrTempModeId ();

        // Layer mode selection for Push 1
        final PushConfiguration config = this.surface.getConfiguration ();
        if (!config.isPush2 () && this.surface.isSelectPressed () && Modes.isLayerMode (currentMode))
        {
            modeManager.setActiveMode (Modes.DEVICE_LAYER_VOLUME);
            return;
        }

        if (Modes.VOLUME.equals (currentMode))
        {
            if (this.model.getHost ().hasCrossfader ())
                modeManager.setActiveMode (Modes.CROSSFADER);
        }
        else
            modeManager.setActiveMode (Modes.VOLUME);
    }
}
