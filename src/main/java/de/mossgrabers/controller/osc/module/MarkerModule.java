// Written by Jürgen Moßgraber - mossgrabers.de
// (c) 2017-2019
// Licensed under LGPLv3 - http://www.gnu.org/licenses/lgpl-3.0.txt

package de.mossgrabers.controller.osc.module;

import de.mossgrabers.controller.osc.exception.IllegalParameterException;
import de.mossgrabers.controller.osc.exception.MissingCommandException;
import de.mossgrabers.controller.osc.exception.UnknownCommandException;
import de.mossgrabers.framework.daw.IHost;
import de.mossgrabers.framework.daw.IMarkerBank;
import de.mossgrabers.framework.daw.IModel;
import de.mossgrabers.framework.daw.data.IMarker;
import de.mossgrabers.framework.osc.IOpenSoundControlWriter;

import java.util.LinkedList;


/**
 * All marker related commands.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public class MarkerModule extends AbstractModule
{
    /**
     * Constructor.
     *
     * @param host The host
     * @param model The model
     * @param writer The writer
     */
    public MarkerModule (final IHost host, final IModel model, final IOpenSoundControlWriter writer)
    {
        super (host, model, writer);
    }


    /** {@inheritDoc} */
    @Override
    public String [] getSupportedCommands ()
    {
        return new String []
        {
            "marker"
        };
    }


    /** {@inheritDoc} */
    @Override
    public void execute (final String command, final LinkedList<String> path, final Object value, final Object... voice) throws IllegalParameterException, UnknownCommandException, MissingCommandException
    {
        if (!"marker".equals (command))
            throw new UnknownCommandException (command);

        final String subCommand = getSubCommand (path);
        try
        {
            final int markerNo = Integer.parseInt (subCommand) - 1;
            final String subCommand2 = getSubCommand (path);
            switch (subCommand2)
            {
                case "launch":
                    this.model.getMarkerBank ().getItem (markerNo).launch (true);
                    break;
                default:
                    throw new UnknownCommandException (subCommand2);
            }
        }
        catch (final NumberFormatException ex)
        {
            final IMarkerBank markerBank = this.model.getMarkerBank ();
            switch (subCommand)
            {
                case "bank":
                    final String subCommand2 = getSubCommand (path);
                    switch (subCommand2)
                    {
                        case "+":
                            markerBank.selectNextPage ();
                            break;
                        case "-":
                            markerBank.selectPreviousPage ();
                            break;
                        default:
                            throw new UnknownCommandException (subCommand2);
                    }
                    break;
                default:
                    throw new UnknownCommandException (subCommand);
            }
        }
    }


    /** {@inheritDoc} */
    @Override
    public void flush (final boolean dump)
    {
        final IMarkerBank markerBank = this.model.getMarkerBank ();
        for (int i = 0; i < markerBank.getPageSize (); i++)
        {
            final String markerAddress = "/marker/" + (i + 1) + "/";
            final IMarker marker = markerBank.getItem (i);
            this.writer.sendOSC (markerAddress + "exists", marker.doesExist (), dump);
            this.writer.sendOSC (markerAddress + "name", marker.getName (), dump);
            final double [] color = marker.getColor ();
            this.writer.sendOSCColor (markerAddress + "color", color[0], color[1], color[2], dump);
        }
    }
}