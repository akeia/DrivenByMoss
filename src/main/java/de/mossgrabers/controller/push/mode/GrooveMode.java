// Written by Jürgen Moßgraber - mossgrabers.de
// (c) 2017-2019
// Licensed under LGPLv3 - http://www.gnu.org/licenses/lgpl-3.0.txt

package de.mossgrabers.controller.push.mode;

import de.mossgrabers.controller.push.controller.PushControlSurface;
import de.mossgrabers.framework.controller.display.Format;
import de.mossgrabers.framework.controller.display.IGraphicDisplay;
import de.mossgrabers.framework.controller.display.ITextDisplay;
import de.mossgrabers.framework.daw.IGroove;
import de.mossgrabers.framework.daw.IModel;
import de.mossgrabers.framework.daw.data.IParameter;


/**
 * Editing of groove parameters.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public class GrooveMode extends BaseMode
{
    /**
     * Constructor.
     *
     * @param surface The control surface
     * @param model The model
     */
    public GrooveMode (final PushControlSurface surface, final IModel model)
    {
        super ("Groove", surface, model);
    }


    /** {@inheritDoc} */
    @Override
    public void onActivate ()
    {
        this.setActive (true);
    }


    /** {@inheritDoc} */
    @Override
    public void onDeactivate ()
    {
        this.setActive (false);
    }


    /** {@inheritDoc} */
    @Override
    public void onKnobValue (final int index, final int value)
    {
        if (index == 0)
            this.surface.getConfiguration ().changeQuantizeAmount (value);
        else if (index > 1)
            this.model.getGroove ().getParameters ()[index - 2].changeValue (value);
    }


    /** {@inheritDoc} */
    @Override
    public void onKnobTouch (final int index, final boolean isTouched)
    {
        final IParameter [] parameters = this.model.getGroove ().getParameters ();
        if (isTouched && this.surface.isDeletePressed ())
        {
            this.surface.setTriggerConsumed (this.surface.getDeleteTriggerId ());
            if (index == 0)
                this.surface.getConfiguration ().resetQuantizeAmount ();
            else if (index > 1 && index - 2 < parameters.length)
                parameters[index - 2].resetValue ();
        }

        if (index > 1 && index - 2 < parameters.length)
            parameters[index - 2].touchValue (isTouched);
    }


    /** {@inheritDoc} */
    @Override
    public void updateDisplay1 (final ITextDisplay display)
    {
        final IParameter [] parameters = this.model.getGroove ().getParameters ();
        final int quantizeAmount = this.surface.getConfiguration ().getQuantizeAmount ();
        display.setCell (0, 0, "Quant Amnt").setCell (1, 0, quantizeAmount + "%").setCell (2, 0, quantizeAmount * 1023 / 100, Format.FORMAT_VALUE);
        for (int i = 0; i < parameters.length; i++)
            display.setCell (0, 2 + i, parameters[i].getName (8)).setCell (1, 2 + i, parameters[i].getDisplayedValue (8)).setCell (2, 2 + i, parameters[i].getValue (), Format.FORMAT_VALUE);
    }


    /** {@inheritDoc} */
    @Override
    public void updateDisplay2 (final IGraphicDisplay display)
    {
        final IParameter [] parameters = this.model.getGroove ().getParameters ();
        final int quantizeAmount = this.surface.getConfiguration ().getQuantizeAmount ();
        display.addParameterElement ("Quant Amnt", quantizeAmount * 1023 / 100, quantizeAmount + "%", this.isKnobTouched[0], -1);
        display.addOptionElement ("     Groove", "", false, "", "", false, false);
        for (int i = 0; i < parameters.length; i++)
            display.addParameterElement (parameters[i].getName (10), parameters[i].getValue (), parameters[i].getDisplayedValue (8), this.isKnobTouched[i], -1);
        for (int i = parameters.length; i < 6; i++)
            display.addEmptyElement ();
    }


    private void setActive (final boolean enable)
    {
        final IGroove groove = this.model.getGroove ();
        groove.enableObservers (enable);
        groove.setIndication (enable);
    }
}