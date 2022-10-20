/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package com.archimatetool.editor.diagram.figures.elements;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Path;

import com.archimatetool.editor.diagram.figures.AbstractTextControlContainerFigure;
import com.archimatetool.editor.diagram.figures.IFigureDelegate;
import com.archimatetool.editor.diagram.figures.RectangleFigureDelegate;




/**
 * Communication Network Figure
 * 
 * @author Phillip Beauvoir
 */
public class CommunicationNetworkFigure extends AbstractTextControlContainerFigure implements IArchimateFigure {
    
    private IFigureDelegate rectangleDelegate;
    
    public CommunicationNetworkFigure() {
        super(TEXT_FLOW_CONTROL);
        rectangleDelegate = new RectangleFigureDelegate(this);
    }
    
    @Override
    protected void drawFigure(Graphics graphics) {
        if(getFigureDelegate() != null) {
            getFigureDelegate().drawFigure(graphics);
            drawIcon(graphics);
            return;
        }
        
        graphics.pushState();
        
        Rectangle rect = getBounds().getCopy();
        Rectangle imageBounds = rect.getCopy();
        
        setFigurePositionFromTextPosition(rect);
        
        if(!isEnabled()) {
            setDisabledState(graphics);
        }
        
        graphics.setAlpha(getLineAlpha());
        graphics.setForegroundColor(getLineColor());
        graphics.setBackgroundColor(getLineColor());
        
        int lineWidth = (int)Math.max(4, (Math.sqrt(rect.width * rect.height) / 24d));
        //graphics.setLineWidth(lineWidth);
        
        int figureMaxSize = Math.min(rect.width, rect.height);
        float diameter = Math.min(40, figureMaxSize / 6f);
        
        int heightOffset = (int)(figureMaxSize / 3.5);
        int widthOffset = (int)(figureMaxSize / 3);
        
        Point center = rect.getCenter();
        int x = center.x - widthOffset;
        int y = center.y - heightOffset;
        int w = widthOffset * 2;
        int h = heightOffset * 2;
        
        //graphics.drawRectangle(x, y, w, h);
        
        Path path = new Path(null);
        
        path.addArc(x, y + h - diameter,
                diameter, diameter, 0, 360);
    
        path.addArc(x + (w /2), y + h - diameter,
                diameter, diameter, 0, 360);
    
        path.addArc(x + (w /4), y,
                diameter, diameter, 0, 360);
    
        path.addArc(x + w - diameter, y,
                diameter, diameter, 0, 360);
    
        graphics.fillPath(path);
        path.dispose();
        
        // Image Icon
        drawIconImage(graphics, imageBounds, 0, 0, 0, 0);
        
        graphics.popState();
    }
    
    private void drawIcon(Graphics graphics) {
        if(!isIconVisible()) {
            return;
        }
        
        graphics.pushState();
        
        graphics.setLineWidthFloat(1);
        graphics.setForegroundColor(getIconColor());
        
        Point pt = getIconOrigin();
        
        Path path = new Path(null);
        
        path.addArc(pt.x, pt.y, 5, 5, 0, 360);
        path.addArc(pt.x + 2, pt.y - 8, 5, 5, 0, 360);
        path.addArc(pt.x + 10, pt.y - 8, 5, 5, 0, 360);
        path.addArc(pt.x + 8, pt.y, 5, 5, 0, 360);
        
        path.moveTo(pt.x + 3, pt.y);
        path.lineTo(pt.x + 4, pt.y - 3);
        
        path.moveTo(pt.x + 11, pt.y);
        path.lineTo(pt.x + 12, pt.y - 3);
        
        path.moveTo(pt.x + 5, pt.y + 2.5f);
        path.lineTo(pt.x + 8, pt.y + 2.5f);
        
        path.moveTo(pt.x + 7, pt.y - 5.5f);
        path.lineTo(pt.x + 10, pt.y - 5.5f);
        
        graphics.drawPath(path);
        path.dispose();
        
        graphics.popState();
    }
    
    /**
     * @return The icon start position
     */
    private Point getIconOrigin() {
        Rectangle bounds = getBounds();
        return new Point(bounds.x + bounds.width - 20, bounds.y + 14);
    }

    @Override
    public int getIconOffset() {
        return getDiagramModelArchimateObject().getType() == 0 ? 22 : 0;
    }

    @Override
    public IFigureDelegate getFigureDelegate() {
        return getDiagramModelArchimateObject().getType() == 0 ? rectangleDelegate : null;
    }
}
