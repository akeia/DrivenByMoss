// Written by Jürgen Moßgraber - mossgrabers.de
// (c) 2017-2018
// Licensed under LGPLv3 - http://www.gnu.org/licenses/lgpl-3.0.txt

package de.mossgrabers.bitwig.framework.daw;

import de.mossgrabers.bitwig.framework.daw.data.SceneImpl;
import de.mossgrabers.framework.daw.ISceneBank;
import de.mossgrabers.framework.daw.data.IScene;

import com.bitwig.extension.controller.api.SceneBank;


/**
 * Encapsulates the data of a scene bank.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public class SceneBankImpl extends AbstractBankImpl<SceneBank, IScene> implements ISceneBank
{
    /**
     * Constructor.
     *
     * @param sceneBank The scene bank
     * @param numScenes The number of scenes in the page of the bank
     */
    public SceneBankImpl (final SceneBank sceneBank, final int numScenes)
    {
        super (sceneBank, numScenes);
        this.initItems ();
    }


    /** {@inheritDoc} */
    @Override
    public void stop ()
    {
        this.bank.stop ();
    }


    /** {@inheritDoc} */
    @Override
    protected void initItems ()
    {
        for (int i = 0; i < this.pageSize; i++)
            this.items.add (new SceneImpl (this.bank.getScene (i), i));
    }
}