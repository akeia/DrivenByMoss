package de.mossgrabers.launchpad.mode;

import de.mossgrabers.framework.daw.IModel;
import de.mossgrabers.launchpad.controller.LaunchpadControlSurface;


/**
 * The rec arm track mode.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public class RecArmMode extends AbstractTrackMode
{
    /**
     * Constructor.
     *
     * @param surface The surface
     * @param model The model
     */
    public RecArmMode (final LaunchpadControlSurface surface, final IModel model)
    {
        super (surface, model);
    }
}