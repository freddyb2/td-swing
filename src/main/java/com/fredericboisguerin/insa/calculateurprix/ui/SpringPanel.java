package com.fredericboisguerin.insa.calculateurprix.ui;

import javax.swing.*;

public class SpringPanel extends JPanel {

    public SpringPanel() {
        super(new SpringLayout());
    }

    public void makeCompactGrid(int numberOfRows, int numberOfColumns, int margin, int padding) {
        //Align all cells in each column and make them the same width.
        Spring x = Spring.constant(margin);
        for (int columnId = 0; columnId < numberOfColumns; columnId++) {
            Spring width = Spring.constant(0);
            for (int r = 0; r < numberOfRows; r++) {
                width = Spring.max(width, getConstraintsForCell(r, columnId, numberOfColumns).
                        getWidth());
            }
            for (int r = 0; r < numberOfRows; r++) {
                SpringLayout.Constraints constraints = getConstraintsForCell(r, columnId, numberOfColumns);
                constraints.setX(x);
                constraints.setWidth(width);
            }
            x = Spring.sum(x, Spring.sum(width, Spring.constant(padding)));
        }

        //Align all cells in each row and make them the same height.
        Spring y = Spring.constant(margin);
        for (int r = 0; r < numberOfRows; r++) {
            Spring height = Spring.constant(0);
            for (int c = 0; c < numberOfColumns; c++) {
                height = Spring.max(height, getConstraintsForCell(r, c, numberOfColumns).
                        getHeight());
            }
            for (int c = 0; c < numberOfColumns; c++) {
                SpringLayout.Constraints constraints = getConstraintsForCell(r, c, numberOfColumns);
                constraints.setY(y);
                constraints.setHeight(height);
            }
            y = Spring.sum(y, Spring.sum(height, Spring.constant(padding)));
        }

        // Set this panel's size
        SpringLayout.Constraints pCons = getLayout().getConstraints(this);
        pCons.setConstraint(SpringLayout.SOUTH, y);
        pCons.setConstraint(SpringLayout.EAST, x);
    }

    @Override
    public SpringLayout getLayout() {
        return (SpringLayout) super.getLayout();
    }

    private SpringLayout.Constraints getConstraintsForCell(int row, int col, int cols) {
        return getLayout().getConstraints(getComponent(row * cols + col));
    }
}
