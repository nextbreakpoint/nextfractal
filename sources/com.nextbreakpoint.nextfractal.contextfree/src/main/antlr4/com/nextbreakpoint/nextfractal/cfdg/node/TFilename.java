/* This file was generated by SableCC (http://www.sablecc.org/). */

package com.nextbreakpoint.nextfractal.cfdg.node;

import com.nextbreakpoint.nextfractal.cfdg.analysis.Analysis;

@SuppressWarnings("nls")
public final class TFilename extends Token
{
    public TFilename(String text)
    {
        setText(text);
    }

    public TFilename(String text, int line, int pos)
    {
        setText(text);
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TFilename(getText(), getLine(), getPos());
    }

    @Override
	public void apply(Switch sw)
    {
        ((Analysis) sw).caseTFilename(this);
    }
}