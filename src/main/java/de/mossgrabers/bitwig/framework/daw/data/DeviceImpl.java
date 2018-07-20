// Written by Jürgen Moßgraber - mossgrabers.de
// (c) 2017-2018
// Licensed under LGPLv3 - http://www.gnu.org/licenses/lgpl-3.0.txt

package de.mossgrabers.bitwig.framework.daw.data;

import de.mossgrabers.framework.daw.data.AbstractItemImpl;
import de.mossgrabers.framework.daw.data.IDevice;

import com.bitwig.extension.controller.api.Device;


/**
 * Encapsulates the data of a device.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public class DeviceImpl extends AbstractItemImpl implements IDevice
{
    private final Device device;


    /**
     * Constructor.
     *
     * @param device The device to encapsulate
     * @param index The index of the device
     */
    public DeviceImpl (final Device device, final int index)
    {
        super (index);

        this.device = device;

        device.exists ().markInterested ();
        device.name ().markInterested ();
    }


    /** {@inheritDoc} */
    @Override
    public void enableObservers (final boolean enable)
    {
        this.device.exists ().setIsSubscribed (enable);
        this.device.name ().setIsSubscribed (enable);
    }


    /** {@inheritDoc} */
    @Override
    public boolean doesExist ()
    {
        return this.device.exists ().get ();
    }


    /** {@inheritDoc} */
    @Override
    public String getName ()
    {
        return this.device.name ().get ();
    }


    /** {@inheritDoc} */
    @Override
    public String getName (final int limit)
    {
        return this.device.name ().getLimited (limit);
    }


    /** {@inheritDoc} */
    @Override
    public void select ()
    {
        this.device.selectInEditor ();
    }
}
