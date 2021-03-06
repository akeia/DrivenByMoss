// Written by Jürgen Moßgraber - mossgrabers.de
// (c) 2017-2019
// Licensed under LGPLv3 - http://www.gnu.org/licenses/lgpl-3.0.txt

package de.mossgrabers.framework.daw.data.empty;

import de.mossgrabers.framework.daw.data.ISlot;


/**
 * Default data for an empty slot.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public class EmptySlot extends EmptyItem implements ISlot
{
    /** The singleton. */
    public static final ISlot INSTANCE = new EmptySlot ();


    /**
     * Constructor.
     */
    private EmptySlot ()
    {
        // Intentionally empty
    }


    /** {@inheritDoc} */
    @Override
    public boolean hasContent ()
    {
        return false;
    }


    /** {@inheritDoc} */
    @Override
    public boolean isRecording ()
    {
        return false;
    }


    /** {@inheritDoc} */
    @Override
    public boolean isPlaying ()
    {
        return false;
    }


    /** {@inheritDoc} */
    @Override
    public boolean isPlayingQueued ()
    {
        return false;
    }


    /** {@inheritDoc} */
    @Override
    public boolean isRecordingQueued ()
    {
        return false;
    }


    /** {@inheritDoc} */
    @Override
    public boolean isStopQueued ()
    {
        return false;
    }


    /** {@inheritDoc} */
    @Override
    public double [] getColor ()
    {
        return COLOR_OFF;
    }


    /** {@inheritDoc} */
    @Override
    public void setColor (final double red, final double green, final double blue)
    {
        // Intentionally empty
    }


    /** {@inheritDoc} */
    @Override
    public void launch ()
    {
        // Intentionally empty
    }


    /** {@inheritDoc} */
    @Override
    public void record ()
    {
        // Intentionally empty
    }


    /** {@inheritDoc} */
    @Override
    public void create (final int length)
    {
        // Intentionally empty
    }


    /** {@inheritDoc} */
    @Override
    public void remove ()
    {
        // Intentionally empty
    }


    /** {@inheritDoc} */
    @Override
    public void duplicate ()
    {
        // Intentionally empty
    }


    /** {@inheritDoc} */
    @Override
    public void browse ()
    {
        // Intentionally empty
    }
}
